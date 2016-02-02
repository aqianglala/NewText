package com.example.qiang_pc.newtalkpal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.activity.DetailActivity;
import com.example.qiang_pc.newtalkpal.adapter.TeacherRecyclerViewAdapter;
import com.example.qiang_pc.newtalkpal.bean.TeacherBean;
import com.example.qiang_pc.newtalkpal.global.BaseFragment;
import com.example.qiang_pc.newtalkpal.viewmodels.TeacherFragmentViewmodel;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class TeacherFragment extends BaseFragment implements BGARefreshLayout
        .BGARefreshLayoutDelegate,BGAOnItemChildClickListener {
    private TeacherRecyclerViewAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView mDataRv;

    private TeacherFragmentViewmodel mViewmodel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_teacher);
        mViewmodel = new TeacherFragmentViewmodel(getActivity(),this);

        mRefreshLayout = getViewById(R.id.rl_recyclerview_refresh);
        mDataRv = getViewById(R.id.rv_recyclerview_data);

        initRefreshViewHolder();

        mDataRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new TeacherRecyclerViewAdapter(mDataRv);
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mRefreshLayout.beginRefreshing();
    }

    @Override
    protected void onUserVisible() {

    }

    private void initRefreshViewHolder() {
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new
                BGAMoocStyleRefreshViewHolder(getActivity(), true);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.custom_imoocstyle);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.talkpal_logo);
        moocStyleRefreshViewHolder.setSpringDistanceScale(0.2f);
        mRefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);
    }

    public boolean isRefresh;
    public void setAdapter(List<TeacherBean.DataEntity> data) {
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
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
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
            isRefresh = false;
            mViewmodel.setPage(mViewmodel.getPage() + 1);
            mViewmodel.loadData();
        }
        return true;
    }


    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
        if(view.getId()==R.id.btn_order){
//            Toast.makeText(getActivity(),"点击了",Toast.LENGTH_LONG).show();
            TeacherBean.DataEntity item = mAdapter.getItem(i);
            Intent intent = new Intent(mActivity, DetailActivity.class);
            intent.putExtra("TEACHERITEM",item);
            startActivity(intent);
        }
    }
}