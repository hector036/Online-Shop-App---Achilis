package com.example.achilis;


import android.os.Bundle;
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
public class MyCartFragment extends Fragment {


    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartItemRecyclerView = view.findViewById(R.id.cart_item_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        List<CartItemModel> cartItemModelList = new ArrayList<>();

        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,1,1));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.image_5,"Pixel 2 (Black)",2,"Tk. 49999/-","Tk. 59999/-",1,1,1));
        cartItemModelList.add(new CartItemModel(1,"Total (2 items)","Tk. 49999/-","free","Tk. 59999/-","Tk.999/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

    cartItemRecyclerView.setLayoutManager(layoutManager);


        return view;
    }

}
