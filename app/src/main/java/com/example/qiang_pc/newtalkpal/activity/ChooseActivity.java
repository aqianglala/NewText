package com.example.qiang_pc.newtalkpal.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.search.core.PoiInfo;
import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.databinding.ActivityPickBinding;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.viewmodels.PickActivityViewmodel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class ChooseActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {

    private ActivityPickBinding mBinding;
    private PickActivityViewmodel mViewmodel;
    private TimePickerDialog tpd;
    private DatePickerDialog dpd;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pick);
        mViewmodel = new PickActivityViewmodel(this, mBinding);

        Calendar now = Calendar.getInstance();
        initTimePicker(now);
    }

    private void initTimePicker(Calendar now) {
        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(false);//设置是否震动
        dpd.dismissOnPause(true);//activity暂停的时候是否销毁对话框
        dpd.showYearPickerFirst(false);//先展示年份选择
        dpd.setMinDate(now);
        dpd.setAccentColor(Color.parseColor("#1B8AEB"));


        tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setThemeDark(false);
        tpd.vibrate(false);
        tpd.dismissOnPause(true);
        tpd.enableSeconds(false);
        tpd.setAccentColor(Color.parseColor("#1B8AEB"));
    }

    @Override
    protected void setListener() {
        mBinding.tvPicktime.setOnClickListener(this);
        mBinding.tvPickdate.setOnClickListener(this);
        mBinding.tvSearchmap.setOnClickListener(this);
        mBinding.btnNext.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mViewmodel.initTime();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_picktime:
                tpd.show(getFragmentManager(), "Timepickerdialog");
                break;
            case R.id.tv_pickdate:
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.tv_searchmap:
                startActivityForResult(new Intent(this,LocationActivity.class),0);
                break;
            case R.id.btn_next:
                mViewmodel.next();
                break;
        }
    }

    /**
     * 点击poi列表项的回调
     */
    PoiInfo poidata;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            poidata = (PoiInfo) data.getParcelableExtra("poidata");
            mBinding.etLocation.setText(poidata.address);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mBinding.etPickdate.setText(year+"."+(monthOfYear+1)+"."+dayOfMonth);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        mBinding.etPicktime.setText(hourOfDay+":"+minute);
    }
}
