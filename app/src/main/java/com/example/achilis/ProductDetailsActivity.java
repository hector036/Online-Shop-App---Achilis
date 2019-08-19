package com.example.achilis;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.achilis.HomeActivity.showcart;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;
    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private TextView codIndicatotText;
    private ImageView codIndicatot;


    //////product des

    private ConstraintLayout productDetailsOnly;
    private ConstraintLayout productDetailsTabContainer;

    private TabLayout productDetailsTabLayout;
    private ViewPager productDetailsViewPager;

    //static
    private String productDescription;
    private String productOtherDetails;

    private TextView productOnlyDesBody;
    private TextView averageRating;

    private  List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();


    //////product des


    ////rating////
    private LinearLayout rateNaowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private LinearLayout ratingsProgressBarContainer;
    ////rating///


    private Button buyNowBtn;

    private FloatingActionButton addToWishListButton;
    private static boolean isAddedToWishList = false;

    private FirebaseFirestore firebaseFirestore;

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

        productTitle = findViewById(R.id.product_details_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);

        codIndicatot = findViewById(R.id.cod_indicator);
        codIndicatotText = findViewById(R.id.tv_cod_indicator);
        productDetailsOnly = findViewById(R.id.product_details_only_container);
        productDetailsTabContainer = findViewById(R.id.product_details_tabs_container);
        productOnlyDesBody = findViewById(R.id.product_details_only_body);

        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.rating_total_number_container);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        averageRating = findViewById(R.id.avg_rating);



        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();

        firebaseFirestore.collection("PRODUCTS").document("NhBbFyE6T4Sim8jkBqjr")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();

                    for (long x = 1; x < (long) documentSnapshot.get("no_of_products_images") + 1; x++) {

                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }

                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImageViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_ratings").toString());
                    totalRatingMiniView.setText("(" + (long)documentSnapshot.get("total_ratings") + ") Ratings");
                    productPrice.setText("Tk. " + documentSnapshot.get("product_price").toString() + "/-");
                    cuttedPrice.setText("Tk. " + documentSnapshot.get("cutted_price").toString() + "/-");

                    if ((boolean) documentSnapshot.get("COD")) {

                        codIndicatot.setVisibility(View.VISIBLE);
                        codIndicatotText.setVisibility(View.VISIBLE);
                    } else {

                        codIndicatot.setVisibility(View.INVISIBLE);
                        codIndicatotText.setVisibility(View.INVISIBLE);
                    }

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabContainer.setVisibility(View.VISIBLE);
                        productDetailsOnly.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("product_description").toString();
                        productOtherDetails = documentSnapshot.get("product_other_details").toString();

                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_title") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));
                            for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_feilds") + 1; y++) {
                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + x + "_feild_" + y + "_name").toString(), documentSnapshot.get("spec_title_" + x + "_feild_" + y + "_value").toString()));

                            }
                        }

                    } else {
                        productDetailsTabContainer.setVisibility(View.GONE);
                        productDetailsOnly.setVisibility(View.VISIBLE);
                        productOnlyDesBody.setText(documentSnapshot.get("product_description").toString());
                    }

                    totalRatings.setText((long) documentSnapshot.get("total_ratings") + "Ratings");

                    for(int x= 0; x< 5 ; x++){
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));
                    }

                    averageRating.setText(documentSnapshot.get("average_ratings").toString());
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(),productDescription,productOtherDetails,productSpecificationModelList));


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                    System.out.println(error);
                }
            }
        });

        /*List<Integer> productImages = new ArrayList<>();
        productImages.add(R.mipmap.order_black);
        productImages.add(R.mipmap.order_black);
        productImages.add(R.mipmap.order_black);
        productImages.add(R.mipmap.order_black);
        productImages.add(R.mipmap.order_black);*/


        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);

        addToWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAddedToWishList) {
                    isAddedToWishList = false;
                    addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B1AEAE")));
                } else {
                    isAddedToWishList = true;
                    addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });

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
        for (int i = 0; i < rateNaowContainer.getChildCount(); i++) {
            final int starPosition = i;
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
        for (int i = 0; i < rateNaowContainer.getChildCount(); i++) {
            ImageView starBtn = (ImageView) rateNaowContainer.getChildAt(i);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#C0BFBF")));
            if (i <= starPosition) {
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
        } else if (id == R.id.home_cart_icon) {
            Intent cartIntent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
            showcart = true;
            startActivity(cartIntent);
            return true;
        } else if (id == R.id.home_search_icon) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
