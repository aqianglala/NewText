package com.example.qiang_pc.newtalkpal.viewmodels;

import com.example.qiang_pc.newtalkpal.activity.CommentListActivity;
import com.example.qiang_pc.newtalkpal.bean.CommentBean;

import java.util.List;

/**
 * Created by admin on 2016/1/26.
 */
public class CommentListActivityViewmodel {

    private CommentListActivity mCommentListActivity;

    private int page = 1;// 默认加载第一页
    private String token;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public CommentListActivityViewmodel(CommentListActivity commentListActivity){
        mCommentListActivity=commentListActivity;
    }

    private List<CommentBean.DataEntity> data;

    public boolean isLoading;
    public void loadComment() {
        isLoading=true;
        CommentBean commentBean = (CommentBean) mCommentListActivity.getIntent().getSerializableExtra
                ("COMMENTLIST");
        mCommentListActivity.setAdapter(commentBean.getData());
    }

}
