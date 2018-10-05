package com.esgi.iaitmansour.myfoot.Adapter;






import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.iaitmansour.myfoot.Activitys.DetailsResultatActivity;
import com.esgi.iaitmansour.myfoot.Models.Resultat;
import com.esgi.iaitmansour.myfoot.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by iaitmansour on 01/07/2018.
 */

public class ResultatAdapter extends RecyclerView.Adapter<ResultatAdapter.ViewHolder>{

    private Context context;
    private List<Resultat> list;
    String nameClub;

    public ResultatAdapter(Context context,List<Resultat> list, String nomClub){
        this.context=context;
        this.list=list;
        nameClub = nomClub;

    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType){

        View v= LayoutInflater.from(context).inflate(R.layout.single_resultat_item,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){


        final Resultat resultat=list.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent detailResultatActivity = new Intent(context, DetailsResultatActivity.class);
                detailResultatActivity.putExtra("Resultat", resultat);
                context.startActivity(detailResultatActivity);


                ;
            }
        });






            String StringGeneratedHome = String.valueOf(resultat.getMatch_hometeam_name()).toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", "");
            int idClubHome = context.getResources().getIdentifier(StringGeneratedHome, "drawable", context.getPackageName());

            String StringGeneratedAway = String.valueOf(resultat.getMatch_awayteam_name()).toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", "");
            int idClubAway = context.getResources().getIdentifier(StringGeneratedAway, "drawable", context.getPackageName());

            String score = resultat.getMatch_hometeam_score() + "-" + resultat.getMatch_awayteam_score() ;

            SimpleDateFormat dateString = new SimpleDateFormat("yyyy-MM-dd");
            String date = null;

            try {
                Date datee = dateString.parse(resultat.getMatch_date());
                DateFormat dfl = DateFormat.getDateInstance(DateFormat.FULL, new Locale("fr","FR"));
                dfl.format(datee);
                date = dfl.format(datee);


            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



            holder.resultat_date.setText(String.valueOf(date));
            holder.resultat_ligue.setText(String.valueOf(resultat.getLeague_name()));
            holder.resultat_nameclub_away.setText(String.valueOf(resultat.getMatch_awayteam_name()));
            holder.resultat_nameclub_home.setText(String.valueOf(resultat.getMatch_hometeam_name()));
            holder.resultat_score.setText(String.valueOf(score));
            holder.resultat_img_home.setImageResource(idClubHome);
            holder.resultat_img_away.setImageResource(idClubAway);








    }








    @Override
    public int getItemCount(){
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView resultat_date, resultat_ligue, resultat_nameclub_home, resultat_score, resultat_nameclub_away;
        public ImageView resultat_img_home,resultat_img_away;

        public ViewHolder(View itemView) {
            super(itemView);

            resultat_date = itemView.findViewById(R.id.resultat_date);
            resultat_ligue= itemView.findViewById(R.id.resultat_ligue);
            resultat_nameclub_home= itemView.findViewById(R.id.resultat_nameclub_home);
            resultat_score= itemView.findViewById(R.id.resultat_score);
            resultat_nameclub_away= itemView.findViewById(R.id.resultat_nameclub_away);
            resultat_img_home = itemView.findViewById(R.id.resultat_img_home);
            resultat_img_away = itemView.findViewById(R.id.resultat_img_away);


        }
    }





}

