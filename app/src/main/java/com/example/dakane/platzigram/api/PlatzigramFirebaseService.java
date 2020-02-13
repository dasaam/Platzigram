package com.example.dakane.platzigram.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dakane on 13/02/20.
 */

public interface PlatzigramFirebaseService {
    @GET("post.json")
    Call<PostResponse> getPostList();
}
