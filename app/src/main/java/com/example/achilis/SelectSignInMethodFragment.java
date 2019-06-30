package com.example.achilis;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import static com.example.achilis.RegisterActivity.onSignInFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectSignInMethodFragment extends Fragment {


    public SelectSignInMethodFragment() {
        // Required empty public constructor
    }

    private Button signInEmailButton,signInPhoneButton;

    private FrameLayout parentFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_sign_in_method, container, false);

        signInEmailButton = view.findViewById(R.id.email_button);
        signInPhoneButton = view.findViewById(R.id.phone_button);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInFragment = true;
                setFragment(new SignInFragment());
            }
        });

        signInPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent homeIntent = new Intent(getActivity(),OtpActivity.class);
               startActivity(homeIntent);
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);

        fragmentTransaction.commit();
    }
}
