package com.esgi.iaitmansour.myfoot.Fragments;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.ResultatAdapter;
import com.esgi.iaitmansour.myfoot.Models.Resultat;
import com.esgi.iaitmansour.myfoot.R;
import com.esgi.iaitmansour.myfoot.Activitys.SupporterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ResultatRFragment extends Fragment {

    private RecyclerView mList;
    private List<Resultat> resultatList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;

    ProgressDialog pd;
    private RecyclerView.Adapter adapter;
    String nameClub = "";

    int tmpp;
    private String urlLigue1 = "https://apifootball.com/api/?action=get_standings&league_id=127&APIkey=1efa2ed903e36f30a5c119f4391b1ca327e8f3405305dab81f21d613fe593144";
    String parametreUrl = "";

    ArrayList arrayNameClub = new ArrayList<String> ( );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {

        }

        SupporterActivity activity = (SupporterActivity) getActivity ( );
        String nomClub = activity.getMyData ( );
        nameClub = nomClub;
        pd = new ProgressDialog (getActivity ( ));
        pd.setMessage ("Chargement...");
        pd.setCancelable (false);
        pd.setCanceledOnTouchOutside (false);
        pd.show ( );

        View rootView = inflater.inflate (R.layout.fragment_resultat_r, container, false);


        getLeague1Team (rootView);

        return rootView;
    }


    private void getLeague1Team(final View view) {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (urlLigue1, new Response.Listener<JSONArray> ( ) {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length ( ); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject (i);


                        arrayNameClub.add (jsonObject.getString ("team_name"));

                    } catch (JSONException e) {
                        e.printStackTrace ( );

                    }
                }


                for (int i = 0; i < arrayNameClub.size ( ); i++) {

                    if (arrayNameClub.get (i).equals (nameClub)) {
                        tmpp++;
                    }


                }


                if (tmpp == 1) {

                    parametreUrl = "127";
                    getData (parametreUrl, view);
                } else {

                    parametreUrl = "128";
                    getData (parametreUrl, view);
                }


            }


        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e ("Volley", error.toString ( ));
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue (getActivity ( ));

        jsonArrayRequest.setRetryPolicy (new DefaultRetryPolicy (
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add (jsonArrayRequest);

    }

    private void getData(String parametreUrl, View view) {

        mList = view.findViewById (R.id.resultat_list);

        resultatList = new ArrayList<> ( );
        adapter = new ResultatAdapter (getActivity ( ), resultatList, nameClub);

        linearLayoutManager = new LinearLayoutManager (getActivity ( ));
        linearLayoutManager.setOrientation (LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration (mList.getContext ( ), linearLayoutManager.getOrientation ( ));

        mList.setHasFixedSize (true);
        mList.setLayoutManager (new LinearLayoutManager (getContext ( )));
        mList.setAdapter (adapter);

        String url = "https://apifootball.com/api/?action=get_events&from=2018-08-01&to=2018-11-09&league_id=" + parametreUrl + "&APIkey=1efa2ed903e36f30a5c119f4391b1ca327e8f3405305dab81f21d613fe593144";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (url, new Response.Listener<JSONArray> ( ) {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length ( ); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject (i);

                        Resultat resultat = new Resultat ( );

                        resultat.setLeague_name (jsonObject.getString ("league_name"));
                        resultat.setMatch_id (jsonObject.getString ("match_id"));
                        resultat.setMatch_date (jsonObject.getString ("match_date"));
                        resultat.setMatch_hometeam_name (jsonObject.getString ("match_hometeam_name"));
                        resultat.setMatch_hometeam_score (jsonObject.getString ("match_hometeam_score"));
                        resultat.setMatch_awayteam_name (jsonObject.getString ("match_awayteam_name"));
                        resultat.setMatch_awayteam_score (jsonObject.getString ("match_awayteam_score"));

                        if ((resultat.getMatch_hometeam_name ( )).equals (nameClub) || (resultat.getMatch_awayteam_name ( )).equals (nameClub)) {
                            resultatList.add (resultat);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace ( );

                    }

                }

                if (resultatList.isEmpty ( )) {
                    mList.setVisibility (View.GONE);
                } else {
                    mList.setVisibility (View.VISIBLE);
                    mList.setAdapter (adapter);
                }


                adapter.notifyDataSetChanged ( );


                pd.dismiss ( );


            }

        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e ("Volley", error.toString ( ));

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue (getActivity ( ));

        jsonArrayRequest.setRetryPolicy (new DefaultRetryPolicy (
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add (jsonArrayRequest);
    }


}
