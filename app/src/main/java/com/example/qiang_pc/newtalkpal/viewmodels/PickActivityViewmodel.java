package com.example.qiang_pc.newtalkpal.viewmodels;

import android.text.TextUtils;

import com.example.qiang_pc.newtalkpal.activity.ChooseActivity;
import com.example.qiang_pc.newtalkpal.databinding.ActivityPickBinding;
import com.example.qiang_pc.newtalkpal.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 2016/2/2.
 */
public class PickActivityViewmodel {
    private ChooseActivity mActivity;
    private ActivityPickBinding mBinding;

    public PickActivityViewmodel(ChooseActivity activity , ActivityPickBinding binding){
        mActivity=activity;
        mBinding=binding;
    }

    private boolean validateUser(String time, String location) {
        if(TextUtils.isEmpty(time) || TextUtils.isEmpty(location)){
            return false;
        }
        return true;
    }

    public void next(){
        String date = mBinding.etPickdate.getText().toString().trim();
        String time = mBinding.etPicktime.getText().toString().trim();
        String location = mBinding.etLocation.getText().toString().trim();
        if(TextUtils.isEmpty(date)){
            mActivity.showToast("请选择日期");
            return ;
        }
        if(TextUtils.isEmpty(time)){
            mActivity.showToast("请选择时间");
            return ;
        }
        if(TextUtils.isEmpty(location)){
            mActivity.showToast("请选择地点");
            return ;
        }
    }

    private ArrayList<Date> mDateList=new ArrayList<>();
    private ArrayList<String>mDateStrList=new ArrayList<>();
    private ArrayList<String>mDuration=new ArrayList<>();
    private ArrayList<String>mTime=new ArrayList<>();
    private int hour=9;
    public void initTime(){
        for(int i=0;i<10;i++){
            Date date = DateUtils.getDateBeforeOrAfter(i);
            mDateList.add(date);
            mDateStrList.add(DateUtils.formatDate(date));
        }
        for(int i=1;i<=3;i++){
            mDuration.add(i+"小时");
        }
        for(int i=0;i<17;i++){
            String time=null;
            if(i%2==0){//整除，:00,hour++
                hour++;
                time=hour+":00";

            }else{//余1 :30
                if(i==0){
                    time="09:00";
                }else if(i==1){
                    time="09:30";
                }else{
                    time=hour+":30";
                }
            }
            mTime.add(time);
        }
    }

    public void initPickerView(){
//        new OptionsPickerView(this);
    }



}
