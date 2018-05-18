package com.jovenulip.sbassignment.main;

import android.net.Uri;

import com.jovenulip.sbassignment.BuildConfig;
import com.jovenulip.sbassignment.data.User;
import com.jovenulip.sbassignment.data.api.UserApi;
import com.jovenulip.sbassignment.di.Injector;

import java.util.List;

import javax.inject.Inject;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private String mSince;

    @Inject
    UserApi mUserApi;

    public MainPresenter(MainContract.View view) {
        Injector.get().inject(this);
        this.mView = view;
    }

    @Override
    public void populateList(final String page) {
        Call<List<User>> call = mUserApi.getUsers(BuildConfig.ACCESS_TOKEN, page, "20");
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.isSuccessful()) {
                    List<User> list = response.body();

                    if (page.equals("0")) {
                        mView.updateList(list);
                    } else {
                        mView.addToList(list);
                    }

                }

                Headers headers = response.headers();
                if (headers != null) {
                    String link = response.headers().get("Link");
                    if (link != null) {
                        Uri uri = Uri.parse(link);
                        mSince = uri.getQueryParameter("since");
                    } else {
                        mSince = null;
                    }
                } else {
                    mSince = null;
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadMore() {
        if (mSince != null) {
            populateList(mSince);
        }
    }
}
