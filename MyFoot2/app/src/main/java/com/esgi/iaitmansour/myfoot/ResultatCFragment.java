package com.esgi.iaitmansour.myfoot;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.ClassementAdapter;
import com.esgi.iaitmansour.myfoot.Models.Classement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ResultatCFragment extends Fragment {


    private RecyclerView mList;
    ArrayList arrayNameClub = new ArrayList<String>();

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Classement> classementList;

    int tmpp = 0 ;
    private String urlLigue1 = "https://apifootball.com/api/?action=get_standings&league_id=127&APIkey=1efa2ed903e36f30a5c119f4391b1ca327e8f3405305dab81f21d613fe593144";
    private String urlLigue2 = "https://apifootball.com/api/?action=get_standings&league_id=128&APIkey=1efa2ed903e36f30a5c119f4391b1ca327e8f3405305dab81f21d613fe593144";

    String parametreUrl = "";


    private RecyclerView.Adapter adapter;
    String nameClub = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        getLeague1Team();
        SupporterActivity activity = (SupporterActivity) getActivity();
        String nomClub = activity.getMyData();
        nameClub = nomClub;

        View rootView = inflater.inflate(R.layout.fragment_resultat_c, container, false);


        mList = rootView.findViewById(R.id.classement_list);

        classementList = new ArrayList<>();
        adapter = new ClassementAdapter(getActivity(),classementList, nameClub);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);




        return rootView;
    }









    private void getLeague2Team(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlLigue2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                        arrayNameClub.add(jsonObject.getString("team_name"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }


                for(int i = 0; i<arrayNameClub.size(); i++){

                    if(arrayNameClub.get(i).equals(nameClub)){
                        tmpp++;
                    }


                }


                if(tmpp == 1){

                    parametreUrl="128";
                    getData(parametreUrl);
                }
                else{

                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);


    }












    private void getLeague1Team(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlLigue1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                        arrayNameClub.add(jsonObject.getString("team_name"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }


                for(int i = 0; i<arrayNameClub.size(); i++){

                    if(arrayNameClub.get(i).equals(nameClub)){
                        tmpp++;
                    }


                }




                if(tmpp == 1){

                    parametreUrl="127";
                    getData(parametreUrl);
                }
                else{

                    getLeague2Team();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }















    private void getData(String parametreUrl) {

        String url = "https://apifootball.com/api/?action=get_standings&league_id="+parametreUrl+"&APIkey=1efa2ed903e36f30a5c119f4391b1ca327e8f3405305dab81f21d613fe593144";

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Classement classement = new Classement();

                        classement.setOverall_league_position(jsonObject.getString("overall_league_position"));
                        classement.setTeam_name(jsonObject.getString("team_name"));
                        classement.setOverall_league_payed(jsonObject.getString("overall_league_payed"));
                        classement.setOverall_league_W(jsonObject.getString("overall_league_W"));
                        classement.setOverall_league_D(jsonObject.getString("overall_league_D"));
                        classement.setOverall_league_L(jsonObject.getString("overall_league_L"));
                        classement.setOverall_league_GF(jsonObject.getString("overall_league_GF"));
                        classement.setOverall_league_GA(jsonObject.getString("overall_league_GA"));
                        classement.setOverall_league_PTS(jsonObject.getString("overall_league_PTS"));


                        classementList.add(classement);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

}
