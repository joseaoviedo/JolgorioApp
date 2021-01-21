package com.jolgorio.jolgorioapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.ui.videocall.Fragment_Contacts;
import com.jolgorio.jolgorioapp.ui.videocall.Fragment_Favorites;
import com.jolgorio.jolgorioapp.ui.videocall.Fragment_Registro;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_contacts, R.string.tab_favorites, R.string.tab_registro};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case(0):
                fragment = new Fragment_Contacts();
                break;
            case(1):
                fragment = new Fragment_Favorites();
                break;
            case(2):
                fragment = new Fragment_Registro();
                break;
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}
