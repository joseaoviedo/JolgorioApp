package com.jolgorio.jolgorioapp.ui.videocall;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.adapter.ContactListAdapter;
import com.jolgorio.jolgorioapp.adapter.FavoriteListAdapter;
import com.jolgorio.jolgorioapp.data.dummy.ContactsDummy;
import com.jolgorio.jolgorioapp.repositories.ContactRepository;

public class FavoriteContactsFragment extends Fragment {
    NavController navController;
    FavoriteListAdapter favListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites,container,false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.favorites_recycle_view);
        favListAdapter = new FavoriteListAdapter(getActivity(), navController);
        recyclerView.setAdapter(favListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.fav_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void refresh(){
        if(favListAdapter != null){
            favListAdapter.reload();
        }

    }
}
