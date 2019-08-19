package com.example.achilis;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDesFragment extends Fragment {


    public ProductDesFragment() {
        // Required empty public constructor
    }

    private TextView descriptionBady;
    public String body;
    ////back
///

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_des, container, false);

        descriptionBady = view.findViewById(R.id.tv_product_details_des);

        descriptionBady.setText(body);
/*
        if (tabPosition == 0) {
            descriptionBady.setText(productDescription);
        } else {
            descriptionBady.setText(productOtherDetails);
        }*/
        return view;
    }

}
