package com.jolgorio.jolgorioapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jolgorio.jolgorioapp.R;

public class IndexActivity extends AppCompatActivity {
    private Button signInBtn;
    private Button signUpBtn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        System.out.println("Iniciado");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        signInBtn = findViewById(R.id.indexLogin);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexActivity.this, SignInActivity.class));
            }
        });

        signUpBtn = findViewById(R.id.indexRegister);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openSignUp();
            }
        });
    }





}
