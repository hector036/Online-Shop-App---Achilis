package com.example.achilis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalScrollProductAdapter extends RecyclerView.Adapter<HorizontalScrollProductAdapter.ViewHolder> {


    private List<HorizontalScrollProductModel> horizontalScrollProductModelList;

    public HorizontalScrollProductAdapter(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    @NonNull
    @Override
    public HorizontalScrollProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout_item, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalScrollProductAdapter.ViewHolder viewHolder, int i) {

        String resource = horizontalScrollProductModelList.get(i).getProductImage();
        String title = horizontalScrollProductModelList.get(i).getProductTitle();
        String des = horizontalScrollProductModelList.get(i).getProductDes();
        String price = horizontalScrollProductModelList.get(i).getProductPrice();
        String productId = horizontalScrollProductModelList.get(i).getProductID();

      viewHolder.setData(productId,resource,title,des,price);

    }

    @Override
    public int getItemCount() {
        if (horizontalScrollProductModelList.size() > 8) {
            return 8;
        } else
            return horizontalScrollProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productDes;
        private TextView productPrice;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.horizontal_scroll_product_image);
            productTitle = itemView.findViewById(R.id.horizontal_scroll_product_title);
            productDes = itemView.findViewById(R.id.horizontal_scroll_product_description);
            productPrice = itemView.findViewById(R.id.horizontal_scroll_product_price);


        }

        private void setData(final String productId, String iconUrl, String t1, String t2, String t3) {
            Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.mipmap.ph_rec)).into(productImage);
            productTitle.setText(t1);
            productDes.setText(t2);
            productPrice.setText("Tk. " + t3 + "/-");

            if (!t1.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                       productDetailsIntent.putExtra("PRODUCT_ID",productId);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }
        }


    }
}
