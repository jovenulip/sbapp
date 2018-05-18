package com.jovenulip.sbassignment.main;

import com.jovenulip.sbassignment.base.BasePresenter;
import com.jovenulip.sbassignment.base.BaseView;
import com.jovenulip.sbassignment.data.User;

import java.util.List;

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void updateList(List<User> list);
        void addToList(List<User> list);
    }

    interface Presenter extends BasePresenter {
        void populateList(String page);
        void loadMore();
    }
}
