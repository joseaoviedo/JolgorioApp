package com.jolgorio.jolgorioapp.ui.videocall;

import android.provider.CallLog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VideoCallPagerAdapter extends FragmentPagerAdapter {

    public VideoCallPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ContactsFragment contactsFragment = new ContactsFragment();
                return contactsFragment;
            case 1:
                FavoriteContactsFragment favoriteContactsFragment = new FavoriteContactsFragment();
                return favoriteContactsFragment;
            case 2:
                CallLogFragment callLogFragment = new CallLogFragment();
                return callLogFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
