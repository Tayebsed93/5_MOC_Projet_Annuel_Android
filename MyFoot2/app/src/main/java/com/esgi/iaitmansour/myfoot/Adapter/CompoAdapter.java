package com.esgi.iaitmansour.myfoot.Adapter;

import android.support.v7.widget.RecyclerView;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;

import com.esgi.iaitmansour.myfoot.Models.CompositionMatch;
import com.esgi.iaitmansour.myfoot.Models.ScorerMatch;
import com.esgi.iaitmansour.myfoot.R;

import java.util.List;

/**
 * Created by iaitmansour on 30/06/2018.
 */

public class CompoAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<CompositionMatch> list;
    private static final int VIEW_TYPE_COMPO_HOME = 1;
    private static final int VIEW_TYPE_COMPO_AWAY = 2;





    public CompoAdapter(Context context, List<CompositionMatch> list){
        this.context=context;
        this.list=list;

    }


    @Override
    public int getItemCount(){
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        CompositionMatch compo=list.get(position);

        if(position < 11){
            return VIEW_TYPE_COMPO_HOME;
        }
        else{
            return VIEW_TYPE_COMPO_AWAY;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_COMPO_HOME) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_compo_item, parent, false);
            return new HomeCompoHolder (view);
        } else if (viewType == VIEW_TYPE_COMPO_AWAY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_compoaway_item, parent, false);
            return new AwayCompoHolder (view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CompositionMatch compo = list.get (position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_COMPO_HOME:
                ((CompoAdapter.HomeCompoHolder) holder).bind (compo);
                break;
            case VIEW_TYPE_COMPO_AWAY:
                ((CompoAdapter.AwayCompoHolder) holder).bind (compo);
        }
    }

    private class HomeCompoHolder extends RecyclerView.ViewHolder {
        TextView namePlayer, numberPlayer;
        ImageView compo_img;


        HomeCompoHolder(View itemView) {
            super(itemView);

            namePlayer = itemView.findViewById(R.id.compo_joueur);
            numberPlayer = itemView.findViewById(R.id.compo_numero);
            compo_img = itemView.findViewById(R.id.compo_img);


        }

        void bind(CompositionMatch compo) {

            String nameLower = String.valueOf(compo.getLineup_player().toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", ""));
            int id = context.getResources().getIdentifier(nameLower, "drawable", context.getPackageName());

            if(id!=0) {
                compo_img.setImageResource(id);
            }
            else{
                String player = "player";
                int idDefault = context.getResources().getIdentifier(player, "drawable", context.getPackageName());
                compo_img.setImageResource(idDefault);

            }

            namePlayer.setText(String.valueOf(compo.getLineup_player()));
            numberPlayer.setText(String.valueOf(compo.getLineup_number()));


        }
    }

    private class AwayCompoHolder extends RecyclerView.ViewHolder {
        TextView namePlayer, numberPlayer;
        ImageView compo_img;


        AwayCompoHolder(View itemView) {
            super(itemView);

            namePlayer = itemView.findViewById(R.id.compoaway_joueur);
            numberPlayer = itemView.findViewById(R.id.compoaway_numero);
            compo_img = itemView.findViewById(R.id.compoaway_img);


        }

        void bind(CompositionMatch compo) {

            String nameLower = String.valueOf(compo.getLineup_player().toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", ""));
            int id = context.getResources().getIdentifier(nameLower, "drawable", context.getPackageName());

            if(id!=0) {
                compo_img.setImageResource(id);
            }
            else{
                String player = "player";
                int idDefault = context.getResources().getIdentifier(player, "drawable", context.getPackageName());
                compo_img.setImageResource(idDefault);

            }

            namePlayer.setText(String.valueOf(compo.getLineup_player()));
            numberPlayer.setText(String.valueOf(compo.getLineup_number()));

        }
    }






}
