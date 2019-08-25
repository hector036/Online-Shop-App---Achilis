package com.example.achilis;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.achilis.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {

    private int previousSelectedAddress;
    private RecyclerView myAddressesRecyclerView;
    private Button deliverHereBtn;
    private TextView addressesSaved;
    private static AddressesAdapter addressesAdapter;
    private LinearLayout addNewAddressBtn;

    private Dialog loadingDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        //////loading dialog////
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.rounded_back_ground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////loading dialog////

        previousSelectedAddress = DBqueries.selectedAddress;

        myAddressesRecyclerView = findViewById(R.id.addresses_recyclerview_myaddress);
        deliverHereBtn = findViewById(R.id.deliver_here_button);
        addNewAddressBtn = findViewById(R.id.add_new_address_btn_myaddress);
        addressesSaved = findViewById(R.id.address_saved_myaddress);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        int mode = getIntent().getIntExtra("mode", -1);

        if (mode == SELECT_ADDRESS) {
            deliverHereBtn.setVisibility(View.VISIBLE);
        } else {
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter = new AddressesAdapter(DBqueries.addressesModelList, mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();

        deliverHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DBqueries.selectedAddress != previousSelectedAddress) {

                    final int prreviousAddressIndex = previousSelectedAddress;
                    loadingDialog.show();

                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selectrd_" + String.valueOf(previousSelectedAddress + 1), false);
                    updateSelection.put("selectrd_" + String.valueOf(DBqueries.selectedAddress + 1), true);

                    previousSelectedAddress = DBqueries.selectedAddress;

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES")
                            .update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                finish();
                            } else {
                                previousSelectedAddress = prreviousAddressIndex;
                                String error = task.getException().getMessage();
                                Toast.makeText(MyAddressActivity.this, error, Toast.LENGTH_SHORT).show();

                            }
                            loadingDialog.dismiss();

                        }
                    });
                }else {
                    finish();
                }
            }
        });


        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAddressIntent = new Intent(MyAddressActivity.this, AddressActivity.class);
                addAddressIntent.putExtra("INTENT","null");
                startActivity(addAddressIntent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        addressesSaved.setText(String.valueOf(DBqueries.addressesModelList.size()+" saved addresses"));


    }

    public static void refreshItem(int deselect, int select) {

        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            if(DBqueries.selectedAddress != previousSelectedAddress){
                DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                DBqueries.addressesModelList.get(previousSelectedAddress).setSelected(true);
                DBqueries.selectedAddress = previousSelectedAddress;
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(DBqueries.selectedAddress != previousSelectedAddress){
            DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
            DBqueries.addressesModelList.get(previousSelectedAddress).setSelected(true);
            DBqueries.selectedAddress = previousSelectedAddress;
        }

        super.onBackPressed();
    }
}
