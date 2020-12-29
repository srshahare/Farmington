package com.clg.farmington;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Random;

public class AddItemActivity extends AppCompatActivity {

    ImageView productImage, addImage;
    EditText name, price, offer;
    Button publish;
    public static final int SELECT_PHOTO = 1;
    String type = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Uri resultUri = null;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        type = getIntent().getStringExtra("type");

        dialog = new ProgressDialog(this);

        Toolbar toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImage = findViewById(R.id.product_imageview);
        addImage = findViewById(R.id.add_image);
        name = findViewById(R.id.edit_product_name);
        price = findViewById(R.id.edit_product_price);
        offer = findViewById(R.id.edit_product_offer);
        publish = findViewById(R.id.publish_product_btn);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        if (!type.equals("ADD")) {
            db.collection("Products").document(type).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        String n = task.getResult().getString("name");
                        String d = task.getResult().getString("offer");
                        String p = task.getResult().getString("price");
                        String url = task.getResult().getString("image");
                        addImage.setVisibility(View.GONE);
                        publish.setText("Save");
                        Glide.with(AddItemActivity.this)
                                .load(url).into(productImage);
                        name.setText(n);
                        offer.setText(d);
                        price.setText(p);
                    }
                }
            });
        }

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String d = price.getText().toString();
                String o = offer.getText().toString();
                if (TextUtils.isEmpty(n) || TextUtils.isEmpty(d) || TextUtils.isEmpty(o) || resultUri == null) {
                    Toast.makeText(AddItemActivity.this, "Type name, price, offer and select image", Toast.LENGTH_SHORT).show();
                }else {
                    proceedGiftSave(n, resultUri, d, o);
                }
            }
        });
    }

    private void proceedGiftSave(String name, Uri resultUri, String price, String offer) {
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("Products").child(random()+".jpg");
        dialog.setMessage("Uploading Product Image");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        UploadTask uploadTask = reference.putFile(resultUri);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dialog.setMessage("Publishing Product, please wait...");
                            String thumb_downloadUrl = uri.toString();
                            String docId = db.collection("Products").document().getId();
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("price", price);
                            hashMap.put("name", name);
                            hashMap.put("offer", offer);
                            hashMap.put("uid", docId);
                            hashMap.put("image", thumb_downloadUrl);

                            db.collection("Products")
                                    .document(docId).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        dialog.dismiss();
                                        onBackPressed();
                                    }
                                    else{
                                        dialog.hide();
                                        Toast.makeText(AddItemActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            resultUri = data.getData();
            Glide.with(this).load(resultUri).into(productImage);
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(15);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}