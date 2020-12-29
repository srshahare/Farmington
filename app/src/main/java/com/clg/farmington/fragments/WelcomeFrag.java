package com.clg.farmington.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.clg.farmington.BuyerActivity;
import com.clg.farmington.R;
import com.clg.farmington.SellerActivity;


public class WelcomeFrag extends Fragment {

    Button buyer, seller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        buyer = view.findViewById(R.id.buyer_button);
        seller = view.findViewById(R.id.seller_button);

        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BuyerActivity.class));
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerActivity.class));
            }
        });

        return view;
    }
}