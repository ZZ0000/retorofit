package com.example.java.retrofit.flow.repos.Presenter;

import android.content.Context;

import com.example.java.retrofit.flow.repos.CommentsDataSource;
import com.example.java.retrofit.flow.repos.CommentsRepository;
import com.example.java.retrofit.flow.repos.View.CommentsView;
import com.example.java.retrofit.model.Commentt;

import java.util.List;

import rx.Single;
import rx.internal.util.SubscriptionList;

/**
 * Created by java on 22.02.2017.
 */

public class CommentsPresenter implements CommentsDataSource {
    CommentsRepository commentsRepository = new CommentsRepository();
    CommentsView commentsView = null;
//    ReposRepository reposRepository = new ReposRepository();
//    ReposView reposView = null;
    SubscriptionList subscriptionList = new SubscriptionList();

    @Override
    public Single<List<Commentt>> getComment(String user, String nameRepo) {
         Single<List<Commentt>> single = commentsRepository.getComment(user,  nameRepo);
        single.subscribe(list -> commentsView.showComments(list),
                throwable -> throwable.printStackTrace()); // Throwable::printStackTrace);
//        Single<List<Repo>> single = reposRepository.getRepos(user);
//        single.subscribe(list -> reposView.showRepos(list),
//                throwable -> throwable.printStackTrace()); // Throwable::printStackTrace);
        return single;
    }

    @Override
    public void clearComments() {
        commentsRepository.clearComments();
    }

    @Override
    public void init(Context context) {

    }
    public void onAttach(CommentsView view){
        commentsView = view;
        commentsRepository.init(view.getContex());
    }

    public void onDetach(){
        subscriptionList.unsubscribe();
    }
}
