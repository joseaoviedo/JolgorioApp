package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder>{

    private static final String TAG = "ActivityListAdapter";
    private ArrayList<JolgorioActivity> mActivities = new ArrayList<>();
    private Context mContext;

    public ActivityListAdapter(ArrayList<JolgorioActivity> mActivities, Context mContext) {
        this.mActivities = mActivities;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_activity, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        JolgorioActivity act = mActivities.get(position);
        boolean completed = act.getCompleted();
        if(completed){
            Drawable d = mContext.getDrawable(R.drawable.check);
            holder.categoryImage.setImageDrawable(d);
        }else{
            Drawable d = mContext.getDrawable(R.drawable.mas);
            holder.categoryImage.setImageDrawable(d);
        }

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
            case 3:
                color = mContext.getDrawable(R.drawable.activity_bg_cultural);
                holder.parentLayout.setBackground(color);
                break;
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d(TAG, "onClick: clicked on: " + act.getTitle());

                if(completed){
                    Toast.makeText(mContext, "La actividad ya ha sido completada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "Abrir actividad", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mActivities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView categoryImage;
        TextView activityTitle;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.list_activity_image);
            activityTitle = itemView.findViewById(R.id.list_activity_title);
            parentLayout = itemView.findViewById(R.id.list_activity_parent_layout);
        }
    }
}