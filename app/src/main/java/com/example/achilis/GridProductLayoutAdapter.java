package com.example.achilis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalScrollProductModel> horizontalScrollProductModelList;

    public GridProductLayoutAdapter(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
       View view;
       if(convertView == null){
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout_item,null);
           ImageView productImage = view.findViewById(R.id.horizontal_scroll_product_image);
           TextView productTitle = view.findViewById(R.id.horizontal_scroll_product_title);
           TextView productDes = view.findViewById(R.id.horizontal_scroll_product_description);
           TextView productPrice = view.findViewById(R.id.horizontal_scroll_product_price);

           productImage.setImageResource(horizontalScrollProductModelList.get(i).getProductImage());
           productTitle.setText(horizontalScrollProductModelList.get(i).getProductTitle());
           productDes.setText(horizontalScrollProductModelList.get(i).getProductDes());
           productPrice.setText(horizontalScrollProductModelList.get(i).getProductPrice());
       }else{
           view = convertView;
       }
       return view;
    }
}
