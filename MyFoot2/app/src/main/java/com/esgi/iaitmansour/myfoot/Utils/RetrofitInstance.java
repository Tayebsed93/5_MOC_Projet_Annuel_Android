package com.esgi.iaitmansour.myfoot.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iaitmansour on 25/06/2018.
 */

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://poubelle-connecte.pe.hu/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
