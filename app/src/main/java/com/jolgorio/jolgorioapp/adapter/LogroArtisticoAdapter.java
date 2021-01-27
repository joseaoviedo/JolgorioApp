package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioLogro;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LogroArtisticoAdapter extends RecyclerView.Adapter<LogroArtisticoAdapter.ViewHolder> {

    private static final String TAG = "LogroArtisticoAdapter";
    ArrayList<JolgorioLogro> mLogros;
    Context mContext;
    NavController navController;

    public LogroArtisticoAdapter(ArrayList<JolgorioLogro> mLogros,Context mContext, NavController navController){
        this.mLogros = mLogros;
        this.mContext = mContext;
        this.navController = navController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_logro, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        JolgorioLogro logro = mLogros.get(position);
        holder.logro = logro;
        holder.activityTitle.setText(act.getTitle());
        Drawable color;
        switch (act.getType()){
            case 1:
                color = mContext.getDrawable(R.drawable.activity_bg_artistica);
                holder.parentLayout.setBackground(color);
                break;
            case 2:
                color = mContext.getDrawable(R.drawable.activity_bg_deportiva);
                holder.parentLayout.setBackground(color);
                break;
            default:
                color = mContext.getDrawable(R.drawable.activity_bg_cultural);
                holder.parentLayout.setBackground(color);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mLogros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView categoryImage;
        TextView activityTitle;
        RelativeLayout parentLayout;
        JolgorioLogro logro;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.list_activity_image);
            activityTitle = itemView.findViewById(R.id.list_activity_title);
            parentLayout = itemView.findViewById(R.id.list_activity_parent_layout);
        }
    }
}
