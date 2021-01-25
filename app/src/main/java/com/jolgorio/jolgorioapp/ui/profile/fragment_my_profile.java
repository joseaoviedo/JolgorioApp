package com.jolgorio.jolgorioapp.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jolgorio.jolgorioapp.R;


public class fragment_my_profile extends Fragment implements View.OnClickListener {


    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        Button misTrabajos = (Button) view.findViewById(R.id.misTrabajosButton);
        misTrabajos.setOnClickListener(this);

        Button miProgreso = (Button) view.findViewById(R.id.miProgresoButton);
        miProgreso.setOnClickListener(this);

        Button editar = (Button) view.findViewById(R.id.editarPerfilButton);
        editar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.misTrabajosButton:
                navController.navigate(R.id.action_miPerfil_to_misTrabajos);
                break;
            case R.id.miProgresoButton:
                navController.navigate(R.id.action_miPerfil_to_miProgreso);
                break;
            case R.id.editarPerfilButton:
                //navController.navigate(R.id.action_mainMenuFragment_to_registerFragment);
                break;
        }
    }
}