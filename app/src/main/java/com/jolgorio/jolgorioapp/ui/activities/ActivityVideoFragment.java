package com.jolgorio.jolgorioapp.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;
import com.jolgorio.jolgorioapp.repositories.ActivityRepository;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ActivityVideoFragment extends Fragment implements View.OnClickListener{
    private JolgorioActivity activity;
    private NavController navController;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private boolean finished;

    View view;

    YouTubePlayerView youtubePlayerView;

    YouTubePlayer youTubePlayer2;

    String rutaImagen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        activity = args.getParcelable("activity");
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity_video, container, false);
        fillInfo(view);
        Button EmergencyCall = (Button) view.findViewById(R.id.EmergencyButton);
        EmergencyCall.setOnClickListener(this);

        Button back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                salirPopUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }

    public void fillInfo(View view){
        Drawable backgroundColor = getActivity().getDrawable(R.color.white);

        View banner = view.findViewById(R.id.activity_video_banner);
        banner.setBackground(backgroundColor);

        TextView title = view.findViewById(R.id.f_video_activity_title);
        title.setText(activity.getTitle());

        youtubePlayerView = view.findViewById(R.id.video_player);

        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer2 = youTubePlayer;
                String videoURL = activity.getVideoLink();
                if(videoURL.contains("watch?v=")){
                    String[] split = videoURL.split("=");
                    String videoId = split[1];
                    youTubePlayer.loadVideo(videoId, 0);

                }
            }

            @Override
            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);
                if(state.toString().equals("ENDED")){
                    finished = true;
                    activityFinished();
                }
            }
        });

    }

    private void activityFinished(){
        Log.d("AAAAAAAAAAAAAAAAAAA", "ACTIVIDAD FINALIZADA");
        ActivityRepository.getInstance().onActivityComplete(activity);
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View finishedActivityView = getLayoutInflater().inflate(R.layout.layout_activity_completed_popup, null);
        AppCompatButton photoBtn = finishedActivityView.findViewById(R.id.activity_completed_photo_btn);
        AppCompatButton close = finishedActivityView.findViewById(R.id.close);
        if(photoBtn != null) {
            photoBtn.setOnClickListener(this);
        }

        if(close != null) {
            photoBtn.setOnClickListener(this);
        }
        alertDialogBuilder.setView(finishedActivityView);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.EmergencyButton:
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;
            case R.id.back:
                salirPopUp();
                break;
            case R.id.siSalir:
                //Regresar a la
                dialog.dismiss();
                navController.popBackStack(R.id.activityListActivity, false);
                break;
            case R.id.noSalir:
                youTubePlayer2.play();
                dialog.dismiss();
                break;
            case R.id.activity_completed_photo_btn:
                System.out.println("Tomar foto perro");
                abrirCamara();
                dialog.dismiss();
                break;
            case R.id.salir:
            case R.id.close:
                navController.popBackStack(R.id.activityListActivity, false);
                dialog.dismiss();
                break;

        }
    }

    //Take photo of the activity

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if(intent.resolveActivity(getActivity().getPackageManager())!= null) {

            File imagenArchivo = null;
            try{
                imagenArchivo = crearImage();
            }catch (IOException e){
                Log.e("Error", e.toString());
            }

            if(imagenArchivo != null){
                Uri fotoUri = FileProvider.getUriForFile(getActivity(), "com.jolgorio.jolgorioapp.fileprovider", imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intent, 1);
            }
        //}
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            //Bundle extras = data.getExtras();
            Bitmap imageBitmap = BitmapFactory.decodeFile(rutaImagen);
            photoTaken();
        }
    }

    private File crearImage() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen,".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }

    public void photoTaken(){
        Log.d("12", "Abrir Imagen");
        alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.WrapContentDialog);
        final View imageDisplay = getLayoutInflater().inflate(R.layout.layout_photo_taken_popup, null);
        AppCompatButton salir = imageDisplay.findViewById(R.id.salir);
        if(salir != null) {
            salir.setOnClickListener(this);
        }
        alertDialogBuilder.setView(imageDisplay);
        dialog = alertDialogBuilder.create();
        dialog.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
    }


    public void salirPopUp(){
        Log.d("5", "Salir Actividad");
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View imageDisplay = getLayoutInflater().inflate(R.layout.layout_finish_activity_popup, null);
        AppCompatButton noSalir = imageDisplay.findViewById(R.id.noSalir);
        AppCompatButton siSalir = imageDisplay.findViewById(R.id.siSalir);
        if(noSalir != null) {
            noSalir.setOnClickListener((View.OnClickListener) this);
        }

        if(siSalir != null) {
            siSalir.setOnClickListener((View.OnClickListener) this);
        }
        alertDialogBuilder.setView(imageDisplay);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

}