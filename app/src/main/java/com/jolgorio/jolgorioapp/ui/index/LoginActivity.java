package com.jolgorio.jolgorioapp.ui.index;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.repositories.LogedInUserRepository;
import com.jolgorio.jolgorioapp.tools.PreferenceUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    EditText email;
    EditText password;
    FirebaseAuth mAuth;
    boolean isLoggedIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button enterBtn = findViewById(R.id.loginContinue);
        mAuth = FirebaseAuth.getInstance();
        enterBtn.setOnClickListener(this);
        progressBar = findViewById(R.id.loginLoading);
        email = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginContinue:
                logIn();
                break;

            case R.id.loginCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    public void logInSuccessful(){
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void logIn(){
        String inputEmail = email.getText().toString().trim();
        String inputPassword = password.getText().toString().trim();
        if(inputEmail.isEmpty()){
            Toast.makeText(this, "Debe ingresar una dirección de correo electrónico", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            Toast.makeText(this, "La dirección de correo electrónico es inválida", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }

        if(inputPassword.isEmpty()){
            Toast.makeText(this, "Debe ingresar su contraseña", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    PreferenceUtils.getInstance().setLoggedInUserMail(inputEmail);
                    logInSuccessful();
                    LogedInUserRepository.getInstance().loadLoggedUserWithMail(inputEmail);
                }else{
                    Toast.makeText(LoginActivity.this, "Ha fallado el ingreso, revise sus credenciales", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}