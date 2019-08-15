package com.example.achilis;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {


    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static final int MANAGE_ADDRESS=1;
    private Button viewAllBtn;
    private Button signOutBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        viewAllBtn = view.findViewById(R.id.view_all_addresses_btn_profile);
        signOutBtn = view.findViewById(R.id.sign_out_btn_profile);
        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressIntent = new Intent(getContext(),MyAddressActivity.class);
                myAddressIntent.putExtra("mode",MANAGE_ADDRESS);
                startActivity(myAddressIntent);
            }
        });
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser != null){
                    FirebaseAuth.getInstance().signOut();
                    Intent loginIntent = new Intent(getActivity(),RegisterActivity.class);
                    startActivity(loginIntent);
                   getActivity().finish();
                }
            }
        });
        return view;
    }

}
