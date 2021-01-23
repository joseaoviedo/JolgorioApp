package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jolgorio.jolgorioapp.R;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.repositories.ContactRepository;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {
    Context mContext;
    NavController navController;
    ContactRepository contactRepository;
    ArrayList<JolgorioUser> mFavContacts;

    public FavoriteListAdapter(Context mContext, NavController navController){
        this.mContext = mContext;
        this.navController = navController;
        contactRepository = ContactRepository.getInstance();
        contactRepository.loadData();
        this.mFavContacts = contactRepository.getFavContacts();
        Log.d("CONTACTOS", "CONTACTOS FAVORITOS: " + mFavContacts.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_contacts, parent, false);
        FavoriteListAdapter.ViewHolder holder = new FavoriteListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JolgorioUser contact = mFavContacts.get(position);
        holder.user = contact;
        holder.contactName.setText(contact.getName());
        holder.contactNumber.setText(contact.getNumber());

        Glide.with(mContext)
                .asBitmap()
                .load(contact.getPhotoURL())
                .into(holder.contactImage);

        Drawable favTrue = mContext.getDrawable(R.drawable.icon_fav_true);

        holder.isFavorite = true;
        holder.favButton.setBackground(favTrue);
        holder.favButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(holder.isFavorite){
                    contactRepository.removeFavorite(holder.user);
                    holder.isFavorite = false;
                }
                reload();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFavContacts.size();
    }

    public void reload(){
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatButton favButton;
        CircleImageView contactImage;
        TextView contactName;
        TextView contactNumber;
        JolgorioUser user;
        boolean isFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contactImage);
            contactName = itemView.findViewById(R.id.contactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);
            favButton = itemView.findViewById(R.id.favButton);
        }
    }
}
