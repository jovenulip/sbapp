package com.jovenulip.sbassignment.data.api;

import com.jovenulip.sbassignment.data.User;
import com.jovenulip.sbassignment.data.UserDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @GET("users")
    Call<List<User>> getUsers(@Query("access_token") String token, @Query("since") String page, @Query("per_page") String perPage);

    @GET("users/{user}")
    Call<UserDetail> getUserDetail(@Path("user") String user, @Query("access_token") String token);
}
