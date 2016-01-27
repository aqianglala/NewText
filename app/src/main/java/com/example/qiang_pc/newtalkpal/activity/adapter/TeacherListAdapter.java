package com.example.qiang_pc.newtalkpal.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.activity.bean.TeacherBean;
import com.example.qiang_pc.newtalkpal.databinding.ItemTeacherListBinding;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by admin on 2016/1/26.
 */
public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.MyViewHolder>{
    private List<TeacherBean.DataEntity>mTeacherList;
    private Context mContext;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private boolean isFooter;

    public  TeacherListAdapter(Context context, List<TeacherBean.DataEntity> teacherList){
        mContext=context;
        mTeacherList=teacherList;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 创建ViewHolder，将视图对象传入，只会调用一次
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_ITEM){
            view = LayoutInflater.from(mContext).inflate(R.layout
                    .item_teacher_list, parent, false);
            isFooter=false;
        }else{
            view=LayoutInflater.from(mContext).inflate(R.layout.list_footer,parent,false);
            isFooter=true;
        }
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mTeacherList.size()+1;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if(position==mTeacherList.size()){
            return;
        }
        holder.bind(mTeacherList.get(position));

        String src = mTeacherList.get(position).getGallery().get(0).getSrc();
        OkHttpUtils.get()
                .url(src)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Bitmap response) {
                        holder.mBinding.ivTeacher.setImageBitmap(response);
                    }
                });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ItemTeacherListBinding mBinding=null;

        public MyViewHolder(View itemView) {
            super(itemView);
            if(isFooter){
                return ;
            }
            mBinding = ItemTeacherListBinding.bind(itemView);
        }

        public void bind(@NonNull TeacherBean.DataEntity teacherItem) {
            mBinding.setTeacherItem(teacherItem);
        }

    }
}
