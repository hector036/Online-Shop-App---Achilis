package com.example.achilis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.achilis.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {

    private RecyclerView myAddressesRecyclerView;
    private Button deliverHereBtn;
    private static AddressesAdapter addressesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        myAddressesRecyclerView = findViewById(R.id.addresses_recyclerview_myaddress);
        deliverHereBtn = findViewById(R.id.deliver_here_button);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",true));
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",false));
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",false));
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",false));
        addressesModelList.add(new AddressesModel("Nayeem Khan","Suhrawardy Hall, Buet Dhaka: 1200","1200",false));

        int mode = getIntent().getIntExtra("mode",-1);

        if(mode == SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else{
            deliverHereBtn.setVisibility(View.GONE);
        }
         addressesAdapter = new AddressesAdapter(addressesModelList,mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect,int select){

        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
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
