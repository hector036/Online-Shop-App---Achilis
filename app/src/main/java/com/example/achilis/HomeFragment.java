package com.example.achilis;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.achilis.DBqueries.categoryModelList;
import static com.example.achilis.DBqueries.firebaseFirestore;


import static com.example.achilis.DBqueries.lists;
import static com.example.achilis.DBqueries.listsCopy;
import static com.example.achilis.DBqueries.loadCategories;
import static com.example.achilis.DBqueries.loadFragmentData;
import static com.example.achilis.DBqueries.loadedCategoriesName;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private RecyclerView categoryRecyclerView;
    private RecyclerView homePageRecyclerView;
    private CategoryAdapter categoryAdapter;
    public static SwipeRefreshLayout swipeRefreshLayout;
    public static ProgressBar progressBar;
   public static boolean  isListNoOneStatic=true;


    private List<CategoryModel> categoryModeFakelList = new ArrayList<>();
    private List<HomePageModel> homePageModelArrayFakeList = new ArrayList<>();

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button refreshButton;



    private HomePageAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    ///////////////horizontal product layout
    private TextView horizontalLayoutTitle;
    private Button horizontalViewAllButton;
    private RecyclerView horizontalRecyclerView;
    private ImageView noInternetConnection;

    ///////////////horizontal product layout


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout_home);
        progressBar = view.findViewById(R.id.progressBar_home);

        categoryRecyclerView = view.findViewById(R.id.categories_reclycler_view);
        homePageRecyclerView = view.findViewById(R.id.testing);
        refreshButton = view.findViewById(R.id.retry_button_home);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);


        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);


        categoryModeFakelList.add(new CategoryModel("null", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));
        categoryModeFakelList.add(new CategoryModel("", ""));

        //////fake

        List<HorizontalScrollProductModel> horizontalScrollProductModelList = new ArrayList<>();
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel("", "", "", "", ""));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel("", "", "", "", ""));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel("", "", "", "", ""));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel("", "", "", "", ""));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel("", "", "", "", ""));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel("", "", "", "", ""));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel("", "", "", "", ""));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel("", "", "", "", ""));

        //homePageModelArrayFakeList.add(new HomePageModel(0,"",horizontalScrollProductModelList,new ArrayList<WishListModel>()));
        //  homePageModelArrayFakeList.add(new HomePageModel(1,"",horizontalScrollProductModelList));
        // homePageModelArrayFakeList.addAll(lists.get(0));


        //////fake

        ///seems bekar
        categoryAdapter = new CategoryAdapter(categoryModeFakelList);
        adapter = new HomePageAdapter(homePageModelArrayFakeList);
        ///seems bekar


        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            HomeActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            refreshButton.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if (categoryModelList.size() == 0) {
                loadCategories(categoryRecyclerView, getContext());


            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();


            }

            categoryRecyclerView.setAdapter(categoryAdapter);

            if (lists.size() == 0 && listsCopy.size() == 0) {
                progressBar.setVisibility(View.VISIBLE);
                loadedCategoriesName.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                listsCopy.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView, getContext(), 0, "Home",false);


            } else {
                if (isListNoOneStatic) {
                    adapter = new HomePageAdapter(lists.get(0));
                    adapter.notifyDataSetChanged();

                } else  {
                    adapter = new HomePageAdapter(listsCopy.get(0));
                    adapter.notifyDataSetChanged();

                }
            }
            homePageRecyclerView.setAdapter(adapter);



        } else {
            HomeActivity.drawer.setDrawerLockMode(1);

            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.mipmap.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.VISIBLE);

        }
        // homePageModelArrayFakeList.addAll(lists.get(0));

        ///// refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();

            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadPage();
            }
        });
        return view;
    }

    private void reloadPage(){
        networkInfo = connectivityManager.getActiveNetworkInfo();

              /*  //we are creating fake list as previout list
                if (homePageModelArrayFakeList.size() != 0) {
                    homePageModelArrayFakeList.clear();

                }
                homePageModelArrayFakeList.addAll(lists.get(0));

                //we are creating fake list as previout list*/
        categoryModelList.clear();

        if (isListNoOneStatic) {
            listsCopy.clear();
        } else {
            lists.clear();
        }
        // lists.clear();
        loadedCategoriesName.clear();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            HomeActivity.drawer.setDrawerLockMode(0);

            noInternetConnection.setVisibility(View.GONE);
            refreshButton.setVisibility(View.GONE);

            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            categoryAdapter = new CategoryAdapter(categoryModeFakelList);
            //adapter = new HomePageAdapter(homePageModelArrayFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            //homePageRecyclerView.setAdapter(adapter);
            loadCategories(categoryRecyclerView, getContext());
            loadedCategoriesName.add("HOME");
            if (isListNoOneStatic) {
                listsCopy.add(new ArrayList<HomePageModel>());
            } else {
                lists.add(new ArrayList<HomePageModel>());
            }
            loadFragmentData(homePageRecyclerView, getContext(), 0, "Home",isListNoOneStatic);
            if(isListNoOneStatic){
                isListNoOneStatic = false;
            }else {
                isListNoOneStatic = true;
            }
            ///////

        } else {
            HomeActivity.drawer.setDrawerLockMode(1);

            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();

            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);

            Glide.with(getContext()).load(R.mipmap.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.VISIBLE);

            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
