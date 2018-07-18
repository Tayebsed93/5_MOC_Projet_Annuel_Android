package com.esgi.iaitmansour.myfoot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Adapter.NewsAdapter;
import com.esgi.iaitmansour.myfoot.Models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<News> mNewsList;
    private RequestQueue mRequestQueue;
    TextView empty;

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

        mNewsList = new ArrayList<>();

        mRecyclerView = rootView.findViewById(R.id.news_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON(userId);


        return rootView;
    }








    private void parseJSON(int userId){
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


                                mNewsList.add(new News(title, content, imgUrl));

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
