package com.jolgorio.jolgorioapp.ui.progress;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.adapter.MyProgressAdapter;
import com.jolgorio.jolgorioapp.adapter.VideoCallPagerAdapter;

public class fragment_my_progress extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_progress, container, false);
        TabLayout tabLayout = view.findViewById(R.id.my_progress_tab_layout);
        ViewPager viewPager = view.findViewById(R.id.my_progress_viewpager);
        MyProgressAdapter adapter = new MyProgressAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager, false);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                adapter.refreshTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                adapter.refreshTab(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                adapter.refreshTab(tab.getPosition());
            }
        });
        return view;
    }
}