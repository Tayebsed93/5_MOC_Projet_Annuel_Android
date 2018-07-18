package com.esgi.iaitmansour.myfoot;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.iaitmansour.myfoot.Utils.CreateClubDataService;
import com.esgi.iaitmansour.myfoot.Utils.FileUtils;
import com.esgi.iaitmansour.myfoot.Utils.ServiceGenerator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddClubActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    static final int VERIFY_LICENCE = 2;
    private int PICK_IMAGE_FROM_GALLERY_REQUESTT = 2;
    EditText mNamePresident;
    EditText mNameClub;
    private Button mLicense;
    int verificationOK = 0;
    int verificationLogo = 0;
    String messageErreur ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_club);



        String title = "Liste des clubs";
        Toolbar mToolbar = findViewById(R.id.toolbar_add_club);
        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mLicense = (Button) findViewById(R.id.licenseButton);
        mNameClub = (EditText) findViewById(R.id.nameClub);
        mNamePresident = (EditText) findViewById(R.id.namePresident);
        mLicense.setBackgroundResource (R.drawable.border_grey);
        mNameClub.addTextChangedListener (addClubWatcher);
        mNamePresident.addTextChangedListener (addClubWatcher);
        final EditText mEmail = (EditText) findViewById(R.id.email);
        final EditText mPassword = (EditText) findViewById(R.id.password);


        mLicense.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (), RecognizeLicenceActivity.class);
                intent.putExtra ("NomPresident" , mNamePresident.getText ().toString ());
                intent.putExtra ("NomClub", mNameClub.getText ().toString ());
                startActivityForResult (intent, VERIFY_LICENCE);
            }
        });


        Button mCreateClub = (Button) findViewById(R.id.createClubButton);

        mCreateClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String verif = verificationChamps (mNameClub,mNamePresident,mEmail,mPassword,verificationLogo,verificationOK);

                if(verif ==""){
                    uploadFile ();


                }
                else{

                    new AlertDialog.Builder( AddClubActivity.this)
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

        if (ContextCompat.checkSelfPermission(AddClubActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddClubActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }


        Button mLogo = (Button) findViewById(R.id.logoButton);
        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra ("LogoOk",1);
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);


            }
        });




    }

    private String verificationChamps(EditText NomClub , EditText NomPresident, EditText email ,EditText password, int logo, int licence){

        String messageErreur ="";
        if(logo == 0 ){
            messageErreur += "Logo manquant \n";
        }
        if(licence == 0){
            messageErreur += "Verification de Licence non effectuée\n";
        }
        if(!isEmailValid(email.getText().toString())){
            messageErreur+= "Email manquant ou invalide\n";
        }
        if(NomClub.getText ().toString ().isEmpty ()){
            messageErreur+="Nom de club manquant\n";
        }
        if(NomPresident.getText ().toString ().isEmpty ()){
            messageErreur+="Nom du président manquant\n";
        }
        if(password.getText ().toString ().isEmpty ()){
            messageErreur+="Nom du président manquant\n";
        }

        return messageErreur;

    }

    private TextWatcher addClubWatcher = new TextWatcher ( ) {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String namePresidentInput = mNamePresident.getText ().toString ().trim ();
            String nameClubInput = mNameClub.getText ().toString ().trim ();

            if(!namePresidentInput.isEmpty () && !nameClubInput.isEmpty ()){
                mLicense.setEnabled (true);
                mLicense.setBackgroundResource (R.drawable.border);
            }
            else{
                mLicense.setEnabled (false);
                mLicense.setBackgroundResource (R.drawable.border_grey);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    Uri uri = null;
    Uri uriLicense = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if((requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() !=null)){
            uri = data.getData();
            verificationLogo = PICK_IMAGE_FROM_GALLERY_REQUEST;
        }

        if(requestCode==VERIFY_LICENCE)
        {

            verificationOK = VERIFY_LICENCE;


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Licence");
            builder.setMessage("Verification de licence réussie ! ");
            builder.setCancelable(true);

            final AlertDialog dlg = builder.create();

            dlg.show();

            final Timer t = new Timer ();
            t.schedule(new TimerTask () {
                public void run() {
                    dlg.dismiss();
                    t.cancel();
                }
            }, 2000);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]grantResults){
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST:{
                if(grantResults.length >0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else{

                }
            }
            return;
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public class ResponseUpload{

        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("message_user")
        @Expose
        private String messageUser;
        @SerializedName("message")
        @Expose
        private String message;


        public String getMessageUser() {
            return messageUser;
        }

        public Boolean getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }

    //Upload new club function
    private void uploadFile(){

        // create upload service client
        CreateClubDataService service =
                ServiceGenerator.createService(CreateClubDataService.class);


        File file = FileUtils.getFile(this, uri);
        File fileLicense = FileUtils.getFile(this, uriLicense);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(uri)),
                        file
                );


        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("logo", file.getName(), requestFile);





        final EditText mNameClub = (EditText) findViewById(R.id.nameClub);
        final EditText mNamePresident = (EditText) findViewById(R.id.namePresident);
        final EditText mEmail = (EditText) findViewById(R.id.email);
        final EditText mPassword = (EditText) findViewById(R.id.password);
        String mrole = "president";

        RequestBody nameClubPart = RequestBody.create(MultipartBody.FORM, mNameClub.getText().toString());
        RequestBody namePresidentPart = RequestBody.create(MultipartBody.FORM, mNamePresident.getText().toString());
        RequestBody emailPart = RequestBody.create(MultipartBody.FORM, mEmail.getText().toString());
        RequestBody passwordPart = RequestBody.create(MultipartBody.FORM, mPassword.getText().toString());
        RequestBody role = RequestBody.create(MultipartBody.FORM, mrole);

        // finally, execute the request
        Call<ResponseUpload> call = service.uploadPhoto(nameClubPart,namePresidentPart,emailPart,passwordPart,role,body);
        call.enqueue(new Callback<ResponseUpload>() {
            @Override
            public void onResponse(Call<ResponseUpload> call,
                                   Response<ResponseUpload> response) {



                Toast.makeText(AddClubActivity.this, response.body().getMessageUser(),
                        Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent (getApplicationContext () , MainActivity.class);
                    startActivity (intent);


            }

            @Override
            public void onFailure(Call<ResponseUpload> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });

    }


}
