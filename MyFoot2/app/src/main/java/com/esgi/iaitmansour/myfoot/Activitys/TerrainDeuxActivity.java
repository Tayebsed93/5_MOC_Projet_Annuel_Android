package com.esgi.iaitmansour.myfoot.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.R;

import java.util.HashMap;
import java.util.Map;



public class TerrainDeuxActivity extends AppCompatActivity implements View.OnClickListener {


    String apiKey = PlayActivity.apiKey;
    String email = PlayActivity.emailFB;
    String name = PlayActivity.nameFB;
    String img = PlayActivity.urlFB;


    static  String position="";
    static String pays="";
    Integer competition=0;
    private RequestQueue mRequestQueue;
    String compo ="";
    static String [] compositionArray = new String[11];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_terrain_deux);



        pays = getIntent ().getStringExtra ("pays");
        competition = getIntent ().getIntExtra ("competition_id",0);
        compo = getIntent ().getStringExtra ("compo");

        mRequestQueue = Volley.newRequestQueue(this);




        Button player1 = findViewById (R.id.player12d);
        player1.setOnClickListener (this);
        Button player2 = findViewById (R.id.player2d);
        player2.setOnClickListener (this);
        Button player3 = findViewById (R.id.player3d);
        player3.setOnClickListener (this);
        Button player4 = findViewById (R.id.player4d);
        player4.setOnClickListener (this);
        Button player5 = findViewById (R.id.player5d);
        player5.setOnClickListener (this);
        Button player6 = findViewById (R.id.player6d);
        player6.setOnClickListener (this);
        Button player7 = findViewById (R.id.player7d);
        player7.setOnClickListener (this);
        Button player8 = findViewById (R.id.player8d);
        player8.setOnClickListener (this);
        Button player9 = findViewById (R.id.player9d);
        player9.setOnClickListener (this);
        Button player10 = findViewById (R.id.player10d);
        player10.setOnClickListener (this);
        Button player11 = findViewById (R.id.player11d);
        player11.setOnClickListener (this);

        Button btnValidate = findViewById (R.id.btnValiderd);
        btnValidate.setOnClickListener (new View.OnClickListener ( ) {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                int tmp = 0;
                for(int i= 0; i<compositionArray.length; i++){
                    if(compositionArray[i] == null){
                        tmp++;
                    }
                }

                if(tmp != 0){
                    Toast.makeText(TerrainDeuxActivity.this, "Merci de rentrer tous les joueurs", Toast.LENGTH_SHORT).show();

                }
                else{


                    String compo = "4-3-3" + String.join ("; ",compositionArray);
                    parseJSON (compo,pays,competition);


                }


            }
        });


        Button btnExit = findViewById (R.id.btnExitd);
        btnExit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                compositionArray=new String[11];
                Intent intent = new Intent (TerrainDeuxActivity.this, PlayActivity.class);
                startActivity (intent);
            }
        });

    }


    @Override
    public void onBackPressed() {

    }


    public void goToPlayersActivity(int id, String position){

        Intent intent = new Intent(this, PlayersNationalityActivity.class);
        intent.putExtra ("button", id );
        intent.putExtra ("position", position );
        intent.putExtra ("compo",compo);
        startActivityForResult (intent,1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){

                String joueur = data.getStringExtra ("playerSelect");
                int idButton = data.getIntExtra ("idButton",0);

                String StringGenerated = String.valueOf(joueur.toLowerCase().replaceAll("([^a-zA-Z]|\\s)+", ""));
                int id = this.getResources().getIdentifier(StringGenerated, "drawable", this.getPackageName());


                Button btn = findViewById (idButton);
                if(id!=0) {
                    btn.setBackgroundResource (id);
                }
                else{
                    String player = "player";
                    int idDefault = this.getResources().getIdentifier(player, "drawable", this.getPackageName());
                    btn.setBackgroundResource (idDefault);

                }



                switch (idButton){
                    case R.id.player12d:
                        compositionArray[0] = joueur;
                        break;
                    case R.id.player2d:
                        compositionArray[1] = joueur;
                        break;
                    case R.id.player3d :
                        compositionArray[2] = joueur;
                        break;
                    case R.id.player4d :
                        compositionArray[3] = joueur;
                        break;
                    case R.id.player5d :
                        compositionArray[4] = joueur;
                        break;
                    case R.id.player6d :
                        compositionArray[5] = joueur;
                        break;
                    case R.id.player7d :
                        compositionArray[6] = joueur;
                        break;
                    case R.id.player8d :
                        compositionArray[7] = joueur;
                        break;
                    case R.id.player9d :
                        compositionArray[8] = joueur;
                        break;
                    case R.id.player10d :
                        compositionArray[9] = joueur;
                        break;
                    case R.id.player11d :
                        compositionArray[10] = joueur;
                        break;

                }

            }

        }

    }



    @Override
    public void onClick(View v) {

        switch (v.getId ()){
            case R.id.player12d:
                goToPlayersActivity (v.getId (), "GK");
                break;
            case R.id.player2d:
                goToPlayersActivity (v.getId (),"DEF");
                break;
            case R.id.player3d :
                goToPlayersActivity (v.getId (),"DEF");
                break;
            case R.id.player4d :
                goToPlayersActivity (v.getId (),"DEF");
                break;
            case R.id.player5d :
                goToPlayersActivity (v.getId (),"DEF");
                break;
            case R.id.player6d :
                goToPlayersActivity (v.getId (),"MIL");
                break;
            case R.id.player7d :
                goToPlayersActivity (v.getId (),"MIL");
                break;
            case R.id.player8d :
                goToPlayersActivity (v.getId (),"MIL");
                break;
            case R.id.player9d :
                goToPlayersActivity (v.getId (),"AT");
                break;
            case R.id.player10d :
                goToPlayersActivity (v.getId (),"AT");
                break;
            case R.id.player11d :
                goToPlayersActivity (v.getId (),"AT");
                break;

        }

    }




    private void parseJSON(final String compo , final String nation , final int idMatch){
        String url ="http://poubelle-connecte.pe.hu/FootAPI/API/v1/composition";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }
                , new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("player",compo);
                params.put("nation",nation);
                params.put("competition_id", String.valueOf (idMatch));


                return params;
            }

            //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", apiKey);
                return params;


            }

        };


        mRequestQueue.add(request);

        Intent intent = new Intent (this, PlayActivity.class);
        intent.putExtra ("email",email);
        intent.putExtra ("name", name );
        intent.putExtra ("img",img);
        startActivity (intent);
    }




}
