package com.example.achilis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totaltabs;

    public ProductDetailsAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totaltabs = totalTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ProductDesFragment productDesFragment1 = new ProductDesFragment();
                return productDesFragment1;

            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                return productSpecificationFragment;
            case 2:
                ProductDesFragment productDesFragment2 = new ProductDesFragment();
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
