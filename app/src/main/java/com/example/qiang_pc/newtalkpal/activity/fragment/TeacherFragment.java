package com.example.qiang_pc.newtalkpal.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.activity.adapter.TeacherListAdapter;
import com.example.qiang_pc.newtalkpal.activity.bean.TeacherBean;
import com.example.qiang_pc.newtalkpal.activity.viewmodels.TeacherFragmentViewmodel;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by qiang-pc on 2016/1/25.
 */
public class TeacherFragment extends Fragment {

    private TeacherListAdapter adapter;
    private RecyclerView mRecyclerView;

    private TeacherFragmentViewmodel mViewmodel;

    private BGARefreshLayout mBGARefreshLayout;;

    private LinearLayoutManager layoutManager;
    private boolean isRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        mViewmodel = new TeacherFragmentViewmodel(getActivity(),this);

        View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);

        initBGARefreshLayout(rootView);

        mRecyclerView= (RecyclerView) rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mBGARefreshLayout.beginRefreshing();

        return rootView;
    }

    private void initBGARefreshLayout(View rootView) {

        mBGARefreshLayout= (BGARefreshLayout) rootView.findViewById(R.id.BGARefreshLayout);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new
                BGAMoocStyleRefreshViewHolder(getActivity(), true);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.custom_imoocstyle);
        moocStyleRefreshViewHolder.setOriginalImage(R.mipmap.talkpal_logo);
        moocStyleRefreshViewHolder.setSpringDistanceScale(0.2f);
        // 设置下拉刷新和上拉加载更多的风格
        mBGARefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);
        moocStyleRefreshViewHolder.setLoadingMoreText("拼命加载中...");

        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                isRefresh=true;
                //加载数据
                mViewmodel.setPage(1);
                mViewmodel.loadData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                isRefresh=false;
                mViewmodel.setPage(mViewmodel.getPage()+1);
                mViewmodel.loadData();
                return false;
            }
        });
    }

    private List<TeacherBean.DataEntity>mData=new ArrayList<>();
    public void setAdapter(List<TeacherBean.DataEntity> data) {
        if(adapter==null){
            mData=data;
            adapter = new TeacherListAdapter(getActivity(),mData);
            mRecyclerView.setAdapter(adapter);
        }else{
            if(isRefresh){
                mData.clear();
                mData.addAll(data);
            }else{
                mData.addAll(data);
            }
            adapter.notifyDataSetChanged();
        }
        stopWait();
    }

    public void stopWait(){
        mBGARefreshLayout.endRefreshing();
        mBGARefreshLayout.endLoadingMore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //退出页面时取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
