package com.jolgorio.jolgorioapp.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.tools.Security;
import com.jolgorio.jolgorioapp.ui.login.LoginActivity;

public class IndexActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState){
        System.out.println("==================================================");
        System.out.println(Security.getHash("Jolgorio"));
        System.out.println(Security.getHash("Jolgoria"));
        System.out.println(Security.getHash("Jolgorio"));
        System.out.println(Security.getHash("Jolgoria"));
        System.out.println("==================================================");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        final Button loginBtn = findViewById(R.id.indexLogin);
        final Button registerBtn = findViewById(R.id.indexRegister);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexActivity.this, LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openSignUp();
            }
        });
    }





}
