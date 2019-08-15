package com.example.achilis;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_my_address, container, false);

        myAddressesRecyclerView = view.findViewById(R.id.addresses_recyclerview_myaddress);
        deliverHereBtn = view.findViewById(R.id.deliver_here_button);
        appBarLayout = view.findViewById(R.id.app_bar_my_addresses);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",true));
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",false));
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",false));
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",false));
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",false));

        deliverHereBtn.setVisibility(View.GONE);
        appBarLayout.setVisibility(View.GONE);

         addressesAdapterFrag = new AddressesAdapter(addressesModelList, MANAGE_ADDRESS_FRAG);
        myAddressesRecyclerView.setAdapter(addressesAdapterFrag);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapterFrag.notifyDataSetChanged();
        return  view;
    }



}
