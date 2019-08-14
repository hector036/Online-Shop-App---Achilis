package com.example.achilis;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyOderAdapter extends RecyclerView.Adapter<MyOderAdapter.ViewHolder> {

    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyOderAdapter.ViewHolder viewHolder, int i) {
        int resource = myOrderItemModelList.get(i).getProductImage();
        int rating = myOrderItemModelList.get(i).getRating();
        String title = myOrderItemModelList.get(i).getProductTitle();
        String deliveryStatus = myOrderItemModelList.get(i).getDeliveryStatus();
        viewHolder.setData(resource,title,deliveryStatus,rating);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;
        private LinearLayout rateNaowContainer;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image_order);
            productTitle = itemView.findViewById(R.id.product_title_order);
            orderIndicator = itemView.findViewById(R.id.order_indicator_order);
            deliveryStatus = itemView.findViewById(R.id.order_delivered_date_order);
            rateNaowContainer = itemView.findViewById(R.id.rate_now_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setData(int r, String t, String d,int rating) {
            productImage.setImageResource(r);
            productTitle.setText(t);
            if (d.equals("Cancelled")) {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
            }else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.green)));

            }
            deliveryStatus.setText(d);

            /////////Rating/////

            setRating(rating);
            for(int i= 0;i<rateNaowContainer.getChildCount();i++){
                final int starPosition=i;
                rateNaowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        setRating(starPosition);
                    }
                });

            }
            //////Rating///////
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setRating(int starPosition) {
            for(int i=0;i<rateNaowContainer.getChildCount();i++){
                ImageView starBtn = (ImageView) rateNaowContainer.getChildAt(i);
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#C0BFBF")));
                if(i<=starPosition){
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
                }
            }
        }


    }
}
