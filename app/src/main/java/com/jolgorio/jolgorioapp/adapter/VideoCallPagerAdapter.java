package com.jolgorio.jolgorioapp.adapter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.provider.CallLog;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jolgorio.jolgorioapp.ui.videocall.CallLogFragment;
import com.jolgorio.jolgorioapp.ui.videocall.ContactsFragment;
import com.jolgorio.jolgorioapp.ui.videocall.FavoriteContactsFragment;

import java.util.ArrayList;

public class VideoCallPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();
    public VideoCallPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        //Asigna el fragmento correspondiente al índice de cada tab
        fragments.add(new ContactsFragment());
        fragments.add(new FavoriteContactsFragment());
        fragments.add(new CallLogFragment());
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
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

    /*
    Se llaman para actualizar la información de los recycleview cada vez que se cambia de tab
     */
    public void refreshTab(int position){
        switch (position){
            case 0:
                ContactsFragment contactsFragment = (ContactsFragment) fragments.get(position);
                contactsFragment.refresh();
                break;
            case 1:
                FavoriteContactsFragment fContactsFragment = (FavoriteContactsFragment) fragments.get(position);
                fContactsFragment.refresh();
                break;
            case 2:
                CallLogFragment callLogFragment = (CallLogFragment) fragments.get(position);
                callLogFragment.refresh();
                break;
        }
    }

}
