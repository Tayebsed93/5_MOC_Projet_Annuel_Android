package com.esgi.iaitmansour.myfoot.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.esgi.iaitmansour.myfoot.Models.News;
import com.esgi.iaitmansour.myfoot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by iaitmansour on 08/07/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context mContext;
    private ArrayList<News> mNewsList;

    public News getNews(int position){
        return this.mNewsList.get(position);
    }

    public NewsAdapter(Context context, ArrayList<News> newsList){
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_news_item, parent, false);
        return new NewsAdapter.NewsViewHolder(v);
    }


    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, int position) {

        News currentNews = mNewsList.get(position);


        String imgUrl = currentNews.getImgUrl();
        String title = currentNews.getTitle();
        String content = currentNews.getContent();

        holder.mTitle.setText(title);
        holder.mContent.setText(content);
        Picasso.with(mContext).load(imgUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


    public class  NewsViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTitle, mContent;

        public NewsViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_news);
            mTitle = itemView.findViewById(R.id.title_news);
            mContent = itemView.findViewById(R.id.content_news);
        }
    }



}
