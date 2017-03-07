package com.example.java.retrofit.flow.repos;

import com.example.java.retrofit.base.BaseDataSource;
import com.example.java.retrofit.model.Repo;

import java.util.List;

import rx.Single;

/**
 * Created by java on 08.02.2017.
 */

public interface ReposDataSource extends BaseDataSource {
    public Single<List<Repo>> getRepos(String user);

    public void clearRepos();
}
