package com.jolgorio.jolgorioapp.ui.index;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jolgorio.jolgorioapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
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

    final Calendar myCalendar = Calendar.getInstance(new Locale("es_ES"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        Locale locale = new Locale("es_ES");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getApplicationContext().getResources().updateConfiguration(config, null);

        maleFlag = false;
        femaleFlag = false;

        RegisterIMG = (CircleImageView) findViewById(R.id.imageProfile);

        entryButton = (Button) findViewById(R.id.signUpEntry);
        entryButton.setEnabled(false);

        leavingAlertButton = (Button) findViewById(R.id.signUpCancel);

        emergencyCallButton = (Button) findViewById(R.id.EmergencyButton);
        emergencyCallButton.setOnClickListener(this);

        maleButton = (ImageButton) findViewById(R.id.MaleButton);
        femaleButton = (ImageButton) findViewById(R.id.FemaleButton);

        birthDateField = (EditText) findViewById(R.id.SignUpBirthDate);

        newPhoto = (AppCompatButton) findViewById((R.id.addImage));

        nameField = (EditText) findViewById((R.id.signUpName));
        surname1Field = (EditText) findViewById((R.id.signUpSurname1));
        surname2Field = (EditText) findViewById((R.id.signUpSurname2));
        usrNameField = (EditText) findViewById((R.id.SignUpUsrName));
        phoneNumberField = (EditText) findViewById((R.id.SignUpPhone));
        pwField = (EditText) findViewById((R.id.SignUpPW));
        pwConfirmField = (EditText) findViewById((R.id.signUpConfirmPW));

        pais = (Spinner) findViewById((R.id.signUpPais));
        provincia = (Spinner) findViewById((R.id.signUpProvincia));
        canton = (Spinner) findViewById((R.id.signUpCanton));
        distrito = (Spinner) findViewById((R.id.signUpDistrito));

        male_true = getDrawable(R.drawable.hombre_true);
        male_false = getDrawable(R.drawable.hombre_false);
        female_true = getDrawable(R.drawable.mujer_true);
        female_false = getDrawable(R.drawable.mujer_false);

        maleButton.setBackground(male_false);
        femaleButton.setBackground(female_false);

        pickBirthDate();
        TakePhoto();
        LeavingAlert();
        GenderSelection();
        Entry();
        allFieldsFilled();
    }

    private void pickBirthDate(){

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
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
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

    private void TakePhoto() {

        newPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(RegisterActivity.this);
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

    private void GenderSelection() {

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleFlag = true;
                femaleFlag = false;
                GenderChange();
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleFlag = true;
                maleFlag = false;
                GenderChange();
            }
        });
        allFieldsFilled();
    }

    private void GenderChange(){

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

    private void Entry(){
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aquí se toman salida que todos los campos estén completos
                // y se toma toda la info para la BD
                navController.navigate(R.id.action_registerFragment_to_mainMenuFragment);

            }
        });
    }

    private void LeavingAlert(){

        leavingAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(RegisterActivity.this);
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
                            dialog.dismiss();
                            navController.navigate(R.id.action_registerFragment_to_mainMenuFragment);
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
    /*
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.EmergencyButton) {
            EmergencyCall call = new EmergencyCall();
            call.EmergencyPopUp(RegisterActivity.this);
        }
    }

     */

    // Take Foto
    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        } else {
        ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.CAMERA},
                REQUEST_IMAGE_CAPTURE);
        }
    }

    public void takePhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //Intent takePictureIntent = new Intent(Intent.ACTION_PICK);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_GALLERY);
        } else {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_GALLERY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            RegisterIMG.setImageBitmap(imageBitmap);
        }
        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imguri = data.getData();
            RegisterIMG.setImageURI(imguri);
        }else{
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_CAPTURE);
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_GALLERY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(RegisterActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}