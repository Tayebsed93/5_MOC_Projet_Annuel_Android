package com.esgi.iaitmansour.myfoot.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.esgi.iaitmansour.myfoot.Adapter.PlayersAdapter;
import com.esgi.iaitmansour.myfoot.Models.Players;
import com.esgi.iaitmansour.myfoot.R;
import com.esgi.iaitmansour.myfoot.Utils.ItemClickSupport;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class PlayersNationalityActivity extends AppCompatActivity {


    private ArrayList<Players> mPlayersList;
    private RecyclerView mRecyclerView;
    private PlayersAdapter mPlayersAdapter;
    String apiKey = PlayActivity.apiKey;
    String pays = TerrainActivity.pays;
    String paysd = TerrainDeuxActivity.pays;
    String[] compo = TerrainActivity.compositionArray;
    String[] compod = TerrainDeuxActivity.compositionArray;
    String compoo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_players_nationality);

        mPlayersList = new ArrayList<> ( );
        mRecyclerView = findViewById (R.id.recycler_view_players_list);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (this));


        Intent intent = getIntent ( );
        int button = intent.getIntExtra ("button", 0);
        String position = intent.getStringExtra ("position");
        compoo = intent.getStringExtra ("compo");

        if (compoo.equals ("442")) {
            sendNetworkRequest (pays, position);
        }
        if (compoo.equals ("433")) {
            sendNetworkRequest (paysd, position);
        }


        this.configureOnClickRecyclerView (button);

    }


    //**************Class Response********************************////
    public class ResponsePlayers {


        @SerializedName("players")
        private ArrayList<Playerss> mPlayers;

        public ArrayList<Playerss> getmPlayers() {
            return mPlayers;
        }
    }


    //**************Class MODEL********************************////
    public class Playerss {

        @SerializedName("id")
        private int id;

        @SerializedName("Name")
        private String name;

        @SerializedName("Age")
        private int age;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    //**************Interface********************************////
    public interface GetPlayersService {

        //@Headers("Authorization: 92496786a8a2b0a155900a7436451972")
        @Multipart
        @POST("player")
        Call<ResponsePlayers> uploadPlayers(
                @Header("Authorization") String header,
                @Part("nationality") RequestBody nationality,
                @Part("position") RequestBody position

        );
    }

    //**************Request Retrofit Multipart********************************////

    private void sendNetworkRequest(String nationality, String position) {


        RequestBody nationalityPart = RequestBody.create (MultipartBody.FORM, nationality);
        RequestBody positionPart = RequestBody.create (MultipartBody.FORM, position);


        Retrofit.Builder builder = new Retrofit.Builder ( )
                .baseUrl ("http://poubelle-connecte.pe.hu/FootAPI/API/v1/")
                .addConverterFactory (GsonConverterFactory.create ( ));

        Retrofit retrofit = builder.build ( );

        GetPlayersService service = retrofit.create (GetPlayersService.class);

        Call<ResponsePlayers> call = service.uploadPlayers (apiKey, nationalityPart, positionPart);

        call.enqueue (new Callback<ResponsePlayers> ( ) {
            @Override
            public void onResponse(Call<ResponsePlayers> call, Response<ResponsePlayers> response) {

                if (response.code ( ) == 200) {


                    for (int i = 0; i < response.body ( ).getmPlayers ( ).size ( ); i++) {
                        String name = response.body ( ).getmPlayers ( ).get (i).getName ( );
                        int age = response.body ( ).getmPlayers ( ).get (i).getAge ( );


                        //Check the compo that the user has chosen
                        //if it's 4-4-2
                        if (compoo.equals ("442")) {
                            //Check if the player ar always selected by the user
                            if (Arrays.asList (compo).contains (name)) {

                            } else {
                                mPlayersList.add (new Players (name, age, pays));
                            }
                        }

                        //if it's 4-4-2
                        if (compoo.equals ("433")) {
                            if (Arrays.asList (compod).contains (name)) {

                            } else {
                                mPlayersList.add (new Players (name, age, paysd));
                            }
                        }


                    }


                    mPlayersAdapter = new PlayersAdapter (PlayersNationalityActivity.this, mPlayersList);
                    mRecyclerView.setAdapter (mPlayersAdapter);


                } else {
                    Toast.makeText (PlayersNationalityActivity.this, "Erreur lors de l'ajout :" + response.code ( ), Toast.LENGTH_SHORT).show ( );

                }

            }

            @Override
            public void onFailure(Call<ResponsePlayers> call, Throwable t) {

            }
        });


    }


    //**************Onclick item Player***************************************//////
    private void configureOnClickRecyclerView(final int idButton) {
        ItemClickSupport.addTo (mRecyclerView, R.layout.single_players_item)
                .setOnItemClickListener (new ItemClickSupport.OnItemClickListener ( ) {

                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Players player = mPlayersAdapter.getPlayers (position);
                        Intent intent = new Intent ( );
                        intent.putExtra ("playerSelect", player.getName ( ));
                        intent.putExtra ("idButton", idButton);
                        setResult (Activity.RESULT_OK, intent);
                        finish ( );


                    }

                });
    }


}
