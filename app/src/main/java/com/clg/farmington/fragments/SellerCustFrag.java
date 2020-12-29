package com.clg.farmington.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clg.farmington.R;
import com.clg.farmington.adapter.AllPredictionAdapter;
import com.clg.farmington.adapter.ForumAdapter;
import com.clg.farmington.utils.Forum;
import com.clg.farmington.utils.Prediction;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SellerCustFrag extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ForumAdapter adapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_cust, container, false);

        db = FirebaseFirestore.getInstance();

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView = view.findViewById(R.id.forum_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        Query query = db.collection("Forums");
        final FirestoreRecyclerOptions<Forum> options = new FirestoreRecyclerOptions.Builder<Forum>()
                .setQuery(query, Forum.class)
                .build();

        adapter = new ForumAdapter(getContext(), options);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClick(new ForumAdapter.OnItemClick() {
            @Override
            public void getPosition(String userId) {
                Toast.makeText(getActivity(), "coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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