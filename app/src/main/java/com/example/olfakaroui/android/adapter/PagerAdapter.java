package com.example.olfakaroui.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.olfakaroui.android.UI.posts.AllFragment;
import com.example.olfakaroui.android.UI.posts.NewsFragment;
import com.example.olfakaroui.android.UI.posts.TrendingFragment;
import com.example.olfakaroui.android.entity.Cause;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Cause cause = new Cause();

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TrendingFragment tab1 = new TrendingFragment();
                tab1.setCause(cause);
                return tab1;
            case 1:
                AllFragment tab2 = new AllFragment();
                tab2.setCause(cause);
                return tab2;
            case 2:
                NewsFragment tab3 = new NewsFragment();
                tab3.setCause(cause);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }
}
