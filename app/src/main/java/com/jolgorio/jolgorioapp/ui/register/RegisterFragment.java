package com.jolgorio.jolgorioapp.ui.register;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private AlertDialog dialog;
    private NavController navController;

    private CircleImageView RegisterIMG;
    private AppCompatButton newPhoto;

    private Button entryButton;
    private Button leavingAlertButton;
    private Button emergencyCallButton;
    private ImageButton maleButton;
    private ImageButton femaleButton;
    private boolean maleFlag;// if maleButton selected
    private boolean femaleFlag;// if femaleButton selected
    private Drawable male_true;
    private Drawable male_false;
    private Drawable female_true;
    private Drawable female_false;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 101;

    private Spinner pais;
    private Spinner provincia;
    private Spinner canton;
    private Spinner distrito;

    private EditText nameField;
    private EditText surname1Field;
    private EditText surname2Field;
    private EditText usrNameField;
    private EditText phoneNumberField;
    private EditText birthDateField;
    private EditText pwField;
    private EditText pwConfirmField;

    private Bitmap bitmap;
    final Calendar myCalendar = Calendar.getInstance(new Locale("es_ES"));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        Locale locale = new Locale("es_ES");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getApplicationContext().getResources().updateConfiguration(config, null);

        maleFlag = false;
        femaleFlag = false;

        RegisterIMG = (CircleImageView) view.findViewById(R.id.imageProfile);

        entryButton = (Button) view.findViewById(R.id.signUpEntry);
        entryButton.setEnabled(false);

        leavingAlertButton = (Button) view.findViewById(R.id.signUpCancel);

        emergencyCallButton = (Button) view.findViewById(R.id.EmergencyButton);
        emergencyCallButton.setOnClickListener(this);

        maleButton = (ImageButton) view.findViewById(R.id.MaleButton);
        femaleButton = (ImageButton) view.findViewById(R.id.FemaleButton);

        birthDateField = (EditText) view.findViewById(R.id.SignUpBirthDate);

        newPhoto = (AppCompatButton) view.findViewById((R.id.addImage));

        nameField = (EditText) view.findViewById((R.id.signUpName));
        surname1Field = (EditText) view.findViewById((R.id.signUpSurname1));
        surname2Field = (EditText) view.findViewById((R.id.signUpSurname2));
        usrNameField = (EditText) view.findViewById((R.id.SignUpUsrName));
        phoneNumberField = (EditText) view.findViewById((R.id.SignUpPhone));
        pwField = (EditText) view.findViewById((R.id.SignUpPW));
        pwConfirmField = (EditText) view.findViewById((R.id.signUpConfirmPW));

        pais = (Spinner) view.findViewById((R.id.signUpPais));
        provincia = (Spinner) view.findViewById((R.id.signUpProvincia));
        canton = (Spinner) view.findViewById((R.id.signUpCanton));
        distrito = (Spinner) view.findViewById((R.id.signUpDistrito));

        male_true = getActivity().getDrawable(R.drawable.hombre_true);
        male_false = getActivity().getDrawable(R.drawable.hombre_false);
        female_true = getActivity().getDrawable(R.drawable.mujer_true);
        female_false = getActivity().getDrawable(R.drawable.mujer_false);

        maleButton.setBackground(male_false);
        femaleButton.setBackground(female_false);

        pickBirthDate(view);
        TakePhoto(view);
        LeavingAlert(view);
        GenderSelection(view);
        Entry(view);
        allFieldsFilled();
        return view;
    }

    private void pickBirthDate(View view){

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        birthDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        allFieldsFilled();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("es"));

        birthDateField.setText(sdf.format(myCalendar.getTime()));
        allFieldsFilled();
    }

    private void TakePhoto(View view) {

        newPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                final View popupView = getLayoutInflater().inflate(R.layout.layout_upload_photo_options_popup, null);

                AppCompatButton galleryBtn = popupView.findViewById(R.id.galleryPhoto);
                AppCompatButton cameraBtn = popupView.findViewById(R.id.cameraPhoto);

                alerta.setView(popupView);
                dialog = alerta.create();
                dialog.show();

                if(galleryBtn != null) {
                    galleryBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            takePhotoFromGallery();
                            dialog.dismiss();
                            //navController.navigate(R.id.action_registerFragment_to_mainMenuFragment);
                        }
                    });
                }
                if(cameraBtn != null) {
                    cameraBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dispatchTakePictureIntent();
                            dialog.dismiss();
                            //navController.navigate(R.id.action_registerFragment_to_mainMenuFragment);
                        }
                    });
                }
            }
        });
        allFieldsFilled();
    }

    private void GenderSelection(View view) {

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleFlag = true;
                femaleFlag = false;
                GenderChange(view);
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleFlag = true;
                maleFlag = false;
                GenderChange(view);
            }
        });
        allFieldsFilled();
    }

    private void GenderChange(View view){

        if(maleFlag){

            maleButton.setBackground(male_true);
            femaleButton.setBackground(female_false);

        }
        if(femaleFlag){

            femaleButton.setBackground(female_true);
            maleButton.setBackground(male_false);
        }
        allFieldsFilled();
    }

    private void Entry(View view){
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aquí se toman salida que todos los campos estén completos
                // y se toma toda la info para la BD
                sendInfo();
                navController.navigate(R.id.action_registerFragment_to_mainMenuFragment);

            }
        });
    }

    private void sendInfo() {
        int gender;
        if (maleFlag == true) gender = 2; //2 es hombre en la BD
        if (femaleFlag == true) gender = 1; //1 es mujer en la BD
        String nombre = nameField.getText().toString();
        String ap1 = surname1Field.getText().toString();
        String ap2 = surname2Field.getText().toString();
        String birthDate = birthDateField.getText().toString();
        String phone = phoneNumberField.getText().toString();
        String ps = pais.getSelectedItem().toString();
        String pv = provincia.getSelectedItem().toString();
        String c = canton.getSelectedItem().toString();
        String d = distrito.getSelectedItem().toString();
        String pw = pwField.getText().toString();
    }

    private void LeavingAlert(View view){

        leavingAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
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
                            //navController.navigate(R.id.action_registerFragment_to_mainMenuFragment);
                            navController.popBackStack(R.id.indexLogin, false);
                            /*
                            getActivity.onBackPressed();
                             */
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.EmergencyButton) {
            EmergencyCall call = new EmergencyCall();
            call.EmergencyPopUp(this);
        }
    }

    // Take Foto
    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE);
        }
    }

    public void takePhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //Intent takePictureIntent = new Intent(Intent.ACTION_PICK);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_GALLERY);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_GALLERY);
        }
    }
    /*
        public void takeContact() {
            Log.e("holaaaaaaaaaaa","1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                Log.e("holaaaaaaaaaaa","----------------------------------");
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
            else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_IMAGE_GALLERY);
            }
        }
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            RegisterIMG.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imguri = data.getData(); //Hay que guardar la img URI
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imguri);//getActivity().getContentResolver(), imguri
            } catch (IOException e) {
                e.printStackTrace();
            }
            //RegisterIMG.setImageBitmap(bitmap);
            //RegisterIMG.setImageBitmap(bitmap);
            RegisterIMG.setImageURI(imguri);
            //RegisterIMG.setImageBitmap(bitmap);
        }
        /*
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            switch (requestCode) {
                case (PICK_CONTACT):
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            // TODO Whatever you want to do with the selected contact name.
                            Log.e("LLEGAAAAAAAAAAAAA", "--------------------------------------------" + name);
                        }
                    }
                    break;
            }

        }*/
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_CAPTURE);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_GALLERY);
        }
    }

    private void getContactList() {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }/*
        if (requestCode == PICK_CONTACT && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void allFieldsFilled(){
        if (nameField.getText() != null && surname1Field.getText() != null &&
                surname2Field.getText() != null && usrNameField.getText() != null &&
                birthDateField.getText() != null && phoneNumberField.getText() != null &&
                pais.getSelectedItem() != null && provincia.getSelectedItem() != null &&
                canton.getSelectedItem() != null && distrito.getSelectedItem() != null &&
                pwConfirmField.getText() != null && pwConfirmField.getText() != null &&
                (maleFlag != false || femaleFlag != false)) {

            entryButton.setEnabled(true);
        }
    }
}