package com.esgi.iaitmansour.myfoot.Fragments;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.CompoAdapter;
import com.esgi.iaitmansour.myfoot.Models.CompositionMatch;
import com.esgi.iaitmansour.myfoot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetailsMatchCompoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CompoAdapter mCompositionAdapter;
    private ArrayList<CompositionMatch> mCompositionMatchList;
    private RequestQueue mRequestQueue;


    String midMatch;


    public DetailsMatchCompoFragment() {
        // Required empty public constructor
    }


    public static DetailsMatchCompoFragment newInstance(String match_id) {
        Bundle bundle = new Bundle ( );
        bundle.putString ("match_id", match_id);


        DetailsMatchCompoFragment fragment = new DetailsMatchCompoFragment ( );
        fragment.setArguments (bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            midMatch = bundle.getString ("match_id");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate (R.layout.fragment_details_match_compo, container, false);


        mRecyclerView = viewRoot.findViewById (R.id.recycler_view_compo);
        mRecyclerView.setHasFixedSize (true);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (getContext ( )));
        mCompositionMatchList = new ArrayList<> ( );


        mRequestQueue = Volley.newRequestQueue (getContext ( ));


        readBundle (getArguments ( ));

        getDataByMatch (midMatch);
        getDataByMatchAway (midMatch);


        return viewRoot;
    }


    public void getDataByMatch(String idMatch) {

        mCompositionMatchList = new ArrayList<> ( );


        String url = "https://apifootball.com/api/?action=get_events&match_id=" + idMatch + "&APIkey=1efa2ed903e36f30a5c119f4391b1ca327e8f3405305dab81f21d613fe593144";


        JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONArray> ( ) {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject (0);
                            JSONObject lineupObject = jsonObject.getJSONObject ("lineup");
                            JSONObject lineupHomee = lineupObject.getJSONObject ("home");

                            JSONArray json = lineupHomee.getJSONArray ("starting_lineups");

                            for (int i = 0; i < json.length ( ); i++) {


                                JSONObject lineupHome = json.getJSONObject (i);

                                String player = lineupHome.getString ("lineup_player");
                                String number = lineupHome.getString ("lineup_number");


                                CompositionMatch compositionMatch = new CompositionMatch (player, number);

                                mCompositionMatchList.add (compositionMatch);


                            }
                            mCompositionAdapter = new CompoAdapter (getContext ( ), mCompositionMatchList);


                            if (mCompositionMatchList.isEmpty ( )) {
                                mRecyclerView.setVisibility (View.GONE);
                            } else {
                                mRecyclerView.setVisibility (View.VISIBLE);
                                mRecyclerView.setAdapter (mCompositionAdapter);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace ( );

                        }


                    }
                }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace ( );
            }
        });

        mRequestQueue.add (request);


    }


    public void getDataByMatchAway(String idMatch) {

        mCompositionMatchList = new ArrayList<> ( );


        String url = "https://apifootball.com/api/?action=get_events&match_id=" + idMatch + "&APIkey=1efa2ed903e36f30a5c119f4391b1ca327e8f3405305dab81f21d613fe593144";


        JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONArray> ( ) {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject (0);
                            JSONObject lineupObject = jsonObject.getJSONObject ("lineup");
                            JSONObject lineupHomee = lineupObject.getJSONObject ("away");

                            JSONArray json = lineupHomee.getJSONArray ("starting_lineups");

                            for (int i = 0; i < json.length ( ); i++) {


                                JSONObject lineupHome = json.getJSONObject (i);

                                String player = lineupHome.getString ("lineup_player");
                                String number = lineupHome.getString ("lineup_number");


                                CompositionMatch compositionMatch = new CompositionMatch (player, number);

                                mCompositionMatchList.add (compositionMatch);


                            }
                            mCompositionAdapter = new CompoAdapter (getContext ( ), mCompositionMatchList);
                            mRecyclerView.setAdapter (mCompositionAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace ( );

                        }


                    }
                }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace ( );
            }
        });

        mRequestQueue.add (request);


    }

}
