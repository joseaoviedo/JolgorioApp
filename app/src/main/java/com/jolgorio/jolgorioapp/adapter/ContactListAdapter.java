package com.jolgorio.jolgorioapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jolgorio.jolgorioapp.R;

import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.repositories.ContactRepository;

import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    ArrayList<JolgorioUser> mContacts;
    Context mContext;
    NavController navController;
    ContactRepository contactRepository;

    public ContactListAdapter(Context mContext, NavController navController){
        this.mContext = mContext;
        this.navController = navController;
        this.contactRepository = ContactRepository.getInstance();
        this.contactRepository.loadData();
        this.mContacts = contactRepository.getContacts();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_contacts, parent, false);
        ContactListAdapter.ViewHolder holder = new ContactListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JolgorioUser contact = mContacts.get(position);
        holder.user = contact;
        holder.contactName.setText(contact.getName());
        holder.contactNumber.setText(contact.getNumber());

        Glide.with(mContext)
                .asBitmap()
                .load(contact.getPhotoURL())
                .into(holder.contactImage);

        Drawable favTrue = mContext.getDrawable(R.drawable.icon_fav_true);
        Drawable favFalse = mContext.getDrawable(R.drawable.icon_fav_false);

        if(contactRepository.isFavorite(contact)){
            Log.d("CONTACTOS", "Contacto favorito: " + contact.getName());
            holder.isFavorite = true;
            holder.favoriteBtn.setBackground(favTrue);
        }else{
            holder.isFavorite = false;
            holder.favoriteBtn.setBackground(favFalse);
        }

        holder.favoriteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(holder.isFavorite){
                    holder.isFavorite = false;
                    holder.favoriteBtn.setBackground(favFalse);
                    contactRepository.removeFavorite(holder.user);
                }else{
                    holder.isFavorite = true;
                    holder.favoriteBtn.setBackground(favTrue);
                    contactRepository.addFavorite(holder.user);
                }
            }
        });

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable("calledUser", holder.user);
                navController.navigate(R.id.calling_fragment, args);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView contactImage;
        TextView contactName;
        TextView contactNumber;
        AppCompatButton favoriteBtn;
        ImageButton callButton;
        JolgorioUser user;
        boolean isFavorite = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contactImage);
            contactName = itemView.findViewById(R.id.contactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);
            favoriteBtn = itemView.findViewById(R.id.favButton);
            callButton = itemView.findViewById(R.id.videocallButton);
        }
    }

    public void reload(){
        notifyDataSetChanged();
    }
}
