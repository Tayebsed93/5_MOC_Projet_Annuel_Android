package com.esgi.iaitmansour.myfoot.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.iaitmansour.myfoot.Models.UserGame;
import com.esgi.iaitmansour.myfoot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by iaitmansour on 28/06/2018.
 */

public class ScoreGameAdapter extends RecyclerView.Adapter<ScoreGameAdapter.ScoreGameViewHolder> {

    private Context mContext;
    private ArrayList<UserGame> mScoreGameList;

    public UserGame getUserGame(int position){
        return this.mScoreGameList.get(position);
    }

    public ScoreGameAdapter(Context context, ArrayList<UserGame> scoreGameList){
        mContext = context;
        mScoreGameList = scoreGameList;
    }

    @Override
    public ScoreGameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_user_score_item, parent, false);
        return new ScoreGameViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ScoreGameViewHolder holder, int position) {
        UserGame currentUser = mScoreGameList.get(position);


        String imgUrl = currentUser.getUrlImgUser();
        String name = currentUser.getName();
        int score = currentUser.getScore();

        holder.mName.setText(name);
        holder.mscoreUser.setText(String.valueOf(score) + " Point(s)");
        Picasso.with(mContext).load(imgUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mScoreGameList.size();
    }


    public class  ScoreGameViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mName , mscoreUser;

        public ScoreGameViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.user_img_game);
            mName = itemView.findViewById(R.id.name_user_game);
            mscoreUser = itemView.findViewById(R.id.score_user_game);
        }
    }


}
