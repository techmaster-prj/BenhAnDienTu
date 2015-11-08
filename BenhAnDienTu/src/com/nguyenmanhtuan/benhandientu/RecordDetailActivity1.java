package com.nguyenmanhtuan.benhandientu;


import com.nguyenmanhtuan.fragment.BloodFrag;
import com.nguyenmanhtuan.fragment.LiverFrag;
import com.nguyenmanhtuan.fragment.UrineFrag;
import com.nguyenmanhtuan.utils.TabPagerAdapter;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class RecordDetailActivity1 extends Activity {
    ViewPager       Tab;
    TabPagerAdapter TabAdapter;
    ActionBar       actionBar;
    private String          pid;
    
   
    public String getPid() {
        return pid;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorddetail_1);
        Intent i = getIntent();
        pid = i.getStringExtra("pid");
       
   
//        // set Fragmentclass Arguments
//        LiverFrag liver = new LiverFrag();
//        liver.setArguments(bundle);
//        
//        
//        BloodFrag blood = new BloodFrag();
//        blood.setArguments(bundle);
//        
//        UrineFrag urine = new UrineFrag();               
//        urine.setArguments(bundle);
        //getFragmentManager().beginTransaction().commit();
        
        //TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        
        Tab = (ViewPager) findViewById(R.id.pager);
        Tab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                
                actionBar = getActionBar();
                actionBar.setSelectedNavigationItem(position);
            }
        });
        //Tab.setAdapter(TabAdapter);
        
        actionBar = getActionBar();
        // Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            
            @Override
            public void onTabReselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                
                Tab.setCurrentItem(tab.getPosition());
                
            }
            
            @Override
            public void onTabUnselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
                
            }
        };
        // Add New Tab
        actionBar.addTab(actionBar.newTab().setText("Liver Exam").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Blood Exam").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Urine Exam").setTabListener(tabListener));
        
    }
//    public  String getPid() {
//     
//        return pid;
//    }
    
}
