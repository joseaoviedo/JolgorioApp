package com.jolgorio.jolgorioapp.ui.videocall;

import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.adapter.CallLogListAdapter;
import com.jolgorio.jolgorioapp.adapter.ContactListAdapter;

public class CallLogFragment extends Fragment {
    NavController navController;
    CallLogListAdapter callLogListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_log,container,false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        initRecyclerView(view);
        return view;
    }

    public void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.call_log_recycler_view);
        callLogListAdapter = new CallLogListAdapter(getActivity(), navController);
        recyclerView.setAdapter(callLogListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void refresh(){
        callLogListAdapter.reload();
    }
}
