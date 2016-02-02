package com.example.qiang_pc.newtalkpal.adapter;

import android.content.Context;
import android.widget.RatingBar;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.bean.CommentBean;
import com.example.qiang_pc.newtalkpal.utils.DateUtils;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by admin on 2016/2/2.
 */
public class CommentListAdapter extends BGAAdapterViewAdapter<CommentBean.DataEntity> {
    public CommentListAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
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
