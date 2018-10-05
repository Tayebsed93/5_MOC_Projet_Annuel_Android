package com.esgi.iaitmansour.myfoot.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.NewsAdapter;
import com.esgi.iaitmansour.myfoot.Models.News;
import com.esgi.iaitmansour.myfoot.R;
import com.esgi.iaitmansour.myfoot.Activitys.SupporterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<News> mNewsList;
    private RequestQueue mRequestQueue;
    TextView empty;
    SwipeRefreshLayout mSwipeRefreshLayout;

    int userId =0;

    public NewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        SupporterActivity activity = (SupporterActivity) getActivity();
        int user = activity.getMyUserId();
        userId=user;

        mRecyclerView = rootView.findViewById(R.id.news_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mNewsList = new ArrayList<>();

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);




        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                parseJSON (userId);
            }
        });



        return rootView;
    }



    @Override
    public void onRefresh() {

        // Fetching data from server
        mNewsList = new ArrayList<>();
        parseJSON (userId);
    }





    private void parseJSON(int userId){

        // Showing refresh animation before making http call
        mSwipeRefreshLayout.setRefreshing(true);

        String url ="http://poubelle-connecte.pe.hu/FootAPI/API/v1/actuality/"+ userId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("news");

                            for (int i = 0; i <jsonArray.length(); i++){
                                JSONObject club = jsonArray.getJSONObject(i);

                                String title = club.getString("title");
                                String content = club.getString("content");
                                String imgUrl = club.getString("photo");
                                int id = club.getInt ("id");


                                mNewsList.add(new News(title, content, imgUrl, id));

                            }


                            mNewsAdapter = new NewsAdapter(getContext(), mNewsList);

                            if (mNewsList.isEmpty()) {
                                mRecyclerView.setVisibility(View.GONE);
                                //empty.setVisibility(View.VISIBLE);
                            }
                            else {
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mRecyclerView.setAdapter(mNewsAdapter);
                                //empty.setVisibility(View.GONE);
                            }



                        } catch (JSONException e){
                            e.printStackTrace();;
                        }


                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mRequestQueue = Volley.newRequestQueue(getContext());

        mRequestQueue.add(request);
    }


}
