package com.example.achilis;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.achilis.RegisterActivity.closeBtnDisabled;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private EditText firstName, lastName, email, password;
    private Button signUpButton;
    private ProgressBar progressBar;
    private FrameLayout parentFrameLayout;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        firstName = view.findViewById(R.id.sign_up_first_name);
        lastName = view.findViewById(R.id.sign_up_last_name);
        email = view.findViewById(R.id.sign_up_email);
        password = view.findViewById(R.id.sign_up_password);
        signUpButton = view.findViewById(R.id.sign_up_button);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        firstName.addTextChangedListener(new TextWatcher() {
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
        lastName.addTextChangedListener(new TextWatcher() {
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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();

            }
        });
    }

    private void sendData() {
        final ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.dialogStyle);
        dialog.setMessage("Signing up...");
        dialog.setCancelable(false);

        signUpButton.setEnabled(false);
        signUpButton.setTextColor(Color.argb(50, 255, 255, 255));

        dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Map<String,Object> userdata = new HashMap<>();
                            userdata.put("firstname", firstName.getText().toString());

                            firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                    .set(userdata)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Map<String,Object> list_size = new HashMap<>();
                                                list_size.put("list_size", (long) 0);


                                                firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                                        .set(list_size).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                            if (closeBtnDisabled) {
                                                                closeBtnDisabled = false;
                                                            } else {
                                                                Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
                                                                startActivity(homeIntent);
                                                            }
                                                            getActivity().finish();
                                                        } else {
                                                            dialog.dismiss();
                                                            signUpButton.setEnabled(true);
                                                            signUpButton.setTextColor(Color.rgb(255, 255, 255));
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });


                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                        } else {
                            dialog.dismiss();
                            signUpButton.setEnabled(true);
                            signUpButton.setTextColor(Color.rgb(255, 255, 255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    private void checkInputs() {
        if (!TextUtils.isEmpty(firstName.getText())) {
            if (!TextUtils.isEmpty(lastName.getText())) {
                if (!TextUtils.isEmpty(email.getText())) {
                    if (!TextUtils.isEmpty(password.getText()) && password.length() >= 6) {
                        signUpButton.setEnabled(true);
                        signUpButton.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        signUpButton.setEnabled(false);
                        signUpButton.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    signUpButton.setEnabled(false);
                    signUpButton.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                signUpButton.setEnabled(false);
                signUpButton.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signUpButton.setEnabled(false);
            signUpButton.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }
}
