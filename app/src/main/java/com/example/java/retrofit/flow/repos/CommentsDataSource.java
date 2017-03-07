package com.example.java.retrofit.flow.repos;

import com.example.java.retrofit.base.BaseDataSource;
import com.example.java.retrofit.model.Commentt;

import java.util.List;

import rx.Single;

/**
 * Created by java on 22.02.2017.
 */

public interface CommentsDataSource extends BaseDataSource {
    public Single<List<Commentt>> getComment(String user, String nameRepo);

    public void clearComments();
}
