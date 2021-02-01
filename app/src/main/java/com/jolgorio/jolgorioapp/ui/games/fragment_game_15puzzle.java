package com.jolgorio.jolgorioapp.ui.games;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class fragment_game_15puzzle extends Fragment implements View.OnClickListener {

    private int emptyX=3;
    private int emptyY=3;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;
    private TextView textSteps;
    private int stepCount = 0;
    private Button reiniciar;
    private Button emergencia;
    private Button back;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    NavController navController;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_15puzzle, container, false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        loadViews();
        loadNumbers();
        generateNumbers();
        loadDataToViews();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                salirPopUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }

    private void loadDataToViews() {
        emptyX=3;
        emptyY=3;
        for(int i = 0; i< group.getChildCount() - 1; i++){
            buttons[i/4][i%4].setText(String.valueOf(tiles[i]));
            buttons[i/4][i%4].setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.cultural));
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorFreeButton));

    }

    private void generateNumbers() {
        int n=15;
        Random random = new Random();
        while(n>1){

            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum]=tiles[n];
            tiles[n] = temp;
        }

        if(!isSolvable())
            generateNumbers();
    }

    private boolean isSolvable(){
        int countInversions = 0;
        for(int i = 0; i< 15; i++){
            for(int j = 0; j < i; j++){
                if(tiles[j]>tiles[i]){
                    countInversions++;
                }
            }
        }
        return countInversions%2 == 0;
    }

    private void loadNumbers(){
        tiles = new int[16];
        for(int i = 0; i< group.getChildCount() -1; i++ ){
            tiles[i] = i +1 ;
        }
    }

    private void loadViews() {
        group = view.findViewById(R.id.group);
        textSteps = view.findViewById(R.id.steps);
        reiniciar = view.findViewById(R.id.revolver);
        emergencia = view.findViewById(R.id.EmergencyButton);
        back = view.findViewById(R.id.back);

        buttons = new Button[4][4];

        for(int i = 0; i< group.getChildCount(); i++){
            buttons[i/4][i%4] = (Button) group.getChildAt(i);
            buttons[i/4][i%4].setOnClickListener(this);
        }

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNumbers();
                loadDataToViews();
                stepCount = 0;
                textSteps.setText("Pasos: "+stepCount);
            }
        });

        back.setOnClickListener((View.OnClickListener) this);
        emergencia.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.EmergencyButton) {
            EmergencyCall call = new EmergencyCall();
            call.EmergencyPopUp(this);

        }else if (v.getId() == R.id.jugarotravez) {
            dialog.dismiss();
            generateNumbers();
            loadDataToViews();
            stepCount = 0;
            textSteps.setText("Pasos: "+stepCount);
        }else if (v.getId() == R.id.otrojuego || v.getId() == R.id.siSalir) {
            //Regresar al menu de juegos
            dialog.dismiss();
            navController.popBackStack(R.id.juegos, false);

        }else if (v.getId() == R.id.salir) {
            //Regresar al menu principal
            dialog.dismiss();
            getActivity().onBackPressed();
            getActivity().onBackPressed();

        }else if (v.getId() == R.id.back) {
            salirPopUp();

        }else if(v.getId() == R.id.noSalir) {
            dialog.dismiss();
        }else {
            buttonClick(v);
        }
    }
    public void buttonClick(View v){
        Button button = (Button) v;

        int x = button.getTag().toString().charAt(0) - '0';
        int y = button.getTag().toString().charAt(1) - '0';

        if((Math.abs(emptyX-x) == 1 && emptyY == y)|| (Math.abs(emptyY - y) == 1 && emptyX == x)){
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.cultural));
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorFreeButton));
            emptyX = x;
            emptyY = y;
            stepCount++;
            textSteps.setText("Pasos: "+stepCount);
            checkWin();
        }

    }

    private void checkWin() {
        boolean isWin = false;
        if(emptyX == 3 && emptyY == 3){
            for(int i = 0; i < group.getChildCount() - 1; i++ ){
                if(buttons[i/4][i%4].getText().toString().equals(String.valueOf(i+1))){
                    isWin = true;
                }else{
                    isWin = false;
                    break;
                }
            }

        }
        if(isWin){
            finish();
            for(int i = 0; i < group.getChildCount(); i++){
                buttons[i/4][i%4].setClickable(false);
            }
            reiniciar.setClickable(false);
        }

    }

    public void finish(){
        Log.d("3", "Juego Terminado");
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View imageDisplay = getLayoutInflater().inflate(R.layout.layout_game_win_popup, null);
        AppCompatButton jugarotravez = imageDisplay.findViewById(R.id.jugarotravez);
        AppCompatButton otrojuego = imageDisplay.findViewById(R.id.otrojuego);
        AppCompatButton salir = imageDisplay.findViewById(R.id.salir);
        if(jugarotravez != null) {
            jugarotravez.setOnClickListener((View.OnClickListener) this);
        }

        if(otrojuego != null) {
            otrojuego.setOnClickListener((View.OnClickListener) this);
        }

        if(salir != null) {
            salir.setOnClickListener((View.OnClickListener) this);
        }
        alertDialogBuilder.setView(imageDisplay);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }
    public void salirPopUp(){
        Log.d("5", "Salir del juego");
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View imageDisplay = getLayoutInflater().inflate(R.layout.layout_exit_game_popup, null);
        AppCompatButton noSalir = imageDisplay.findViewById(R.id.noSalir);
        AppCompatButton siSalir = imageDisplay.findViewById(R.id.siSalir);
        if(noSalir != null) {
            noSalir.setOnClickListener((View.OnClickListener) this);
        }

        if(siSalir != null) {
            siSalir.setOnClickListener((View.OnClickListener) this);
        }
        alertDialogBuilder.setView(imageDisplay);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }
}