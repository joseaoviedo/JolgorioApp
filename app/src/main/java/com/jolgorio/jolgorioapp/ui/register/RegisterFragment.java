package com.jolgorio.jolgorioapp.ui.register;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment {
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
    private EditText usrNameField;
    private EditText phoneNumberField;
    private EditText birthDateField;
    private EditText pwField;
    private EditText pwConfirmField;

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

        maleButton = (ImageButton) view.findViewById(R.id.MaleButton);
        femaleButton = (ImageButton) view.findViewById(R.id.FemaleButton);

        birthDateField = (EditText) view.findViewById(R.id.SignUpBirthDate);

        newPhoto = (AppCompatButton) view.findViewById((R.id.addImage));

        nameField = (EditText) view.findViewById((R.id.signUpFullName));
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
        EmergencyCallAlert(view);
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
                navController.navigate(R.id.action_registerFragment_to_mainMenuFragment);

            }
        });
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

    private void EmergencyCallAlert(View view){

        emergencyCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                final View popupView = getLayoutInflater().inflate(R.layout.layout_emergencycall_popup, null);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            RegisterIMG.setImageBitmap(imageBitmap);
        }
        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imguri = data.getData();
            RegisterIMG.setImageURI(imguri);
        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_CAPTURE);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_GALLERY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void allFieldsFilled(){
        if (nameField.getText() != null && usrNameField.getText() != null &&
        birthDateField.getText() != null && phoneNumberField.getText() != null &&
        pais.getSelectedItem() != null && provincia.getSelectedItem() != null &&
        canton.getSelectedItem() != null && distrito.getSelectedItem() != null &&
        pwConfirmField.getText() != null && pwConfirmField.getText() != null) {
            entryButton.setEnabled(true);
        }

    }
}
