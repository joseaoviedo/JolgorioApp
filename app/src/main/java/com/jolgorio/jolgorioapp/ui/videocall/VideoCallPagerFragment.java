package com.jolgorio.jolgorioapp.ui.videocall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.adapter.VideoCallPagerAdapter;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;


public class VideoCallPagerFragment extends Fragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videocall_pager, container, false);
        TabLayout tabLayout = view.findViewById(R.id.videocall_tab_layout);
        ViewPager viewPager = view.findViewById(R.id.videocall_viewpager);
        VideoCallPagerAdapter adapter = new VideoCallPagerAdapter(getChildFragmentManager());
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

        Button EmergencyCall = (Button) view.findViewById(R.id.EmergencyButton);
        EmergencyCall.setOnClickListener(this);

        Button back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.EmergencyButton:
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;
            case R.id.back:
                //volver
                break;
        }
    }
}