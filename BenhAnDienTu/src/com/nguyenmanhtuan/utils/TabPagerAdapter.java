package com.nguyenmanhtuan.utils;

import com.nguyenmanhtuan.fragment.BloodFrag;
import com.nguyenmanhtuan.fragment.LiverFrag;
import com.nguyenmanhtuan.fragment.UrineFrag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPagerAdapter extends FragmentStatePagerAdapter {
   //Bundle bundle;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
       
       
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
        case 0:
            return new LiverFrag();
//            LiverFrag liver = new LiverFrag();
//            liver.setArguments();
//            return liver;
        case 1:           
            return new BloodFrag();
        case 2:

            return new UrineFrag();       
        }
        return null;
        
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3; //No of Tabs
    }


    }