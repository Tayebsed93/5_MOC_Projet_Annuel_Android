package com.esgi.iaitmansour.myfoot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.NewsAdapter;
import com.esgi.iaitmansour.myfoot.Models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MembreActualityActivity extends AppCompatActivity {

    String nameClubb = "";
    int userId = 0;
    String apiKey ="";

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<News> mNewsList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membre_actuality);

        //********CONFIGURE TOOLBAR************//
        this.configureToolbar();
        //********CONFIGURE TOOLBAR************//



        if(getIntent ().hasExtra ("clubId") && getIntent ().hasExtra ("ApiKeyReturn") && getIntent ().hasExtra ("nomClubReturn")){
            Intent intent = getIntent ();
            int id= intent.getIntExtra ("clubId",0);
            String key = intent.getStringExtra ("ApiKeyReturn");
            String nomClub = intent.getStringExtra ("nomClubReturn");

            nameClubb= nomClub;
            userId = id;
            apiKey = key;
        }



        if(getIntent ().hasExtra ("nameClub") && getIntent ().hasExtra ("userId") && getIntent ().hasExtra ("apiKey")){
            Intent i = getIntent();
            String nameClub = i.getStringExtra("nameClub");
            int user = i.getIntExtra("userId",0);
            String apiKeyi = i.getStringExtra("apiKey");

            userId = user;
            nameClubb= nameClub;
            apiKey = apiKeyi;
        }



        mRecyclerView = findViewById(R.id.news_membre_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNewsList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(userId);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_add_actuality, menu);
        return true;
    }


    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar_actuality_membre);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_membre_actuality_title);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_add:


                Intent addActualityActivity = new Intent(this, AddActualityActivity.class);
                addActualityActivity.putExtra("apiKey", apiKey);
                addActualityActivity.putExtra ("userId",userId);
                addActualityActivity.putExtra ("nomClub", nameClubb);
                startActivity(addActualityActivity);
                break;

            case R.id.menu_activity_main_disconnect:

                Intent retour = new Intent(MembreActualityActivity.this, ConnectionMembreActivity.class);
                retour.putExtra ("userIdRetour",userId);
                retour.putExtra ("nameClubRetour", nameClubb);
                startActivity(retour);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void parseJSON(int userId){
        String url ="http://poubelle-connecte.pe.hu/FootAPI/API/v1/actuality/"+ userId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("news");

                            for (int i = 0; i <jsonArray.length(); i++){
                                JSONObject club = jsonArray.getJSONObject(i);

                                String title = club.getString("title");
                                String content = club.getString("content");
                                String imgUrl = club.getString("photo");


                                mNewsList.add(new News(title, content, imgUrl));

                            }


                            mNewsAdapter = new NewsAdapter(MembreActualityActivity.this, mNewsList);
                            mRecyclerView.setAdapter(mNewsAdapter);

                        } catch (JSONException e){
                            e.printStackTrace();;
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }



}
