package com.nguyenmanhtuan.fragment;

import com.nguyenmanhtuan.benhandientu.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BloodFrag extends Fragment {
    View                        blood;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        blood = inflater.inflate(R.layout.frag_blood, container, false);
        String pid = this.getArguments().getString("pid");
        return blood;
    }
    
    
}
