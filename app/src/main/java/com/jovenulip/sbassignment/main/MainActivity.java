package com.jovenulip.sbassignment.main;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jovenulip.sbassignment.R;
import com.jovenulip.sbassignment.base.BaseView;
import com.jovenulip.sbassignment.data.User;
import com.jovenulip.sbassignment.util.OnAdapterListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnAdapterListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.list_users)
    RecyclerView rcyUserList;

    private List<User> mList = new ArrayList<>();
    private MainPresenter mMainPresenter;
    private boolean reachLimit;
    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter = new MainPresenter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcyUserList.setLayoutManager(manager);
        rcyUserList.setHasFixedSize(true);

        mAdapter = new UserListAdapter(this, mList, rcyUserList);
        mAdapter.setAdapterEventListener(this);
        rcyUserList.setAdapter(mAdapter);

        mMainPresenter.populateList("0");
    }

    @Override
    public void updateList(List<User> list) {
        mAdapter.updateList(list);
    }

    @Override
    public void addToList(List<User> list) {
        if (list.size() > 0) {
            mList.addAll(list);
            mAdapter.addToList(list);
            loadComplete();

            if (mList.size() > 100) {
                reachLimit = true;
                mAdapter.limitReached(true);
            }
        }
    }

    private void loadComplete() {
        mAdapter.removeItem(null);
        mAdapter.setLoaded();
    }

    @Override
    public void onLoadMore() {
        if (!reachLimit) {
            mAdapter.addItem(null);
            mMainPresenter.loadMore();
        }
    }

    @Override
    public void onClickItem(int pos, List<Pair<View, String>> pair) {
        if(pos >= 0){

        }
    }
}
