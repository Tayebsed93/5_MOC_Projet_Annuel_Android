package com.esgi.iaitmansour.myfoot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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
import com.esgi.iaitmansour.myfoot.Adapter.DetailsResultatAdapter;
import com.esgi.iaitmansour.myfoot.Models.ScorerMatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetailsMatchScorerFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private DetailsResultatAdapter mDetailsResultatAdapter;
    private ArrayList<ScorerMatch> mScorerMatchList;
    private RequestQueue mRequestQueue;



    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;

    String midMatch;



    public DetailsMatchScorerFragment() {
        // Required empty public constructor

    }




    public static DetailsMatchScorerFragment newInstance(String match_id) {
        Bundle bundle = new Bundle();
        bundle.putString("match_id", match_id);


        DetailsMatchScorerFragment fragment = new DetailsMatchScorerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            midMatch = bundle.getString("match_id");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_details_match_scorer, container, false);


        mRecyclerView = viewRoot.findViewById(R.id.recycler_view_details_resultat);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mScorerMatchList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getContext());




        readBundle(getArguments());



        getDataByMatch(midMatch);




        return viewRoot;
    }


    public void getDataByMatch(String idMatch){

        mScorerMatchList = new ArrayList<>();


        String url ="https://apifootball.com/api/?action=get_events&match_id="+idMatch+"&APIkey=1efa2ed903e36f30a5c119f4391b1ca327e8f3405305dab81f21d613fe593144";



        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            JSONArray json = jsonObject.getJSONArray("goalscorer");

                            for(int i =0; i<json.length(); i++){



                                JSONObject but = json.getJSONObject(i);

                                String time = but.getString("time");
                                String homeScorer = but.getString("home_scorer");
                                String awayScorer = but.getString("away_scorer");


                                ScorerMatch scorerMatch = new ScorerMatch(time,homeScorer,awayScorer);

                                mScorerMatchList.add(scorerMatch);


                            }
                            mDetailsResultatAdapter = new DetailsResultatAdapter(getContext(), mScorerMatchList);
                            mRecyclerView.setAdapter(mDetailsResultatAdapter);



                            //adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

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
