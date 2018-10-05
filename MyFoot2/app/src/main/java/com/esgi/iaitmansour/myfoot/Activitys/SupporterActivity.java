package com.esgi.iaitmansour.myfoot.Activitys;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.esgi.iaitmansour.myfoot.Fragments.ActualityFragment;
import com.esgi.iaitmansour.myfoot.Fragments.GameFragment;
import com.esgi.iaitmansour.myfoot.Fragments.NewsFragment;
import com.esgi.iaitmansour.myfoot.Fragments.ResultatFragment;
import com.esgi.iaitmansour.myfoot.Fragments.ScoreGameFragment;
import com.esgi.iaitmansour.myfoot.R;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class SupporterActivity extends AppCompatActivity {


    final Fragment resultatFragment = new ResultatFragment ();
    final Fragment actualityFragment = new ActualityFragment ();
    final Fragment gameFragment = new GameFragment ();
    final Fragment scoreGameFragment = new ScoreGameFragment ();
    Fragment active = resultatFragment;
    final FragmentManager fm = getSupportFragmentManager();

    String nameClubb = "";
    int userId = 0;
    String twitterr = "";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity (intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_supporter);


        if (getIntent ( ).hasExtra ("nameClub") && getIntent ( ).hasExtra ("userId") && getIntent ( ).hasExtra ("twitter")) {
            Intent intent = getIntent ( );
            String nameClub = intent.getStringExtra ("nameClub");
            int user = intent.getIntExtra ("userId", 0);
            String twitter = intent.getStringExtra ("twitter");

            userId = user;
            nameClubb = nameClub;
            twitterr = twitter;

        }


        if (getIntent ( ).hasExtra ("nomClubMorray") && getIntent ( ).hasExtra ("userIdMorray")) {
            Intent intent = getIntent ( );
            String nameClub = intent.getStringExtra ("nomClubMorray");
            int user = intent.getIntExtra ("userIdMorray", 0);

            userId = user;
            nameClubb = nameClub;

        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById (R.id.navigation);
        navigation.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener);

        fm.beginTransaction ().add (R.id.container,scoreGameFragment ,"4").hide(scoreGameFragment).commit ();
        fm.beginTransaction ().add (R.id.container,gameFragment ,"3").hide(gameFragment).commit ();
        fm.beginTransaction ().add (R.id.container,actualityFragment ,"2").hide(actualityFragment).commit ();
        fm.beginTransaction ().add (R.id.container , resultatFragment, "1").commit ();


        //initiate Twitter config
        TwitterConfig config = new TwitterConfig.Builder (this)
                .logger (new DefaultLogger (Log.DEBUG))
                .twitterAuthConfig (new TwitterAuthConfig ("6GYm9kkEL7z6dUAb3GvZmKTbh", "xqRxTZe7PX7QJX5EtLO8qt8hqY7j1sYOSDivlgLL92JJ1FbZqj"))//pass Twitter API Key and Secret
                .debug (true)
                .build ( );
        Twitter.initialize (config);



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener ( ) {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId ( )) {
                case R.id.resultat_item:
                    fm.beginTransaction ().hide (active).show (resultatFragment).commit ();
                    active = resultatFragment;
                    return true;
                case R.id.actualite_item:
                    fm.beginTransaction ().hide (active).show (actualityFragment).commit ();
                    active = actualityFragment;
                    return true;
                case R.id.composition_item:
                    fm.beginTransaction ().hide (active).show (gameFragment).commit ();
                    active = gameFragment;
                    return true;
                case R.id.classement_item:
                    fm.beginTransaction ().hide (active).show (scoreGameFragment).commit ();
                    active = scoreGameFragment;
                    return true;
            }
            return false;
        }
    };


    public String getTwitter() {
        return twitterr;
    }

    public String getMyData() {
        return nameClubb;

    }


    public int getMyUserId() {
        return userId;
    }


}
