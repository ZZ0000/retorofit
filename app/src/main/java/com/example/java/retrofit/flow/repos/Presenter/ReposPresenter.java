package com.example.java.retrofit.flow.repos.Presenter;

import android.content.Context;

import com.example.java.retrofit.model.Repo;
import com.example.java.retrofit.flow.repos.ReposDataSource;
import com.example.java.retrofit.flow.repos.ReposRepository;
import com.example.java.retrofit.flow.repos.View.ReposView;

import java.util.List;

import rx.Single;
import rx.internal.util.SubscriptionList;

/**
 * Created by java on 13.02.2017.
 */

public class ReposPresenter implements ReposDataSource {
    ReposRepository reposRepository = new ReposRepository();
    ReposView reposView = null;
    SubscriptionList subscriptionList = new SubscriptionList();

   @Override
    public Single<List<Repo>> getRepos(String user) {
       Single<List<Repo>> single = reposRepository.getRepos(user);
       single.subscribe(list -> reposView.showRepos(list),
               throwable -> throwable.printStackTrace()); // Throwable::printStackTrace);
       return single;
   }

    @Override
    public void clearRepos() {
        reposRepository.clearRepos();
    }

    @Override
    public void init(Context context) {

    }

   public void onAttach(ReposView view){
        reposView = view;
        reposRepository.init(view.getContex());
    }

    public void onDetach(){
       subscriptionList.unsubscribe();
    }
}
