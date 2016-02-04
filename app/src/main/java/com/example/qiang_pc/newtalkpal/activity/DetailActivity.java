package com.example.qiang_pc.newtalkpal.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.databinding.ActivityDetailBinding;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.viewmodels.DetailActivityViewmodel;

public class DetailActivity extends BaseActivity {

    private DetailActivityViewmodel mViewmodel;
    private ActivityDetailBinding mBinding;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_detail);
        mViewmodel = new DetailActivityViewmodel(mBinding, this);
    }

    @Override
    protected void setListener() {
        mBinding.btnOrder.setOnClickListener(this);
        mBinding.tvShowmore.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mViewmodel.setData();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_showmore){
            Intent intent = new Intent(this, CommentListActivity.class);
            intent.putExtra("COMMENTLIST",mViewmodel.getData());
            startActivity(intent);
        }else if(v.getId()==R.id.btn_order){
            Intent intent = new Intent(this, ChooseActivity.class);
            intent.putExtra("COMMENTLIST",mViewmodel.getData());
            startActivity(intent);
        }
    }
}
