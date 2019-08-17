package com.example.achilis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerViewViewAll;
    private GridView gridViewViewAll;

    public static  List<WishListModel> wishListModelList;
    public static  List<HorizontalScrollProductModel> horizontalScrollProductModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        recyclerViewViewAll = findViewById(R.id.recycler_view_view_all);
        gridViewViewAll = findViewById(R.id.grid_view_view_all);

        int layout_code = getIntent().getIntExtra("layout_code", -1);


        if (layout_code == 0) {
            recyclerViewViewAll.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewViewAll.setLayoutManager(layoutManager);


          /*  List<WishListModel> wishListModelList = new ArrayList<>();

            wishListModelList.add(new WishListModel(R.mipmap.image_5, "Pixel 2XL (Black)", 1, "3", 124, "Tk. 49999/-", "TTk. 5999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.mipmap.image_5, "Pixel 2XL (Black)", 5, "3.6", 124, "Tk. 49999/-", "TTk. 5999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.mipmap.image_5, "Pixel 2XL (Black)", 1, "3", 124, "Tk. 49999/-", "TTk. 5999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.mipmap.image_5, "Pixel 2XL (Black)", 0, "3", 14, "Tk. 49999/-", "TTk. 5999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.mipmap.image_5, "Pixel 2XL (Black)", 1, "3", 12, "Tk. 49999/-", "TTk. 5999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.mipmap.image_5, "Pixel 2XL (Black)", 1, "3", 124, "Tk. 49999/-", "TTk. 5999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.mipmap.image_5, "Pixel 2XL (Black)", 0, "4.5", 124, "Tk. 49999/-", "TTk. 5999/-", "Cash On Delivery"));
*/
            WishListAdapter adapter = new WishListAdapter(wishListModelList, false);
            recyclerViewViewAll.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else if (layout_code == 1) {
            gridViewViewAll.setVisibility(View.VISIBLE);


          // horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.image_5, "RedMi", "Description blank", "100 tk"));


            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalScrollProductModelList);
           gridViewViewAll.setAdapter(gridProductLayoutAdapter);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
