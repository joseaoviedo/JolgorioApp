package com.jolgorio.jolgorioapp.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.ui.profile.fragment_my_profile;


public class EmergencyCall extends Fragment implements View.OnClickListener {

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    Fragment frag;

    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;

    void call() {
        if (ContextCompat.checkSelfPermission(frag.getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "123"));
            frag.getActivity().startActivity(callIntent);
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



    public void EmergencyPopUp(Fragment fragment){
        Log.d("3", "Call");
        frag = fragment;
        alertDialogBuilder = new AlertDialog.Builder(frag.getActivity());
        final View imageDisplay = frag.getLayoutInflater().inflate(R.layout.layout_emergencycall_popup, null);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alertNObutton:
                dialog.dismiss();
                break;
            case R.id.alertYESbutton:
                call();
                break;
        }
    }
}
