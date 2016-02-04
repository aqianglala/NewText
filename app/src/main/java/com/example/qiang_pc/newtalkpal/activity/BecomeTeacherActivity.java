package com.example.qiang_pc.newtalkpal.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.databinding.ActivityBecomeTeacherBinding;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.viewmodels.BecomeTeacherActivityViewmodel;

public class BecomeTeacherActivity extends BaseActivity {

    private ActivityBecomeTeacherBinding mBinding;
    private BecomeTeacherActivityViewmodel mViewmodel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_become_teacher);
        mViewmodel = new BecomeTeacherActivityViewmodel(this, mBinding);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void confirm(View view){
        mViewmodel.confirm();
    }

}
