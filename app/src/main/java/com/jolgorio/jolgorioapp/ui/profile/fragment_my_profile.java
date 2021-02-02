package com.jolgorio.jolgorioapp.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jolgorio.jolgorioapp.R;
//import com.jolgorio.jolgorioapp.tools.SQLConnection;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class fragment_my_profile extends Fragment implements View.OnClickListener {


    NavController navController;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    //Emergencia
    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        Button misTrabajos = (Button) view.findViewById(R.id.misTrabajosButton);
        misTrabajos.setOnClickListener(this);

        Button miProgreso = (Button) view.findViewById(R.id.miProgresoButton);
        miProgreso.setOnClickListener(this);

        Button editar = (Button) view.findViewById(R.id.editarPerfilButton);
        editar.setOnClickListener(this);

        Button EmergencyCall = (Button) view.findViewById(R.id.EmergencyButton);
        EmergencyCall.setOnClickListener(this);

        Button back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);

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
                navController.navigate(R.id.action_miPerfil_to_EditarPerfil);
                break;
            case R.id.EmergencyButton:
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;
            case R.id.back:
                navController.popBackStack(R.id.mainMenuFragment, false);
                break;
        }
    }
}