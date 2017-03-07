package com.example.java.retrofit.flow.repos;

import android.content.Context;

import com.example.java.retrofit.model.Commentt;

import java.util.List;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by java on 22.02.2017.
 */

public class CommentsRepository implements CommentsDataSource {
    CommentsLocalDataSource commentsLocalDataSource = new CommentsLocalDataSource();
    CommentsRemouteDataSource commentsRemouteDataSource = new CommentsRemouteDataSource();
    @Override
    public Single<List<Commentt>> getComment(String user, String nameRepo) {
        return commentsRemouteDataSource.getComment(user, nameRepo)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(list -> commentsLocalDataSource.saveComments(list))
                .onErrorResumeNext(error -> commentsLocalDataSource.getComment(user, nameRepo));
//        return reposRemouteDataSource.getRepos(user)
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(list -> reposLocalDataSource.saveRepos(list))
//                .onErrorResumeNext(error -> reposLocalDataSource.getRepos(user));
    }

    @Override
    public void clearComments() {
        commentsLocalDataSource.clearComments();
    }

    @Override
    public void init(Context context) {
        commentsLocalDataSource.init(context);
        commentsRemouteDataSource.init(context);
    }

}
