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
import com.clg.farmington.utils.Forum;
import com.clg.farmington.utils.Request;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;


public class ForumAdapter extends FirestoreRecyclerAdapter<Forum, ForumAdapter.ViewHolder> {

    private Context mContext;
    OnItemClick onItemClick;

    public ForumAdapter(Context mContext, FirestoreRecyclerOptions<Forum> options) {
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
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int i, @NonNull final Forum model) {

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        Glide.with(mContext).load(model.getImage()).into(holder.image);
        holder.day.setText(model.getTime() + " days ago");

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.forum_item, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, desc, day;
        private ImageView share, like, more, image;
        private ConstraintLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.forum_title);
            desc = itemView.findViewById(R.id.forum_desc);
            day = itemView.findViewById(R.id.forum_day);
            image = itemView.findViewById(R.id.forum_image);
            share = itemView.findViewById(R.id.forum_share);
            like = itemView.findViewById(R.id.forum_like);
            more = itemView.findViewById(R.id.forum_more);
            root = itemView.findViewById(R.id.forum_root);
        }
    }
}
