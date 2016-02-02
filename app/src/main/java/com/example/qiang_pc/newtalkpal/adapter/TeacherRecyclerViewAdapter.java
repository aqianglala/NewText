package com.example.qiang_pc.newtalkpal.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.bean.TeacherBean;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class TeacherRecyclerViewAdapter extends BGARecyclerViewAdapter<TeacherBean.DataEntity> {
    public TeacherRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_teacher_list);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.btn_order);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, TeacherBean.DataEntity model) {
        //设置文字
        viewHolderHelper.setText(R.id.tv_from, model.getFrom()).setText(R.id.tv_languages, model
                .getLanguages()).setText(R.id.tv_teach,model.getTeach()).setText(R.id
                .tv_location,model.getLocation()).setText(R.id.tv_price,"¥"+model.getPrice()
                +"/小时").setText(R.id.tv_expert,model.getSkill());
        //设置图片
        String src = model.getGallery().get(0).getSrc();
        Uri uri = Uri.parse(src);
        SimpleDraweeView draweeView = viewHolderHelper.getView(R.id.iv_teacher);
        draweeView.setImageURI(uri);


    }
}