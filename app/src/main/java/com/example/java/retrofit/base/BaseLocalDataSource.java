package com.example.java.retrofit.base;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by java on 08.02.2017.
 */

public abstract class BaseLocalDataSource implements BaseDataSource{
    protected Realm realm = null;

    @Override
    public void init(Context context) {
        realm = Realm.getDefaultInstance();

    }
}
