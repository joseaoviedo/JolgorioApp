package com.jolgorio.jolgorioapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;

public class MainMenuFragment extends Fragment implements View.OnClickListener{

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        view.findViewById(R.id.PM_button0).setOnClickListener(this);
        view.findViewById(R.id.PM_button1).setOnClickListener(this);
        view.findViewById(R.id.PM_button2).setOnClickListener(this);
        view.findViewById(R.id.PM_button3).setOnClickListener(this);
        view.findViewById(R.id.PM_button4).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PM_button0:
                navController.navigate(R.id.action_mainMenuFragment_to_activityListActivity);
                break;
            case R.id.PM_button1:
                navController.navigate(R.id.action_mainMenuFragment_to_juegos);
                break;
            case R.id.PM_button2:
                navController.navigate(R.id.action_mainMenuFragment_to_videoCallPagerFragment);
                break;
            case R.id.PM_button3:
                navController.navigate(R.id.action_mainMenuFragment_to_miPerfil);
                break;
            case R.id.PM_button4:
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;
        }
    }
}