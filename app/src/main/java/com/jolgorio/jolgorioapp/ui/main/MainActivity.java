package com.jolgorio.jolgorioapp.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.telecom.InCallService;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.repositories.ActivityRepository;
import com.jolgorio.jolgorioapp.repositories.ContactRepository;
import com.jolgorio.jolgorioapp.repositories.LogedInUserRepository;
import com.jolgorio.jolgorioapp.tools.PreferenceUtils;
import com.jolgorio.jolgorioapp.tools.SQLConnection;
import com.jolgorio.jolgorioapp.ui.login.IndexActivity;

import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    boolean isCalled = false;
    boolean doubleBackToExitPressedOnce;
    static AlertDialog.Builder dialogBuilder;
    static AlertDialog dialog;
    static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("user");
    final int REQUEST_CAMERA = 1;
    final int REQUEST_AUDIO_MODIFY = 2;
    static final int REQUEST_RECORD_AUDIO = 3;

    @Override
    public void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                REQUEST_AUDIO_MODIFY);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                REQUEST_RECORD_AUDIO);
        SharedPreferences preferences = getSharedPreferences("default", MODE_PRIVATE);
        PreferenceUtils pUtils = PreferenceUtils.getInstance();
        pUtils.setMainContext(this);
        if(!pUtils.isUserLogedIn()){
            Intent intent = new Intent(this, IndexActivity.class);
            startActivityForResult(intent, 0);
        }
        try {
            ActivityRepository.getInstance().loadAllActivities();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listenToCalls();
        dialogBuilder = new AlertDialog.Builder(this);
        LogedInUserRepository.getInstance();
        Locale locale = new Locale("es_ES");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
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

        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    /*
    * Si es llamado antes de que un usuario inicie sesión, la aplicación no recibirá llamadas
    * en lo que reste de su ejecución, por lo tanto debe realizarse una verificación de usuario
    * previo a llamar esta función
     */

    private void listenToCalls(){
        JolgorioUser logedInUser = LogedInUserRepository.getInstance().getLogedInUser();
        if(logedInUser == null){return;}

        mDatabase.child(logedInUser.getNumber()).child("incoming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    Log.d("MAIN", "LLAMADA ENTRANTE DE: " + snapshot.getValue().toString());
                    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController navController = navHostFragment.getNavController();
                    onCallRequest(snapshot.getValue().toString(), navController);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onCallRequest(String caller, NavController controller){
        if(caller == null) {
            return;
        }

        View view = View.inflate(this, R.layout.layout_incoming_call_popup, null);
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        CircleImageView callImage = view.findViewById(R.id.incoming_call_photo);
        String displayName;
        JolgorioUser contact = ContactRepository.getInstance().getContactFromPhone(caller);
        if(contact == null){
            callImage.setBackgroundResource(R.drawable.icon_default_user);
            displayName = caller;
        }else{
            Glide.with(this)
                    .asBitmap()
                    .load(contact.getPhotoURL())
                    .into(callImage);

            displayName = contact.getName();
        }

        TextView callingText = view.findViewById(R.id.incoming_call_text);
        callingText.setText(displayName + " te está llamando");
        JolgorioUser user = LogedInUserRepository.getInstance().getLogedInUser();
        AppCompatButton declineButton = view.findViewById(R.id.incoming_call_decline);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(user.getNumber()).child("isAvailable").setValue(false);
                dialog.dismiss();
            }
        });

        AppCompatButton acceptButton = view.findViewById(R.id.incoming_call_answer);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mDatabase.child(user.getNumber()).child("connId").setValue(LogedInUserRepository.getInstance().getUserUniqueId());
                mDatabase.child(user.getNumber()).child("isAvailable").setValue(true);
                controller.navigate(R.id.videoCallFragment);
            }
        });
        dialog.show();

        mDatabase.child(user.getNumber()).child("incoming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    /*public void EmergencyCall(){

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0377778888"));

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);

    }*/

}
