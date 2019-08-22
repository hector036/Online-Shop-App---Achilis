package com.example.achilis;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.achilis.CategoryActivity.progressBarCategotyActivity;
import static com.example.achilis.HomeFragment.progressBar;


public class DBqueries {


    // public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    // public static FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList();

    ///list of list (for category)
    public static List<List<HomePageModel>> lists = new ArrayList();
    public static List<List<HomePageModel>> listsCopy = new ArrayList();
    public static List<String> loadedCategoriesName = new ArrayList();
    public static List<String> wishList = new ArrayList();
    public static List<String> myRatedIds = new ArrayList();
    public static List<Long> myRating = new ArrayList();
    public static List<WishListModel> wishListModelList = new ArrayList();


    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context) {

        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public static void loadFragmentData(final RecyclerView homeRecyclerViewfinal, final Context context, final int index, String categoryName, final boolean isListNoOne) {

        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (progressBarCategotyActivity != null) {
                                progressBarCategotyActivity.setVisibility(View.INVISIBLE);

                            }

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if ((long) documentSnapshot.get("view_type") == 0) {

                                    List<HorizontalScrollProductModel> horizontalScrollProductModelList = new ArrayList<>();
                                    List<WishListModel> viewAllProductModelList = new ArrayList<>();

                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x <= no_of_products; x++) {
                                        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));


                                        viewAllProductModelList.add(new WishListModel(documentSnapshot.get("product_ID_" + x).toString(), documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_full_title_" + x).toString()
                                                , (long) documentSnapshot.get("free_coupens_" + x)
                                                , documentSnapshot.get("average_rating_" + x).toString()
                                                , (long) documentSnapshot.get("total_ratings_" + x)
                                                , documentSnapshot.get("product_price_" + x).toString()
                                                , documentSnapshot.get("cutted_price_" + x).toString()
                                                , (boolean) documentSnapshot.get("COD_" + x)));
                                    }


                                    if (isListNoOne) {

                                        listsCopy.get(index).add(new HomePageModel(0, documentSnapshot.get("layout_title").toString(), horizontalScrollProductModelList, viewAllProductModelList));

                                    } else {

                                        lists.get(index).add(new HomePageModel(0, documentSnapshot.get("layout_title").toString(), horizontalScrollProductModelList, viewAllProductModelList));
                                    }


                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    List<HorizontalScrollProductModel> gridProductModelList = new ArrayList<>();

                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x <= no_of_products; x++) {
                                        gridProductModelList.add(new HorizontalScrollProductModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));
                                    }

                                    if (isListNoOne) {

                                        listsCopy.get(index).add(new HomePageModel(1, documentSnapshot.get("layout_title").toString(), gridProductModelList));
                                    } else {


                                        lists.get(index).add(new HomePageModel(1, documentSnapshot.get("layout_title").toString(), gridProductModelList));
                                    }

                                }

                                if (isListNoOne) {

                                   /* if(lists.size()!=0){
                                        lists.clear();

                                    }*/
                                    HomePageAdapter homePageAdapter = new HomePageAdapter(listsCopy.get(index));
                                    homeRecyclerViewfinal.setAdapter(homePageAdapter);
                                    homePageAdapter.notifyDataSetChanged();


                                } else {
                                    /*if(listsCopy.size()!=0){
                                        listsCopy.clear();
                                    }*/
                                    HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                                    homeRecyclerViewfinal.setAdapter(homePageAdapter);
                                    homePageAdapter.notifyDataSetChanged();


                                }

                                HomeFragment.swipeRefreshLayout.setRefreshing(false);
                            }
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);

                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public static void loadWishList(final Context context, final Dialog dialog, final boolean loadProductData) {

        wishList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        wishList.add(task.getResult().get("product_ID_" + x).toString());

                        if (DBqueries.wishList.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.isAddedToWishList = true;
                            if (ProductDetailsActivity.addToWishListButton != null) {
                                ProductDetailsActivity.addToWishListButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            }
                        } else {
                            if (ProductDetailsActivity.addToWishListButton != null) {
                                ProductDetailsActivity.addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B1AEAE")));
                            }
                            ProductDetailsActivity.isAddedToWishList = false;
                        }
                        if (loadProductData) {
                            wishListModelList.clear();
                            final String productId = task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productId)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        wishListModelList.add(new WishListModel(productId, task.getResult().get("product_image_1").toString()
                                                , task.getResult().get("product_title").toString()
                                                , (long) task.getResult().get("free_coupens")
                                                , task.getResult().get("average_ratings").toString()
                                                , (long) task.getResult().get("total_ratings")
                                                , task.getResult().get("product_price").toString()
                                                , task.getResult().get("cutted_price").toString()
                                                , (boolean) task.getResult().get("COD")));

                                        MyWishListFragment.wishListAdapter.notifyDataSetChanged();

                                    } else {

                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }

                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();
            }
        });
    }

    public static void removeFromWishList(final int index, final Context context) {
        wishList.remove(index);
        Map<String, Object> updateWishList = new HashMap<>();

        for (int x = 0; x < wishList.size(); x++) {
            updateWishList.put("product_ID" + x, wishList.get(x));
        }

        updateWishList.put("list_size", (long) wishList.size());


        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    if (wishListModelList.size() != 0) {
                        wishListModelList.remove(index);
                        MyWishListFragment.wishListAdapter.notifyDataSetChanged();
                    }

                    ProductDetailsActivity.isAddedToWishList = false;
                    Toast.makeText(context, "Removed from wishlist!", Toast.LENGTH_SHORT).show();
                } else {
                    if (ProductDetailsActivity.addToWishListButton != null) {
                        ProductDetailsActivity.addToWishListButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                    }
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                }


                ProductDetailsActivity.running_wishlist_query = false;

            }
        });

    }

    public static void loadRatingList(final Context context) {

        if (!ProductDetailsActivity.running_rating_query) {

            ProductDetailsActivity.running_rating_query = true;
            myRatedIds.clear();
            myRating.clear();

            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATINGS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {

                            myRatedIds.add(task.getResult().get("product_ID_" + x).toString());
                            myRating.add((long) task.getResult().get("rating_" + x));

                            if (task.getResult().get("product_ID_" + x).toString().equals(ProductDetailsActivity.productID)) {
                                ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                                if (ProductDetailsActivity.rateNaowContainer != null) {
                                    ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);
                                }
                            }
                        }

                    } else {

                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                    }

                    ProductDetailsActivity.running_rating_query = false;
                }
            });
        }

    }


    public static void clearData() {
        categoryModelList.clear();
        lists.clear();
        listsCopy.clear();
        loadedCategoriesName.clear();
        wishList.clear();
        wishListModelList.clear();
    }
}
