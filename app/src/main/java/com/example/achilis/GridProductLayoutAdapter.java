package com.example.achilis;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalScrollProductModel> horizontalScrollProductModelList;

    public GridProductLayoutAdapter(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    @Override
    public int getCount() {
        return horizontalScrollProductModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, final ViewGroup parent) {
       View view;
       if(convertView == null){
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout_item,null);
          view.setElevation(0);
          view.setBackgroundColor(Color.parseColor("#ffffff"));

          view.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent productDetailsIntent = new Intent(parent.getContext(),ProductDetailsActivity.class);
                  parent.getContext().startActivity(productDetailsIntent);
              }
          });
           ImageView productImage = view.findViewById(R.id.horizontal_scroll_product_image);
           TextView productTitle = view.findViewById(R.id.horizontal_scroll_product_title);
           TextView productDes = view.findViewById(R.id.horizontal_scroll_product_description);
           TextView productPrice = view.findViewById(R.id.horizontal_scroll_product_price);

         Glide.with(parent.getContext()).load(horizontalScrollProductModelList.get(i).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.ph_rec)).into(productImage);
           productTitle.setText(horizontalScrollProductModelList.get(i).getProductTitle());
           productDes.setText(horizontalScrollProductModelList.get(i).getProductDes());
           productPrice.setText("Tk. "+horizontalScrollProductModelList.get(i).getProductPrice()+"/-");
       }else{
           view = convertView;
       }
       return view;
    }
}
