package com.esgi.iaitmansour.myfoot.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.ExistingClubsAdapter;
import com.esgi.iaitmansour.myfoot.Models.ExistingClubs;
import com.esgi.iaitmansour.myfoot.R;
import com.esgi.iaitmansour.myfoot.Utils.ItemClickSupport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ExistingClubsAdapter mExistingClubsAdapter;
    private ArrayList<ExistingClubs> mExistingClubsList;
    private RequestQueue mRequestQueue;

    @Override
    public void onBackPressed() {

        Intent a = new Intent (Intent.ACTION_MAIN);
        a.addCategory (Intent.CATEGORY_HOME);
        a.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity (a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        //Configuring Toolbar
        this.configureToolbar ( );


        mRecyclerView = findViewById (R.id.recycler_view_clubs_list);
        mRecyclerView.setHasFixedSize (true);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (this));

        mExistingClubsList = new ArrayList<> ( );

        //Configure the swipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById (R.id.swipe_containerr);
        mSwipeRefreshLayout.setOnRefreshListener (this);
        mSwipeRefreshLayout.setColorSchemeResources (R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post (new Runnable ( ) {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing (true);

                // Fetching data from server
                parseJSON ( );
            }
        });

        //Configure the recyclerView
        this.configureOnClickRecyclerView ( );
    }


    @Override
    public void onRefresh() {

        // Fetching data from server and reset List to avoid duplication of data
        mExistingClubsList = new ArrayList<> ( );
        parseJSON ( );
    }


    //Function to get existingsClubs from server
    private void parseJSON() {

        mSwipeRefreshLayout.setRefreshing (true);

        String url = "http://poubelle-connecte.pe.hu/FootAPI/API/v1/club";

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONObject> ( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray ("clubs");

                            for (int i = 0; i < jsonArray.length ( ); i++) {
                                JSONObject club = jsonArray.getJSONObject (i);

                                String nomClub = club.getString ("nom");
                                String logoUrl = club.getString ("logo");
                                String twitter = club.getString ("screen_name");
                                int userId = club.getInt ("user_id");

                                mExistingClubsList.add (new ExistingClubs (userId, nomClub, logoUrl, twitter));

                            }

                            mExistingClubsAdapter = new ExistingClubsAdapter (MainActivity.this, mExistingClubsList);
                            mRecyclerView.setAdapter (mExistingClubsAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace ( );
                            ;
                        }


                        mSwipeRefreshLayout.setRefreshing (false);
                    }
                }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace ( );
                mSwipeRefreshLayout.setRefreshing (false);
            }
        });

        mRequestQueue = Volley.newRequestQueue (this);
        mRequestQueue.add (request);
    }

    //Function to configure the click of one element of the recyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo (mRecyclerView, R.layout.row_existing_clubs)
                .setOnItemClickListener (new ItemClickSupport.OnItemClickListener ( ) {

                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        // 1 - Get club from adapter
                        ExistingClubs club = mExistingClubsAdapter.getExistingClub (position);
                        alertdialog (club.getNom ( ), club.getUserId ( ), club.getTwitter ( ));


                    }

                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater ( ).inflate (R.menu.menu_activity_main, menu);
        return true;
    }

    //Function to configure the toolbar
    private void configureToolbar() {
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById (R.id.activity_main_toolbar);
        setSupportActionBar (toolbar);
        getSupportActionBar ( ).setTitle (null);
    }


    //Function to go add club
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle actions on menu items
        switch (item.getItemId ( )) {
            case R.id.menu_activity_main_add:
                Intent addClubActivity = new Intent (MainActivity.this, AddClubActivity.class);
                startActivity (addClubActivity);
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    //Function to create Alertdialog to choose the "role" of the user (Supporter or Membre)
    public void alertdialog(final String nomClub, final int userId, final String twitter) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder (MainActivity.this);

        // Set a title for alert dialog
        builder.setTitle ("Sélectionnez votre rôle");


        // Initializing an array of colors
        final String[] colors = new String[]{
                "Supporter",
                "Membre",
                "Annuler"
        };

        // Set the list of items for alert dialog
        builder.setItems (colors, new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedColor = Arrays.asList (colors).get (which);

                if (selectedColor == "Membre") {

                    Intent intent = new Intent (MainActivity.this, ConnectionMembreActivity.class);
                    intent.putExtra ("nameClub", nomClub);
                    intent.putExtra ("userId", userId);
                    startActivity (intent);

                }

                if (selectedColor == "Supporter") {

                    Intent intent = new Intent (MainActivity.this, SupporterActivity.class);
                    intent.putExtra ("nameClub", nomClub);
                    intent.putExtra ("userId", userId);
                    intent.putExtra ("twitter", twitter);

                    startActivity (intent);


                }

            }
        });

        AlertDialog dialog = builder.create ( );
        // Display the alert dialog on interface
        dialog.show ( );
    }


}