package com.jolgorio.jolgorioapp.ui.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;

public class ActivityInfoFragment extends Fragment implements View.OnClickListener{
    JolgorioActivity activity;
    NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        activity = bundle.getParcelable("activity");
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity_info, container, false);
        fillInfo(view);
        return view;
    }

    public void fillInfo(View view){
        Drawable backgroundColor = null;
        Drawable backgroundDrawable = null;
        switch (activity.getType()){
            case 1:
                backgroundColor = getActivity().getDrawable(R.color.artistica);
                backgroundDrawable = getActivity().getDrawable(R.drawable.activity_bg_artistica);
                break;
            case 2:
                backgroundColor = getActivity().getDrawable(R.color.deportiva);
                backgroundDrawable = getActivity().getDrawable(R.drawable.activity_bg_deportiva);
                break;
            default:
                backgroundColor = getActivity().getDrawable(R.color.cultural);
                backgroundDrawable = getActivity().getDrawable(R.drawable.activity_bg_cultural);
                break;
        }
        View banner = view.findViewById(R.id.activity_info_banner);
        banner.setBackground(backgroundColor);

        TextView title = view.findViewById(R.id.f_info_activity_title);
        title.setText(activity.getTitle());

        AppCompatButton btn = view.findViewById(R.id.f_activity_enter_btn);
        btn.setBackground(backgroundDrawable);
        btn.setOnClickListener(this);

        TextView description = view.findViewById(R.id.infoDescription);
        description.setText(activity.getDescription());

        TextView materials = view.findViewById(R.id.infoMaterials);
        materials.setText(activity.getMaterials());

        TextView time = view.findViewById(R.id.infoTime);
        time.setText(activity.getTimeDescription());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.f_activity_enter_btn:
                Bundle bundle = new Bundle();
                bundle.putParcelable("activity", activity);
                navController.navigate(R.id.action_activityInfoFragment_to_activityVideoFragment, bundle);
        }
    }
}