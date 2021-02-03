package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.jolgorio.jolgorioapp.R;

import java.io.File;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    private ArrayList<File> fileList = new ArrayList<File>();
    LayoutInflater inflter;
    public CustomAdapter(Context applicationContext, ArrayList<File> fileList) {
        this.context = applicationContext;
        this.fileList = fileList;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return fileList.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.layout_image_gridview, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView

        Bitmap myBitmap = BitmapFactory.decodeFile(fileList.get(i).getAbsolutePath());

        icon.setImageBitmap(myBitmap);
        return view;
    }
}
