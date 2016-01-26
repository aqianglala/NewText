package com.example.qiang_pc.newtalkpal.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.activity.bean.TeacherBean;
import com.example.qiang_pc.newtalkpal.databinding.ItemTeacherListBinding;

import java.util.List;

/**
 * Created by admin on 2016/1/26.
 */
public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.MyViewHolder>{
    private List<TeacherBean.DataEntity>mTeacherList;
    private Context mContext;
    public  TeacherListAdapter(Context context, List<TeacherBean.DataEntity> teacherList){
        mContext=context;
        mTeacherList=teacherList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .item_teacher_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mTeacherList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mTeacherList.get(position));
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final ItemTeacherListBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemTeacherListBinding.bind(itemView);
        }

        public void bind(@NonNull TeacherBean.DataEntity teacherItem) {
            mBinding.setTeacherItem(teacherItem);
        }

    }
}
