package com.jolgorio.jolgorioapp.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telecom.InCallService;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.repositories.ContactRepository;
import com.jolgorio.jolgorioapp.repositories.LogedInUserRepository;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoCallPeer {
    private boolean isPeerConected;
    private String userId;
    private DatabaseReference mDatabase;
    private static VideoCallPeer instance;
    private AlertDialog.Builder alertBuilder;
    private AlertDialog dialog;
    private Context mContext;
    private boolean isContextSet;
    private String isCallingText = " te está llamando";

    public static VideoCallPeer getInstance(){
        if(instance == null){
            instance = new VideoCallPeer();
        }
        return instance;
    }

    private VideoCallPeer(){
        userId = UUID.randomUUID().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        isContextSet = false;
    }

    public void setContext(Context context){
        this.mContext = context;
        alertBuilder = new AlertDialog.Builder(mContext);
        isContextSet = true;
    }

    public String getUserId(){
        return userId;
    }

    public DatabaseReference getDatabaseReference(){
        return mDatabase;
    }

    public void setPeerConnected(boolean b){
        isPeerConected = b;
    }


    public String getIsCallingText(String displayName){
        return displayName + isCallingText;
    }

    public void onCallRequest(String caller, NavController controller){
        if(caller == null) {
            return;
        }

        View view = View.inflate(mContext, R.layout.layout_incoming_call_popup, null);
        alertBuilder.setView(view);
        dialog = alertBuilder.create();
        CircleImageView callImage = view.findViewById(R.id.incoming_call_photo);
        String displayName;
        JolgorioUser contact = ContactRepository.getInstance().getContactFromPhone(caller);
        if(contact == null){
            callImage.setBackgroundResource(R.drawable.icon_default_user);
            displayName = caller;
        }else{
            Glide.with(mContext)
                    .asBitmap()
                    .load(contact.getPhotoURL())
                    .into(callImage);

            displayName = contact.getName();
        }

        TextView callingText = view.findViewById(R.id.incoming_call_text);
        callingText.setText(getIsCallingText(displayName));
        JolgorioUser user = LogedInUserRepository.getInstance().getLogedInUser();
        AppCompatButton declineButton = view.findViewById(R.id.incoming_call_decline);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(user.getNumber()).child("isAvailable").setValue(false);
                dialog.dismiss();
            }
        });

        AppCompatButton acceptButton = view.findViewById(R.id.incoming_call_answer);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mDatabase.child(user.getNumber()).child("connId").setValue(userId);
                mDatabase.child(user.getNumber()).child("isAvailable").setValue(true);
                controller.navigate(R.id.videoCallFragment);
            }
        });
        dialog.show();

        mDatabase.child(user.getNumber()).child("incoming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void onCallFinished(String user1, String user2){

    }
}
