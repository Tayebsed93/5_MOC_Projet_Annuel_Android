package com.esgi.iaitmansour.myfoot.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.iaitmansour.myfoot.Models.Players;
import com.esgi.iaitmansour.myfoot.R;

import java.util.ArrayList;

/**
 * Created by iaitmansour on 14/07/2018.
 */

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayersViewHolder> {
    private Context mContext;
    private ArrayList<Players> mPlayersList;

    public Players getPlayers(int position){
        return this.mPlayersList.get(position);
    }

    public PlayersAdapter(Context context, ArrayList<Players> playersClubsList){
        mContext = context;
        mPlayersList = playersClubsList;
    }

    @Override
    public PlayersAdapter.PlayersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_players_item, parent, false);
        return new PlayersAdapter.PlayersViewHolder (v);
    }


    @Override
    public void onBindViewHolder(PlayersAdapter.PlayersViewHolder holder, int position) {
        Players currentPlayer = mPlayersList.get(position);

        String name = currentPlayer.getName ();
        int age = currentPlayer.getAge ();
        String flag = currentPlayer.getPays ();


        String StringGenerated = String.valueOf(currentPlayer.getName ().toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", ""));
        int id = mContext.getResources().getIdentifier(StringGenerated, "drawable", mContext.getPackageName());

        String StringGeneratedd = String.valueOf(flag.toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", ""));
        int idd = mContext.getResources().getIdentifier(StringGeneratedd, "drawable", mContext.getPackageName());

        holder.mAgePlayers.setText(age + " ans");
        holder.mPlayersName.setText (name);
        holder.mFlagPlayers.setImageResource (idd);

        if(id !=0){
            holder.mImgPlayers.setImageResource (id);
        }
        else{

            String player ="player";
            int idDefault = mContext.getResources().getIdentifier(player, "drawable", mContext.getPackageName());
            holder.mImgPlayers.setImageResource(idDefault);
        }

    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }


    public class  PlayersViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImgPlayers, mFlagPlayers;
        public TextView mPlayersName, mAgePlayers;

        public PlayersViewHolder(View itemView){
            super(itemView);
            mImgPlayers= itemView.findViewById (R.id.imgPlayers);
            mFlagPlayers = itemView.findViewById (R.id.flagPlayers);
            mPlayersName = itemView.findViewById (R.id.playersName);
            mAgePlayers = itemView.findViewById (R.id.agePlayers);

        }
    }
}
