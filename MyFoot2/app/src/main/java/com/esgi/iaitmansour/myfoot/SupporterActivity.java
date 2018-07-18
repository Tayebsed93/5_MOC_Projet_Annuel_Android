package com.esgi.iaitmansour.myfoot;





import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class SupporterActivity extends AppCompatActivity {

    //private ActionBar toolbar;


    String nameClubb = "";
    int userId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supporter);


        if(getIntent ().hasExtra ("nameClub") && getIntent ().hasExtra ("userId")){
            Intent intent = getIntent();
            String nameClub = intent.getStringExtra("nameClub");
            int user = intent.getIntExtra("userId",0);

            userId = user;
            nameClubb= nameClub;

        }


        if(getIntent ().hasExtra ("nomClubMorray") && getIntent ().hasExtra ("userIdMorray")){
            Intent intent = getIntent();
            String nameClub = intent.getStringExtra("nomClubMorray");
            int user = intent.getIntExtra("userIdMorray",0);

            userId = user;
            nameClubb= nameClub;

        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        //initiate Twitter config
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6GYm9kkEL7z6dUAb3GvZmKTbh", "xqRxTZe7PX7QJX5EtLO8qt8hqY7j1sYOSDivlgLL92JJ1FbZqj"))//pass Twitter API Key and Secret
                .debug(true)
                .build();
        Twitter.initialize(config);

        loadFragment(new ResultatFragment());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.resultat_item:
                    fragment = new ResultatFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.actualite_item:
                    fragment = new ActualityFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.composition_item:
                    fragment = new GameFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.classement_item:
                    fragment = new ScoreGameFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public String getMyData() {
        return nameClubb;
    }


    public int getMyUserId(){
        return userId;
    }


}
