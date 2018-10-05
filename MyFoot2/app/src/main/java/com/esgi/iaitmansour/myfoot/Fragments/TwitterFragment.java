package com.esgi.iaitmansour.myfoot.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.iaitmansour.myfoot.R;
import com.esgi.iaitmansour.myfoot.Activitys.SupporterActivity;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;


public class TwitterFragment extends Fragment {



    private Context context;
    private RecyclerView searchTimelineRecyclerView;
    private TweetTimelineRecyclerViewAdapter adapter;

    ProgressDialog pd;
    String nameClub = "";
    String twitter ="";



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
        twitter = activity.getTwitter ();
        nameClub = nomClub;

        loadUserTimeline(rootView,twitter);

        return rootView;



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

       super.onViewCreated(view, savedInstanceState);

    }


    private void setUpRecyclerView(View view) {


        searchTimelineRecyclerView = view.findViewById(R.id.search_timeline_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        searchTimelineRecyclerView.setLayoutManager(linearLayoutManager);



    }



    private void loadUserTimeline(View view, String twitter) {

        setUpRecyclerView(view);

       UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(twitter)//any screen name
                .includeReplies(true)//Whether to include replies. Defaults to false.
                .includeRetweets(true)//Whether to include re-tweets. Defaults to true.
                .maxItemsPerRequest(5)//Max number of items to return per request
                .build();

        //now build adapter for recycler view
        adapter = new TweetTimelineRecyclerViewAdapter.Builder(context)
                .setTimeline(userTimeline)//set the created timeline
                //action callback to listen when user like/unlike the tweet
                .setOnActionCallback(new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        //do something on success response
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        //do something on failure response
                    }

                })
                //set tweet view style
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build();


        //finally set the created adapter to recycler view
        searchTimelineRecyclerView.setAdapter(adapter);



    }

}
