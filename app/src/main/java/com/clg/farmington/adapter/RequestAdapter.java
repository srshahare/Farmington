package com.clg.farmington.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.clg.farmington.R;
import com.clg.farmington.utils.Product;
import com.clg.farmington.utils.Request;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;


public class RequestAdapter extends FirestoreRecyclerAdapter<Request, RequestAdapter.ViewHolder> {

    private Context mContext;
    OnItemClick onItemClick;

    public RequestAdapter(Context mContext, FirestoreRecyclerOptions<Request> options) {
        super(options);
        this.mContext = mContext;
        this.notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {

        void getPosition(String userId, Button accept, Button reject);

    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int i, @NonNull final Request model) {

        holder.name.setText(model.getName());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products").document(model.getProduct()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String img = task.getResult().getString("image");
                    String name = task.getResult().getString("name");
                    String price = task.getResult().getString("price");
                    Glide.with(mContext).load(img).into(holder.imageView);
                    holder.product.setText(name);
                    holder.info.setText("Quantity: "+model.getQuantity()+"\n"+"Waiting Time: "+model.getWaiting_time()
                    +" Hrs\n"+"Price: "+price);
                }
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.getPosition(model.getUid(), holder.accept, holder.reject);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_request, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, product, info;
        private ImageView imageView;
        private Button accept, reject;
        private ConstraintLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.req_user_name);
            info = itemView.findViewById(R.id.req_details);
            product = itemView.findViewById(R.id.req_prod_name);
            imageView = itemView.findViewById(R.id.req_prod_image);
            accept = itemView.findViewById(R.id.req_accept);
            reject = itemView.findViewById(R.id.req_reject);
            root = itemView.findViewById(R.id.req_root);
        }
    }
}
