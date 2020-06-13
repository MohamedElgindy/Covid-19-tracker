package com.example.covid19_test.UI.Main;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.covid19_test.UI.Countries.Countries_Fragment;
import com.example.covid19_test.UI.Countries.CountryFragment_Container;
import com.example.covid19_test.UI.Home.Home_Fragment;
import com.example.covid19_test.UI.Map.MapFragment;
import com.example.covid19_test.UI.More.More_Fragment;


public class TabAdapter extends FragmentStatePagerAdapter {

    private Fragment mHomeFragment = new Home_Fragment();
    private Fragment mCountryFragment_Container = new CountryFragment_Container();
    private Fragment mMapFragment = new  MapFragment();
    private Fragment mMore_Fragment = new  More_Fragment();


    public TabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Home_Fragment();
            case 1:
                return new CountryFragment_Container();
            case 2:
                return new MapFragment();
            case 3:
                return new More_Fragment();
        }

        return null ;

    }

    @Override
    public int getCount() {
        return 4;
    }}
