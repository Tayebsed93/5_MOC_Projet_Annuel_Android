package com.esgi.iaitmansour.myfoot.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.esgi.iaitmansour.myfoot.Fragments.DetailsMatchCompoFragment;
import com.esgi.iaitmansour.myfoot.Fragments.DetailsMatchScorerFragment;

/**
 * Created by iaitmansour on 04/07/2018.
 */

public class DetailsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String midMatch;

    public DetailsPagerAdapter(Context context, FragmentManager fm, String idMatch) {
        super(fm);
        mContext = context;
        midMatch = idMatch;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Fragment myfragment = DetailsMatchScorerFragment.newInstance(midMatch);
            return myfragment;
        } else {
            Fragment myfragment = DetailsMatchCompoFragment.newInstance(midMatch);
            return myfragment;
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Temps forts";
            case 1:
                return "Composition";
            default:
                return null;
        }
    }

}
