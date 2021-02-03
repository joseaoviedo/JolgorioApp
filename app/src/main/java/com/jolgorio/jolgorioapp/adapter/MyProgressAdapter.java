package com.jolgorio.jolgorioapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jolgorio.jolgorioapp.ui.progress.ArtisticaFragment;
import com.jolgorio.jolgorioapp.ui.progress.CulturalFragment;
import com.jolgorio.jolgorioapp.ui.progress.DeportivoFragment;

import java.util.ArrayList;

public class MyProgressAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    public MyProgressAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments.add(new ArtisticaFragment());
        fragments.add(new DeportivoFragment());
        fragments.add(new CulturalFragment());
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
                return "Artistica";
            case 1:
                return "Deportivo";
            case 2:
                return "Cultural";
            default:
                return "None";
        }
    }


    public void refreshTab(int position){
        switch (position){
            case 0:
                ArtisticaFragment artisticaFragment = (ArtisticaFragment) fragments.get(position);
                artisticaFragment.refresh();
                break;
            case 1:
                DeportivoFragment deportivoFragment = (DeportivoFragment) fragments.get(position);
                deportivoFragment.refresh();
                break;
            case 2:
                CulturalFragment culturalFragment = (CulturalFragment) fragments.get(position);
                culturalFragment.refresh();
                break;
        }
    }
}
