package com.example.achilis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.example.achilis.RegisterActivity.closeBtnDisabled;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;

    private static final String TAG = "PhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    private boolean mVerificationInProgress = false;
    private String mVerificationId,mPhoneNumber,myCCP;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private TextView tvPhoneNumber;
    private EditText mPhoneNumberField;

    private TextView mResend;
    private ImageView refreshIcon;

    Pinview otp;
    CountryCodePicker ccp;
    CountDownTimer countdownTimer;



    private Button nextButton;

    private ProgressDialog dialogVerify,dialogSignin;

    private boolean onPhoneLayout=true;
    private boolean onOtpLayout=false;



    private ConstraintLayout constraintLayoutPhone, constraintLayoutOtp;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        mAuth = FirebaseAuth.getInstance();


        ccp = findViewById(R.id.countryCodePicker);
        mPhoneNumberField = findViewById(R.id.phone_login_phne_number);
        nextButton = findViewById(R.id.next_button);
        ccp.registerCarrierNumberEditText(mPhoneNumberField);

        tvPhoneNumber = findViewById(R.id.varification_phone_field);
        otp = findViewById(R.id.pinview);
        mResend = findViewById(R.id.tv_resend_code);
        refreshIcon = findViewById(R.id.refresh_icon);

        constraintLayoutPhone= findViewById(R.id.const_layout_phone);
        constraintLayoutOtp= findViewById(R.id.const_layout_otp);


        nextButton.setOnClickListener(this);
        mResend.setOnClickListener(this);
        refreshIcon.setOnClickListener(this);

        dialogVerify = new ProgressDialog(this,R.style.dialogStyle);
        dialogVerify.setMessage("Verifying number...");
        dialogVerify.setCancelable(false);

        dialogSignin = new ProgressDialog(this,R.style.dialogStyle);
        dialogSignin.setMessage("Varifying...");
        dialogSignin.setCancelable(false);

        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if(isValidNumber){
                    nextButton.setEnabled(true);
                    nextButton.setTextColor(Color.rgb(255,255,255));

                }else{
                    nextButton.setEnabled(false);
                    nextButton.setTextColor(Color.argb(50,255,255,255));
                }
            }
        });

        otp.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {

                dialogSignin.show();
                String code = otp.getValue();
                verifyPhoneNumberWithCode(mVerificationId, code);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;

                Toast.makeText(OtpActivity.this,"Login success",Toast.LENGTH_SHORT).show();
                updateUI(STATE_VERIFY_SUCCESS, credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                String error = e.getMessage();
                dialogVerify.dismiss();

                Toast.makeText(OtpActivity.this,error,Toast.LENGTH_SHORT).show();

                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mPhoneNumberField.setError("Invalid phone number.");
                    onPhoneLayout = true;
                    onOtpLayout = false;
                    constraintLayoutOtp.setVisibility(View.GONE);
                    constraintLayoutPhone.setVisibility(View.VISIBLE);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    onPhoneLayout = true;
                    onOtpLayout = false;
                    constraintLayoutOtp.setVisibility(View.GONE);
                    constraintLayoutPhone.setVisibility(View.VISIBLE);
                }

                updateUI(STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(OtpActivity.this,"Verification code sent to your phone",Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId;
                mResendToken = token;
                updateUI(STATE_CODE_SENT);
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        if (!mVerificationInProgress){
            mPhoneNumber = phoneNumber;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks

            mVerificationInProgress = true;
        }else {

        }
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");

                            if(closeBtnDisabled){
                                closeBtnDisabled=false;
                            }else {
                                Intent homeIntent = new Intent(OtpActivity.this,HomeActivity.class);
                                startActivity(homeIntent);
                            }
                            finish();
                            RegisterActivity.getInstance().finish();
                            FirebaseUser user = task.getResult().getUser();
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //String error = task.getException().getMessage();
                            //Toast.makeText(OtpActivity.this, error, Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OtpActivity.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                            }
                            updateUI(STATE_SIGNIN_FAILED);
                        }
                        dialogSignin.dismiss();
                    }
                });
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:

                break;
            case STATE_CODE_SENT:

                dialogVerify.dismiss();
                onOtpLayout=true;
                onPhoneLayout=false;
                constraintLayoutOtp.setVisibility(View.VISIBLE);
                constraintLayoutPhone.setVisibility(View.GONE);
                startCountdown();

                break;
            case STATE_VERIFY_FAILED:

                if (countdownTimer != null) {
                    countdownTimer.cancel();
                }

                break;
            case STATE_VERIFY_SUCCESS:

                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        otp.setValue(cred.getSmsCode());
                    } else {

                    }
                }

                break;
            case STATE_SIGNIN_FAILED:

                break;
            case STATE_SIGNIN_SUCCESS:

                break;
        }

        if (user == null) {
            // Signed out
            //mPhoneNumberViews.setVisibility(View.VISIBLE);
            // mVerifyViews.setVisibility(View.VISIBLE);

            // mStatusText.setText(R.string.signed_out);

        } else {
            // Signed in
            //mPhoneNumberViews.setVisibility(View.GONE);

        }
    }

    private void startCountdown() {
        tvPhoneNumber.setText(mPhoneNumber);
        setResendButtonEnabled(false);
        countdownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override public void onTick(long millisUntilFinished) {
                setResendButtonTimerCount(millisUntilFinished / 1000);
            }

            @Override public void onFinish() {
                setResendButtonEnabled(true);
            }
        }.start();
    }


    public void setResendButtonEnabled(boolean isEnabled) {
        if (isEnabled) {
            refreshIcon.setVisibility(View.VISIBLE);
            mResend.setEnabled(true);
            mResend.setText("Resend Code");
            mResend.setTextColor(Color.rgb(255,255,255));
        } else {
            refreshIcon.setVisibility(View.GONE);
            mResend.setEnabled(false);
            mResend.setTextColor(Color.argb(99,255,255,255));
        }
    }

    private void setResendButtonTimerCount(long secondsRemaining) {
        mResend.setText(
                String.format(Locale.ENGLISH, getString(R.string.resend_code_timer), secondsRemaining));

    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.next_button:
                 {
                     dialogVerify.show();
                    String phoneWithPrefix = ccp.getFullNumberWithPlus();
                     startPhoneNumberVerification(phoneWithPrefix);
                    // Toast.makeText(OtpActivity.this, phoneWithPrefix, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_resend_code:
                resendVerificationCode(mPhoneNumber, mResendToken);
                mResend.setEnabled(false);
                mResend.setTextColor(Color.argb(99,255,255,255));
                refreshIcon.setVisibility(View.GONE);
                break;
            case R.id.refresh_icon:
                resendVerificationCode(mPhoneNumber, mResendToken);
                mResend.setEnabled(false);
                mResend.setTextColor(Color.argb(99,255,255,255));
                refreshIcon.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(onOtpLayout){
                mVerificationInProgress=false;

                if(countdownTimer!=null){
                    countdownTimer.cancel();
                }

                onPhoneLayout = true;
                onOtpLayout = false;
                constraintLayoutPhone.setVisibility(View.VISIBLE);
                constraintLayoutOtp.setVisibility(View.GONE);
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

}
