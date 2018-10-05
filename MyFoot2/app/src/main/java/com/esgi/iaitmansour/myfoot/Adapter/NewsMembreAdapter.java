package com.esgi.iaitmansour.myfoot.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esgi.iaitmansour.myfoot.Activitys.MembreActualityActivity;
import com.esgi.iaitmansour.myfoot.Models.News;
import com.esgi.iaitmansour.myfoot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iaitmansour on 08/07/2018.
 */

public class NewsMembreAdapter extends RecyclerView.Adapter<NewsMembreAdapter.NewsMembreViewHolder> {

    private Context mContext;
    private ArrayList<News> mNewsList;
    private  int pos=0;

    public News getNews(int position){
        return this.mNewsList.get(position);
    }

    public NewsMembreAdapter(Context context, ArrayList<News> newsList){
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public NewsMembreAdapter.NewsMembreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_news_membre_item, parent, false);
        return new NewsMembreAdapter.NewsMembreViewHolder(v);
    }


    @Override
    public void onBindViewHolder(NewsMembreAdapter.NewsMembreViewHolder holder, int position) {

        final News currentNews = mNewsList.get(position);

        pos = position;

        String imgUrl = currentNews.getImgUrl();
        String title = currentNews.getTitle();
        String content = currentNews.getContent();
        final int id = currentNews.getId ();


        holder.mTitle.setText(title);
        holder.mContent.setText(content);
        Picasso.with(mContext).load(imgUrl).fit().centerInside().into(holder.mImageView);
        final News infoData = mNewsList.get(position);


        holder.mDelete.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {


                alertdialog (MembreActualityActivity.returnApiKey (),id, currentNews);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


    public class  NewsMembreViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTitle, mContent;
        public Button mDelete;

        public NewsMembreViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_news);
            mTitle = itemView.findViewById(R.id.title_news);
            mContent = itemView.findViewById(R.id.content_news);
            mDelete = itemView.findViewById (R.id.btnDelete);
        }
    }

    public void alertdialog(final String apiKey, final int id, final News news){
        // Build an AlertDialog

        //final News infoData = mNewsList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        // Set a title for alert dialog
        builder.setTitle("Etes vous sur de vouloir supprimer cette actualité ?");
        //builder.setMessage("Selectionner votre rôle");

        // Initializing an array of colors
        final String[] colors = new String[]{
                "OUI",
                "NON",

        };

        // Set the list of items for alert dialog
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedColor = Arrays.asList(colors).get(which);

                if(selectedColor == "OUI"){

                    parseJSON (apiKey, id);
                    removeItem (news);



                }

            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }


    private void removeItem(News NewsData) {

        int currPosition = mNewsList.indexOf(NewsData);
        mNewsList.remove(currPosition);
        notifyItemRemoved(currPosition);
    }



    private void parseJSON(final String apiKey, final int id){
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(mContext);


        String url = "http://poubelle-connecte.pe.hu/FootAPI/API/v1/actuality/"+id;


        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }
                , new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String> ();
                //params.put("id", String.valueOf (id));


                return params;
            }

            //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", apiKey);
                return params;


            }

        };


        mRequestQueue.add(request);


    }



}
