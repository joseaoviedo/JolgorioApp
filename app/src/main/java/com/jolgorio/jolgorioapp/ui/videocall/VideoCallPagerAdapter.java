package com.jolgorio.jolgorioapp.ui.videocall;

import android.provider.CallLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Contactos";
            case 1:
                return "Favoritos";
            case 2:
                return "Registro";
            default:
                return "None";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
