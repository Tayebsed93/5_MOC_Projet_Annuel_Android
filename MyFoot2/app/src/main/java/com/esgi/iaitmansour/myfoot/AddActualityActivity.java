
package com.esgi.iaitmansour.myfoot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.iaitmansour.myfoot.Utils.AutoWrapFilter;
import com.esgi.iaitmansour.myfoot.Utils.FileUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

import okhttp3.MediaType;
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

import static com.esgi.iaitmansour.myfoot.AddClubActivity.isEmailValid;

public class AddActualityActivity extends AppCompatActivity {

    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    Uri uri = null;
    String apiKey ="";
    String nameClub="";
    int userID= 0;
    int verificationOk = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_actuality);


        Intent i = getIntent();
        String apiKeyi = i.getStringExtra("apiKey");
        final int idClub = i.getIntExtra ("userId",0);
        String nomCmub = i.getStringExtra ("nomClub");

        nameClub=nomCmub;
        userID = idClub;
        apiKey = apiKeyi;

        //********CONFIGURE TOOLBAR************//
        //String title = "Back";
        Toolbar mToolbar = findViewById(R.id.toolbar_add_news);
        //mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //********CONFIGURE TOOLBAR************//


        final EditText mContent = findViewById(R.id.content_news);
        final EditText mTitle = findViewById(R.id.title_news);
        Button btnSend = findViewById(R.id.btn_send_news);
        Button imgBtn = findViewById(R.id.file_news);



        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLicense = new Intent();
                intentLicense.setType("image/*");
                intentLicense.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intentLicense, "Select Picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String verif = verificationChamps (mTitle,mContent, verificationOk);

                if(verif ==""){
                    String title = mTitle.getText().toString();
                    String content = mContent.getText().toString();
                    sendNetworkRequest(title,content,uri);

                    final Handler handler = new Handler ();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddActualityActivity.this, "Actualité ajoutée avec succès ! ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (getApplicationContext (),MembreActualityActivity.class);
                            intent.putExtra ("clubId",userID);
                            intent.putExtra ("ApiKeyReturn",apiKey);
                            intent.putExtra ("nomClubReturn",nameClub);
                            startActivity (intent);
                        }
                    }, 2000);


                }
                else{

                    new AlertDialog.Builder( AddActualityActivity.this)
                            .setTitle( "Erreur" )
                            .setMessage(verif)
                            .setPositiveButton( "Annuler", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d( "AlertDialog", "Positive" );
                                }
                            })
                            .show();
                }


            }
        });



    }


    //*******************************VERIFICATION DES CHAMPS****************************************//

    private String verificationChamps(EditText title , EditText content, int photo){

        String messageErreur ="";


        if(title.getText ().toString ().isEmpty ()){
            messageErreur+="Titre manquant\n";
        }
        if(content.getText ().toString ().isEmpty ()){
            messageErreur+="Contenu manquant\n";
        }
        if(photo == 0 ){
            messageErreur += "Image manquante \n";
        }

        return messageErreur;

    }


    //**************URI IMAGE********************************////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST) {
            uri = data.getData();
            verificationOk=PICK_IMAGE_FROM_GALLERY_REQUEST;
        }
    }




    //**************Class Response********************************////
    public class ResponseUploadNews {


        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("message")
        @Expose
        private String message;

        public Boolean getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }



    //**************Interface********************************////
    public interface CreateNewsDataService {

        //@Headers("Authorization: 92496786a8a2b0a155900a7436451972")
        @Multipart
        @POST("actuality")
        Call<ResponseUploadNews> uploadNews(
                @Header("Authorization") String header,
                @Part("title") RequestBody title,
                @Part("content")RequestBody content,
                @Part MultipartBody.Part photo

        );
    }


    //**************Request Retrofit Multipart********************************////

    private void sendNetworkRequest(String title, String content, Uri photo){


        File file = FileUtils.getFile(this, photo);



        //RequestBody apiKeyPart = RequestBody.create(MultipartBody.FORM, apiKey);
        RequestBody titlePart = RequestBody.create(MultipartBody.FORM, title);
        RequestBody contentPart = RequestBody.create(MultipartBody.FORM, content);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(photo)),
                        file
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://poubelle-connecte.pe.hu/FootAPI/API/v1/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        AddActualityActivity.CreateNewsDataService service = retrofit.create(AddActualityActivity.CreateNewsDataService.class);


        Call<ResponseUploadNews> call = service.uploadNews(apiKey,titlePart,contentPart,body);

        call.enqueue(new Callback<ResponseUploadNews>() {
            @Override
            public void onResponse(Call<ResponseUploadNews> call, Response<ResponseUploadNews> response) {
                if(response.code()==201){
                    Toast.makeText(AddActualityActivity.this, "Actualité ajoutée avec succès ! ", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(AddActualityActivity.this, "Erreur lors de l'ajout :(", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseUploadNews> call, Throwable t) {

            }
        });



    }






}
