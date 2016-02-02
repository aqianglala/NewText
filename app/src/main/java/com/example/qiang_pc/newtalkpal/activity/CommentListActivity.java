package com.example.qiang_pc.newtalkpal.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.adapter.CommentListRecyclerViewAdapter;
import com.example.qiang_pc.newtalkpal.bean.CommentBean;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.utils.DividerGridItemDecoration;
import com.example.qiang_pc.newtalkpal.viewmodels.CommentListActivityViewmodel;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by admin on 2016/2/2.
 */
public class CommentListActivity extends BaseActivity implements BGARefreshLayout
        .BGARefreshLayoutDelegate{

    private CommentListRecyclerViewAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView mDataRv;

    private CommentListActivityViewmodel mViewmodel;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment);
        mRefreshLayout=getViewById(R.id.rl_refresh);
        mDataRv=getViewById(R.id.id_recyclerView);
        initRefreshViewHolder();
        mAdapter=new CommentListRecyclerViewAdapter(mDataRv,R.layout.item_list_comment);
        mDataRv.setLayoutManager(new LinearLayoutManager(this));
        mDataRv.addItemDecoration(new DividerGridItemDecoration(this));
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mRefreshLayout.beginRefreshing();
    }

    private void initRefreshViewHolder() {
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new
                BGAMoocStyleRefreshViewHolder(this, true);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.custom_imoocstyle);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.talkpal_logo);
        moocStyleRefreshViewHolder.setSpringDistanceScale(0.2f);
        mRefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);
    }

    public boolean isRefresh;
    public void setAdapter(List<CommentBean.DataEntity> data) {
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
            mViewmodel.loadComment();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if(!mViewmodel.isLoading) {
            isRefresh = false;
            mViewmodel.setPage(mViewmodel.getPage() + 1);
            mViewmodel.loadComment();
        }
        return true;
    }
}
