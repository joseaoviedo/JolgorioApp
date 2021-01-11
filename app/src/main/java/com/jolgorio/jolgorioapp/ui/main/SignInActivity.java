package com.jolgorio.jolgorioapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jolgorio.jolgorioapp.R;

public class SignInActivity extends AppCompatActivity {
    private Button cancelBtn;
    private Button enterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        cancelBtn = findViewById(R.id.signInCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, IndexActivity.class));
            }
        });
    }
}
