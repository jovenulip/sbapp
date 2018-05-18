package com.jovenulip.sbassignment.util;

import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

public interface OnAdapterListener {
    void onLoadMore();

    void onClickItem(int pos);
}
