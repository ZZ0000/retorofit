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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.java.retrofit.flow.repos.Presenter.CommentsPresenter;
import com.example.java.retrofit.flow.repos.View.CommentsView;
import com.example.java.retrofit.model.Commentt;
import com.example.java.retrofit.model.Repo;
import com.example.java.retrofit.flow.repos.Presenter.ReposPresenter;
import com.example.java.retrofit.flow.repos.View.ReposView;
import com.example.java.retrofit.model.RecyclerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements ReposView, CommentsView, View.OnClickListener, RecyclerAdapter.CallbackOpen_url {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReposPresenter reposPresenter = new ReposPresenter();
    private Observable<CharSequence> queryObservable = null;
    private CommentsPresenter commentsPresenter = new CommentsPresenter();

    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    CustomTabsServiceConnection mCustomTabsServiceConnection = null;
    CustomTabsSession mCustomTabsSession = null;
    CustomTabsIntent mCustomTabsIntent;
    CustomTabsClient mCustomTabsClient;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        reposPresenter.onAttach(this);
        commentsPresenter.onAttach(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
        mAdapter = new RecyclerAdapter(list, this, this);
        //mAdapter.registerCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
        //mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showComments(List<Commentt> list, String nameRepo) {
        if (list.size() == 0) {
            Toast.makeText(MainActivity.this, "Количество комментариев: "+list.size()+" ("+nameRepo+")" , Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(MainActivity.this, "Количество комментариев: "+list.size()+" ("+nameRepo+")" , Toast.LENGTH_SHORT).show();

        ArrayList<String> data = new ArrayList<>();
        for (Commentt commentt : list){
            data.add(commentt.getBody());
        }
        startActivity(CommentsActivity.newInstance(this, data));
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

        String url = holder.getRepo().getHtmlUrl();

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .build();

        mCustomTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @Override
    public void callingBackOpen_url(String user, String nameRepo) {
        //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
        //user = "square";
        //nameRepo = "okhttp";
        commentsPresenter.getComment(user, nameRepo);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
