package com.example.achilis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

   private List<WishListModel> wishListModelList;

    public WishListAdapter(List<WishListModel> wishListModelList) {
        this.wishListModelList = wishListModelList;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder viewHolder, int i) {
        int resoure = wishListModelList.get(i).getProductImage();
        String title = wishListModelList.get(i).getProuctTitle();
        int freeCoupon = wishListModelList.get(i).getFreeCoupon();
        String rating = wishListModelList.get(i).getRating();
        int totalrating = wishListModelList.get(i).getTotalRating();
        String productPrice = wishListModelList.get(i).getProductPrice();
        String cuttedPrice = wishListModelList.get(i).getCuttedPrice();
        String paymentMethod = wishListModelList.get(i).getPaymentMethods();
        viewHolder.setData(resoure,title,freeCoupon,rating,totalrating,productPrice,cuttedPrice,paymentMethod);

    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupon;
        private ImageView couponIcon;
        private TextView rating;
        private TextView totalRating;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image_wishlist);
            productTitle = itemView.findViewById(R.id.product_title_wishlist);
            freeCoupon = itemView.findViewById(R.id.free_coupon_wishlist);
            couponIcon = itemView.findViewById(R.id.coupon_icon_wishlist);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRating = itemView.findViewById(R.id.total_rating_wishlist);
            priceCut = itemView.findViewById(R.id.price_cut_wishlist);
            productPrice = itemView.findViewById(R.id.product_price_wishlist);
            cuttedPrice = itemView.findViewById(R.id.cutted_price_wishlist);
            paymentMethod = itemView.findViewById(R.id.payment_methods_wishlist);
            deleteBtn = itemView.findViewById(R.id.delete_button_wishlist);
        }

        private void setData(int r, String t, int couponNo,String rate,int totalRate,String price,String cprice, String pmethod){

            productImage.setImageResource(r);
            productTitle.setText(t);

            if (couponNo > 0) {
                freeCoupon.setVisibility(View.VISIBLE);
                couponIcon.setVisibility(View.VISIBLE);
                if (couponNo == 1) {
                    freeCoupon.setText("free " + couponNo + " coupon");
                } else {
                    freeCoupon.setText("free " + couponNo + " coupons");
                }

            } else {
                freeCoupon.setVisibility(View.INVISIBLE);
                couponIcon.setVisibility(View.INVISIBLE);
            }

            rating.setText(rate);
            totalRating.setText(totalRate+"(ratings)");
            productPrice.setText(price);
            cuttedPrice.setText(cprice);
            paymentMethod.setText(pmethod);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"Deleted",Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
