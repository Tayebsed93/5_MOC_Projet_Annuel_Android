package com.esgi.iaitmansour.myfoot.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.MatchGameAdapter;
import com.esgi.iaitmansour.myfoot.Models.MatchGame;
import com.esgi.iaitmansour.myfoot.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public class PlayActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private MatchGameAdapter mMatchGameAdapter;
    private ArrayList<MatchGame> mMatchGameListPasZero = new ArrayList<> ( );
    private ArrayList<MatchGame> mMatchGameList;
    private RequestQueue mRequestQueue;

    static String nameFB = "nomFB";
    static String urlFB = "UrlFB";
    static String emailFB = "EmailFB";
    static String apiKey = "";
    static String nomClub = "nomClub";
    static String twitter = "twitter";
    static int userId = 0;

    private Button mLogOutButton;
    private ImageView mPhotoImageView;
    private TextView mUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_play);


        mUserName = findViewById (R.id.tvUser);
        mLogOutButton = findViewById (R.id.logout_button);
        mPhotoImageView = findViewById (R.id.imageViewFB);


        if (getIntent ( ).hasExtra ("NAME") && getIntent ( ).hasExtra ("EMAIL") && getIntent ( ).hasExtra ("PHOTO_URL") && getIntent ( ).hasExtra ("nameClub") && getIntent ( ).hasExtra ("userId") && getIntent ( ).hasExtra ("twitter")) {
            String iName = getIntent ( ).getStringExtra ("NAME");
            String iEmail = getIntent ( ).getStringExtra ("EMAIL");
            String iPhotoUrl = getIntent ( ).getStringExtra ("PHOTO_URL");
            String nc = getIntent ( ).getStringExtra ("nameClub");
            int userIdd = getIntent ( ).getIntExtra ("userId", 0);
            twitter = getIntent ( ).getStringExtra ("twitter");

            nameFB = iName;
            emailFB = iEmail;
            urlFB = iPhotoUrl;
            nomClub = nc;
            userId = userIdd;
        }


        if (getIntent ( ).hasExtra ("name") && getIntent ( ).hasExtra ("email") && getIntent ( ).hasExtra ("img")) {
            String iName = getIntent ( ).getStringExtra ("name");
            String iEmail = getIntent ( ).getStringExtra ("email");
            String iPhotoUrl = getIntent ( ).getStringExtra ("img");

            nameFB = iName;
            emailFB = iEmail;
            urlFB = iPhotoUrl;
        }


        mUserName.setText (nameFB);


        //Use Picasso library to load the profil image fb of the user
        Picasso.with (this).load (urlFB).placeholder (R.mipmap.ic_launcher).into (mPhotoImageView);

        mLogOutButton.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                logOut ( );


            }
        });

        mRecyclerView = findViewById (R.id.recycler_view_competition);
        mRecyclerView.setHasFixedSize (true);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (this));

        mMatchGameList = new ArrayList<> ( );
        mRequestQueue = Volley.newRequestQueue (this);
        register ( );

        getApiKey (emailFB, "azerty");

    }


    //Funtion to logout the user from facebook
    private void logOut() {
        LoginManager.getInstance ( ).logOut ( );
        Intent intent = new Intent (PlayActivity.this, SupporterActivity.class);
        intent.putExtra ("nameClub", nomClub);
        intent.putExtra ("userId", userId);
        intent.putExtra ("twitter", twitter);
        startActivity (intent);

    }

    @Override
    public void onBackPressed() {

    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken ( ) == null) {
            return; // already logged out
        }

        new GraphRequest (AccessToken.getCurrentAccessToken ( ), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback ( ) {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance ( ).logOut ( );

            }
        }).executeAsync ( );
    }

    private void parseJSON() {
        String url = "http://poubelle-connecte.pe.hu/FootAPI/API/v1/competition";

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONObject> ( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray ("competitions");

                            for (int i = 0; i < jsonArray.length ( ); i++) {
                                JSONObject match = jsonArray.getJSONObject (i);

                                int id = match.getInt ("id");
                                String matchHome = match.getString ("match_home");
                                String matchAway = match.getString ("match_away");
                                String groupe = match.getString ("groupe");
                                String compositionName = match.getString ("competition_name");
                                String timeStart = match.getString ("time_start");
                                int userId = match.getInt ("user_id");

                                if (userId != 0) {
                                    mMatchGameListPasZero.add (new MatchGame (id, matchHome, matchAway, groupe, compositionName, timeStart, userId));
                                }

                                mMatchGameList.add (new MatchGame (id, matchHome, matchAway, groupe, compositionName, timeStart, userId));

                                for (int j = 0; j < mMatchGameListPasZero.size ( ); j++) {
                                    for (int k = 0; k < mMatchGameList.size ( ); k++) {
                                        if (mMatchGameListPasZero.get (j).getId ( ) == mMatchGameList.get (k).getId ( ) && mMatchGameList.get (k).getUserId ( ) == 0) {
                                            mMatchGameList.remove (k);
                                        }
                                    }
                                }

                                Collections.sort (mMatchGameList);
                            }


                            mMatchGameAdapter = new MatchGameAdapter (PlayActivity.this, mMatchGameList);
                            mRecyclerView.setAdapter (mMatchGameAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace ( );
                            ;
                        }


                    }
                }
                , new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace ( );
            }

        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String> ( );
                params.put ("Authorization", apiKey);
                return params;


            }

        };


        mRequestQueue.add (request);
    }

    private void register() {
        String url = "http://poubelle-connecte.pe.hu/FootAPI/API/v1/register";
        StringRequest postRequest = new StringRequest (Request.Method.POST, url,
                new Response.Listener<String> ( ) {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d ("Response", response);
                    }
                },
                new Response.ErrorListener ( ) {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d ("Error.Response", "Une erreur s'est produite");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String> ( );
                params.put ("name", nameFB);
                params.put ("email", emailFB);
                params.put ("password", "azerty");
                params.put ("role", "supporter");
                params.put ("picture", urlFB);

                return params;
            }
        };
        mRequestQueue.add (postRequest);
    }

    private class ResponseApiKey {
        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("apiKey")
        @Expose
        private String apiKey;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public ResponseApiKey() {
        }

        public ResponseApiKey(Boolean error, String name, String email, String apiKey, String createdAt) {
            this.error = error;
            this.name = name;
            this.email = email;
            this.apiKey = apiKey;
            this.createdAt = createdAt;
        }

        public Boolean getError() {
            return error;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getApiKey() {
            return apiKey;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }

    public interface ApiKeyClient {

        @Multipart
        @POST("loginfb")
        Call<ResponseApiKey> checkLogin(@Part("email") RequestBody email,
                                        @Part("password") RequestBody password);
    }


    //Function to get the apikey of the user
    private void getApiKey(String email, String password) {


        RequestBody emailPart = RequestBody.create (MultipartBody.FORM, email);
        RequestBody passwordPart = RequestBody.create (MultipartBody.FORM, password);


        Retrofit.Builder builder = new Retrofit.Builder ( )
                .baseUrl ("http://poubelle-connecte.pe.hu/FootAPI/API/v1/")
                .addConverterFactory (GsonConverterFactory.create ( ));

        Retrofit retrofit = builder.build ( );

        ApiKeyClient client = retrofit.create (ApiKeyClient.class);
        Call<ResponseApiKey> call = client.checkLogin (emailPart, passwordPart);

        call.enqueue (new Callback<ResponseApiKey> ( ) {
            @Override
            public void onResponse(Call<ResponseApiKey> call, retrofit2.Response<ResponseApiKey> response) {
                apiKey = response.body ( ).getApiKey ( );
                parseJSON ( );
            }

            @Override
            public void onFailure(Call<ResponseApiKey> call, Throwable t) {

                Toast.makeText (PlayActivity.this, "La requête s'est mal déroulée", Toast.LENGTH_SHORT).show ( );

            }
        });


    }


}

