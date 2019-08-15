package com.example.achilis;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;

    private TabLayout productDetailsTabLayout;
    private ViewPager productDetailsViewPager;

    ////rating////
    private LinearLayout rateNaowContainer;
    ////rating///

    private Button buyNowBtn;

    private FloatingActionButton addToWishListButton;
    private static boolean isAddedToWishList=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager = findViewById(R.id.product_images_view_pagers);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        addToWishListButton = findViewById(R.id.add_to_wishlist_button);
        productDetailsTabLayout = findViewById(R.id.product_details_tabLayout);
        productDetailsViewPager = findViewById(R.id.product_details_viewpagers);
        buyNowBtn = findViewById(R.id.buy_now_button);
        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.mipmap.order_black);
        productImages.add(R.mipmap.order_black);
        productImages.add(R.mipmap.order_black);
        productImages.add(R.mipmap.order_black);
        productImages.add(R.mipmap.order_black);

        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        productImageViewPager.setAdapter(productImagesAdapter);

        viewPagerIndicator.setupWithViewPager(productImageViewPager,true);

        addToWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAddedToWishList){
                    isAddedToWishList = false;
                    addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B1AEAE")));
                }else{
                    isAddedToWishList = true;
                    addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });

        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /////////Rating/////

        rateNaowContainer = findViewById(R.id.rate_now_container);
        for(int i= 0;i<rateNaowContainer.getChildCount();i++){
            final int starPosition=i;
            rateNaowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                   setRating(starPosition); 
                }
            });

        }
        //////Rating///////

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setRating(int starPosition) {
        for(int i=0;i<rateNaowContainer.getChildCount();i++){
            ImageView starBtn = (ImageView) rateNaowContainer.getChildAt(i);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#C0BFBF")));
            if(i<=starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            finish();
            return true;
        }else if(id == R.id.home_cart_icon){


            return true;
        }else if(id ==  R.id.home_search_icon){


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
