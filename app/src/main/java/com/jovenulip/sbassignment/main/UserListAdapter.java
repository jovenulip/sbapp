package com.jovenulip.sbassignment.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jovenulip.sbassignment.R;
import com.jovenulip.sbassignment.data.User;
import com.jovenulip.sbassignment.util.OnAdapterListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = UserListAdapter.class.getSimpleName();

    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_PROGRESSBAR = 2;

    private Context mContext;
    private List<User> mList;
    private boolean isLoading;
    private boolean limitReached;
    private int mVisibleThreshold = 2;
    private int mLastVisibleItem, mTotalItemCount;
    private OnAdapterListener onAdapterListener;

    public UserListAdapter(Context context, List<User> list, RecyclerView recyclerView) {
        mContext = context;
        mList = list;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mList.size() >= mVisibleThreshold) {
                    mTotalItemCount = linearLayoutManager.getItemCount();
                    mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!isLoading && !limitReached && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                        if (onAdapterListener != null) {
                            onAdapterListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main)
        public RelativeLayout vwMain;
        @BindView(R.id.name)
        public TextView txtName;
        @BindView(R.id.admin)
        public TextView txtAdmin;
        @BindView(R.id.avatar)
        public ImageView imgAvatar;

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            vwMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onAdapterListener.onClickItem(getAdapterPosition());
                        }
                    }, 200);
                }
            });

        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progress)
        public ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == VIEW_TYPE_PROGRESSBAR) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progress, parent, false);
            holder = new ProgressViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user, parent, false);

            holder = new BaseViewHolder(v);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof BaseViewHolder) {
            final BaseViewHolder holder = (BaseViewHolder) viewHolder;
            User user = mList.get(position);
            holder.txtName.setText(user.login);
            holder.txtAdmin.setVisibility(user.site_admin ? View.VISIBLE : View.GONE);

            Picasso.with(mContext)
                    .load(user.avatar_url)
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.imgAvatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) holder.imgAvatar.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            holder.imgAvatar.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position) == null) {
            return VIEW_TYPE_PROGRESSBAR;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    public void addToList(List<User> data) {
        List<User> tempData = new ArrayList<>(data);
        mList.addAll(tempData);
        notifyItemInserted(mList.size() - 1);
    }

    public void updateList(List<User> data) {
        List<User> tempData = new ArrayList<>(data);
        mList.clear();
        mList.addAll(tempData);

        notifyDataSetChanged();
    }

    public void addItem(User item) {
        if (!mList.contains(item)) {
            mList.add(item);

            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {
                    notifyItemInserted(mList.size() - 1);
                }
            };

            handler.post(r);

        }
    }

    public void removeItem(User item) {
        int indexOfItem = mList.indexOf(item);
        if (indexOfItem != -1) {
            mList.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void limitReached(boolean isReached) {
        limitReached = isReached;
    }

    public void setAdapterEventListener(OnAdapterListener onAdapterEventListener) {
        this.onAdapterListener = onAdapterEventListener;
    }

}
