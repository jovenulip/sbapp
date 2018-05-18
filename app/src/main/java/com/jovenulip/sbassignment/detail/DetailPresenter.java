package com.jovenulip.sbassignment.detail;

import com.jovenulip.sbassignment.BuildConfig;
import com.jovenulip.sbassignment.data.UserDetail;
import com.jovenulip.sbassignment.data.api.UserApi;
import com.jovenulip.sbassignment.di.Injector;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;

    @Inject
    UserApi mUserApi;

    public DetailPresenter(DetailContract.View view) {
        Injector.get().inject(this);
        this.mView = view;
    }

    @Override
    public void getUserDetail(String user) {
        Call<UserDetail> call = mUserApi.getUserDetail(user, BuildConfig.ACCESS_TOKEN);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                if (response.isSuccessful()) {
                    UserDetail userDetail = response.body();
                    mView.showUserDetail(userDetail);
                } else {
                    mView.showError();
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                mView.showError();
            }
        });
    }
}

