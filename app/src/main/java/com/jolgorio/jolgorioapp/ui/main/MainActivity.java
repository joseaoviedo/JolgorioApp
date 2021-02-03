package com.jolgorio.jolgorioapp.ui.main;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telecom.InCallService;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
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
import com.jolgorio.jolgorioapp.ui.index.IndexActivity;
import com.jolgorio.jolgorioapp.ui.videocall.VideoCallFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PHONE_CALL = 4;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 5;
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
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                REQUEST_PHONE_CALL);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_EXTERNAL_STORAGE);

        //getContacts();

        SharedPreferences preferences = getSharedPreferences("default", MODE_PRIVATE);
        PreferenceUtils pUtils = PreferenceUtils.getInstance();
        //ES NECESARIO HACER ESTE PASO
        pUtils.setMainContext(this);
        if(!pUtils.isUserLogedIn()){
            Intent intent = new Intent(this, IndexActivity.class);
            startActivityForResult(intent, 0);
        }
        try {
            ActivityRepository.getInstance().loadAllActivities();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
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

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof MainMenuFragment) {
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
            super.onBackPressed();
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

                    onCallRequest(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onCallRequest(String caller){
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
                Intent videoCallIntent = new Intent(MainActivity.this, VideoCallFragment.class);
                startActivity(videoCallIntent);
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

    public List<String> getContacts() {
        List<String> numbers = new ArrayList<String>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {
                        numbers.add(cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }

                    cursorInfo.close();
                }
            }/*
            for (int i=0; i<numbers.size();i++){
                System.out.println("Número: "+numbers.get(i));
            }*/
            cursor.close();
        }
        return numbers;
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
