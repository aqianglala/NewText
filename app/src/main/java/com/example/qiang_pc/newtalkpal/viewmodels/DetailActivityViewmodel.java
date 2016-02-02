package com.example.qiang_pc.newtalkpal.viewmodels;

import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.activity.DetailActivity;
import com.example.qiang_pc.newtalkpal.adapter.CommentListAdapter;
import com.example.qiang_pc.newtalkpal.bean.CommentBean;
import com.example.qiang_pc.newtalkpal.bean.TeacherBean;
import com.example.qiang_pc.newtalkpal.databinding.ActivityDetailBinding;
import com.example.qiang_pc.newtalkpal.utils.L;
import com.example.qiang_pc.newtalkpal.utils.ScreenUtils;
import com.example.qiang_pc.newtalkpal.utils.UrlsOrKeys;
import com.example.qiang_pc.newtalkpal.widget.AutoScrollViewPager;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/2/2.
 */
public class DetailActivityViewmodel {

    private ActivityDetailBinding mBinding;
    private DetailActivity mDetailActivity;
    private final TeacherBean.DataEntity teacheritem;

    public DetailActivityViewmodel(ActivityDetailBinding mBinding , DetailActivity detailActivity) {
        this.mBinding = mBinding;
        this.mDetailActivity=detailActivity;
        teacheritem = (TeacherBean.DataEntity) mDetailActivity.getIntent().getSerializableExtra("TEACHERITEM");
    }

    /**
     * 获取传过来的数据
     */
    public void setData(){
        mBinding.setData(teacheritem);
        initAutoScrollView();
        initTag();
        //获取评论列表
        loadComment();
    }

    public static String formatPrice(int price){
        return "¥"+price+"/小时";
    }

    private void initTag() {
        // 设置tag
        List<String> tags = teacheritem.getTag();
        int size = tags.size();
        if(size>0&& !TextUtils.isEmpty(tags.get(0))){
            for (int i=0;i<size;i++){
                TextView textView = new TextView(mDetailActivity);
                textView.setText(tags.get(i));
                int padding = ScreenUtils.dip2px(6);
                textView.setPadding(padding,padding,padding,padding);
                //设置字体加粗
                TextPaint tp = textView.getPaint();
                tp.setFakeBoldText(true);
                textView.setTextColor(android.graphics.Color.parseColor("#F5A623"));
                textView.setBackgroundResource(R.drawable.shape_tag_bg);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                if(i>0){
                    layoutParams.leftMargin= ScreenUtils.dip2px(10);
                }
                textView.setLayoutParams(layoutParams);
                mBinding.llTag.addView(textView);
            }
        }
    }

    private void loadComment() {
        OkHttpUtils
                .post()
                .url(UrlsOrKeys.COMMENT)
                .addParams("teacher_id", URLEncoder.encode(teacheritem.get_id()))
                .tag(mDetailActivity)
                .build()
                .execute(new MyCallback());
    }

    private List<CommentBean.DataEntity> data;
    private CommentBean mCommentBean;

    public CommentBean getData(){
        return mCommentBean;
    }

    private class MyCallback extends Callback<CommentBean> {

        @Override
        public CommentBean parseNetworkResponse(Response response) throws Exception {
            mCommentBean = new Gson().fromJson(response.body().string(), CommentBean
                    .class);
            return mCommentBean;
        }

        @Override
        public void onError(Call call, Exception e) {
            L.i(mDetailActivity.TAG,"数据加载错误");
            mDetailActivity.showToast("加载失败");
        }

        @Override
        public void onResponse(CommentBean response) {
            L.i(mDetailActivity.TAG,"数据长度："+response.getData().size()+"");
            data = response.getData();
            if (data.size() > 0) {
                // 如果data的长度大于0，代表有评论
                mBinding.llComment.setVisibility(View.VISIBLE);
                CommentListAdapter adapter = new CommentListAdapter(mDetailActivity, R.layout
                        .item_list_comment);
                adapter.setDatas(data.size()>=3?getArrays(0,3):data);
                mBinding.idLvfsv.setAdapter(adapter);
                if(data.size()>=3){//取到3条数据则显示查看更多
                    mBinding.tvShowmore.setVisibility(View.VISIBLE);
                    mBinding.tvShowmore.setOnClickListener(mDetailActivity);
                }
            }
        }
    }

    private ArrayList getArrays(int start ,int end){
        ArrayList<CommentBean.DataEntity> dataEntities = new ArrayList<>();
        for(int i=start;i<end;i++){
            dataEntities.add(data.get(i));
        }
        return dataEntities;
    }

    /**
     * 初始化轮播图
     */
    private void initAutoScrollView() {
        // 设置图片的链接
        ArrayList<String> imgUrls = new ArrayList<String>();
        for (TeacherBean.DataEntity.GalleryEntity entity : teacheritem.getGallery()) {
            imgUrls.add(entity.getSrc());
        }
        mBinding.autoView.setImgUrls(imgUrls);
        // 点布局
        mBinding.autoView.init(imgUrls.size(), mBinding.llDots);
        // 设置轮播图的点击监听
        mBinding.autoView.setOnViewItemClickListener(new AutoScrollViewPager.OnViewItemClickListener() {

            @Override
            public void onItemClick(int position) {
                mDetailActivity.showToast("点击了");
            }
        });
    }
}
