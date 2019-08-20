package com.example.achilis;


import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.achilis.RegisterActivity.closeBtnDisabled;
import static com.example.achilis.RegisterActivity.onResetPasswordFragment;
import static com.example.achilis.RegisterActivity.onSignInFragment;
import static com.example.achilis.RegisterActivity.onSignUnFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private  TextView forgotPassword;
    private FrameLayout parentFrameLayout;

    private TextView email,password;
    private Button signInButton;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        dontHaveAnAccount  = view.findViewById(R.id.tv_dont_have_an_account);
        forgotPassword = view.findViewById(R.id.sign_in_forgot_password);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        signInButton = view.findViewById(R.id.sign_in_button);

        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUnFragment = true;
                setFragment(new SignUpFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });



        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
    }

    private void checkEmailAndPassword() {
        final ProgressDialog dialog = new ProgressDialog(getActivity(),R.style.dialogStyle);
        dialog.setMessage("Signing in...");
        dialog.setCancelable(false);

        signInButton.setEnabled(false);
        signInButton.setTextColor(Color.argb(50,255,255,255));
        dialog.show();
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            if(closeBtnDisabled){
                                closeBtnDisabled=false;
                            }else {
                                Intent homeIntent = new Intent(getActivity(),HomeActivity.class);
                                startActivity(homeIntent);
                            }

                            getActivity().finish();
                        }else{
                            dialog.dismiss();
                            signInButton.setEnabled(true);
                            signInButton.setTextColor(Color.rgb(255,255,255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);

        fragmentTransaction.commit();
    }

    private void checkInputs() {
                if(!TextUtils.isEmpty(email.getText())){
                    if(!TextUtils.isEmpty(password.getText())){
                        signInButton.setEnabled(true);
                        signInButton.setTextColor(Color.rgb(255,255,255));
                    }else{
                        signInButton.setEnabled(false);
                        signInButton.setTextColor(Color.argb(50,255,255,255));
                    }
                }else{
                    signInButton.setEnabled(false);
                    signInButton.setTextColor(Color.argb(50,255,255,255));
                }
    }
}
