package com.jolgorio.jolgorioapp.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.adapter.CustomAdapter;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;

public class fragment_my_work extends Fragment implements View.OnClickListener {

    NavController navController;
    GridView gridView;
    int[] imagesTest = {R.drawable.test1,R.drawable.test2,R.drawable.test3};
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        View view =  inflater.inflate(R.layout.fragment_my_work, container, false);
        gridView = view.findViewById(R.id.gridViewGallery);
        CustomAdapter custonAdapter = new CustomAdapter(view.getContext(), imagesTest);
        gridView.setAdapter(custonAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               displayImage(position);

            }
        });

        Button EmergencyCall = (Button) view.findViewById(R.id.EmergencyButton);
        EmergencyCall.setOnClickListener(this);

        Button back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        return view;
    }

    public void displayImage(int position){
        Log.d("12", "Abrir Imagen");
        alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.WrapContentDialog);
        final View imageDisplay = getLayoutInflater().inflate(R.layout.fragment_image_display, null);
        AppCompatButton exitBtn = imageDisplay.findViewById(R.id.closeImageDisplay);
        if(exitBtn != null) {
            exitBtn.setOnClickListener(this);
        }
        alertDialogBuilder.setView(imageDisplay);
        dialog = alertDialogBuilder.create();
        ImageView img= (ImageView) imageDisplay.findViewById(R.id.imageDisplay);
        img.setImageResource(imagesTest[position]);
        dialog.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.closeImageDisplay:
                dialog.dismiss();
                break;
            case R.id.EmergencyButton:
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;
            case R.id.back:
                navController.popBackStack(R.id.miPerfil, false);
                break;
        }
    }
}