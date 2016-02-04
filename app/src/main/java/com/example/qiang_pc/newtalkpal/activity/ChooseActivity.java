package com.example.qiang_pc.newtalkpal.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.databinding.ActivityPickBinding;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.viewmodels.PickActivityViewmodel;

public class ChooseActivity extends BaseActivity {

    private ActivityPickBinding mBinding;
    private PickActivityViewmodel mViewmodel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pick);
        mViewmodel = new PickActivityViewmodel(this, mBinding);
    }

    @Override
    protected void setListener() {
        mBinding.tvPicktime.setOnClickListener(this);
        mBinding.tvSearchmap.setOnClickListener(this);
        mBinding.btnNext.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
//        mViewmodel.initTime();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_picktime:

                break;
            case R.id.tv_searchmap:

                break;
            case R.id.btn_next:

                break;
        }
    }
}
