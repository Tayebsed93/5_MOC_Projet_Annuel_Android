package com.esgi.iaitmansour.myfoot.Utils;

import com.esgi.iaitmansour.myfoot.AddClubActivity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by iaitmansour on 26/06/2018.
 */

public interface CreateClubDataService {

    @Multipart
    @POST("FootAPI/API/v1/club")
    Call<AddClubActivity.ResponseUpload>uploadPhoto(
            @Part("nom")RequestBody nom,
            @Part("name")RequestBody name,
            @Part("email")RequestBody email,
            @Part("password")RequestBody password,
            @Part("role")RequestBody role,
            @Part MultipartBody.Part logo


            );
}
