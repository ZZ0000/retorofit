package com.example.java.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.java.retrofit.flow.repos.Presenter.ReposPresenter;
import com.example.java.retrofit.model.Repo;
import com.example.java.retrofit.flow.repos.View.ReposView;

import java.util.List;

/**
 * Created by java on 13.02.2017.
 */

public class ReposActivity extends AppCompatActivity implements ReposView {
    ReposPresenter reposPresenter = new ReposPresenter();

    @Override
    public void showRepos(List<Repo> list) {

    }

    @Override
    public Context getContex() {
        return this.getContex();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reposPresenter.onAttach(this);

    }

    @Override
    protected void onDestroy() {
        reposPresenter.onDetach();
    }
}
