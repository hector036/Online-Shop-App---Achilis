package com.example.achilis;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {

            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
                return new CartItemViewHolder(cartItem);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotal = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout, viewGroup, false);
                return new CartTotalAmountViewHolder(cartTotal);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        switch (cartItemModelList.get(i).getType()) {
            case CartItemModel.CART_ITEM:
                    int resource = cartItemModelList.get(i).getProductImage();
                    String title = cartItemModelList.get(i).getProductTitle();
                    int freeCoupon = cartItemModelList.get(i).getFreeCoupons();
                    String productPrice = cartItemModelList.get(i).getProductPrice();
                    String cuttedPrice = cartItemModelList.get(i).getCuttedPrice();
                    int offerApplied = cartItemModelList.get(i).getOfferApplied();
                    //int couponApplied = cartItemModelList.get(i).getCouponApplied();
                   // int productQty = cartItemModelList.get(i).getProductQty();
                    ((CartItemViewHolder)viewHolder).setItemDetails(resource,title,freeCoupon,productPrice,cuttedPrice,offerApplied);
                break;
            case CartItemModel.TOTAL_AMOUNT:

                String totalItem = cartItemModelList.get(i).getTotalItem();
                String totalItemPrice = cartItemModelList.get(i).getTotalItemsPrice();
                String deliveryPrice = cartItemModelList.get(i).getDeliveryPrice();
                String totalAmount = cartItemModelList.get(i).getTotalAmmount();
                String savedAmount = cartItemModelList.get(i).getSavedAmount();
                ((CartTotalAmountViewHolder)viewHolder).setTotalAmount(totalItem,totalItemPrice,deliveryPrice,totalAmount,savedAmount);
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupon;
        private ImageView freeCouponIcon;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offerApplied;
        private TextView couponApplied;
        private TextView productQty;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image_cart);
            productTitle = itemView.findViewById(R.id.product_title_cart);
            freeCoupon = itemView.findViewById(R.id.tv_free_coupon_cart);
            freeCouponIcon = itemView.findViewById(R.id.free_coupon_icon_cart);
            productPrice = itemView.findViewById(R.id.product_price_cart);
            cuttedPrice = itemView.findViewById(R.id.cuttred_price_cart);
            offerApplied = itemView.findViewById(R.id.offer_appied_cart);
            couponApplied = itemView.findViewById(R.id.coupon_applied_cart);
            productQty = itemView.findViewById(R.id.product_quantity_cart);



        }

        private void setItemDetails(int r, String s1, int couponNo, String s3, String s4, int offerAppliedNo) {

            productImage.setImageResource(r);
            productTitle.setText(s1);


            if (couponNo > 0) {
                freeCoupon.setVisibility(View.VISIBLE);
                freeCouponIcon.setVisibility(View.VISIBLE);
                if (couponNo == 1) {
                    freeCoupon.setText("free " + couponNo + " coupon");
                } else {
                    freeCoupon.setText("free " + couponNo + " coupons");
                }

            } else {
                freeCoupon.setVisibility(View.INVISIBLE);
                freeCouponIcon.setVisibility(View.INVISIBLE);
            }

            productPrice.setText(s3);
            cuttedPrice.setText(s4);

            if (offerAppliedNo > 0) {
                offerApplied.setVisibility(View.VISIBLE);
                offerApplied.setText(offerAppliedNo + " offer applied");
            } else {
                offerApplied.setVisibility(View.INVISIBLE);
            }

            productQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.setCancelable(false);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                    final EditText quantityNum = quantityDialog.findViewById(R.id.quantity_num_dialog);
                    Button cancelBtn = quantityDialog.findViewById(R.id.cancel_btn_dialog);
                    Button okBtn = quantityDialog.findViewById(R.id.ok_btn_dialog);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            quantityDialog.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            productQty.setText("Qty: " + quantityNum.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });



            //couponApplied.setText(s6);
           // productQty.setText(s7);

        }


    }

    class CartTotalAmountViewHolder extends RecyclerView.ViewHolder {

        private TextView totalItem;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);

            totalItem = itemView.findViewById(R.id.total_item_cart);
            totalItemPrice = itemView.findViewById(R.id.total_item_price_cart);
            deliveryPrice = itemView.findViewById(R.id.delevary_charge_cart);
            totalAmount = itemView.findViewById(R.id.total_amount_cart);
            savedAmount = itemView.findViewById(R.id.saved_amount_cart);
        }

        private void setTotalAmount(String s1, String s2, String s3, String s4, String s5) {

            totalItem.setText(s1);
            totalItemPrice.setText(s2);
            deliveryPrice.setText(s3);
            totalAmount.setText(s4);
            savedAmount.setText(s5);

        }
    }
}
