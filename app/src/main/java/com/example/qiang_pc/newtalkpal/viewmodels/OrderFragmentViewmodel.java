package com.example.qiang_pc.newtalkpal.viewmodels;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.qiang_pc.newtalkpal.bean.Appointments;
import com.example.qiang_pc.newtalkpal.databinding.FragmentOrderBinding;
import com.example.qiang_pc.newtalkpal.fragment.OrderListFragment;
import com.example.qiang_pc.newtalkpal.utils.HasLoginUtils;
import com.example.qiang_pc.newtalkpal.utils.L;
import com.example.qiang_pc.newtalkpal.utils.UrlsOrKeys;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.net.URLEncoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/1/26.
 */
public class OrderFragmentViewmodel {

    private Context mContext;
    private OrderListFragment mOrderListFragment;
    private FragmentOrderBinding mBinding;

    private int page = 1;// 默认加载第一页
    public String token;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLoading;

    public OrderFragmentViewmodel(OrderListFragment orderListFragment,FragmentOrderBinding binding){
        mOrderListFragment=orderListFragment;
        mContext=mOrderListFragment.getActivity();
        mBinding=binding;
    }

    public void loadData() {
        token=HasLoginUtils.getToken();
        if(!TextUtils.isEmpty(token)){// 已经登录，则加载数据，隐藏请先登录视图
            mBinding.tvLoginfirst.setVisibility(View.GONE);
            isLoading=true;
            OkHttpUtils
                    .post()
                    .url(UrlsOrKeys.GETORDER)
                    .addParams("token", URLEncoder.encode(token))
                    .addParams("done",URLEncoder.encode("0"))
                    .addParams("page", URLEncoder.encode(page + ""))
                    .addParams("size", URLEncoder.encode("10"))
                    .tag(mOrderListFragment)
                    .build()
                    .execute(new MyCallback());
        }else{
            mOrderListFragment.mAdapter.clear();
            mBinding.tvLoginfirst.setVisibility(View.VISIBLE);
            mOrderListFragment.stopWait();
        }
    }

    private class MyCallback extends Callback<Appointments> {

        @Override
        public Appointments parseNetworkResponse(Response response) throws Exception {
            Appointments bean = new Gson().fromJson(response.body().string(), Appointments
                    .class);
            return bean;
        }

        @Override
        public void onError(Call call, Exception e) {
            L.i("数据加载错误");
            isLoading=false;
            mOrderListFragment.stopWait();
        }

        @Override
        public void onResponse(Appointments response) {
            isLoading=false;
            L.i(mOrderListFragment.TAG,"数据长度："+response.getData().size()+"");
            List<Appointments.DataEntity> data = response.getData();
            mOrderListFragment.setAdapter(data);
        }
    }
}
