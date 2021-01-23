package com.jolgorio.jolgorioapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jolgorio.jolgorioapp.R;

public class Activityregister extends AppCompatActivity {
    private Button leavingAlertButton;
    private AlertDialog dialog;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        LeavingAlert();

    }

    private void LeavingAlert(){
        leavingAlertButton = (Button) findViewById(R.id.signUpCancel);

        leavingAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Activityregister.this);
                final View popupView = getLayoutInflater().inflate(R.layout.fragment_leaving_register_alert, null);

                AppCompatButton exitBtn = popupView.findViewById(R.id.alertYESbutton);
                AppCompatButton cancelBtn = popupView.findViewById(R.id.alertNObutton);

                alerta.setView(popupView);
                dialog = alerta.create();
                dialog.show();

                if(exitBtn != null) {
                    exitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                if(cancelBtn != null) {
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}
