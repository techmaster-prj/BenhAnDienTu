package com.nguyenmanhtuan.fragment;

import com.nguyenmanhtuan.benhandientu.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UrineFrag extends Fragment {
    View urine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        urine = inflater.inflate(R.layout.frag_urine, container, false);
        String pid = this.getArguments().getString("pid");
        return urine;
    }
    
}
