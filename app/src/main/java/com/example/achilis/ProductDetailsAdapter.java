package com.example.achilis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totaltabs;
    private String productDescription;
    private String productOtherDetails;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailsAdapter(FragmentManager fm,int totalTabs, String productDescription, String productOtherDetails, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.productDescription = productDescription;
        this.productOtherDetails = productOtherDetails;
        this.productSpecificationModelList = productSpecificationModelList;
        this.totaltabs = totalTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ProductDesFragment productDesFragment1 = new ProductDesFragment();
                productDesFragment1.body = productDescription;
                return productDesFragment1;

            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
               productSpecificationFragment.productSpecificationModelList = productSpecificationModelList;
                return productSpecificationFragment;
            case 2:
                ProductDesFragment productDesFragment2 = new ProductDesFragment();
                productDesFragment2.body= productOtherDetails;
                return productDesFragment2;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
