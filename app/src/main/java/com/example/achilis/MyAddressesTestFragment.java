package com.example.achilis;


import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.achilis.DeliveryActivity.SELECT_ADDRESS;
import static com.example.achilis.HomeActivity.MANAGE_ADDRESS_FRAG;
import static com.example.achilis.HomeActivity.addressesAdapterFrag;
import static com.example.achilis.MyAccountFragment.MANAGE_ADDRESS;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAddressesTestFragment extends Fragment {


    public MyAddressesTestFragment() {
        // Required empty public constructor
    }

    private RecyclerView myAddressesRecyclerView;
    private Button deliverHereBtn;
    private AppBarLayout appBarLayout;

    private int previousSelectedAddress;
    private TextView addressesSaved;
    private static AddressesAdapter addressesAdapter;
    private LinearLayout addNewAddressBtn;

    private Dialog loadingDialog;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_my_address, container, false);

        myAddressesRecyclerView = view.findViewById(R.id.addresses_recyclerview_myaddress);
        deliverHereBtn = view.findViewById(R.id.deliver_here_button);
        appBarLayout = view.findViewById(R.id.app_bar_my_addresses);

        previousSelectedAddress = DBqueries.selectedAddress;

        addNewAddressBtn = view.findViewById(R.id.add_new_address_btn_myaddress);
        addressesSaved = view.findViewById(R.id.address_saved_myaddress);



        //////loading dialog////
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.rounded_back_ground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////loading dialog////

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);


        deliverHereBtn.setVisibility(View.GONE);
        appBarLayout.setVisibility(View.GONE);

         addressesAdapterFrag = new AddressesAdapter(DBqueries.addressesModelList, MANAGE_ADDRESS_FRAG);
        myAddressesRecyclerView.setAdapter(addressesAdapterFrag);

        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapterFrag.notifyDataSetChanged();



        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAddressIntent = new Intent(getContext(), AddressActivity.class);
                addAddressIntent.putExtra("INTENT","homeIntent");
                startActivity(addAddressIntent);
            }
        });

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addressesSaved.setText(String.valueOf(DBqueries.addressesModelList.size()+" saved addresses"));

    }
}
