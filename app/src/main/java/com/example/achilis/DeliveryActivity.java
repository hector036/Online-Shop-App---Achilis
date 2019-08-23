package com.example.achilis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddAddressBtn;
    public static final int SELECT_ADDRESS=0;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        List<CartItemModel> cartItemModelList = new ArrayList<>();

      /*  cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,1,1));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,1,1));
        cartItemModelList.add(new CartItemModel(1,"Total (2 items)","Tk. 49999/-","free","Tk. 59999/-","Tk.999/-"));
*/
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
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
