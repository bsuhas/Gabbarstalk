package com.gabbarstalk.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.gabbarstalk.fragments.AgendaFragment;
import com.gabbarstalk.fragments.RecentVideosFragment;


public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Agendas", "Most Liked Video"};

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AgendaFragment.newInstance();
            case 1:
                return RecentVideosFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
