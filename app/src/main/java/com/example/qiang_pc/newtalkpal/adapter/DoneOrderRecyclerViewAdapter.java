package com.example.qiang_pc.newtalkpal.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.bean.Appointments;
import com.example.qiang_pc.newtalkpal.utils.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class DoneOrderRecyclerViewAdapter extends BGARecyclerViewAdapter<Appointments.DataEntity> {
    public DoneOrderRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_order_yijieshu_list);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, Appointments.DataEntity
            model) {
        String time = DateUtils.getStringToCal(model.getTime());
        //设置文字
        viewHolderHelper.setText(R.id.tv_teacher_name, model.getTeacher_name()).setText(R.id
                .tv_time, time);
        //设置状态
        String state = model.getState();
        TextView tv_state = viewHolderHelper.getView(R.id.tv_state);
        if (state.equals("已付款")) {
            tv_state.setTextColor(android.graphics.Color.parseColor("#7CD33A"));
            tv_state.setText(model.getState());
        } else if (state.equals("待处理") && System.currentTimeMillis() > DateUtils
            .getDate(model.getTime())
            .getTime()) {//如果时间已过期，且没付款则视为已作废
            tv_state.setTextColor(android.graphics.Color.parseColor("#FF4600"));
            tv_state.setText("已作废");
        } else {//待处理,已评价
            tv_state.setTextColor(android.graphics.Color.parseColor("#80D63F"));
            tv_state.setText(model.getState());
        }
        //设置图片
        String src = model.getTeacher_headImg();
        SimpleDraweeView draweeView = viewHolderHelper.getView(R.id.profile_image);
        if(!TextUtils.isEmpty(src)){
            Uri uri = Uri.parse(src);
            draweeView.setImageURI(uri);
        }else{
            draweeView.setImageURI(null);
        }
    }
}