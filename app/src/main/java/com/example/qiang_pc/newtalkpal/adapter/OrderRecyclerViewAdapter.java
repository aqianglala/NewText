package com.example.qiang_pc.newtalkpal.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;

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
public class OrderRecyclerViewAdapter extends BGARecyclerViewAdapter<Appointments.DataEntity> {
    public OrderRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_order_jinixingzhong_list);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, Appointments.DataEntity
            model) {
        String time = DateUtils.getStringToCal(model.getTime());
        //设置文字
        viewHolderHelper.setText(R.id.tv_teacher_name, model.getTeacher_name()).setText(R.id.tv_state, model
                .getState()).setText(R.id.tv_duration,model.getTimeLong()+"小时").setText(R.id
                .tv_location,model.getPlace()).setText(R.id.tv_time,time);
        //设置图片
        String src = model.getTeacher_headImg();
        Uri uri = Uri.parse(src);
        SimpleDraweeView draweeView = viewHolderHelper.getView(R.id.iv_teacher);
        draweeView.setImageURI(uri);


    }
}