package com.esgi.iaitmansour.myfoot.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.iaitmansour.myfoot.Adapter.DetailsPagerAdapter;
import com.esgi.iaitmansour.myfoot.Models.Resultat;
import com.esgi.iaitmansour.myfoot.R;

public class DetailsResultatActivity extends AppCompatActivity {


    Resultat resultatGlobal = new Resultat ( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_details_resultat);

        String title = "Back";
        Toolbar mToolbar = findViewById (R.id.toolbar_details_resultat_activity);
        mToolbar.setTitle (title);
        mToolbar.setTitleTextColor (Color.WHITE);
        mToolbar.setNavigationIcon (R.drawable.ic_arrow_back_black_24dp);


        mToolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                finish ( );
            }
        });


        Intent i = getIntent ( );
        resultatGlobal = (Resultat) i.getSerializableExtra ("Resultat");


        System.out.println (resultatGlobal.getMatch_id ());

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById (R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        DetailsPagerAdapter adapter = new DetailsPagerAdapter (this, getSupportFragmentManager ( ), resultatGlobal.getMatch_id ( ));

        // Set the adapter onto the view pager
        viewPager.setAdapter (adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById (R.id.sliding_tabs);
        tabLayout.setupWithViewPager (viewPager);


        displayTop ( );


    }


    //Function to display the top of the details of the results game
    public void displayTop() {

        TextView nameClubAway = findViewById (R.id.textViewDetailsAway);
        nameClubAway.setText (resultatGlobal.getMatch_awayteam_name ( ));

        TextView nameClubHome = findViewById (R.id.textViewDetailsHome);
        nameClubHome.setText (resultatGlobal.getMatch_hometeam_name ( ));


        ImageView imgDetailsHome = findViewById (R.id.img_details_home);
        String StringGeneratedHome = String.valueOf (resultatGlobal.getMatch_hometeam_name ( )).toLowerCase ( ).replaceAll ("([^a-zA-Z]|\\s)+", "");
        int idClubHome = this.getResources ( ).getIdentifier (StringGeneratedHome, "drawable", this.getPackageName ( ));
        imgDetailsHome.setImageResource (idClubHome);

        ImageView imgDetailsAway = findViewById (R.id.img_details_away);
        String StringGeneratedAway = String.valueOf (resultatGlobal.getMatch_awayteam_name ( )).toLowerCase ( ).replaceAll ("([^a-zA-Z]|\\s)+", "");
        int idClubAway = this.getResources ( ).getIdentifier (StringGeneratedAway, "drawable", this.getPackageName ( ));
        imgDetailsAway.setImageResource (idClubAway);


        TextView scoreDetails = findViewById (R.id.score_details);
        String score = resultatGlobal.getMatch_hometeam_score ( ) + " : " + resultatGlobal.getMatch_awayteam_score ( );
        scoreDetails.setText (score);


    }


}
