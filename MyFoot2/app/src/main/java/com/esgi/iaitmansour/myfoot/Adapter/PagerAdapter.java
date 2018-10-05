package com.esgi.iaitmansour.myfoot.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.esgi.iaitmansour.myfoot.Fragments.ResultatCFragment;
import com.esgi.iaitmansour.myfoot.Fragments.ResultatRFragment;

/**
 * Created by iaitmansour on 29/06/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    ResultatRFragment tabResultat;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if(tabResultat ==null)
                    tabResultat = new ResultatRFragment();
                return tabResultat;
            case 1:
                ResultatCFragment tabClassement = new ResultatCFragment();
                return tabClassement;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}