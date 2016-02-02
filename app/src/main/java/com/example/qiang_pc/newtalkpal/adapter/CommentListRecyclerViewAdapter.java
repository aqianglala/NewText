package com.example.qiang_pc.newtalkpal.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.bean.CommentBean;
import com.example.qiang_pc.newtalkpal.utils.DateUtils;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by admin on 2016/2/2.
 */
public class CommentListRecyclerViewAdapter extends BGARecyclerViewAdapter<CommentBean.DataEntity> {

    public CommentListRecyclerViewAdapter(RecyclerView recyclerView, int itemLayoutId) {
        super(recyclerView, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, CommentBean
            .DataEntity model) {
        //设置星
        RatingBar ratingbar = bgaViewHolderHelper.getView(R.id.rb_comment_star);
        ratingbar.setRating(model.getStar());
        //设置文字
        bgaViewHolderHelper.setText(R.id.tv_username, model.getUser_nickname()).setText(R.id.tv_comment, model
                .getComment()).setText(R.id.tv_time, DateUtils.getStringToCal(model.getCreate_at
                ()));
    }
}
