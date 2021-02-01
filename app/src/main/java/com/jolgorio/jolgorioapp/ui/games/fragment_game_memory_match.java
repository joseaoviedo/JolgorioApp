package com.jolgorio.jolgorioapp.ui.games;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;

import java.util.ArrayList;
import java.util.Collections;

public class fragment_game_memory_match extends Fragment implements View.OnClickListener {

    NavController navController;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    //Emergencia
    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;

    //los botones del juego
    ImageButton el0, el1, el2, el3, el4, el5, el6, el7,
            el8, el9, el10, el11, el12, el13, el14, el15;

    //los botones del menú
    Button reiniciar, salirJuego, emergencia;

    //las imágenes
    int imagenes[];
    //se guardan duplicadas en un array
    ImageButton [] botonera = new ImageButton[16];

    //imagen de fondo;
    int fondo;

    //para barajar
//el ArrayList que recoge el resultado de barajar
    ArrayList<Integer> arrayBarajado;

    //COMPARACIÓN
//los botones que se han pulsado y se comparan
    ImageButton primero;
    //posiciones de las imágenes a comparar en el vector de imágenes
    int numeroPrimero, numeroSegundo;

    //durante un segundo se bloquea el juego y no se puede pulsar ningún botón
    boolean bloqueo = false;

    //para controlar las pausas del juego
    final Handler handler = new Handler();

