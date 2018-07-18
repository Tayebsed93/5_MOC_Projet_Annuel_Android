package com.esgi.iaitmansour.myfoot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.ScoreGameAdapter;
import com.esgi.iaitmansour.myfoot.Models.UserGame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ScoreGameFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ScoreGameAdapter mScoreGameAdapter;
    private ArrayList<UserGame> mScoreGameList;
    private RequestQueue mRequestQueue;


    public ScoreGameFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_score_game, container, false);;

        mRecyclerView = rootView.findViewById(R.id.score_game_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mScoreGameList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON();
        // Inflate the layout for this fragment
        return rootView;
    }



    private void parseJSON(){
        String url ="http://poubelle-connecte.pe.hu/FootAPI/API/v1/user";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("scores");

                            for (int i = 0; i <jsonArray.length(); i++){
                                JSONObject user = jsonArray.getJSONObject(i);

                                String userName = user.getString("name");
                                int score = user.getInt("score");
                                String urlImgUser = user.getString("picture");

                                mScoreGameList.add(new UserGame(userName,score,urlImgUser));


                            }

                            mScoreGameAdapter = new ScoreGameAdapter(getContext(),mScoreGameList);
                            mRecyclerView.setAdapter(mScoreGameAdapter);

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
