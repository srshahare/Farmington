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
import com.clg.farmington.utils.Prediction;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SellerAnaFrag extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AllPredictionAdapter adapter;
    private FirebaseFirestore db;
    public List<String> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_ana, container, false);

        db = FirebaseFirestore.getInstance();

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView = view.findViewById(R.id.prediction_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        Query query = db.collection("Predictions");
        final FirestoreRecyclerOptions<Prediction> options = new FirestoreRecyclerOptions.Builder<Prediction>()
                .setQuery(query, Prediction.class)
                .build();

        adapter = new AllPredictionAdapter(getContext(), options);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClick(new AllPredictionAdapter.OnItemClick() {
            @Override
            public void getPosition(String userId) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

//        data.add("carrot");
//        data.add("broccoli");
//        data.add("asparagus");
//        data.add("cauliflower");
//        data.add("corn");
//        data.add("cucumber");
//
//        for (int i=0; i<data.size(); i++) {
//            Random r = new Random();
//            int low = 10;
//            int high = 100;
//            int result = r.nextInt(high-low) + low;
//            String uid = db.collection("Predictions").document().getId();
//            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("name", data.get(i));
//            hashMap.put("uid", uid);
//            hashMap.put("quantity", String.valueOf(result));
//            db.collection("Predictions").document(uid).set(hashMap);
//        }

        return  view;
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