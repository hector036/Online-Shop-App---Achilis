package com.example.achilis;

import android.app.Dialog;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.achilis.HomeActivity.showcart;
import static com.example.achilis.RegisterActivity.closeBtnDisabled;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_wishlist_query = false;
    public static boolean running_rating_query = false;
    public static boolean running_cart_query = false;

    public static boolean isAddedToWishList = false;
    public static boolean isAddedToCart = false;

    private FirebaseUser currentUser;


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

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();


    //////product des


    ////rating////
    public static int initialRating;
    public static LinearLayout rateNaowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private LinearLayout ratingsProgressBarContainer;
    private String totalRatingFigure;
    ////rating///


    private Button buyNowBtn;
    private Dialog signInDialog;
    private Dialog loadingDialog;

    private LinearLayout addToCart;
    public static MenuItem cartItem;
    private LinearLayout coupenRedeemLayout;
    public static String productID;

    private  TextView badgeCount;
    public static FloatingActionButton addToWishListButton;

    private DocumentSnapshot documentSnapshot;

    private FirebaseFirestore firebaseFirestore;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        addToCart = findViewById(R.id.add_to_cart);
        coupenRedeemLayout = findViewById(R.id.copon_redemtion_layout);


        //////loading dialog////
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_back_ground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        initialRating = -1;

        //////loading dialog////
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();

        productID = getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    documentSnapshot = task.getResult();

                    for (long x = 1; x < (long) documentSnapshot.get("no_of_products_images") + 1; x++) {

                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }

                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImageViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_ratings").toString());
                    totalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_ratings") + ") Ratings");
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

                    totalRatings.setText((long) documentSnapshot.get("total_ratings") + " Ratings");
                    totalRatingFigure = String.valueOf((long) documentSnapshot.get("total_ratings"));

                    for (int x = 0; x < 5; x++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long) documentSnapshot.get((5 - x) + "_star")));

                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));
                    }
                    averageRating.setText(documentSnapshot.get("average_ratings").toString());
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));

                    if (currentUser != null) {

                        if (DBqueries.myRating.size() == 0) {
                            DBqueries.loadRatingList(ProductDetailsActivity.this);

                        }

                        if (DBqueries.cartList.size() == 0) {

                            DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false,badgeCount);

                        }

                        if (DBqueries.wishList.size() == 0) {

                            DBqueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);

                        } else {
                            loadingDialog.dismiss();
                        }


                    } else {
                        loadingDialog.dismiss();

                    }

                    if (DBqueries.myRatedIds.contains(productID)) {
                        int index = DBqueries.myRatedIds.indexOf(productID);
                        initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index))) - 1;
                        setRating(initialRating);
                    }


                    if (DBqueries.cartList.contains(productID)) {
                        isAddedToCart = true;

                    } else {
                        isAddedToCart = false;
                    }

                    if (DBqueries.wishList.contains(productID)) {
                        isAddedToWishList = true;
                        addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                    } else {
                        addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B1AEAE")));

                        isAddedToWishList = false;
                    }


                } else {
                    loadingDialog.dismiss();
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

                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    // addToWishListButton.setEnabled(false);

                    if (!running_wishlist_query) {
                        running_wishlist_query = true;
                        if (isAddedToWishList) {


                            int index = DBqueries.wishList.indexOf(productID);
                            DBqueries.removeFromWishList(index, ProductDetailsActivity.this);


                            addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B1AEAE")));
                        } else {

                            addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                            Map<String, Object> addProductWishList = new HashMap<>();
                            addProductWishList.put("product_ID_" + String.valueOf(DBqueries.wishList.size()), productID);
                            addProductWishList.put("list_size", (long) (DBqueries.wishList.size() + 1));

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProductWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        if (DBqueries.wishListModelList.size() != 0) {

                                            DBqueries.wishListModelList.add(new WishListModel(productID, documentSnapshot.get("product_image_1").toString()
                                                    , documentSnapshot.get("product_title_").toString()
                                                    , (long) documentSnapshot.get("free_coupens")
                                                    , documentSnapshot.get("average_rating").toString()
                                                    , (long) documentSnapshot.get("total_ratings")
                                                    , documentSnapshot.get("product_price").toString()
                                                    , documentSnapshot.get("cutted_price").toString()
                                                    , (boolean) documentSnapshot.get("COD")));
                                        }

                                        isAddedToWishList = true;
                                        addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                        DBqueries.wishList.add(productID);
                                        Toast.makeText(ProductDetailsActivity.this, "Added to wishlist!", Toast.LENGTH_SHORT).show();


                                    } else {
                                        addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B1AEAE")));
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                    running_wishlist_query = false;

                                }
                            });
                        }
                    }
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
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {

                        if (starPosition != initialRating) {
                            if (!running_rating_query) {
                                running_rating_query = true;
                                setRating(starPosition);
                                Map<String, Object> updateRating = new HashMap<>();

                                if (DBqueries.myRatedIds.contains(productID)) {

                                    TextView oldrating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                    TextView finalrating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);


                                    updateRating.put(initialRating + 1 + "_star", Long.parseLong(oldrating.getText().toString()) - 1);
                                    updateRating.put(starPosition + 1 + "_star", Long.parseLong(finalrating.getText().toString()) + 1);
                                    updateRating.put("average_ratings", calculateAverageRating((long) starPosition - initialRating, true));


                                } else {

                                    updateRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                    updateRating.put("average_ratings", calculateAverageRating((long) starPosition + 1, false));
                                    updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);

                                }


                                firebaseFirestore.collection("PRODUCTS").document(productID)
                                        .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Map<String, Object> myRating = new HashMap<>();

                                            if (DBqueries.myRatedIds.contains(productID)) {

                                                myRating.put("rating_" + DBqueries.myRatedIds.indexOf(productID), (long) starPosition + 1);


                                            } else {

                                                myRating.put("list_size", (long) DBqueries.myRatedIds.size() + 1);
                                                myRating.put("product_ID_" + DBqueries.myRatedIds.size(), productID);
                                                myRating.put("rating_" + DBqueries.myRatedIds.size(), (long) starPosition + 1);

                                            }

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATINGS")
                                                    .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        if (DBqueries.myRatedIds.contains(productID)) {

                                                            DBqueries.myRating.set(DBqueries.myRatedIds.indexOf(productID), (long) starPosition + 1);


                                                            TextView oldrating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                            TextView finalrating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                            oldrating.setText(String.valueOf(Integer.parseInt(oldrating.getText().toString()) - 1));

                                                            finalrating.setText(String.valueOf(Integer.parseInt(finalrating.getText().toString()) + 1));

                                                        } else {
                                                            DBqueries.myRatedIds.add(productID);
                                                            DBqueries.myRating.add((long) starPosition + 1);

                                                            TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));


                                                            totalRatingMiniView.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + ") Ratings");
                                                            totalRatings.setText((long) documentSnapshot.get("total_ratings") + 1 + " Ratings");
                                                            totalRatingFigure = String.valueOf((long) documentSnapshot.get("total_ratings") + 1);
                                                            Toast.makeText(ProductDetailsActivity.this, "Thank you for rating!", Toast.LENGTH_SHORT).show();
                                                        }


                                                        for (int x = 0; x < 5; x++) {
                                                            TextView ratingfigure = (TextView) ratingsNoContainer.getChildAt(x);

                                                            ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                                            int maxProgress = Integer.parseInt(totalRatingFigure);
                                                            progressBar.setMax(maxProgress);

                                                            progressBar.setProgress(Integer.parseInt(ratingfigure.getText().toString()));
                                                        }

                                                        initialRating = starPosition;
                                                        averageRating.setText(calculateAverageRating(0, true));
                                                        averageRatingMiniView.setText(calculateAverageRating(0, true));
                                                        if (DBqueries.wishList.contains(productID) && DBqueries.wishListModelList.size() != 0) {
                                                            int index = DBqueries.wishList.indexOf(productID);
                                                            DBqueries.wishListModelList.get(index).setRating(averageRating.getText().toString());
                                                            DBqueries.wishListModelList.get(index).setTotalRating(Long.parseLong(totalRatingFigure));
                                                        }


                                                    } else {
                                                        setRating(initialRating);

                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();


                                                    }

                                                    running_rating_query = false;

                                                }
                                            });

                                        } else {
                                            running_rating_query = false;
                                            setRating(initialRating);
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });


                            }
                        }
                    }

                }
            });

        }
        //////Rating///////

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (!running_cart_query) {
                        running_cart_query = true;
                        if (isAddedToCart) {
                            running_cart_query = false;
                            Toast.makeText(ProductDetailsActivity.this, "Already added to cart", Toast.LENGTH_SHORT).show();
                        } else {

                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBqueries.cartList.size()), productID);
                            addProduct.put("list_size", (long) (DBqueries.cartList.size() + 1));


                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        if (DBqueries.cartItemModelList.size() != 0) {

                                            DBqueries.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID, documentSnapshot.get("product_image_1").toString()
                                                    , documentSnapshot.get("product_title").toString()
                                                    , (long) documentSnapshot.get("free_coupens")
                                                    , documentSnapshot.get("product_price").toString()
                                                    , documentSnapshot.get("cutted_price").toString()
                                                    , (long) 1
                                                    , (long) 0
                                                    , (long) 0));
                                        }

                                        isAddedToCart = true;
                                        DBqueries.cartList.add(productID);
                                        Toast.makeText(ProductDetailsActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
                                        invalidateOptionsMenu();
                                        running_cart_query = false;
                                    } else {
                                        //    addToWishListButton.setEnabled(true);
                                        running_cart_query = false;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                    }

                }
            }
        });

        /////dialog
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogLogInBtn = signInDialog.findViewById(R.id.login_btn_dialog);

        final Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        dialogLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setSignUpFragment = false;
                closeBtnDisabled = true;
                startActivity(registerIntent);
                signInDialog.dismiss();
            }
        });


        /////dialog


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            coupenRedeemLayout.setVisibility(View.GONE);
        } else {
            coupenRedeemLayout.setVisibility(View.VISIBLE);

        }

        /////// wish list
        if (currentUser != null) {

            if (DBqueries.myRating.size() == 0) {
                DBqueries.loadRatingList(ProductDetailsActivity.this);

            }



            if (DBqueries.wishList.size() == 0) {

                DBqueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);

            } else {
                loadingDialog.dismiss();
            }


        } else {
            loadingDialog.dismiss();

        }

        if (DBqueries.myRatedIds.contains(productID)) {
            int index = DBqueries.myRatedIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index))) - 1;
            //  initialRating = (int) (DBqueries.myRating.get(index) - 1);
            setRating(initialRating);
        }

        if (DBqueries.cartList.contains(productID)) {
            isAddedToCart = true;

        } else {
            isAddedToCart = false;
        }

        if (DBqueries.wishList.contains(productID)) {
            isAddedToWishList = true;
            addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

        } else {
            addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B1AEAE")));

            isAddedToWishList = false;
        }

        invalidateOptionsMenu();
        /////// wish list

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setRating(int starPosition) {


        for (int i = 0; i < rateNaowContainer.getChildCount(); i++) {
            ImageView starBtn = (ImageView) rateNaowContainer.getChildAt(i);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#C0BFBF")));
            if (i <= starPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
            }
        }

    }

    private String calculateAverageRating(long currentUserRatings, boolean update) {
        Double totalstars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {

            TextView ratingNo = (TextView) ratingsNoContainer.getChildAt(5 - x);

            totalstars = totalstars + (Long.parseLong(ratingNo.getText().toString()) * x);


            //  totalstars = totalstars + ((long) documentSnapshot.get(x + "_star") * x);
        }
        totalstars = totalstars + currentUserRatings;
        if (update) {
            //return totalstars / ((long) documentSnapshot.get("total_ratings"));
            /// they used totalrating figure here
            return String.valueOf(totalstars / Long.parseLong(totalRatingFigure)).substring(0, 3);

        } else {
            return String.valueOf(totalstars / (Long.parseLong(totalRatingFigure) + 1)).substring(0, 3);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_menu, menu);

        cartItem = menu.findItem(R.id.home_cart_icon);

            cartItem.setActionView(R.layout.badge_layout);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.mipmap.cart);
             badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);

        if(currentUser!=null){
            if (DBqueries.cartList.size() == 0) {

                DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false,badgeCount);

            }else {
                badgeCount.setVisibility(View.VISIBLE);

                if (DBqueries.cartList.size() < 99) {
                    badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                } else {
                    badgeCount.setText("99");
                }
            }
        }

            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        Intent cartIntent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                        showcart = true;
                        startActivity(cartIntent);

                    }
                }
            });

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

            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                showcart = true;
                startActivity(cartIntent);
                return true;
            }
        } else if (id == R.id.home_search_icon) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
