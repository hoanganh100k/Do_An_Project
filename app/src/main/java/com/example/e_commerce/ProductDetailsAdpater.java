package com.example.e_commerce;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


public class ProductDetailsAdpater extends FragmentStatePagerAdapter {

    private int totalTabs;
    private String productDescription;
    private String productOtherDetails;
    private List<ProductInformationModel> productInformationModelList;
    public ProductDetailsAdpater(@NonNull FragmentManager fm, int totalTabs, String productDescription, String productOtherDetails, List<ProductInformationModel> productInformationModelList) {
        super(fm);
        this.totalTabs = totalTabs;
        this.productDescription = productDescription;
        this.productOtherDetails = productOtherDetails;
        this.productInformationModelList = productInformationModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment();
                productDescriptionFragment.body = productDescription;
                return productDescriptionFragment;

            case 1:
                ProductInformationFragment productInformationFragment  = new ProductInformationFragment();
                productInformationFragment.productInformationModelList = productInformationModelList;
                return productInformationFragment;

            case 2:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                productDescriptionFragment1.body = productOtherDetails;
                return productDescriptionFragment1;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
