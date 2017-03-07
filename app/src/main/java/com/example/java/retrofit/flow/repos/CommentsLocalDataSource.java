package com.example.java.retrofit.flow.repos;

import com.example.java.retrofit.base.BaseLocalDataSource;
import com.example.java.retrofit.model.Commentt;

import java.util.List;

import rx.Single;

/**
 * Created by java on 22.02.2017.
 */

public class CommentsLocalDataSource extends BaseLocalDataSource implements CommentsDataSource {
    @Override
    public Single<List<Commentt>> getComment(String user, String nameRepo) {
        return null;
    }

    @Override
    public void clearComments() {

    }

    public Single<List<Commentt>> saveComments(List<Commentt> list){
        realm.executeTransaction(query -> {
            realm.copyToRealmOrUpdate(list);
        });

        return Single.just(list);

    }
}
