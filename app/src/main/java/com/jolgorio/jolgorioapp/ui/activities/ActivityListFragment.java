package com.jolgorio.jolgorioapp.ui.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;

public class ActivityListFragment extends Fragment {
    private ArrayList<JolgorioActivity> activities = ActivityDummy.getDummyData();
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ActivityListAdapter adapter = new ActivityListAdapter(activities, getActivity(), navController);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
