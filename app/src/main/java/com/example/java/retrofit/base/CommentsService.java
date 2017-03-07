package com.example.java.retrofit.base;

import com.example.java.retrofit.model.Commentt;

import java.util.List;

import retrofit2.http.GET;
import rx.Single;

/**
 * Created by java on 22.02.2017.
 */

public interface CommentsService {
    @GET("/users/{user}/repos/{nameRepo}/issues/comments")
    public Single<List<Commentt>> getComments(String user, String nameRepo);


}
