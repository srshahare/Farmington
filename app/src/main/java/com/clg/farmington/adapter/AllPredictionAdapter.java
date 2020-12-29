package com.clg.farmington.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.clg.farmington.R;
import com.clg.farmington.utils.Prediction;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class AllPredictionAdapter extends FirestoreRecyclerAdapter<Prediction, AllPredictionAdapter.ViewHolder> {

    private Context mContext;
    OnItemClick onItemClick;

    public AllPredictionAdapter(Context mContext, FirestoreRecyclerOptions<Prediction> options) {
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
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int i, @NonNull final Prediction model) {

        holder.name.setText(model.getName());
        holder.count.setText(model.getQuantity() + " Kg");
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_item, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, count;
        private ConstraintLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.single_name);
            count = itemView.findViewById(R.id.single_quant);
            root = itemView.findViewById(R.id.single_root);
        }
    }
}
