package com.jolgorio.jolgorioapp.ui.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.adapter.ActivityListAdapter;
import com.jolgorio.jolgorioapp.data.dummy.ActivityDummy;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;

import java.util.ArrayList;

public class ActivityListActivity extends AppCompatActivity {
    private ArrayList<JolgorioActivity> activities = ActivityDummy.getDummyData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activities);
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Log.d("Equisde: " , "" + activities.size());
        ActivityListAdapter adapter = new ActivityListAdapter(activities, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
