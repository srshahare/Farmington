package com.clg.farmington.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.clg.farmington.AddItemActivity;
import com.clg.farmington.R;
import com.clg.farmington.adapter.AllPredictionAdapter;
import com.clg.farmington.adapter.MyProductsAdapter;
import com.clg.farmington.adapter.RequestAdapter;
import com.clg.farmington.utils.Prediction;
import com.clg.farmington.utils.Product;
import com.clg.farmington.utils.Request;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SellerShopFrag extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyProductsAdapter adapter;
    private FirebaseFirestore db;
    private Button addBtn, myProd, prodReq;
    private RequestAdapter reqAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_shop, container, false);

        db = FirebaseFirestore.getInstance();

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView = view.findViewById(R.id.my_products_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        addBtn = view.findViewById(R.id.add_product_button);
        myProd = view.findViewById(R.id.my_product_btn);
        prodReq = view.findViewById(R.id.product_request_btn);

        //my products list
        Query query = db.collection("Products");
        final FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        adapter = new MyProductsAdapter(getContext(), options);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClick(new MyProductsAdapter.OnItemClick() {
            @Override
            public void getPosition(String userId) {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                intent.putExtra("type", userId);
                startActivity(intent);
            }
        });

        //requests list
        Query reqQuery = db.collection("Requests").orderBy("time", Query.Direction.DESCENDING);
        final FirestoreRecyclerOptions<Request> reqOptions = new FirestoreRecyclerOptions.Builder<Request>()
                .setQuery(reqQuery, Request.class)
                .build();

        reqAdapter = new RequestAdapter(getContext(), reqOptions);

        reqAdapter.setOnItemClick(new RequestAdapter.OnItemClick() {
            @Override
            public void getPosition(String userId, Button accept, Button reject) {
                accept.setText("Accepted");
                reject.setVisibility(View.GONE);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                intent.putExtra("type", "ADD");
                startActivity(intent);
            }
        });

        //setting my product and product requests
        prodReq.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                prodReq.setBackground(getResources().getDrawable(R.drawable.card6));
                prodReq.setTextColor(Color.WHITE);
                myProd.setBackground(getResources().getDrawable(R.drawable.card));
                myProd.setTextColor(Color.BLACK);

                //setting recyclerview
                recyclerView.setAdapter(reqAdapter);
            }
        });

        myProd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                myProd.setBackground(getResources().getDrawable(R.drawable.card6));
                myProd.setTextColor(Color.WHITE);
                prodReq.setBackground(getResources().getDrawable(R.drawable.card));
                prodReq.setTextColor(Color.BLACK);

                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        reqAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        reqAdapter.stopListening();
    }
}