    //finalmente, el número de aciertos y la puntuación
    int aciertos=0;
    int puntuacion=0;
    TextView textoPuntuacion;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_game_memory_match, container, false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        cargarImagenes();
        botonesMenu();
        iniciar();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                salirPopUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return view;
    }

    public void cargarImagenes(){
        imagenes = new int[]{
                R.drawable.la0,
                R.drawable.la1,
                R.drawable.la2,
                R.drawable.la3,
                R.drawable.la4,
                R.drawable.la5,
                R.drawable.la6,
                R.drawable.la7,
        };
        fondo = R.drawable.fondo;
    }

    public ArrayList<Integer> barajar(int longitud) {
        ArrayList resultadoA = new ArrayList<Integer>();
        for(int i=0; i<longitud; i++)
            resultadoA.add(i % longitud/2);
        Collections.shuffle(resultadoA);
        return  resultadoA;
    }

    public void cargarBotones(){
        el0 = (ImageButton) view.findViewById(R.id.boton00);
        botonera[0] = el0;
        el1 = (ImageButton) view.findViewById(R.id.boton01);
        botonera[1] = el1;
        el2 = (ImageButton) view.findViewById(R.id.boton02);
        botonera[2] = el2;
        el3 = (ImageButton) view.findViewById(R.id.boton03);
        botonera[3] = el3;
        el4 = (ImageButton) view.findViewById(R.id.boton04);
        botonera[4] = el4;
        el5 = (ImageButton) view.findViewById(R.id.boton05);
        botonera[5] = el5;
        el6 = (ImageButton) view.findViewById(R.id.boton06);
        botonera[6] = el6;
        el7 = (ImageButton) view.findViewById(R.id.boton07);
        botonera[7] = el7;
        el8 = (ImageButton) view.findViewById(R.id.boton08);
        botonera[8] = el8;
        el9 = (ImageButton) view.findViewById(R.id.boton09);
        botonera[9] = el9;
        el10 = (ImageButton) view.findViewById(R.id.boton10);
        botonera[10] = el10;
        el11 = (ImageButton) view.findViewById(R.id.boton11);
        botonera[11] = el11;
        el12 = (ImageButton) view.findViewById(R.id.boton12);
        botonera[12] = el12;
        el13 = (ImageButton) view.findViewById(R.id.boton13);
        botonera[13] = el13;
        el14 = (ImageButton) view.findViewById(R.id.boton14);
        botonera[14] = el14;
        el15 = (ImageButton) view.findViewById(R.id.boton15);
        botonera[15] = el15;

        textoPuntuacion = (TextView) view.findViewById(R.id.textoPuntuacion);
        textoPuntuacion.setText("Puntuación: " + puntuacion);
    }

    public void botonesMenu(){
        reiniciar = (Button) view.findViewById(R.id.Reiniciar);
        reiniciar.setOnClickListener((View.OnClickListener) this);

        salirJuego = (Button) view.findViewById(R.id.back);
        salirJuego.setOnClickListener((View.OnClickListener) this);

        emergencia =(Button) view.findViewById(R.id.EmergencyButton);
        emergencia.setOnClickListener((View.OnClickListener) this);

    }

    //Controlador de funcion de botones
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Reiniciar:
                //Reiniciar el juego
                iniciar();
                break;
            case R.id.back:
                //Regresar al menu de juegos
                salirPopUp();
                break;
            case R.id.EmergencyButton:
                //PopUp de llamada de emergencia
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;
            case R.id.jugarotravez:
                //Se reinicia el juego desde PopUp
                dialog.dismiss();
                iniciar();
                break;
            case R.id.otrojuego:
            case R.id.siSalir:
                //Regresar al menu de juegos
                dialog.dismiss();
                navController.popBackStack(R.id.juegos, false);
                break;
            case R.id.salir:
                //Regresar al menu principal
                dialog.dismiss();
                navController.popBackStack(R.id.mainMenuFragment, false);
                break;
            case R.id.noSalir:
                dialog.dismiss();
                break;
        }
    }

    public void comprobar(int i, final ImageButton imgb){
        if(primero==null){//ningún botón ha sido pulsado
            //el botón primero será el que acabamos de pulsar
            primero = imgb;
        /*le asignamos la imagen del vector imágenes situada
        en la posición arrayBarajado.get(i), con un valor entre 0 y 7*/
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imagenes[arrayBarajado.get(i)]);
            //bloqueamos el botón
            primero.setEnabled(false);
            //almacenamos el valor de arrayBarajado[i]
            numeroPrimero=arrayBarajado.get(i);
        }else{//ya hay un botón descubierto
            //bloqueamos todos los demás
            bloqueo=true;
            //el botón segundo será el que acabamos de pulsar
        /*le asignamos la imagen del vector imágenes situada
        en la posición arrayBarajado.get(i), con un valor entre 0 y 7*/
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageResource(imagenes[arrayBarajado.get(i)]);
            //bloqueamos el botón
            imgb.setEnabled(false);
            //almacenamos el valor de arrayBarajado.get(i)
            numeroSegundo=arrayBarajado.get(i);
            if(numeroPrimero==numeroSegundo){//si coincide el valor los dejamos   destapados
                //reiniciamos
                primero=null;
                bloqueo=false;
                //aumentamos los aciertos y la puntuación
                aciertos++;
                puntuacion++;
                textoPuntuacion.setText("Puntuación: " + puntuacion);
                //al llegar a 8 aciertos se ha ganado el juego
                if(aciertos==8){
                    finish();
                }
            }else{//si NO coincide el valor los volvemos a tapar al cabo de un segundo
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //les ponemos la imagen de fondo
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(R.drawable.fondo);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(R.drawable.fondo);
                        //los volvemos a habilitar
                        primero.setEnabled(true);
                        imgb.setEnabled(true);
                        //reiniciamos la variables auxiliares
                        primero = null;
                        bloqueo = false;
                        //restamos uno a la puntuación
                        if (puntuacion > 0) {
                            puntuacion--;
                            textoPuntuacion.setText("Puntuación: " + puntuacion);
                        }
                    }
                }, 1000);//al cabo de un segundo
            }
        }
    }
    public void iniciar(){
        arrayBarajado = barajar(imagenes.length*2);
        cargarBotones();

        //MOSTRAMOS LA IMAGEN
        for(int i=0; i<botonera.length; i++) {
            botonera[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            botonera[i].setImageResource(imagenes[arrayBarajado.get(i)]);
        }

        //Y EN UN SEGUNDO LA OCULTAMOS
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < botonera.length; i++) {
                    botonera[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    botonera[i].setImageResource(fondo);
                }
            }
        }, 1000);

        //AÑADIMOS LOS EVENTOS A LOS BOTONES DEL JUEGO
        for(int i=0; i <arrayBarajado.size(); i++){
            final int j=i;
            botonera[i].setEnabled(true);
            botonera[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bloqueo)
                        comprobar(j, botonera[j]);
                }
            });
        }
        aciertos=0;
        puntuacion=0;
        textoPuntuacion.setText("Puntuación: " + puntuacion);
    }


    public void finish(){
        Log.d("3", "Juego Terminado");
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View imageDisplay = getLayoutInflater().inflate(R.layout.layout_game_win_popup, null);
        AppCompatButton jugarotravez = imageDisplay.findViewById(R.id.jugarotravez);
        AppCompatButton otrojuego = imageDisplay.findViewById(R.id.otrojuego);
        AppCompatButton salir = imageDisplay.findViewById(R.id.salir);
        if(jugarotravez != null) {
            jugarotravez.setOnClickListener((View.OnClickListener) this);
        }

        if(otrojuego != null) {
            otrojuego.setOnClickListener((View.OnClickListener) this);
        }

        if(salir != null) {
            salir.setOnClickListener((View.OnClickListener) this);
        }
        alertDialogBuilder.setView(imageDisplay);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

    public void salirPopUp(){
        Log.d("5", "Salir del juego");
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View imageDisplay = getLayoutInflater().inflate(R.layout.layout_exit_game_popup, null);
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