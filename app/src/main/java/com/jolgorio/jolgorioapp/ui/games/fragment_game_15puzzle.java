package com.jolgorio.jolgorioapp.ui.games;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
    private TextView textTime;
    private Timer timer;
    private int timeCount = 0;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_15puzzle, container, false);

        loadViews();
        loadNumbers();
        generateNumbers();
        loadDataToViews();

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
        textTime = view.findViewById(R.id.time);
        buttons = new Button[4][4];

        for(int i = 0; i< group.getChildCount(); i++){
            buttons[i/4][i%4] = (Button) group.getChildAt(i);
            buttons[i/4][i%4].setOnClickListener(this);
        }
    }

    private void loadTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeCount++;
                        setTime(timeCount);
                    }
                });
            }
        },1000,1000);
    }

    private void setTime(int timeCount){
        int second = timeCount % 60;
        int hour = timeCount / 3600;
        int minute = (timeCount - hour*3600)/60;
        textTime.setText(String.format("Time: %02d:%02d",minute,second));
    }

    @Override
    public void onClick(View v) {
        buttonClick(v);
    }

    public void buttonClick(View v){
        Button button = (Button) v;
        if(stepCount == 0){
            loadTimer();
        }
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
            Toast.makeText(view.getContext(), "Gano!", Toast.LENGTH_SHORT).show();
            for(int i = 0; i < group.getChildCount(); i++){
                buttons[i/4][i%4].setClickable(false);
            }
            timer.cancel();
        }

    }

}