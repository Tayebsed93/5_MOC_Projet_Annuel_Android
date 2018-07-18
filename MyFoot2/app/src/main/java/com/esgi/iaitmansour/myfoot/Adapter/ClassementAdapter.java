package com.esgi.iaitmansour.myfoot.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;

import com.esgi.iaitmansour.myfoot.Models.Classement;
import com.esgi.iaitmansour.myfoot.R;

import java.util.List;

/**
 * Created by iaitmansour on 30/06/2018.
 */

public class ClassementAdapter extends RecyclerView.Adapter<ClassementAdapter.ViewHolder>{

private Context context;
private List<Classement> list;
String nameClub;




public ClassementAdapter(Context context,List<Classement> list, String nomClub){
        this.context=context;
        this.list=list;
        nameClub = nomClub;

        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){

        View v= LayoutInflater.from(context).inflate(R.layout.single_classement_item,parent,false);
        return new ViewHolder(v);

        }

@Override
public void onBindViewHolder(ViewHolder holder,int position){
        Classement classement=list.get(position);

    if(position %2 == 1 && !(nameClub.equals(classement.getTeam_name())))
    {
        holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }
    else if(position %2 == 0 && !(nameClub.equals(classement.getTeam_name())))
    {
        holder.itemView.setBackgroundColor(Color.parseColor("#f0f0f5"));
    }
    else if(position %2 == 0 && nameClub.equals(classement.getTeam_name())){
        holder.itemView.setBackgroundColor(Color.parseColor("#599C78"));
    }
    else if(position %2 == 1 && nameClub.equals(classement.getTeam_name())){
        holder.itemView.setBackgroundColor(Color.parseColor("#599C78"));
    }




    Integer butsPour = Integer.parseInt(classement.getOverall_league_GF());
    Integer butsContre = Integer.parseInt(classement.getOverall_league_GA());
    Integer differenceButs = butsPour - butsContre;

    String StringGenerated = String.valueOf(classement.getTeam_name()).toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", "");
    int id = context.getResources().getIdentifier(StringGenerated, "drawable", context.getPackageName());

    holder.classement_position.setText(String.valueOf(classement.getOverall_league_position()));
    holder.classement_nomClub.setText(String.valueOf(classement.getTeam_name()));
    holder.classement_matchs_joue.setText(String.valueOf(classement.getOverall_league_payed()));
    holder.classement_victoire.setText(String.valueOf(classement.getOverall_league_W()));
    holder.classement_nul.setText(String.valueOf(classement.getOverall_league_D()));
    holder.classement_defaite.setText(String.valueOf(classement.getOverall_league_L()));
    holder.classement_difference.setText(String.valueOf(differenceButs));
    holder.classement_img.setImageResource(id);
    holder.classement_points.setText(String.valueOf(classement.getOverall_league_PTS()));



        }

    @Override
    public int getItemCount(){
        return list.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView classement_position, classement_nomClub, classement_matchs_joue, classement_victoire, classement_nul, classement_defaite, classement_difference,classement_points;
    public ImageView classement_img;

    public ViewHolder(View itemView) {
        super(itemView);


        classement_position = itemView.findViewById(R.id.classement_position);
        classement_nomClub=itemView.findViewById(R.id.classement_nomClub);
        classement_points = itemView.findViewById(R.id.classement_points);
        classement_matchs_joue=itemView.findViewById(R.id.classement_matchs_joue);
        classement_victoire=itemView.findViewById(R.id.classement_victoire);
        classement_nul=itemView.findViewById(R.id.classement_nul);
        classement_defaite=itemView.findViewById(R.id.classement_defaite);
        classement_difference=itemView.findViewById(R.id.classement_difference);
        classement_img=itemView.findViewById(R.id.classement_img);

    }
}


}
