package com.esgi.iaitmansour.myfoot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class GameFragment extends Fragment {


    private LoginButton mLoginButton;
    private CallbackManager callbackManager;
    String nameClub = "";
    int userId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        SupporterActivity activity = (SupporterActivity) getActivity();
        String nomClub = activity.getMyData();
        nameClub = nomClub;
        userId = activity.getMyUserId ();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);




        disconnectFromFacebook();
        //assign call back manager
        callbackManager = CallbackManager.Factory.create();

        //find the login button
        mLoginButton = rootView.findViewById(R.id.login_button);
        mLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        mLoginButton.setFragment(this);

        logInCallBackRegister();

        return rootView;

    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }



    private void logInCallBackRegister(){


        // Callback registration
        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                //use profile tracker to get user public info, except email and gender which you will have to use graph request
                //getFbInfoWithProfileTracker();

                //we use this to get user email as well as the rest of the info also included using the profile tracker
                getUserProfileWithGraphRequest(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getContext(), "login canceled by user", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getContext(), "Error " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }



    /**get public and private data like fetch email by requesting user permissions**/
    private void getUserProfileWithGraphRequest(AccessToken accessToken){
        //Toast.makeText(MainActivity.this,"Please Wait...",Toast.LENGTH_SHORT).show();
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String email_id = object.getString("email");
                            String profile_name = object.getString("name");
                            String fb_id = object.getString("id");
                            //get photo URL and convert to string, make sure you use https as facebook photo with android can cause problems
                            String photoUrl = "https://graph.facebook.com/"+fb_id+"/picture?type=large";
                            //String photoUrl = img_value.toString();


                            goToViewUserProfileActivity(email_id, profile_name, fb_id, photoUrl);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void goToViewUserProfileActivity(String email, String name, String id,  String photoUrl){
        Intent goToFbUserActivity = new Intent(getActivity(), PlayActivity.class);

        goToFbUserActivity.putExtra("EMAIL", email);
        goToFbUserActivity.putExtra("NAME", name);
        goToFbUserActivity.putExtra("ID", id);
        goToFbUserActivity.putExtra("PHOTO_URL", photoUrl);
        goToFbUserActivity.putExtra("nameClub", nameClub);
        goToFbUserActivity.putExtra("userId", userId);

        startActivity(goToFbUserActivity);

    }


}







