package com.example.achilis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {

    private Button saveBtn;

    private EditText city;
    private EditText locality;
    private EditText flatNo;
    private EditText pincode;
    private EditText landmark;
    private EditText mobileNo;
    private Spinner stateSpinner;

    private String[] stateList;
    private String selectedState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Address");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        saveBtn = findViewById(R.id.save_btn_address);
        city = findViewById(R.id.city_address);
        locality = findViewById(R.id.locality_address);
        flatNo = findViewById(R.id.flat_on_address);
        pincode = findViewById(R.id.pincode_address);
        landmark = findViewById(R.id.landmark_address);
        mobileNo = findViewById(R.id.mobile_num_address);
        stateSpinner = findViewById(R.id.state_spnner);
        stateList = getResources().getStringArray(R.array.bd_divition);
        ArrayAdapter spinerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(spinerAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedState = stateList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(city.getText())) {
                    if (!TextUtils.isEmpty(locality.getText())) {
                        if (!TextUtils.isEmpty(flatNo.getText())) {
                            if (!TextUtils.isEmpty(pincode.getText())) {
                                if (!TextUtils.isEmpty(mobileNo.getText())) {

                                    /////map/////

                                    final String fullAddress = city.getText().toString() + locality.getText().toString() + flatNo.getText().toString() + landmark.getText().toString() + selectedState;

                                    Map<String, Object> addAddress = new HashMap<>();
                                    addAddress.put("list_size", String.valueOf((long) DBqueries.addressesModelList.size() + 1));
                                    addAddress.put("fullname_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), "Nayeem Khan" + " ( " + mobileNo.getText().toString() + " ) ");
                                    addAddress.put("address_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), fullAddress);
                                    addAddress.put("pincode_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), pincode.getText().toString());
                                    addAddress.put("selected_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), true);

                                    if (DBqueries.addressesModelList.size() > 0) {
                                        addAddress.put("selected_" + (DBqueries.selectedAddress + 1), false);
                                    }

                                    ////map////


                                    FirebaseFirestore.getInstance().collection("USERS")
                                            .document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                                            .document("MY_ADDRESSES")
                                            .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                if (DBqueries.addressesModelList.size() > 0) {
                                                    DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                                                }
                                                DBqueries.addressesModelList.add(new AddressesModel("Nayeem Khan" + " ( " + mobileNo.getText().toString(), fullAddress, pincode.getText().toString(), true));

                                                if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                                    Intent deliveryIntent = new Intent(AddressActivity.this, DeliveryActivity.class);
                                                    startActivity(deliveryIntent);
                                                } else if(getIntent().getStringExtra("INTENT").equals("null")){
                                                    MyAddressActivity.refreshItem(DBqueries.selectedAddress, DBqueries.addressesModelList.size() - 1);
                                                }
                                                DBqueries.selectedAddress = DBqueries.addressesModelList.size() - 1;

                                                finish();
                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(AddressActivity.this, error, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                } else {
                                    mobileNo.requestFocus();
                                }
                            } else {
                                pincode.requestFocus();
                            }
                        } else {
                            flatNo.requestFocus();
                        }
                    } else {
                        locality.requestFocus();
                    }
                } else {
                    city.requestFocus();
                }


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
