package com.esgi.iaitmansour.myfoot.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.iaitmansour.myfoot.Models.MatchGame;
import com.esgi.iaitmansour.myfoot.R;
import com.esgi.iaitmansour.myfoot.Activitys.TerrainActivity;
import com.esgi.iaitmansour.myfoot.Activitys.TerrainDeuxActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Created by iaitmansour on 12/07/2018.
 */

public class MatchGameAdapter extends RecyclerView.Adapter<MatchGameAdapter.MatchGameViewHolder> {

    private Context mContext;
    private ArrayList<MatchGame> mMatchGameList;

    public MatchGame getMatchGame(int position){
        return this.mMatchGameList.get(position);
    }

    public MatchGameAdapter(Context context, ArrayList<MatchGame> matchGameList){
        mContext = context;
        mMatchGameList = matchGameList;
    }

    @Override
    public MatchGameAdapter.MatchGameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_match_item, parent, false);
        return new MatchGameAdapter.MatchGameViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MatchGameAdapter.MatchGameViewHolder holder, int position) {
        MatchGame currentmatch = mMatchGameList.get(position);



        final String matchHome = currentmatch.getMatchHome();
        String competition = currentmatch.getCompositionName();
        final String dateMatch = currentmatch.getTimeStart();
        final int id = currentmatch.getId ();


        String StringGeneratedHome = String.valueOf(matchHome.toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", ""));
        int idMatchHome = mContext.getResources().getIdentifier(StringGeneratedHome, "drawable", mContext.getPackageName());


        SimpleDateFormat dateString = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;

        try {
            Date datee = dateString.parse(dateMatch);
            DateFormat dfl = DateFormat.getDateInstance(DateFormat.FULL, new Locale("fr","FR"));
            dfl.format(datee);
            date = dfl.format(datee);


        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        holder.mNomPays.setText(matchHome);
        holder.mDateMatch.setText(date);
        holder.mCompetition.setText(competition);
        holder.mDrapeau.setImageResource(idMatchHome);

        if(currentmatch.getUserId () >0 ){
            holder.mPlay.setEnabled (false);
            holder.mPlay.setBackgroundResource (R.drawable.border_grey);
        }
        else{
            holder.mPlay.setBackgroundResource (R.drawable.border_blue);
            holder.mPlay.setOnClickListener (new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {


                    alertdialog (matchHome,id);


                }
            });
        }






    }



    public void alertdialog(final String matchHome, final int id){
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        // Set a title for alert dialog
        builder.setTitle("Selectionnez une composition");
        //builder.setMessage("Selectionner votre rôle");

        // Initializing an array of colors
        final String[] colors = new String[]{
                "4-4-2",
                "4-3-3",
                "Annuler"
        };

        // Set the list of items for alert dialog
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedColor = Arrays.asList(colors).get(which);

                if(selectedColor == "4-4-2"){

                    Intent intent = new Intent (mContext , TerrainActivity.class);
                    intent.putExtra ("pays",matchHome);
                    intent.putExtra ("competition_id", id);
                    intent.putExtra ("compo","442");
                    mContext.startActivity (intent);

                }

                if(selectedColor == "4-3-3"){



                    Intent intent = new Intent (mContext , TerrainDeuxActivity.class);
                    intent.putExtra ("pays",matchHome);
                    intent.putExtra ("competition_id", id);
                    intent.putExtra ("compo","433");
                    mContext.startActivity (intent);

                }

            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }





    @Override
    public int getItemCount() {
        return mMatchGameList.size();
    }


    public class  MatchGameViewHolder extends RecyclerView.ViewHolder{

        public ImageView mDrapeau;
        public TextView mNomPays , mDateMatch, mCompetition;
        public Button mPlay;


        public MatchGameViewHolder(View itemView){
            super(itemView);

            mDrapeau = itemView.findViewById(R.id.drapeau);
            mNomPays = itemView.findViewById(R.id.nomPays);
            mDateMatch = itemView.findViewById(R.id.dateMatch);
            mCompetition = itemView.findViewById(R.id.competition);
            mPlay = itemView.findViewById (R.id.btnPlay);


        }
    }


}

