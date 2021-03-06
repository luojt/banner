package com.luo.banner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.luo.banner.R;
import com.luo.banner.adapter.ImageNetAdapter;
import com.luo.banner.bean.DataBean;
import com.luo.banner.Banner;
import com.luo.banner.indicator.RectangleIndicator;
import com.luo.banner.util.BannerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerFragment extends Fragment {

    @BindView(R.id.banner)
    Banner banner;

    public static Fragment newInstance() {
        return new BannerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.banner, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));
        banner.setIndicator(new RectangleIndicator(getActivity()));
        banner.setIndicatorNormalWidth((int) BannerUtils.dp2px(12));
        banner.setIndicatorSpace((int) BannerUtils.dp2px(4));
        banner.setIndicatorRadius(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stop();
    }
}
