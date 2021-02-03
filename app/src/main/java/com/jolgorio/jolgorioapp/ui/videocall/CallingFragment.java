package com.jolgorio.jolgorioapp.ui.videocall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.telecom.Call;
import android.telecom.InCallService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.repositories.LogedInUserRepository;


import de.hdodenhof.circleimageview.CircleImageView;


public class CallingFragment extends Fragment {
    JolgorioUser calledUser;
    NavController navController;
    static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("user");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        calledUser = args.getParcelable("calledUser");
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calling, container, false);
        CircleImageView userImage = view.findViewById(R.id.calling_image);
        if(calledUser.getPhotoURL() != null) {
            Glide.with(getActivity())
                    .asBitmap()
                    .load(calledUser.getPhotoURL())
                    .into(userImage);
        }
        TextView userName = view.findViewById(R.id.calling_name);
        userName.setText(calledUser.getName());

        AppCompatButton endCallButton = view.findViewById(R.id.calling_cancel);
        endCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCall();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sendCallRequest();
    }

    public void sendCallRequest(){
        JolgorioUser logedInUser = LogedInUserRepository.getInstance().getLogedInUser();

        mDatabase.child(calledUser.getNumber()).child("incoming").setValue(logedInUser.getNumber());
        mDatabase.child(calledUser.getNumber()).child("isAvailable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    if(snapshot.getValue().toString() == "true") {
                        listenForConnectionId();
                    }
                    if(snapshot.getValue().toString() == "false"){
                        cancelCall();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void listenForConnectionId(){

        mDatabase.child(calledUser.getNumber()).child("connId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    String connId = snapshot.getValue().toString();
                    Bundle args = new Bundle();
                    args.putString("userCalledId", calledUser.getNumber());
                    args.putString("connId", connId);
                    Intent callIntent = new Intent(getActivity(), VideoCallFragment.class);
                    callIntent.putExtras(args);
                    getActivity().startActivity(callIntent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void cancelCall(){
        mDatabase.child(calledUser.getNumber()).child("incoming").setValue(null);
        mDatabase.child(calledUser.getNumber()).child("isAvailable").setValue(null);
        mDatabase.child(calledUser.getNumber()).child("connId").setValue(null);
        navController.popBackStack(R.id.videoCallPagerFragment, true);
    }
}