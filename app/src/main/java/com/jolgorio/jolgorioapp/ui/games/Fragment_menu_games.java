package com.jolgorio.jolgorioapp.ui.games;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;

public class Fragment_menu_games extends Fragment implements View.OnClickListener {

    NavController navController;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        View view = inflater.inflate(R.layout.fragment_menu_games, container, false);

        Button memoria = (Button) view.findViewById(R.id.memorygame);
        memoria.setOnClickListener(this);

        Button puzzle = (Button) view.findViewById(R.id.puzzle);
        puzzle.setOnClickListener(this);

        Button EmergencyCall = (Button) view.findViewById(R.id.EmergencyButton);
        EmergencyCall.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.memorygame:
                navController.navigate(R.id.action_juegos_to_Memoria);
                break;
            case R.id.puzzle:
                navController.navigate(R.id.action_juegos_to_Puzzle);
                break;
            case R.id.EmergencyButton:
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;

        }
    }

}