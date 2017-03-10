package com.example.java.retrofit.model;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.java.retrofit.MainActivity;
import com.example.java.retrofit.R;

import java.util.List;

/**
 * Created by Adm on 12.02.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    //private String[] mDataset;
    private List<Repo> mDataset;
    private View.OnClickListener mOnItemClickListener = null;
    private static CallbackOpen_url mCallbackOpen_url = null;

    public interface CallbackOpen_url {
        void callingBackOpen_url(String user, String nameRepo);
    }
    public void registerCallBack(CallbackOpen_url callbackOpen_url){
        this.mCallbackOpen_url = callbackOpen_url;
    }

    // Конструктор
    public RecyclerAdapter(List<Repo> dataset, View.OnClickListener OnItemClickListener, CallbackOpen_url callbackOpen_url) {
        mDataset = dataset;
        mOnItemClickListener = OnItemClickListener;
        mCallbackOpen_url = callbackOpen_url;
    }

    public void setOnItemClickListener(View.OnClickListener OnItemClickListener) {
        this.mOnItemClickListener = OnItemClickListener;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
       // String nameRepo = mDataSet[list.indexOf(repo)] = repo.getName()
        holder.mTextView.setText(mDataset.get(position).getName());
        holder.itemView.setOnClickListener(mOnItemClickListener);
       // holder.mTextView
        holder.bindView(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        public TextView mTextView;
        private Repo mRepo;
        public Repo getRepo() {
            return mRepo;
        }

        public MyViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_recycler_item);
        }

        void bindView(final Repo repo) {
            mRepo = repo;
            String nameRepo = repo.getName();
            mTextView.setText(nameRepo);
            SpannableString ss = new SpannableString(nameRepo);
            ClickableSpan clickableSpan = new ClickableSpan() {

                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(), nameRepo, Toast.LENGTH_SHORT).show();
                    mCallbackOpen_url.callingBackOpen_url(mRepo.getOwner().getLogin(), nameRepo);
                }
            };
            ss.setSpan(clickableSpan, 0, nameRepo.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, nameRepo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mTextView.setText(ss);
            mTextView.setMovementMethod(LinkMovementMethod.getInstance());

       }

    }

}
