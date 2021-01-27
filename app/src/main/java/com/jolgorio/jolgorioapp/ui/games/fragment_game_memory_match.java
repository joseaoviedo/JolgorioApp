package com.jolgorio.jolgorioapp.ui.games;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.Collections;

public class fragment_game_memory_match extends Fragment implements View.OnClickListener {

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    //Emergencia
    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;
    //los botones del juego
    ImageButton el0, el1, el2, el3, el4, el5, el6, el7,
            el8, el9, el10, el11, el12, el13, el14, el15;

    //los botones del menú
    Button reiniciar, salir, emergencia;

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
        cargarImagenes();
        botonesMenu();
        iniciar();

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
        salir = (Button) view.findViewById(R.id.salir);
        emergencia =(Button) view.findViewById(R.id.EmergencyButton);
        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
            }
        });
        emergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmergencyPopUp();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alertNObutton:
                dialog.dismiss();
                break;
            case R.id.alertYESbutton:
                call();
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
                    Toast toast = Toast.makeText(getContext(),
                            "Has  ganado!!", Toast.LENGTH_LONG);
                    toast.show();
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

    //Llamar Emergencias
    void call() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "123"));
            getActivity().startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void EmergencyPopUp(){
        Log.d("3", "Call");
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View imageDisplay = getLayoutInflater().inflate(R.layout.layout_emergencycall_popup, null);
        AppCompatButton exitBtn = imageDisplay.findViewById(R.id.alertNObutton);
        AppCompatButton calltBtn = imageDisplay.findViewById(R.id.alertYESbutton);
        if(exitBtn != null) {
            exitBtn.setOnClickListener((View.OnClickListener) this);
        }

        if(calltBtn != null) {
            calltBtn.setOnClickListener((View.OnClickListener) this);
        }
        alertDialogBuilder.setView(imageDisplay);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }
}