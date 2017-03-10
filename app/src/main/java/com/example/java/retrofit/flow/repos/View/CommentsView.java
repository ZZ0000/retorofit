package com.example.java.retrofit.flow.repos.View;

import android.content.Context;

import com.example.java.retrofit.model.Commentt;

import java.util.List;

/**
 * Created by java on 22.02.2017.
 */

public interface CommentsView {
    void showComments(List<Commentt> list, String nameRepo);
    Context getContex();
}
