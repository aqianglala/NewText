package com.example.qiang_pc.newtalkpal.viewmodels;

import com.example.qiang_pc.newtalkpal.activity.CommentListActivity;
import com.example.qiang_pc.newtalkpal.bean.CommentBean;
import com.example.qiang_pc.newtalkpal.utils.ArraysUtils;
import com.example.qiang_pc.newtalkpal.utils.L;

/**
 * Created by admin on 2016/1/26.
 */
public class CommentListActivityViewmodel {

    private CommentListActivity mCommentListActivity;

    public boolean isLoading;
    public boolean hasMore=true;

    private int page = 1;// 默认加载第一页
    private final CommentBean mCommentBean;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public CommentListActivityViewmodel(CommentListActivity commentListActivity){
        mCommentListActivity=commentListActivity;
        mCommentBean = (CommentBean) mCommentListActivity.getIntent().getSerializableExtra
                ("COMMENTLIST");
    }
    public void loadComment() {
        isLoading=true;
        L.i(mCommentListActivity.TAG,mCommentBean.getData().size()+"");

        if(mCommentBean.getData().size()<=10){
            //没有更多了,最后一页
            hasMore=false;
            mCommentListActivity.setAdapter(ArraysUtils.getArrays(mCommentBean.getData(),
                    (page-1)*10,mCommentBean.getData().size()));
        }else{
            hasMore=true;
            mCommentListActivity.setAdapter(ArraysUtils.getArrays(mCommentBean.getData(),
                    (page-1)*10,(page-1)*10+10));
        }

    }

}
