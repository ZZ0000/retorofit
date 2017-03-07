package com.example.java.retrofit.flow.repos;

import android.content.Context;

import com.example.java.retrofit.base.BaseRemouteDataSource;
import com.example.java.retrofit.model.Commentt;

import java.util.List;

import rx.Single;

/**
 * Created by java on 22.02.2017.
 */

public class CommentsRemouteDataSource extends BaseRemouteDataSource implements CommentsDataSource {
    @Override
    public void init(Context context) {

    }

    @Override
    public Single<List<Commentt>> getComment(String user, String nameRepo) {
        {
            return commentsService.getComments(user, nameRepo);
        }
    }

    @Override
    public void clearComments() {

    }
}
