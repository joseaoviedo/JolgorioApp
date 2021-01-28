package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioLogro;
import com.jolgorio.jolgorioapp.repositories.LogroRepository;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LogroListAdapter extends RecyclerView.Adapter<LogroListAdapter.ViewHolder> {

    ArrayList<JolgorioLogro> mLogros;
    Context mContext;
    NavController navController;
    LogroRepository logroRepository;
    int tipo;

    public LogroListAdapter(Context mContext, NavController navController, int tipoLogro){
        this.mContext = mContext;
        this.navController = navController;
        this.logroRepository = LogroRepository.getInstance();
        this.logroRepository.loadData();
        this.tipo = tipoLogro;
        if(tipo == 1){
            this.mLogros = logroRepository.getLogrosArtisticos();
        }else if(tipo == 2){
            this.mLogros = logroRepository.getLogrosDeportivo();
        }else if(tipo == 3){
            this.mLogros= logroRepository.getLogrosCultural();
        }
        else{
            System.out.println("Error codigo de logros no reconocido");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_logro, parent, false);
        LogroListAdapter.ViewHolder holder = new LogroListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JolgorioLogro logro = mLogros.get(position);

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

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iconImage;
        TextView logroText;
        JolgorioLogro logro;

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
