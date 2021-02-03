package com.jolgorio.jolgorioapp.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
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

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

public class fragment_my_work extends Fragment implements View.OnClickListener {

    NavController navController;
    GridView gridView;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    private ArrayList<File> fileList = new ArrayList<File>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        View view =  inflater.inflate(R.layout.fragment_my_work, container, false);

        Button EmergencyCall = (Button) view.findViewById(R.id.EmergencyButton);
        EmergencyCall.setOnClickListener(this);

        Button back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        File root = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        getFile(root);
        for (int i = 0; i < fileList.size(); i++) {
            String stringFile = fileList.get(i).getAbsolutePath();
            Log.d("1200000", stringFile);

        }

        CustomAdapter customAdapter = new CustomAdapter(view.getContext(), fileList);
        gridView = view.findViewById(R.id.gridViewGallery);
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                displayImage(position);
            }
        });

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
        Bitmap myBitmap = BitmapFactory.decodeFile(fileList.get(position).getAbsolutePath());
        img.setImageBitmap(myBitmap);
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

public ArrayList<File> getFile(File dir) {
    File[] listFile = dir.listFiles();
    if (listFile != null && listFile.length > 0) {
    for (int i = 0; i < listFile.length; i++) {
        if (listFile[i].getName().endsWith(".png")
                || listFile[i].getName().endsWith(".jpg")
                || listFile[i].getName().endsWith(".jpeg")
                || listFile[i].getName().endsWith(".gif"))
            {
                String temp = listFile[i].getPath();
                fileList.add(new File(temp));
            }
        }
    }
    return fileList;
}

}