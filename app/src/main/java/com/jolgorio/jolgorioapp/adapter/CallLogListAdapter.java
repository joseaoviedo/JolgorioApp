package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioCallLog;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.repositories.CallLogRepository;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallLogListAdapter extends RecyclerView.Adapter<CallLogListAdapter.ViewHolder> {
    ArrayList<JolgorioCallLog> callLogs;
    Context mContext;
    NavController navController;
    CallLogRepository callLogRepository;



    public CallLogListAdapter(Context mContext, NavController navController){
        this.mContext = mContext;
        this.navController = navController;
        this.callLogRepository = CallLogRepository.getInstance();
        this.callLogRepository.loadData();
        this.callLogs = callLogRepository.getCallLogs();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_call_log, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JolgorioCallLog callLog = callLogs.get(position);
        holder.callLog = callLog;
        JolgorioUser user = callLog.getUserCalled();

        holder.callDate.setText(callLog.getDate().getDay() + "/" + callLog.getDate().getMonth() + "/" + callLog.getDate().getYear());
        holder.contactName.setText(user.getName());

        Glide.with(mContext)
                .asBitmap()
                .load(user.getPhotoURL())
                .into(holder.contactImage);

        holder.removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callLogRepository.removeCallLog(callLog);
                reload();
            }
        });
    }

    @Override
    public int getItemCount() {
        return callLogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        JolgorioCallLog callLog;
        AppCompatButton removeButton;
        CircleImageView contactImage;
        TextView contactName;
        TextView callDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            removeButton = itemView.findViewById(R.id.remove_call_log_btn);
            contactImage = itemView.findViewById(R.id.call_log_image);
            contactName = itemView.findViewById(R.id.call_log_name);
            callDate = itemView.findViewById(R.id.call_log_date);
        }
    }

    public void reload(){
        notifyDataSetChanged();
    }
}
