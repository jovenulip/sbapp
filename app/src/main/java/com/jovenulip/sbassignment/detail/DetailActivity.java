package com.jovenulip.sbassignment.detail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jovenulip.sbassignment.Constants;
import com.jovenulip.sbassignment.R;
import com.jovenulip.sbassignment.data.UserDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    public static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.name)
    TextView txtName;
    @BindView(R.id.bio)
    TextView txtBio;
    @BindView(R.id.login)
    TextView txtLogin;
    @BindView(R.id.admin)
    TextView txtAdmin;
    @BindView(R.id.location)
    TextView txtLocation;
    @BindView(R.id.blog)
    TextView txtBlog;
    @BindView(R.id.avatar)
    ImageView imgAvatar;

    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        String username = getIntent().getStringExtra(Constants.USER_NAME);
        mPresenter = new DetailPresenter(this);
        mPresenter.getUserDetail(username);
    }

    @Override
    public void showUserDetail(UserDetail detail) {
        if (detail != null) {
            checkNullSetText(txtName, detail.name);
            checkNullSetText(txtBio, detail.bio);
            checkNullSetText(txtLogin, detail.login);
            checkNullSetText(txtLocation, detail.location);

            if (detail.site_admin) {
                txtAdmin.setVisibility(View.VISIBLE);
            }

            if (detail.blog != null) {
                txtBlog.setText(detail.blog);
            } else {
                txtBlog.setVisibility(View.GONE);
            }

            if (detail.avatar_url != null) {
                Picasso.with(this)
                        .load(detail.avatar_url)
                        .config(Bitmap.Config.RGB_565)
                        .into(imgAvatar, new Callback() {
                            @Override
                            public void onSuccess() {
                                Bitmap imageBitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
                                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                                imageDrawable.setCircular(true);
                                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                                imgAvatar.setImageDrawable(imageDrawable);
                            }

                            @Override
                            public void onError() {

                            }
                        });
            }


        }
    }

    private void checkNullSetText(TextView textView, String data) {
        if (data != null) {
            textView.setText(data);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

}
