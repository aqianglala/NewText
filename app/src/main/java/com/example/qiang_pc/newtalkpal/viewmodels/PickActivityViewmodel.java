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

    public void next() {
        String time = mBinding.etPicktime.getText().toString().trim();
        String location = mBinding.etLocation.getText().toString().trim();
        if(validateUser(time,location)){//前端验证

        }
    }

    private boolean validateUser(String time, String location) {
        if(TextUtils.isEmpty(time) || TextUtils.isEmpty(location)){
            return false;
        }
        return true;
    }

    private ArrayList<Date> mDateList=new ArrayList<>();
    private ArrayList<String>mDateStrList=new ArrayList<>();
    private ArrayList<String>mDuration=new ArrayList<>();
    private ArrayList<String>mTime=new ArrayList<>();
    private int i=9;
    public void initTime(){
        for(int i=0;i<10;i++){
            Date date = DateUtils.getDateBeforeOrAfter(i);
            mDateList.add(date);
            mDateStrList.add(DateUtils.formatDate(date));
        }
        for(int i=1;i<=3;i++){
            mDuration.add(i+"小时");
        }
        while(i<=19){
            String time=null;
            if(i%2==0){//整除 :30
                time=i+":30";
            }else{//余1，:00,hour++
                if(i==9){
                    time="09:00";
                }else{
                    i++;
                    time=i+":00";
                }
            }
            mTime.add(time);
        }
    }

    public void initPickerView(){
//        new OptionsPickerView(this);
    }



}
