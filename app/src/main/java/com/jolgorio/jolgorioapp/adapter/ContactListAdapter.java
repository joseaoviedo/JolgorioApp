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
import com.jolgorio.jolgorioapp.data.dummy.ContactsDummy;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.repositories.ContactRepository;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    ArrayList<JolgorioUser> mContacts;
    Context mContext;
    NavController navController;
    ContactRepository contactRepository = ContactRepository.getInstance();

    public ContactListAdapter(Context mContext, NavController navController){
        this.mContext = mContext;
        this.navController = navController;
        contactRepository.loadData();
        mContacts = ContactsDummy.getContacts();
        Log.d("CONTACTOS", "CANTIDAD DE CONTACTOS: " + mContacts.size());
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
        JolgorioUser user;
        boolean isFavorite = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contactImage);
            contactName = itemView.findViewById(R.id.contactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);
            favoriteBtn = itemView.findViewById(R.id.favButton);
        }
    }
}
