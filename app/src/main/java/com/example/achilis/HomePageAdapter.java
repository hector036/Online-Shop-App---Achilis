package com.example.achilis;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }


    @NonNull
    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 1:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductScrollView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scrol_layout, viewGroup, false);
                return new HorizontalProductViewholder(horizontalProductScrollView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductScrollView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup, false);
                return new GridProductViewholder(gridProductScrollView);
            default:
                return null;

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        switch (homePageModelList.get(i).getType()) {

            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String htitle = homePageModelList.get(i).getTitle();
                List<HorizontalScrollProductModel> hhorizontalScrollProductModelList = homePageModelList.get(i).getHorizontalScrollProductModelList();
                List<WishListModel> viewAllProductList = homePageModelList.get(i).getViewAllWishList();
                ((HorizontalProductViewholder) viewHolder).setHorizontalProductLayout(hhorizontalScrollProductModelList, htitle,viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gtitle = homePageModelList.get(i).getTitle();
                List<HorizontalScrollProductModel> ghorizontalScrollProductModelList = homePageModelList.get(i).getHorizontalScrollProductModelList();
                ((GridProductViewholder) viewHolder).setGridProductLayout(ghorizontalScrollProductModelList, gtitle);
                break;
            default:
                return;

        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class HorizontalProductViewholder extends RecyclerView.ViewHolder {

        private TextView horizontalLayoutTitle;
        private Button horizontalViewAllButton;
        private RecyclerView horizontalRecyclerView;

        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);

            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewAllButton = itemView.findViewById(R.id.horizontal_scroll_layout_title_button);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_title_recycler_view);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalProductLayout(List<HorizontalScrollProductModel> horizontalScrollProductModelList, final String title, final List<WishListModel> viewAllProductList) {

            horizontalLayoutTitle.setText(title);

            if (horizontalScrollProductModelList.size() > 8) {
                horizontalViewAllButton.setVisibility(View.VISIBLE);
                horizontalViewAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewAllActivity.wishListModelList = viewAllProductList;
                        Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        viewAllIntent.putExtra("title",title);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            } else {
                horizontalViewAllButton.setVisibility(View.INVISIBLE);
            }

            HorizontalScrollProductAdapter horizontalScrollProductAdapter = new HorizontalScrollProductAdapter(horizontalScrollProductModelList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalScrollProductAdapter);
            horizontalScrollProductAdapter.notifyDataSetChanged();

        }


    }

    public class GridProductViewholder extends  RecyclerView.ViewHolder{

        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllButton;
        //private GridView gridView;
        private GridLayout gridProductLayout;

        public GridProductViewholder(@NonNull View itemView) {
            super(itemView);

            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllButton = itemView.findViewById(R.id.grid_product_layout_viewall_button);
           // gridView = itemView.findViewById(R.id.grid_product_layout_grid_view);
            gridProductLayout = itemView.findViewById(R.id.grid_layout_);

        }

        public void setGridProductLayout(final List<HorizontalScrollProductModel> horizontalScrollProductModelList, final String title){

            gridLayoutTitle.setText(title);


            //gridView.setAdapter(new GridProductLayoutAdapter(horizontalScrollProductModelList));
            for(int x = 0 ; x<4 ; x++){
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_scroll_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_scroll_product_title);
                TextView productDes = gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_scroll_product_description);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_scroll_product_price);

               // productImage.setImageResource(horizontalScrollProductModelList.get(x).getProductImage());
                Glide.with(itemView.getContext()).load(horizontalScrollProductModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.home_black)).into(productImage);

                productTitle.setText(horizontalScrollProductModelList.get(x).getProductTitle());
                productDes.setText(horizontalScrollProductModelList.get(x).getProductDes());
                productPrice.setText("Tk. "+horizontalScrollProductModelList.get(x).getProductPrice()+"/-");

                // check this out //
                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent productDetailIntent = new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        itemView.getContext().startActivity(productDetailIntent);
                    }
                });
            }

            gridLayoutViewAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewAllActivity.horizontalScrollProductModelList = horizontalScrollProductModelList;
                    Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
    }

}
