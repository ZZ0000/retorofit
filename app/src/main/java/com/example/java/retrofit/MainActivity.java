package com.example.java.retrofit;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.retrofit.model.Repo;
import com.example.java.retrofit.flow.repos.Presenter.ReposPresenter;
import com.example.java.retrofit.flow.repos.View.ReposView;
import com.example.java.retrofit.model.RecyclerAdapter;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements ReposView, View.OnClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReposPresenter reposPresenter = new ReposPresenter();
    private Observable<CharSequence> queryObservable = null;

    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    CustomTabsServiceConnection mCustomTabsServiceConnection = null;
    CustomTabsSession mCustomTabsSession = null;
    CustomTabsIntent mCustomTabsIntent;
    CustomTabsClient mCustomTabsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.java.retrofit.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.example.java.retrofit.R.id.toolbar);
        setSupportActionBar(toolbar);
        reposPresenter.onAttach(this);

        mRecyclerView = (RecyclerView) findViewById(com.example.java.retrofit.R.id.recyclerView);

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(true);
        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mCustomTabsClient = null;
            }

            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient = customTabsClient;
                mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }
        };
        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.java.retrofit.R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search...");

        //queryObservable =
        RxSearchView.queryTextChanges(searchView)
                .debounce(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(query -> reposPresenter.getRepos(query.toString()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showRepos(List<Repo> list) {
        mAdapter = new RecyclerAdapter(list, this);
        mRecyclerView.setAdapter(mAdapter);
        //   mAdapter.se
    }

    @Override
    public Context getContex() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reposPresenter.onDetach();
    }

    @Override
    public void onClick(View view) {
        RecyclerAdapter.MyViewHolder holder = (RecyclerAdapter.MyViewHolder) mRecyclerView.findContainingViewHolder(view);
        if (holder == null) return;
        //  Toast.makeText(this, holder.getRepo().getUrl(), Toast.LENGTH_SHORT).show();

        TextView mTextView = holder.mTextView;
        String nameRepo = holder.mTextView.getText().toString();
        SpannableString ss = new SpannableString(nameRepo);
        ClickableSpan clickableSpan = new ClickableSpan() {

            @Override
            public void onClick(View textView) {
                //startActivity(new (MyActivity.this, NextActivity.class));
                Toast.makeText(MainActivity.this, nameRepo, Toast.LENGTH_SHORT).show();
            }
        };
        ss.setSpan(clickableSpan, 0, nameRepo.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, nameRepo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTextView.setText(ss);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        String url = holder.getRepo().getHtmlUrl();
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//       customTabsIntent.launchUrl(this, Uri.parse(url));

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .build();

        mCustomTabsIntent.launchUrl(this, Uri.parse(url));
    }

}
