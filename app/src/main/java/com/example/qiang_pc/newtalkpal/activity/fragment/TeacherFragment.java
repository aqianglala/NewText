package com.example.qiang_pc.newtalkpal.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.activity.adapter.TeacherListAdapter;
import com.example.qiang_pc.newtalkpal.activity.bean.TeacherBean;
import com.example.qiang_pc.newtalkpal.activity.viewmodels.TeacherFragmentViewmodel;
import com.example.qiang_pc.newtalkpal.databinding.FragmentTeacherBinding;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created by qiang-pc on 2016/1/25.
 */
public class TeacherFragment extends Fragment {

    private FragmentTeacherBinding mBinding;
    private TeacherListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);
        mBinding=FragmentTeacherBinding.bind(rootView);
        TeacherFragmentViewmodel viewmodel = new TeacherFragmentViewmodel(getActivity(),this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //加载数据
        viewmodel.initData();

        return rootView;
    }

    public void setAdapter(List<TeacherBean.DataEntity> data) {
        if(adapter==null){
            adapter = new TeacherListAdapter(getActivity(),data);
            mBinding.recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //退出页面时取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }


}
