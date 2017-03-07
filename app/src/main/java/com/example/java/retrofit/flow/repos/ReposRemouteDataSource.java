package com.example.java.retrofit.flow.repos;

import com.example.java.retrofit.model.Repo;
import com.example.java.retrofit.base.BaseRemouteDataSource;

import java.util.List;

import rx.Single;

/**
 * Created by java on 08.02.2017.
 */

public class ReposRemouteDataSource extends BaseRemouteDataSource implements ReposDataSource{
    @Override
    public Single<List<Repo>> getRepos(String user) {
        return repoService.getRepos(user);
    }

    @Override
    public void clearRepos() {

    }
}
