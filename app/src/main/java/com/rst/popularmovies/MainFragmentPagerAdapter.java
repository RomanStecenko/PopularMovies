package com.rst.popularmovies;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES_COUNT = MovieFragment.MoviesTypes.values().length;

    private Context context;

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieFragment.newInstance(MovieFragment.MoviesTypes.values()[position]);
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(MovieFragment.MoviesTypes.values()[position].getNameId());
    }
}
