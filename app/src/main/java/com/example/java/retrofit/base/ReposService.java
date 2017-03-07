package com.example.java.retrofit.base;

import com.example.java.retrofit.model.Repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Single;

/**
 * Created by java on 08.02.2017.
 */

public interface ReposService {
    @GET("/users/{user}/repos")
    Single <List<Repo>> getRepos(@Path("user") String user);
}
