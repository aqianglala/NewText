package com.example.qiang_pc.newtalkpal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by qiang-pc on 2016/1/25.
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter{
    private List<Fragment>mFragments;
    private List<String>mTitles;
    public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments,List<String> titles) {
        super(fm);
        mFragments=fragments;
        mTitles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
