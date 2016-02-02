package com.example.qiang_pc.newtalkpal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.adapter.OrderRecyclerViewAdapter;
import com.example.qiang_pc.newtalkpal.bean.Appointments;
import com.example.qiang_pc.newtalkpal.bean.EventMessage;
import com.example.qiang_pc.newtalkpal.databinding.FragmentOrderBinding;
import com.example.qiang_pc.newtalkpal.global.BaseFragment;
import com.example.qiang_pc.newtalkpal.utils.DividerGridItemDecoration;
import com.example.qiang_pc.newtalkpal.utils.L;
import com.example.qiang_pc.newtalkpal.viewmodels.OrderFragmentViewmodel;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by qiang-pc on 2016/1/25.
 */
public class OrderListFragment extends BaseFragment implements BGARefreshLayout
        .BGARefreshLayoutDelegate,BGAOnRVItemClickListener {

    public boolean isRefresh;
    public boolean mHasMore=true;
    private OrderRecyclerViewAdapter mAdapter;
    private OrderFragmentViewmodel mViewmodel;
    private FragmentOrderBinding mBinding;

    @Override
    protected void initView(Bundle savedInstanceState) {
        // register the receiver object
        EventBus.getDefault().register(this);
        setContentView(R.layout.fragment_order);

        mBinding = DataBindingUtil.bind(mContentView);

        mViewmodel = new OrderFragmentViewmodel(this , mBinding);

        initRefreshViewHolder();

        mBinding.idRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.idRecyclerView.addItemDecoration(new DividerGridItemDecoration(mActivity));
        mAdapter = new OrderRecyclerViewAdapter(mBinding.idRecyclerView);
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

    @Override
    protected void onUserVisible() {

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
                BGAMoocStyleRefreshViewHolder(getActivity(), true);
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

    @Override
    public void onDestroy() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // When there is a “my_tag”, only events designated with “my_tag” can
    // trigger the function and execute on UI thread when a user posts an event.
    @Subscriber(tag = "update_order_list")
    private void updateUserWithTag(EventMessage message) {
        L.i(TAG,message.getMessage());
        mBinding.rlRefresh.beginRefreshing();
    }
}
