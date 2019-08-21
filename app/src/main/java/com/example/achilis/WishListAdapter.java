package com.example.achilis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

   private List<WishListModel> wishListModelList;
   private Boolean wishlist;
   private int lastPosition =-1;

    public WishListAdapter(List<WishListModel> wishListModelList, boolean wishlist) {
        this.wishListModelList = wishListModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder viewHolder, int i) {
        String productId = wishListModelList.get(i).getProductId();
        String resoure = wishListModelList.get(i).getProductImage();
        String title = wishListModelList.get(i).getProuctTitle();
        long freeCoupon = wishListModelList.get(i).getFreeCoupon();
        String rating = wishListModelList.get(i).getRating();
        long totalrating = wishListModelList.get(i).getTotalRating();
        String productPrice = wishListModelList.get(i).getProductPrice();
        String cuttedPrice = wishListModelList.get(i).getCuttedPrice();
        boolean paymentMethod = wishListModelList.get(i).isCOD();
        viewHolder.setData(productId,resoure,title,freeCoupon,rating,totalrating,productPrice,cuttedPrice,paymentMethod,i);

        if (lastPosition < i) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = i;
        }
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

        private void setData(final String productId, String iconUrl, String t, long couponNo, String rate, long totalRate, String price, String cprice, boolean pmethod, final int index){

           //productImage.setImageResource(r);

            Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.mipmap.ph_rec)).into(productImage);

            productTitle.setText(t);

            if (couponNo > 0) {
                freeCoupon.setVisibility(View.VISIBLE);
                couponIcon.setVisibility(View.VISIBLE);
                if (couponNo == 1) {
                    freeCoupon.setText("Free " + couponNo + " coupon");
                } else {
                    freeCoupon.setText("Free " + couponNo + " coupons");
                }

            } else {
                freeCoupon.setVisibility(View.INVISIBLE);
                couponIcon.setVisibility(View.INVISIBLE);
            }

            rating.setText(rate);
            totalRating.setText("("+totalRate+") ratings");
            productPrice.setText("Tk. "+price+"/-");
            cuttedPrice.setText("Tk. "+cprice+"/-");
            if(pmethod){
                paymentMethod.setVisibility(View.VISIBLE);
            }else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if(wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
            }else{
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!ProductDetailsActivity.running_wishlist_query) {
                        ProductDetailsActivity.running_wishlist_query = true;
                        DBqueries.removeFromWishList(index, itemView.getContext());
                    }
                    Toast.makeText(itemView.getContext(),"Removed from wishlist",Toast.LENGTH_SHORT).show();

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID",productId);

                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });


        }
    }
}
