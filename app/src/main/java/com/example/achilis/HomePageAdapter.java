package com.example.achilis;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
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
                ((HorizontalProductViewholder) viewHolder).setHorizontalProductLayout(hhorizontalScrollProductModelList, htitle);
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

        }

        private void setHorizontalProductLayout(List<HorizontalScrollProductModel> horizontalScrollProductModelList, String title) {

            horizontalLayoutTitle.setText(title);

            if (horizontalScrollProductModelList.size() > 8) {
                horizontalViewAllButton.setVisibility(View.VISIBLE);
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
        private GridView gridView;
        public GridProductViewholder(@NonNull View itemView) {
            super(itemView);

            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllButton = itemView.findViewById(R.id.grid_product_layout_viewall_button);
            gridView = itemView.findViewById(R.id.grid_product_layout_grid_view);

        }

        public void setGridProductLayout(List<HorizontalScrollProductModel> horizontalScrollProductModelList,String title){

            gridLayoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalScrollProductModelList));

        }
    }

}
