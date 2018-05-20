package com.ascend.wangfeng.wifimanage.delegates.launch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.views.WaveProgressView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/18.
 * email 1040441325@qq.com
 */

public class ScanDelegate extends LatteDelegate{
    @BindView(R.id.wp_scan)
    WaveProgressView mWpScan;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    // 测试按钮
    @OnClick(R.id.btn_success)
    void su(){
        setScanSuccess();
    }
    @OnClick(R.id.btn_error)
    void error(){
        setScanError();
    }
    public static final String TAG = ScanDelegate.class.getSimpleName();

    @Override
    public Object setLayout() {
        return R.layout.delegate_scan;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mToolbarTitle.setText("发现设备");
        mWpScan.setText(Color.WHITE,60);
        mWpScan.setCurrent(100,"搜索中...");
        mWpScan.setWaveColor(getResources().getColor(R.color.colorAccent));
        // setScanError();
        // setScanSuccess();
    }

    private void setScanSuccess() {
        mWpScan.setAllowProgressInBothDirections(false);
        mWpScan.setWaveColor(getResources().getColor(R.color.colorBlue));
        mWpScan.setCurrent(100,"注册/登录");
        mWpScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进入登录页
                startWithPop(new RegisterDelegate());
            }
        });
    }

    private void setScanError() {
        mWpScan.setAllowProgressInBothDirections(false);
        mWpScan.setWaveColor(getResources().getColor(R.color.colorRed));
        mWpScan.setCurrent(100,"重新搜索");
        mWpScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 重新搜索
                mWpScan.reSet();
                mWpScan.setAllowProgressInBothDirections(true);
                mWpScan.setCurrent(50,"搜索中...");
                mWpScan.setWaveColor(getResources().getColor(R.color.colorAccent));
            }
        });
        showDialog();
    }
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("匹配失败");
        builder.setItems(R.array.scan_error_reasons,null);
        builder.show();
    }
}