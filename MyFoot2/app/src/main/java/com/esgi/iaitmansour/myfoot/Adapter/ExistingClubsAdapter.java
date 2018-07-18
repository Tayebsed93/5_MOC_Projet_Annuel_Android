package com.esgi.iaitmansour.myfoot.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.iaitmansour.myfoot.Models.ExistingClubs;
import com.esgi.iaitmansour.myfoot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by iaitmansour on 28/06/2018.
 */

public class ExistingClubsAdapter extends RecyclerView.Adapter<ExistingClubsAdapter.ExistingClubsViewHolder> {

    private Context mContext;
    private ArrayList<ExistingClubs> mExistingClubsList;

    public ExistingClubs getExistingClub(int position){
        return this.mExistingClubsList.get(position);
    }

    public ExistingClubsAdapter(Context context, ArrayList<ExistingClubs> existingClubsList){
        mContext = context;
        mExistingClubsList = existingClubsList;
    }

    @Override
    public ExistingClubsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_existing_clubs, parent, false);
            return new ExistingClubsViewHolder(v);
        }


    @Override
    public void onBindViewHolder(ExistingClubsViewHolder holder, int position) {
        ExistingClubs currentClub = mExistingClubsList.get(position);

        String logoUrl = currentClub.getLogoUrl();
        String nom = currentClub.getNom();

        holder.mNomClub.setText(nom);
        Picasso.with(mContext).load(logoUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mExistingClubsList.size();
    }


    public class  ExistingClubsViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mNomClub;

        public ExistingClubsViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_club_logo);
            mNomClub = itemView.findViewById(R.id.txt_club_name);
        }
    }


}
