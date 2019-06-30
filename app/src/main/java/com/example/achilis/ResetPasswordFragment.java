package com.example.achilis;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.achilis.RegisterActivity.onResetPasswordFragment;
import static com.example.achilis.RegisterActivity.onSignInFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private TextView email;
    private Button resetButton,buttonOkalertDialog;
    private TextView goBack;
    private TextView errorMsg;

    private FrameLayout parentFrameLayout;

    private FirebaseAuth firebaseAuth;
    private  ViewGroup viewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        email  = view.findViewById(R.id.forgot_password_email);
        resetButton = view.findViewById(R.id.forgot_password_button);
        goBack = view.findViewById(R.id.tv_go_back);

        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        firebaseAuth = FirebaseAuth.getInstance();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInFragment = true;
                onResetPasswordFragment = false;
                setFragment(new SignInFragment());
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = new ProgressDialog(getActivity(),R.style.dialogStyle);
                dialog.setMessage("Sending email...");
                dialog.setCancelable(false);

                resetButton.setEnabled(false);
                resetButton.setTextColor(Color.argb(50,255,255,255));
                dialog.show();
                firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            showCustomDialogSuccess();
                        }else
                        {
                            String error = task.getException().getMessage();
                            showCustomDialogError(error);

                        }
                        dialog.dismiss();
                        resetButton.setEnabled(true);
                        resetButton.setTextColor(Color.rgb(255,255,255));
                    }
                });

            }
        });

    }

    private void showCustomDialogError(String msg) {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.my_error_alertdialog, viewGroup, false);

        errorMsg = dialogView.findViewById(R.id.tv_error_msg);
        errorMsg.setText("Failed to send email. "+msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        buttonOkalertDialog = dialogView.findViewById(R.id.buttonOk);
        buttonOkalertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    private void showCustomDialogSuccess() {

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.my_success_alertdialog, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        buttonOkalertDialog = dialogView.findViewById(R.id.buttonOk);
        buttonOkalertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                onSignInFragment = true;
                onResetPasswordFragment = false;
                setFragment(new SignInFragment());
            }
        });
    }

    private void checkInput() {

        if(TextUtils.isEmpty(email.getText())){
            resetButton.setEnabled(false);
            resetButton.setTextColor(Color.argb(50,255,255,255));

        }else{
            resetButton.setEnabled(true);
            resetButton.setTextColor(Color.rgb(255,255,255));
        }
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);

       // fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        fragmentTransaction.commit();
    }
}
