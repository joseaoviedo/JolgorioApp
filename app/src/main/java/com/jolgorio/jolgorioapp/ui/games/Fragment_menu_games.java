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
            case R.id.EmergencyButton:
                EmergencyPopUp();
                break;
            case R.id.alertNObutton:
                dialog.dismiss();
                break;
            case R.id.alertYESbutton:
                call();
                break;
        }
    }

    //Llamar Emergencias
    void call() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "123"));
            getActivity().startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }



    public void EmergencyPopUp(){
        Log.d("3", "Call");
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View imageDisplay = getLayoutInflater().inflate(R.layout.layout_emergencycall_popup, null);
        AppCompatButton exitBtn = imageDisplay.findViewById(R.id.alertNObutton);
        AppCompatButton calltBtn = imageDisplay.findViewById(R.id.alertYESbutton);
        if(exitBtn != null) {
            exitBtn.setOnClickListener(this);
        }

        if(calltBtn != null) {
            calltBtn.setOnClickListener(this);
        }
        alertDialogBuilder.setView(imageDisplay);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }
}