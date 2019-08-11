package com.example.achilis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HorizontalScrollProductAdapter extends RecyclerView.Adapter<HorizontalScrollProductAdapter.ViewHolder> {


    private List<HorizontalScrollProductModel> horizontalScrollProductModelList;

    public HorizontalScrollProductAdapter(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    @NonNull
    @Override
    public HorizontalScrollProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout_item,viewGroup,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalScrollProductAdapter.ViewHolder viewHolder, int i) {

        int resource = horizontalScrollProductModelList.get(i).getProductImage();
        String title = horizontalScrollProductModelList.get(i).getProductTitle();
        String des = horizontalScrollProductModelList.get(i).getProductDes();
        String price = horizontalScrollProductModelList.get(i).getProductPrice();

        viewHolder.setProductImage(resource);
        viewHolder.setProductTitle(title);
        viewHolder.setProductDes(des);
        viewHolder.setProductPrice(price);

    }

    @Override
    public int getItemCount() {
        return horizontalScrollProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productDes;
        private TextView productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage =  itemView.findViewById(R.id.horizontal_scroll_product_image);
            productTitle =  itemView.findViewById(R.id.horizontal_scroll_product_title);
            productDes =  itemView.findViewById(R.id.horizontal_scroll_product_description);
            productPrice =  itemView.findViewById(R.id.horizontal_scroll_product_price);


        }

        private void setProductImage(int r){
            productImage.setImageResource(r);
        }


        private void setProductTitle(String t){
            productTitle.setText(t);
        }

        private void setProductDes(String t){
            productDes.setText(t);
        }

        private void setProductPrice(String t){
            productPrice.setText(t);
        }



    }
}
