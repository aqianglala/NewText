package com.example.qiang_pc.newtalkpal.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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

/**
 * Created by qiang-pc on 2016/1/25.
 */
public class TeacherFragment extends Fragment {

    private TeacherListAdapter adapter;
    private RecyclerView mRecyclerView;

    private TeacherFragmentViewmodel mViewmodel;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static final int REFRESH=1;
    private static final int LOADMORE=2;

    private int lastVisibleItem;

    private LinearLayoutManager layoutManager;
    private boolean isRefresh;

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH:
                    isRefresh=true;
                    //加载数据
                    mViewmodel.setPage(1);
                    mViewmodel.loadData();
                    break;
                case LOADMORE:
                    isRefresh=false;
                    mViewmodel.setPage(mViewmodel.getPage()+1);
                    mViewmodel.loadData();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        mViewmodel = new TeacherFragmentViewmodel(getActivity(),this);

        View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);
        mRecyclerView= (RecyclerView) rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        initSwipeRefreshLayout(rootView);
        //此处我们是对recyclerview添加scrollListener ,监听滑倒最后一个可见的条目的时候，刷新加载数据
        addLoadMoreListener();

        //加载数据
        mViewmodel.loadData();

        return rootView;
    }

    private void addLoadMoreListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //SCROLL_STATE_DRAGGING  和   SCROLL_STATE_IDLE 两种效果自己看着来
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()){
                    Message obtain = Message.obtain();
                    obtain.what=LOADMORE;
                    handler.sendMessage(obtain);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initSwipeRefreshLayout(View rootView) {

        mSwipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.id_swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //此处是android自带的只支持下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Message obtain = Message.obtain();
                obtain.what=REFRESH;
                handler.sendMessage(obtain);
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //退出页面时取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
