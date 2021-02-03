package com.jolgorio.jolgorioapp.ui.index;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.tools.RestAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import jnr.a64asm.Register;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private AlertDialog dialog;
    private NavController navController;

    private CircleImageView RegisterIMG;
    private AppCompatButton newPhoto;

    private Button entryButton;
    private Button leavingAlertButton;
    private Button emergencyCallButton;
    private Button backButton;
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
    private EditText emailField;
    private EditText phoneNumberField;
    private EditText birthDateField;
    private EditText pwField;
    private EditText pwConfirmField;

    private String imageURL = "";

    private Bitmap bitmap;
    final Calendar myCalendar = Calendar.getInstance(new Locale("es_ES"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        emailField = (EditText) findViewById((R.id.SignUpEmail));
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

        backButton = (Button) findViewById(R.id.back);

        BackAlert();
        pickBirthDate();
        verifyPhone();
        verifyEmail();
        TakePhoto();
        LeavingAlert();
        GenderSelection();
        Entry();
        allFieldsFilled();
        loadPaises();
        spinnerSelect();
    }

    private void verifyPhone(){
        phoneNumberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 8) {
                    ProgressBar loading = findViewById(R.id.registerLoading);
                    loading.setVisibility(View.VISIBLE);
                    JSONObject obj = null;
                    try {
                        obj = new RestAPI.GetQuerySingle().execute(com.jolgorio.jolgorioapp.tools.Configuration.restApiUrl
                                + "usuario/numero/" + phoneNumberField.getText() + "/").get();
                    } catch (Exception e) {
                        Log.d("REGISTER", "Número de teléfono no encontrado en la base");
                    }
                    if (obj != null) {
                        if(!obj.has("error")) {
                            phoneNumberField.setText(null);
                            Toast.makeText(RegisterActivity.this, "El número de teléfono ya está en uso", Toast.LENGTH_LONG).show();
                        }
                    }
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    private void verifyEmail(){
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    ProgressBar loading = findViewById(R.id.registerLoading);
                    loading.setVisibility(View.VISIBLE);
                    JSONObject obj = null;
                    try {
                        obj = new RestAPI.GetQuerySingle().execute(com.jolgorio.jolgorioapp.tools.Configuration.restApiUrl
                                + "usuario/correo/" + phoneNumberField.getText() + "/").get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (obj != null) {
                        if(!obj.has("error")) {
                            phoneNumberField.setText(null);
                            Toast.makeText(RegisterActivity.this, "El correo electrónico ya está en uso", Toast.LENGTH_LONG).show();
                        }
                    }
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }


    private void BackAlert() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(RegisterActivity.this);
                final View popupView = getLayoutInflater().inflate(R.layout.layout_exit_edit_profile_popup, null);
                navController.popBackStack(R.id.indexLogin, false);
            }
        });
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
        String myFormat = "dd/MM/yyyy"; //In which you need put here
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

    public void spinnerSelect(){
        loadProvincias(0);

        this.provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadCantones(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        this.canton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDistritos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void loadPaises(){
        ArrayList<String> paises = new ArrayList<>();
        paises.add("Costa Rica");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paises);
        this.pais.setAdapter(arrayAdapter);
    }

    public void loadProvincias(int pais){
        ArrayList<String> provincias = new ArrayList<>();
        try{
            JSONObject provJson = new RestAPI.GetQuerySingle().execute(
                    "https://ubicaciones.paginasweb.cr/provincias.json").get();
            for(int i = 1; i <= provJson.length(); i++){
                provincias.add((String) provJson.get("" + i));
                Log.d("PROVINCIA:", provincias.get(i-1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provincias);
        this.provincia.setAdapter(arrayAdapter);
    }

    public void loadCantones(int provincia){
        ArrayList<String> cantones = new ArrayList<>();
        try{
            JSONObject provJson = new RestAPI.GetQuerySingle().execute(
                    "https://ubicaciones.paginasweb.cr/provincia/" + (provincia + 1) +"/cantones.json").get();
            for(int i = 1; i <= provJson.length(); i++){
                cantones.add((String) provJson.get("" + i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cantones);
        this.canton.setAdapter(arrayAdapter);
    }

    public void loadDistritos(int canton){
        ArrayList<String> distritos = new ArrayList<>();
        try{
            int provincia = this.provincia.getSelectedItemPosition();
            JSONObject provJson = new RestAPI.GetQuerySingle().execute(
                    "https://ubicaciones.paginasweb.cr/provincia/" + (provincia + 1) +
                            "/canton/" + (canton + 1 ) + "/distritos.json").get();
            for(int i = 1; i <= provJson.length(); i++){
                distritos.add((String) provJson.get("" + i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, distritos);
        this.distrito.setAdapter(arrayAdapter);
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
                registerUser();
            }
        });
    }

    private void registerUser() {

        String email = emailField.getText().toString();
        String pw = pwField.getText().toString();

        ProgressBar loading = findViewById(R.id.registerLoading);
        loading.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pw).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String uuid = UUID.randomUUID().toString();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference("/images/" + uuid + ".jpg");
                            RegisterIMG.setDrawingCacheEnabled(true);
                            RegisterIMG.buildDrawingCache();
                            Bitmap bitmap = RegisterIMG.getDrawingCache();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();
                            UploadTask uploadTask = (UploadTask) storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageURL = uri.toString();
                                            sendData(imageURL);
                                            return;
                                        }
                                    });
                                }
                            });
                            sendData("NO_IMAGE");
                        }
                    }
                }
        );
        loading.setVisibility(View.GONE);
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

    public void sendData(String photoURL){
        int gender;
        if (maleFlag == true) gender = 2; //2 es hombre en la BD
        else gender = 1; //1 es mujer en la BD
        String name = nameField.getText().toString();
        String ap1 = surname1Field.getText().toString();
        String ap2 = surname2Field.getText().toString();
        String birthDate = birthDateField.getText().toString();
        String phone = phoneNumberField.getText().toString();
        String email = emailField.getText().toString();
        String ps = pais.getSelectedItem().toString();
        String pv = provincia.getSelectedItem().toString();
        String c = canton.getSelectedItem().toString();
        String d = distrito.getSelectedItem().toString();
        String pw = pwField.getText().toString();
        try {
            String photo = imageURL;
            Log.d("XDXDXDXD", photo);
            JSONObject userDetails = new JSONObject();
            userDetails.put("urlFoto", photo);
            userDetails.put("nombre", name);
            userDetails.put("apellido1", ap1);
            userDetails.put("apellido2", ap2);
            userDetails.put("distrito", d);
            String cntn;
            if(canton.getSelectedItem().toString().equals("Central")){
                cntn = provincia.getSelectedItem().toString();
            }else{
                cntn = canton.getSelectedItem().toString();
            }
            userDetails.put("provincia", provincia.getSelectedItem().toString());
            userDetails.put("canton", cntn);
            userDetails.put("email", email);
            userDetails.put("fechanac", birthDate);
            userDetails.put("numero", phone);
            userDetails.put("sexo", gender);
            String jsonString = userDetails.toString();
            Log.d("SENDING TO REST API", jsonString);
            JSONObject registeredUser = new RestAPI.PostQuery().execute(
                    com.jolgorio.jolgorioapp.tools.Configuration.restApiUrl
                            + "usuarios/registrar/", jsonString).get();
            if(registeredUser.has("idusuario")){
                Toast.makeText(RegisterActivity.this, "Registrado " +
                        "correctamente", Toast.LENGTH_SHORT).show();
            }
            FirebaseAuth.getInstance().getCurrentUser();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this,
                    "Ha ocurrido un error en el registro",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void dispatchTakePictureIntent(){
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE);
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.length();
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            RegisterIMG.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imguri = data.getData(); //Hay que guardar la img URI
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imguri);//getActivity().getContentResolver(), imguri
            } catch (IOException e) {
                e.printStackTrace();
            }
            RegisterIMG.setImageURI(imguri);
        }
        else {
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
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
            Toast.makeText(RegisterActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

    private void allFieldsFilled(){
        if (nameField.getText() != null && surname1Field.getText() != null &&
                surname2Field.getText() != null && emailField.getText() != null &&
                birthDateField.getText() != null && phoneNumberField.getText() != null &&
                pais.getSelectedItem() != null && provincia.getSelectedItem() != null &&
                canton.getSelectedItem() != null && distrito.getSelectedItem() != null &&
                pwConfirmField.getText() != null && pwConfirmField.getText() != null &&
                (maleFlag != false || femaleFlag != false) &&
                Patterns.EMAIL_ADDRESS.matcher(emailField.getText()).matches()) {

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