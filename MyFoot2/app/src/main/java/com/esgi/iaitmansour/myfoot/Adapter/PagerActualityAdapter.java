package com.esgi.iaitmansour.myfoot.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.esgi.iaitmansour.myfoot.Fragments.NewsFragment;
import com.esgi.iaitmansour.myfoot.Fragments.TwitterFragment;

/**
 * Created by iaitmansour on 08/07/2018.
 */

public class PagerActualityAdapter extends FragmentStatePagerAdapter {


    int mNumOfTabs;

    public PagerActualityAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NewsFragment tabNews = new NewsFragment();
                return tabNews;
            case 1:
                TwitterFragment tabTwitter = new TwitterFragment();
                return tabTwitter;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
