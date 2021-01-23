package com.jolgorio.jolgorioapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.jolgorio.jolgorioapp.R;

public class ActivityLeavingRegisterPopUp extends AppCompatActivity {
    private Button leave;
    private Button stay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        leaveButton();

    }

    private void leaveButton(){
        leave = (Button) findViewById(R.id.alertYESbutton);
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

}
