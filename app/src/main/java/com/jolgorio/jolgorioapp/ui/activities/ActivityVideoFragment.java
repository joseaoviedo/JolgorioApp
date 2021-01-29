package com.jolgorio.jolgorioapp.ui.activities;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class ActivityVideoFragment extends Fragment implements View.OnClickListener{
    private JolgorioActivity activity;
    private NavController navController;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private boolean finished;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        activity = args.getParcelable("activity");
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_video, container, false);
        fillInfo(view);
        Button EmergencyCall = (Button) view.findViewById(R.id.EmergencyButton);
        EmergencyCall.setOnClickListener(this);

        Button back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);
        return view;
    }

    public void fillInfo(View view){
        Drawable backgroundColor = getActivity().getDrawable(R.color.white);

        View banner = view.findViewById(R.id.activity_video_banner);
        banner.setBackground(backgroundColor);

        TextView title = view.findViewById(R.id.f_video_activity_title);
        title.setText(activity.getTitle());

        YouTubePlayerView youtubePlayerView = view.findViewById(R.id.video_player);

        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                String videoURL = activity.getVideoLink();
                if(videoURL.contains("watch?v=")){
                    String[] split = videoURL.split("=");
                    String videoId = split[1];
                    youTubePlayer.loadVideo(videoId, 0);
                }
            }

            @Override
            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);
                if(state.toString().equals("ENDED")){
                    finished = true;
                    activityFinished();
                }
            }
        });

    }

    private void activityFinished(){
        Log.d("AAAAAAAAAAAAAAAAAAA", "ACTIVIDAD FINALIZADA");
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final View finishedActivityView = getLayoutInflater().inflate(R.layout.layout_activity_completed_popup, null);
        AppCompatButton photoBtn = finishedActivityView.findViewById(R.id.activity_completed_photo_btn);
        if(photoBtn != null) {
            photoBtn.setOnClickListener(this);
        }
        alertDialogBuilder.setView(finishedActivityView);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.EmergencyButton:
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;
            case R.id.back:
                navController.popBackStack(R.id.activityListActivity, false);
                break;
        }
    }




}