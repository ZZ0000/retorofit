package com.example.java.retrofit.base;

import com.example.java.retrofit.model.Commentt;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Single;

/**
 * Created by java on 22.02.2017.
 */

public interface CommentsService {
    //https://api.github.com/repos/square/okhttp/issues/comments
    @GET("/repos/{user}/{nameRepo}/issues/comments")
    public Single<List<Commentt>> getComments(@Path("user") String user, @Path("nameRepo") String nameRepo);
}
