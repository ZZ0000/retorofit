package com.example.java.retrofit.flow.repos;

import android.content.Context;

import com.example.java.retrofit.model.Repo;

import java.util.List;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by java on 13.02.2017.
 */

public class ReposRepository implements ReposDataSource {
    ReposLocalDataSource reposLocalDataSource = new ReposLocalDataSource();
    ReposRemouteDataSource reposRemouteDataSource = new ReposRemouteDataSource();

    @Override
    public Single<List<Repo>> getRepos(String user) {
        return reposRemouteDataSource.getRepos(user)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(list -> reposLocalDataSource.saveRepos(list))
                .onErrorResumeNext(error -> reposLocalDataSource.getRepos(user));
    }

    @Override
    public void clearRepos() {
        reposLocalDataSource.clearRepos();
    }

    @Override
    public void init(Context context) {
        reposLocalDataSource.init(context);
        reposRemouteDataSource.init(context);
    }
}
