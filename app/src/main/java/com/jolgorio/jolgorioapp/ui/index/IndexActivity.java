package com.jolgorio.jolgorioapp.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jolgorio.jolgorioapp.R;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {
    boolean doubleBackToExitPressedOnce;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Button loginBtn = findViewById(R.id.indexLogin);
        loginBtn.setOnClickListener(this);
        Button registerBtn = findViewById(R.id.indexRegister);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.indexLogin:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivityForResult(loginIntent, 0);
                break;
            case R.id.indexRegister:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivityForResult(registerIntent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("INDEX", "ACTIVIDAD TERMINADA CON CÓDIGO: " + resultCode);
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Ingresado correctamente", Toast.LENGTH_SHORT).show();
            setResult(1);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            setResult(0);
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Presione otra vez para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
