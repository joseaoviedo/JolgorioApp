package com.jolgorio.jolgorioapp.ui.progress;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.adapter.AchievementListAdapter;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class ArtisticaFragment extends Fragment {

    NavController navController;
    AchievementListAdapter achievementListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_my_progress,container,false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        initRecyclerView(view);

        //Cambiar el icono
        ImageView icon = (ImageView) view.findViewById(R.id.iconProgress);
        icon.setImageResource(R.drawable.artisticas);

        //Cambiar el color del Circulo
        CircularProgressBar circularProgressBar = (CircularProgressBar) view.findViewById(R.id.circularProgressBar);
        circularProgressBar.setProgressBarColor(Color.rgb(45,138,143));

        //Porcentaje
        circularProgressBar.setProgress(20f);

        //Texto del porcentaje
        TextView porcentaje = (TextView) view.findViewById(R.id.porcentage);
        porcentaje.setText("20%");

        return view;
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.logros_recycler);
        achievementListAdapter = new AchievementListAdapter(getActivity(), navController,1 );
        recyclerView.setAdapter(achievementListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void refresh(){
        achievementListAdapter.reload();
    }
}
