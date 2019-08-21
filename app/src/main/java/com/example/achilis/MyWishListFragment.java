package com.example.achilis;


import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishListFragment extends Fragment {




    public MyWishListFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerview;
    private Dialog loadingDialog;

    public static WishListAdapter wishListAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        //////loading dialog////
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.rounded_back_ground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        //////loading dialog////

        wishlistRecyclerview = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerview.setLayoutManager(linearLayoutManager);


       /* wishListModelList.add(new WishListModel(R.mipmap.image_5,"Pixel 2XL (Black)",1,"3",124,"Tk. 49999/-","TTk. 5999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.mipmap.image_5,"Pixel 2XL (Black)",5,"3.6",124,"Tk. 49999/-","TTk. 5999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.mipmap.image_5,"Pixel 2XL (Black)",1,"3",124,"Tk. 49999/-","TTk. 5999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.mipmap.image_5,"Pixel 2XL (Black)",0,"3",14,"Tk. 49999/-","TTk. 5999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.mipmap.image_5,"Pixel 2XL (Black)",1,"3",12,"Tk. 49999/-","TTk. 5999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.mipmap.image_5,"Pixel 2XL (Black)",1,"3",124,"Tk. 49999/-","TTk. 5999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.mipmap.image_5,"Pixel 2XL (Black)",0,"4.5",124,"Tk. 49999/-","TTk. 5999/-","Cash On Delivery"));
*/
       if(DBqueries.wishListModelList.size()==0){
           DBqueries.wishList.clear();
           DBqueries.loadWishList(getContext(),loadingDialog,true);
       }else {
           loadingDialog.dismiss();
       }

         wishListAdapter = new WishListAdapter(DBqueries.wishListModelList,true);
        wishlistRecyclerview.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();
        return view;
    }

}
