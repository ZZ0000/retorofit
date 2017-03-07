package com.example.java.retrofit.flow.repos.View;

import android.content.Context;

import com.example.java.retrofit.model.Repo;

import java.util.List;

/**
 * Created by java on 13.02.2017.
 */

public interface ReposView {
    void showRepos(List<Repo> list);
    Context getContex();
}
