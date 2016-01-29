package com.example.qiang_pc.newtalkpal.activity.viewmodels;

import android.content.Context;

import com.example.qiang_pc.newtalkpal.activity.bean.TeacherBean;
import com.example.qiang_pc.newtalkpal.activity.fragment.TeacherFragment;
import com.example.qiang_pc.newtalkpal.activity.utils.L;
import com.example.qiang_pc.newtalkpal.activity.utils.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/1/26.
 */
public class TeacherFragmentViewmodel {

    private Context mContext;
    private TeacherFragment mTeacherFragment;

    private int page = 1;// 默认加载第一页

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLoading;

    public TeacherFragmentViewmodel(Context context, TeacherFragment teacherFragment){
        mContext=context;
        mTeacherFragment=teacherFragment;
    }

    public void loadData() {
        isLoading=true;
        OkHttpUtils
                .post()
                .url(Urls.TEACHER_LIST)
                .addParams("page", page + "")
                .addParams("size", "10")
                .tag(mTeacherFragment)
                .build()
                .execute(new MyCallback());
    }

    private class MyCallback extends Callback<TeacherBean> {

        @Override
        public TeacherBean parseNetworkResponse(Response response) throws Exception {
            TeacherBean teacherBean = new Gson().fromJson(response.body().string(), TeacherBean
                    .class);
            return teacherBean;
        }

        @Override
        public void onError(Call call, Exception e) {
            L.i("数据加载错误");
            isLoading=false;
            mTeacherFragment.stopWait();
        }

        @Override
        public void onResponse(TeacherBean response) {
            isLoading=false;
            L.i("数据长度："+response.getData().size()+"");
            List<TeacherBean.DataEntity> data = response.getData();
            mTeacherFragment.setAdapter(data);
        }
    }
}
