package com.clg.farmington;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.clg.farmington.adapter.AllProductsAdapter;
import com.clg.farmington.adapter.MyProductsAdapter;
import com.clg.farmington.utils.Product;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clg.farmington.databinding.ActivityBuyerBinding;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class BuyerActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private GridLayoutManager linearLayoutManager;
    private AllProductsAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        toolbar = findViewById(R.id.buyer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Farmington Store");

        db = FirebaseFirestore.getInstance();

        linearLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView = findViewById(R.id.market_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        //my products list
        Query query = db.collection("Products");
        final FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        adapter = new AllProductsAdapter(this, options);
        recyclerView.setAdapter(adapter);
        ProgressDialog progressDialog = new ProgressDialog(this);

        adapter.setOnItemClick(new AllProductsAdapter.OnItemClick() {
            @Override
            public void getPosition(String userId) {
                View dialogView = getLayoutInflater().inflate(R.layout.bottom_dialog, null);
                final BottomSheetDialog dialog = new BottomSheetDialog(BuyerActivity.this);
                dialog.setContentView(dialogView);
                dialog.show();

                EditText name, quant, wait;
                name = dialog.findViewById(R.id.all_user_name);
                quant = dialog.findViewById(R.id.all_quantity);
                wait = dialog.findViewById(R.id.all_waiting);
                Button purchase = dialog.findViewById(R.id.all_purchase);

                purchase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("Products").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.setMessage("Processing your request");
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    String title = task.getResult().getString("name");
                                    String price = task.getResult().getString("price");
                                    String uid = db.collection("Requests").document().getId();
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("name", name.getText().toString());
                                    hashMap.put("product", userId);
                                    hashMap.put("quantity", quant.getText().toString());
                                    hashMap.put("uid", uid);
                                    hashMap.put("waiting_time", wait.getText().toString());
                                    hashMap.put("time", Timestamp.now());
                                    db.collection("Requests").document(uid).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(BuyerActivity.this, "Congratulations your request has been submitted you can visit the seller after "+ wait.getText().toString() + " hours\"", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}