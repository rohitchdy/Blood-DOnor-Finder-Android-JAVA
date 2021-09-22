package com.technowesome.blooddonorfinder.Services;

import com.technowesome.blooddonorfinder.Models.Feed;
import com.technowesome.blooddonorfinder.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface FeedService {
    @GET("home/feed/list")
    Call<List<Feed>> getFeeds();

    @POST("home/feed/addNew")
    Call<Feed> addUser(@Body Feed feed);

    @PUT("home/feed/update")
    Call<Feed> updateFeed(@Query("feedId") Long feedId, @Body Feed feed);

}
