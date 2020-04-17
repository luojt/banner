package com.luo.banner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.luo.banner.adapter.ImageAdapter;
import com.luo.banner.adapter.ImageNetAdapter;
import com.luo.banner.adapter.ImageTitleAdapter;
import com.luo.banner.adapter.ImageTitleNumAdapter;
import com.luo.banner.adapter.MultipleTypesAdapter;
import com.luo.banner.adapter.TopLineAdapter;
import com.luo.banner.bean.DataBean;
import com.luo.banner.ui.ConstraintLayoutBannerActivity;
import com.luo.banner.ui.RecyclerViewBannerActivity;
import com.luo.banner.ui.TVActivity;
import com.luo.banner.ui.VideoActivity;
import com.luo.banner.ui.Vp2FragmentRecyclerviewActivity;
import com.luo.banner.Banner;
import com.luo.banner.config.BannerConfig;
import com.luo.banner.config.IndicatorConfig;
import com.luo.banner.indicator.CircleIndicator;
import com.luo.banner.indicator.RectangleIndicator;
import com.luo.banner.indicator.RoundLinesIndicator;
import com.luo.banner.listener.OnPageChangeListener;
import com.luo.banner.transformer.ZoomOutPageTransformer;
import com.luo.banner.util.BannerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnPageChangeListener {
    private static final String TAG = "banner_log";
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.indicator)
    RoundLinesIndicator indicator;
    @BindView(R.id.banner2)
    Banner banner2;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //设置适配器
        banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
        //设置指示器
        banner.setIndicator(new CircleIndicator(this));
        //设置点击事件
        banner.setOnBannerListener((data, position) -> {
            Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
        });
        //添加切换监听
        banner.addOnPageChangeListener(this);
        //圆角
        banner.setBannerRound(BannerUtils.dp2px(5));

        //添加画廊效果，可以参考我给的参数自己调试(不要和其他PageTransformer同时使用)
//        banner.setBannerGalleryEffect(25, 40, 0.14f);

        //设置组合PageTransformer
//        banner.addPageTransformer(new ZoomOutPageTransformer());
//        banner.addPageTransformer(new DepthPageTransformer());


        //实现1号店和淘宝头条类似的效果
        banner2.setAdapter(new TopLineAdapter(DataBean.getTestData2()))
                .setOrientation(Banner.VERTICAL)
                .setPageTransformer(new ZoomOutPageTransformer())
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                });


        //和下拉刷新配套使用
        refresh.setOnRefreshListener(() -> {
            //模拟网络请求需要3秒，请求完成，设置setRefreshing 为false
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh.setRefreshing(false);
                    //给banner重新设置数据
                    banner.setDatas(DataBean.getTestData2());
                }
            }, 3000);
        });

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "onPageSelected:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    /**
     * 如果你需要考虑更好的体验，可以这么操作
     */
    @Override
    protected void onStart() {
        super.onStart();
        banner.start();
        banner2.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stop();
        banner2.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.destroy();
        banner2.destroy();
    }

    @OnClick({R.id.style_image, R.id.style_image_title, R.id.style_image_title_num, R.id.style_multiple,
            R.id.style_net_image, R.id.change_indicator, R.id.rv_banner, R.id.cl_banner, R.id.vp_banner,
            R.id.banner_video,R.id.banner_tv})
    public void click(View view) {
        indicator.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.style_image:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this));
                banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
                break;
            case R.id.style_image_title:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this));
                banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
                banner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                        BannerConfig.INDICATOR_MARGIN, (int) BannerUtils.dp2px(12)));
                break;
            case R.id.style_image_title_num:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleNumAdapter(DataBean.getTestData()));
                banner.removeIndicator();
                break;
            case R.id.style_multiple:
                refresh.setEnabled(true);
                banner.setAdapter(new MultipleTypesAdapter(this, DataBean.getTestData()));
                banner.setIndicator(new RectangleIndicator(this));
                banner.setIndicatorNormalWidth((int) BannerUtils.dp2px(12));
                banner.setIndicatorSpace((int) BannerUtils.dp2px(4));
                banner.setIndicatorRadius(0);
                break;
            case R.id.style_net_image:
                refresh.setEnabled(false);
                banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));
                banner.setIndicator(new RoundLinesIndicator(this));
                banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
                break;
            case R.id.change_indicator:
                indicator.setVisibility(View.VISIBLE);
                //在布局文件中使用指示器，这样更灵活
                banner.setIndicator(indicator,false);
                banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
                break;
            case R.id.rv_banner:
                startActivity(new Intent(this, RecyclerViewBannerActivity.class));
                break;
            case R.id.cl_banner:
                startActivity(new Intent(this, ConstraintLayoutBannerActivity.class));
                break;
            case R.id.vp_banner:
                startActivity(new Intent(this, Vp2FragmentRecyclerviewActivity.class));
                break;
            case R.id.banner_video:
                startActivity(new Intent(this, VideoActivity.class));
                break;
            case R.id.banner_tv:
                startActivity(new Intent(this, TVActivity.class));
                break;
        }
    }
}
