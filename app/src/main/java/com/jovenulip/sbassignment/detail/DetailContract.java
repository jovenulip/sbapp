package com.jovenulip.sbassignment.detail;

import com.jovenulip.sbassignment.base.BasePresenter;
import com.jovenulip.sbassignment.base.BaseView;
import com.jovenulip.sbassignment.data.UserDetail;
import com.jovenulip.sbassignment.main.MainContract;

public interface DetailContract {
    interface View extends BaseView<MainContract.Presenter> {
        void showUserDetail(UserDetail detail);
    }

    interface Presenter extends BasePresenter {
        void getUserDetail(String user);
    }
}

