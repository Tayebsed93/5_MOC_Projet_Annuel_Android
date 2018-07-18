package com.esgi.iaitmansour.myfoot.Adapter;

/**
 * Created by iaitmansour on 03/07/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esgi.iaitmansour.myfoot.Models.ScorerMatch;
import com.esgi.iaitmansour.myfoot.R;

import java.util.List;

/**
 * Created by iaitmansour on 28/06/2018.
 */

public class DetailsResultatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_SCORER_HOME = 1;
    private static final int VIEW_TYPE_SCORER_AWAY = 2;

    private Context mContext;
    private List<ScorerMatch> mDetailsResultatList;


    public DetailsResultatAdapter(Context context, List<ScorerMatch> detailsResultatList){
        this.mContext = context;
        this.mDetailsResultatList = detailsResultatList;
    }

    @Override
    public int getItemCount() {
        return mDetailsResultatList.size();
    }



    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ScorerMatch scorer =  mDetailsResultatList.get(position);


        if(scorer.getAwayScorer().equals("")){
            return VIEW_TYPE_SCORER_HOME;
        }
        else{
            return VIEW_TYPE_SCORER_AWAY;
        }

    }


    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_SCORER_HOME) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_scorer, parent, false);
            return new HomeScorerHolder(view);
        } else if (viewType == VIEW_TYPE_SCORER_AWAY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_away_scorer, parent, false);
            return new AwayScorerHolder(view);
        }

        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ScorerMatch scorer =  mDetailsResultatList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_SCORER_HOME:
                ((HomeScorerHolder) holder).bind(scorer);
                break;
            case VIEW_TYPE_SCORER_AWAY:
                ((AwayScorerHolder) holder).bind(scorer);
        }
    }

    private class HomeScorerHolder extends RecyclerView.ViewHolder {
        TextView timeScorer , homeScorer;


        HomeScorerHolder(View itemView) {
            super(itemView);

            homeScorer = itemView.findViewById(R.id.name_home_scorer);
            timeScorer = itemView.findViewById(R.id.time_home_scorer);

        }

        void bind(ScorerMatch scorerMatch) {

            homeScorer.setText("  " + scorerMatch.getHomeScorer()+ "  ");
            timeScorer.setText("  " + scorerMatch.getTime() + "  ");


        }
    }


    private class AwayScorerHolder extends RecyclerView.ViewHolder {
        TextView timeScorer , awayScorer;


        AwayScorerHolder(View itemView) {
            super(itemView);

            awayScorer = itemView.findViewById(R.id.name_away_scorer);
            timeScorer = itemView.findViewById(R.id.time_away_scorer);

        }

        void bind(ScorerMatch scorerMatch) {

            awayScorer.setText("  " +scorerMatch.getAwayScorer()+ "  ");
            timeScorer.setText("  " +scorerMatch.getTime()+ "  ");


        }
    }


}
