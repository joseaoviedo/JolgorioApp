package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioAchievement;
import com.jolgorio.jolgorioapp.repositories.AchievementRepository;

import java.util.ArrayList;

public class AchievementListAdapter extends RecyclerView.Adapter<AchievementListAdapter.ViewHolder> {

    ArrayList<JolgorioAchievement> mLogros;
    Context mContext;
    NavController navController;
    AchievementRepository achievementRepository;
    int tipo;

    public AchievementListAdapter(Context mContext, NavController navController, int tipoLogro){
        this.mContext = mContext;
        this.navController = navController;
        this.achievementRepository = AchievementRepository.getInstance();
        this.achievementRepository.loadData();
        this.tipo = tipoLogro;
        if(tipo == 1){
            this.mLogros = achievementRepository.getType1();
        }else if(tipo == 2){
            this.mLogros = achievementRepository.getType2();
        }else if(tipo == 3){
            this.mLogros= achievementRepository.getType3();
        }
        else{
            System.out.println("Error codigo de logros no reconocido");
        }
    }

    /*
     Crea la instancia de View que va a ser utilizada por el fragmento
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_logro, parent, false);
        AchievementListAdapter.ViewHolder holder = new AchievementListAdapter.ViewHolder(view);
        return holder;
    }

    /*
    Esta función asigna los valores a cada elemento del RecycleView, aquí es donde se asignan tanto
    los datos como el comportamiento de las secciones de la lista
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JolgorioAchievement logro = mLogros.get(position);

        holder.logro = logro;
        holder.logroText.setText(logro.getTitle());
        switch (logro.getType()){
            case 1:
                holder.iconImage.setImageResource(R.drawable.artisticas);
                break;
            case 2:
                holder.iconImage.setImageResource(R.drawable.deportivos);
                break;
            default:
                holder.iconImage.setImageResource(R.drawable.culturales);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mLogros.size();
    }

    /*
    Representa cada fila de la lista y los datos que posee, así como los elementos de la vista
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iconImage;
        TextView logroText;
        JolgorioAchievement logro;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconLogro);
            logroText = itemView.findViewById(R.id.logroText);

        }
    }

    public void reload(){
        notifyDataSetChanged();
    }
}
