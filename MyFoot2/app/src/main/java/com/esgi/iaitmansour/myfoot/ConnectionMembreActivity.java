package com.esgi.iaitmansour.myfoot;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ConnectionMembreActivity extends AppCompatActivity {

    String apiKey ="";
    String nameClubb = "";
    int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_membre);

        if(getIntent ().hasExtra ("userId") && getIntent ().hasExtra ("nameClub")){
            Intent intent = getIntent();
            String nameClub = intent.getStringExtra("nameClub");
            int user = intent.getIntExtra("userId",0);

            userId = user;
            nameClubb= nameClub;

        }
        if(getIntent ().hasExtra ("userIdRetour") && getIntent ().hasExtra ("nameClubRetour")){
            Intent intent = getIntent();
            String nameClub = intent.getStringExtra("nameClubRetour");
            int user = intent.getIntExtra("userIdRetour",0);

            userId = user;
            nameClubb= nameClub;

        }


        //********CONFIGURE TOOLBAR************//
        String title = "Liste des clubs";
        Toolbar mToolbar = findViewById(R.id.activity_membre_toolbar);
        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ConnectionMembreActivity.this, MainActivity.class);
                startActivity (intent);
            }
        });
        //********CONFIGURE TOOLBAR************//



       final EditText email = findViewById(R.id.email_membre);
       final EditText password = findViewById(R.id.pswd_membre);

        Button connexionMembre = findViewById(R.id.connexion_membre);
        connexionMembre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String memail = email.getText().toString();
                String mpassword= password.getText().toString();


                sendNetworkRequest(memail,mpassword);
            }
        });

    }





    public interface MembreClient {



        @Multipart
        @POST("login")
        Call<ResponseMembre> checkLogin(@Part("email") RequestBody email,
                                        @Part("password")RequestBody password);
    }

    public class ResponseMembre {


        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("name_club")
        @Expose
        private String nameClub;
        @SerializedName("apiKey")
        @Expose
        private String apiKey;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public String getNameClub() {
            return nameClub;
        }

        public String getMessage() {
            return message;
        }

        public Boolean getError() {
            return error;
        }

        public void setError(Boolean error) {
            this.error = error;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }



    private void sendNetworkRequest(String email, String password){

        RequestBody emailPart = RequestBody.create(MultipartBody.FORM, email);
        RequestBody passwordPart = RequestBody.create(MultipartBody.FORM, password);



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://poubelle-connecte.pe.hu/FootAPI/API/v1/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MembreClient client = retrofit.create(MembreClient.class);
        Call<ResponseMembre> call = client.checkLogin(emailPart,passwordPart);



       call.enqueue(new Callback<ResponseMembre>() {
            @Override
            public void onResponse(Call<ResponseMembre> call, Response<ResponseMembre> response) {


                if(response.body().getError() == false && response.body().getNameClub().equals(nameClubb)){
                    //Toast.makeText(ConnectionMembreActivity.this, "Voici mon api key morray : "+ response.body().getApiKey(), Toast.LENGTH_SHORT).show();
                    apiKey = response.body().getApiKey();

                    Intent intent = new Intent(ConnectionMembreActivity.this, MembreActualityActivity.class);
                    intent.putExtra("nameClub", nameClubb);
                    intent.putExtra("userId", userId);
                    intent.putExtra("apiKey", apiKey);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ConnectionMembreActivity.this, "Email ou mot de passe incorrect ! ", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseMembre> call, Throwable t) {

                Toast.makeText(ConnectionMembreActivity.this, "La requête s'est mal déroulée", Toast.LENGTH_SHORT).show();

            }
        });
    }




}

