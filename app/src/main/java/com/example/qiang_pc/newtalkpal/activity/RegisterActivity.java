package com.example.qiang_pc.newtalkpal.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.databinding.ActivityRegisterBinding;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.viewmodels.RegisterActivityViewmodel;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding mBinding;
    private RegisterActivityViewmodel mViewmode;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_register);
        mViewmode = new RegisterActivityViewmodel(this, mBinding);

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void getCode(View v){
        mViewmode.getCode();
    }

    public void register(View v){
        mViewmode.register();
    }
}
