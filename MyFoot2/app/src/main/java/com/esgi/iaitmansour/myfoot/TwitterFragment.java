package com.esgi.iaitmansour.myfoot;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

import java.util.Locale;


public class TwitterFragment extends Fragment {



    private Context context;
    private RecyclerView searchTimelineRecyclerView;
    private TweetTimelineRecyclerViewAdapter adapter;

    String nameClub = "";



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;


    }


    public TwitterFragment() {
    }

    public static TwitterFragment newInstance() {

        Bundle args = new Bundle();

        TwitterFragment fragment = new TwitterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {




        final View rootView = inflater.inflate(R.layout.fragment_twitter, container, false);


        SupporterActivity activity = (SupporterActivity) getActivity();
        String nomClub = activity.getMyData();
        nameClub = nomClub;
        setUpRecyclerView(rootView);
        setUpSearchQuery(nameClub);

        return rootView;



    }


    private void setUpRecyclerView(View view) {


        searchTimelineRecyclerView = view.findViewById(R.id.search_timeline_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        searchTimelineRecyclerView.setLayoutManager(linearLayoutManager);

    }


    private void setUpSearchQuery(String nameClub){

        //get the text from edit text
        String searchQuery = nameClub;

        //check if query should not empty
        if (!TextUtils.isEmpty(searchQuery)) {


            //search entered query

            doTwitterSearch(searchQuery);

        } else {
            //if query is empty show toast
            Toast.makeText(context, "Pas de tweets disponibles", Toast.LENGTH_SHORT).show();
        }

    }


    private void doTwitterSearch(String query) {

        SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query(query)
                .languageCode(Locale.FRANCE.getLanguage())
                .maxItemsPerRequest(50)
                .build();

        //create adapter for RecyclerView
        adapter = new TweetTimelineRecyclerViewAdapter.Builder(context)
                .setTimeline(searchTimeline)//set created timeline

                .setOnActionCallback(new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        //do something on failure response
                    }
                })
                //set tweet view style
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build();

        //finally set created adapter to recycler view
        searchTimelineRecyclerView.setAdapter(adapter);

    }

}
