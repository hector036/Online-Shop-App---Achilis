package com.example.achilis;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    public static List<CartItemModel> cartItemModelList;
    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddAddressBtn;
    public static final int SELECT_ADDRESS=0;

    private TextView totalAmount;
    private TextView fullName;
    private TextView fullAddress;
    private TextView pincode;
    private Button continueBtn;
    private Dialog loadingDialog;
    private Dialog paymentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");


        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview_delivery);
        changeOrAddAddressBtn= findViewById(R.id.change_or_add_address_btn);
        totalAmount= findViewById(R.id.total_cart_amount);
        fullName= findViewById(R.id.full_name_order_details);
        fullAddress= findViewById(R.id.address_order_details);
        pincode= findViewById(R.id.pincode_order_details);
        continueBtn= findViewById(R.id.cart_continue_button);

        //////loading dialog////
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
      //  loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_back_ground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////loading dialog////


        //////payment dialog////
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.payment_method);
        loadingDialog.setCancelable(true);
        //  loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_back_ground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////payment dialog////



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList,totalAmount,false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        deliveryRecyclerView.setLayoutManager(layoutManager);

        changeOrAddAddressBtn.setVisibility(View.VISIBLE);

        changeOrAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressIntent = new Intent(DeliveryActivity.this,MyAddressActivity.class);
                myAddressIntent.putExtra("mode",SELECT_ADDRESS);
                startActivity(myAddressIntent);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        fullName.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFullName());
        fullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPinCode());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
