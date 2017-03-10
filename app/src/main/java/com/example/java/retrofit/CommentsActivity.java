package com.example.java.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.java.retrofit.flow.repos.Presenter.ReposPresenter;
import com.example.java.retrofit.flow.repos.View.CommentsView;
import com.example.java.retrofit.model.Commentt;
import com.example.java.retrofit.model.Repo;
import com.example.java.retrofit.flow.repos.View.ReposView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by java on 13.02.2017.
 */

public class CommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //Bundle data = getIntent().getStringArrayListExtra("list");
        ArrayList<String> mListComments = getIntent().getStringArrayListExtra("list");
        // получаем экземпляр элемента ListView
        ListView listView = (ListView)findViewById(R.id.listView);
        // используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mListComments);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      }

    @NonNull
    public static Intent newInstance(@NonNull Context context, ArrayList<String> list) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra("list", list);
        return intent;
    }

}
