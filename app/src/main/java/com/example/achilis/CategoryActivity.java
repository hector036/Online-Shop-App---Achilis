package com.example.achilis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_activity_recycler_view);

        List<HorizontalScrollProductModel> horizontalScrollProductModelList = new ArrayList<>();

        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"RedMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"RedMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"RedMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"RedMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.order_black,"GreenMi","Description blank","100 tk"));

        ///////////////Horizontal product view


        //////////////////////// Multiple Recycler View ///////////

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();

        homePageModelList.add(new HomePageModel(0,"Deals Of The Day",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(1,"#Treding",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(1,"#NewCollection",horizontalScrollProductModelList));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //////////////////////// Multiple Recycler View ///////////
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id ==  R.id.home_search_icon){
            return true;
        }else if(id==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
