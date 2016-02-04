package com.example.qiang_pc.newtalkpal.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.adapter.DoneOrderRecyclerViewAdapter;
import com.example.qiang_pc.newtalkpal.bean.Appointments;
import com.example.qiang_pc.newtalkpal.databinding.FragmentOrderBinding;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.utils.DividerGridItemDecoration;
import com.example.qiang_pc.newtalkpal.viewmodels.DoneOrderActivityViewmodel;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class DoneOrderActivity extends BaseActivity implements BGARefreshLayout
        .BGARefreshLayoutDelegate,BGAOnRVItemClickListener {

    public boolean isRefresh;
    public boolean mHasMore=true;
    public DoneOrderRecyclerViewAdapter mAdapter;
    private DoneOrderActivityViewmodel mViewmodel;
    private FragmentOrderBinding mBinding;
    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding=DataBindingUtil.setContentView(this,R.layout.fragment_order);

        mViewmodel = new DoneOrderActivityViewmodel(this , mBinding);

        initRefreshViewHolder();

        mBinding.idRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.idRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mAdapter = new DoneOrderRecyclerViewAdapter(mBinding.idRecyclerView);
        mBinding.idRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mBinding.rlRefresh.setDelegate(this);
        mAdapter.setOnRVItemClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mBinding.rlRefresh.beginRefreshing();
    }

    public void setAdapter(List<Appointments.DataEntity> data) {
        if(data.size()==10){
            mHasMore=true;
        }else{
            mHasMore=false;
            if(data.size()==0){
                mBinding.tvEmpty.setVisibility(View.VISIBLE);
            }else{
                mBinding.tvEmpty.setVisibility(View.GONE);
            }

        }
        if(isRefresh){
            mAdapter.clear();
            //将数据添加在开头，形成一种下拉加载更多的效果
//            mAdapter.addNewDatas(data);
            mAdapter.addMoreDatas(data);
        }else{
            mAdapter.addMoreDatas(data);
        }

        stopWait();
    }

    public void stopWait(){
        mBinding.rlRefresh.endRefreshing();
        mBinding.rlRefresh.endLoadingMore();
    }

    private void initRefreshViewHolder() {
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new
                BGAMoocStyleRefreshViewHolder(this, true);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.custom_imoocstyle);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.talkpal_logo);
        moocStyleRefreshViewHolder.setSpringDistanceScale(0.2f);
        // 设置下拉刷新和上拉加载更多的风格
        mBinding.rlRefresh.setRefreshViewHolder(moocStyleRefreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if(!mViewmodel.isLoading){
            isRefresh=true;
            //加载数据
            mViewmodel.setPage(1);
            mViewmodel.loadData();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if(!mViewmodel.isLoading) {
            if(mHasMore){
                isRefresh = false;
                mViewmodel.setPage(mViewmodel.getPage() + 1);
                mViewmodel.loadData();
            }else{
                showToast("没有更多数据了");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        showToast("点击了");
    }

}
