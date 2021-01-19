package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder>{

    private static final String TAG = "ActivityListAdapter";
    private ArrayList<String> mActivityTitle = new ArrayList<>();
    private ArrayList<Boolean> mIsCompleted = new ArrayList<>();
    private int mActivityID;
    private Context mContext;

    public ActivityListAdapter(ArrayList<String> mActivityTitle, ArrayList<Boolean> mIsCompleted, int mActivityID, Context mContext) {
        this.mActivityTitle = mActivityTitle;
        this.mIsCompleted = mIsCompleted;
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


        holder.activityTitle.setText(mActivityTitle.get(position));


    }

    @Override
    public int getItemCount() {
        return 0;
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
