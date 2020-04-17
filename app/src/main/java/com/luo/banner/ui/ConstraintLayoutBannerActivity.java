package com.luo.banner.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.luo.banner.R;
import com.luo.banner.adapter.ImageAdapter;
import com.luo.banner.bean.DataBean;
import com.luo.banner.Banner;
import com.luo.banner.indicator.CircleIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConstraintLayoutBannerActivity extends AppCompatActivity {
    private static final String TAG = "banner_log";
    @BindView(R.id.banner)
    Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout_banner);
        ButterKnife.bind(this);
        banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
        banner.setIndicator(new CircleIndicator(this));
        banner.isAutoLoop(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.destroy();
    }
}
