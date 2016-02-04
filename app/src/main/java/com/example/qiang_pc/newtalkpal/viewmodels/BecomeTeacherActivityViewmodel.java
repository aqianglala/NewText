package com.example.qiang_pc.newtalkpal.viewmodels;

import android.text.TextUtils;

import com.example.qiang_pc.newtalkpal.activity.BecomeTeacherActivity;
import com.example.qiang_pc.newtalkpal.bean.LoginBean;
import com.example.qiang_pc.newtalkpal.databinding.ActivityBecomeTeacherBinding;
import com.example.qiang_pc.newtalkpal.utils.L;
import com.example.qiang_pc.newtalkpal.utils.UrlsOrKeys;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.net.URLEncoder;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/2/2.
 */
public class BecomeTeacherActivityViewmodel {
    private BecomeTeacherActivity mBecomeTeacherActivity;
    private ActivityBecomeTeacherBinding mBinding;
    private SweetAlertDialog sweetAlertDialog;

    public BecomeTeacherActivityViewmodel(BecomeTeacherActivity activity , ActivityBecomeTeacherBinding binding){
        mBecomeTeacherActivity=activity;
        mBinding=binding;
    }

    public void confirm() {
        String phone = mBinding.etPhone.getText().toString().trim();
        String name = mBinding.etName.getText().toString().trim();
        if(validateUser(phone,name)){//前端验证
            mBecomeTeacherActivity.showLoadingDialog();
            OkHttpUtils
                    .post()
                    .url(UrlsOrKeys.BECOMETEACHER)
                    .addParams("phone", URLEncoder.encode(phone))
                    .addParams("user_nickname", URLEncoder.encode(name))
                    .tag(mBecomeTeacherActivity)
                    .build()
                    .execute(new MyCallback());
        }
    }

    private boolean validateUser(String phone, String name) {
        boolean isAccessPhone=false;
        boolean isAccessPass=false;
        if(TextUtils.isEmpty(phone)){
            mBinding.tilPhone.setErrorEnabled(true);
            mBinding.tilPhone.setError("手机号不能为空");
            isAccessPhone = false;
        }else{
            mBinding.tilPhone.setErrorEnabled(false);
            isAccessPhone=true;
        }
        if(TextUtils.isEmpty(name)){
            mBinding.tilName.setErrorEnabled(true);
            mBinding.tilName.setError("姓名不能为空");
            isAccessPass = false;
        }else{
            mBinding.tilName.setErrorEnabled(false);
            isAccessPass = true;
        }
        return isAccessPhone & isAccessPass;
    }

    private class MyCallback extends Callback<LoginBean> {

        @Override
        public LoginBean parseNetworkResponse(Response response) throws Exception {
            LoginBean bean = new Gson().fromJson(response.body().string(), LoginBean
                    .class);
            return bean;
        }

        @Override
        public void onError(Call call, Exception e) {
            L.i(mBecomeTeacherActivity.TAG,"数据加载错误");
            mBecomeTeacherActivity.dismissLoadingDialog();
        }

        @Override
        public void onResponse(LoginBean response) {
            mBecomeTeacherActivity.dismissLoadingDialog();
            //验证是否登录成功
            if(response.getCode()==0){//申请成功
                SweetAlertDialog
                        sweetAlertDialog =  new SweetAlertDialog(mBecomeTeacherActivity,
                        SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("申请成功！")
                        .setConfirmText("返回主页")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                mBecomeTeacherActivity.finish();
                            }
                        });
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.show();
                mBinding.tilPhone.setErrorEnabled(false);
            }else if(response.getCode()==2000){//申请失败
                mBinding.tilPhone.setErrorEnabled(true);
                mBinding.tilPhone.setError(response.getError());
            }
        }
    }

}
