package com.luo.banner.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luo.banner.R;
import com.luo.banner.adapter.MyRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewBannerActivity extends AppCompatActivity {
    @BindView(R.id.net_rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_banner);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(this));
    }

}
