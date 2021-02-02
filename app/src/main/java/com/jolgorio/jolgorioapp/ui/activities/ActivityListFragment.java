package com.jolgorio.jolgorioapp.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.adapter.ActivityListAdapter;
import com.jolgorio.jolgorioapp.data.dummy.ActivityDummy;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;
import com.jolgorio.jolgorioapp.repositories.ActivityRepository;
import com.jolgorio.jolgorioapp.ui.EmergencyCall;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

public class ActivityListFragment extends Fragment implements View.OnClickListener {
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        initRecyclerView(view);

        Button EmergencyCall = (Button) view.findViewById(R.id.EmergencyButton);
        EmergencyCall.setOnClickListener(this);
        setProgress(view);
        Button back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        return view;
    }

    public void setProgress(View v){
        CircularProgressBar type1Progress = v.findViewById(R.id.artistica_progress);
        CircularProgressBar type2Progress = v.findViewById(R.id.deportiva_progress);
        CircularProgressBar type3Progress = v.findViewById(R.id.cultural_progress);

        //Barra artística
        type1Progress.setProgressBarColor(Color.rgb(61,127,132));
        type1Progress.setProgress(ActivityRepository.getInstance().calculateActivityProgress(1));

        //Barra deportiva
        type2Progress.setProgressBarColor(Color.rgb(57,178,195));
        type2Progress.setProgress(ActivityRepository.getInstance().calculateActivityProgress(2));

        //Barra cultural
        type3Progress.setProgressBarColor(Color.rgb(226,142,21));
        type3Progress.setProgress(ActivityRepository.getInstance().calculateActivityProgress(3));
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ActivityListAdapter adapter = new ActivityListAdapter(getActivity(), navController);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.EmergencyButton:
                EmergencyCall call = new EmergencyCall();
                call.EmergencyPopUp(this);
                break;
            case R.id.back:
                navController.popBackStack(R.id.mainMenuFragment, false);
                break;
        }
    }
}
