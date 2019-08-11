package com.example.achilis;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    ///////////////horizontal product layout
    private TextView horizontalLayoutTitle;
    private Button horizontalViewAllButton;
    private RecyclerView horizontalRecyclerView;

    ///////////////horizontal product layout


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerView =  view.findViewById(R.id.categories_reclycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);


        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();

        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Food"));
        categoryModelList.add(new CategoryModel("link","Fashion"));
        categoryModelList.add(new CategoryModel("link","Phones"));
        categoryModelList.add(new CategoryModel("link","Tablets"));
        categoryModelList.add(new CategoryModel("link","Glosaries"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Services"));
        categoryModelList.add(new CategoryModel("link","More"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


        ///////////////Horizontal product view


        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalViewAllButton = view.findViewById(R.id.horizontal_scroll_layout_title_button);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_title_recycler_view);

        List<HorizontalScrollProductModel> horizontalScrollProductModelList = new ArrayList<>();

        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"RedMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"RedMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"RedMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"RedMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));

        HorizontalScrollProductAdapter horizontalScrollProductAdapter = new HorizontalScrollProductAdapter(horizontalScrollProductModelList);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);

        horizontalRecyclerView.setAdapter(horizontalScrollProductAdapter);
        horizontalScrollProductAdapter.notifyDataSetChanged();




        ///////////////Horizontal product view


        ////////////////Grid product layout /////////

        TextView gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutViewAllButton = view.findViewById(R.id.grid_product_layout_viewall_button);
        GridView gridView = view.findViewById(R.id.grid_product_layout_grid_view);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalScrollProductModelList));

        ////////////////Grid product layout /////////

        ////////////////////////
        RecyclerView testing = view.findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();

        homePageModelList.add(new HomePageModel(0,"Deals Of The Day",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(1,"#Treding",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(1,"#NewCollection",horizontalScrollProductModelList));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ////////////////////////




        return view;
    }

}
