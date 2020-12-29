package com.clg.farmington.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.clg.farmington.R;
import com.clg.farmington.utils.Prediction;
import com.clg.farmington.utils.Product;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class MyProductsAdapter extends FirestoreRecyclerAdapter<Product, MyProductsAdapter.ViewHolder> {

    private Context mContext;
    OnItemClick onItemClick;

    public MyProductsAdapter(Context mContext, FirestoreRecyclerOptions<Product> options) {
        super(options);
        this.mContext = mContext;
        this.notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {

        void getPosition(String userId);

    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int i, @NonNull final Product model) {

        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        Glide.with(mContext).load(model.getImage()).into(holder.imageView);
        holder.offer.setText(model.getOffer());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.getPosition(model.getUid());
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, price, offer;
        private ImageView imageView;
        private ConstraintLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            offer = itemView.findViewById(R.id.product_offer);
            imageView = itemView.findViewById(R.id.product_image);
            root = itemView.findViewById(R.id.product_root);
        }
    }
}
