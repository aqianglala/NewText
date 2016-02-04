package com.example.qiang_pc.newtalkpal.viewmodels;

import android.text.TextUtils;

import com.example.qiang_pc.newtalkpal.activity.RegisterActivity;
import com.example.qiang_pc.newtalkpal.bean.RegisterBean;
import com.example.qiang_pc.newtalkpal.databinding.ActivityRegisterBinding;
import com.example.qiang_pc.newtalkpal.utils.L;
import com.example.qiang_pc.newtalkpal.utils.UrlsOrKeys;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/2/2.
 */
public class RegisterActivityViewmodel {
    private RegisterActivity mRegisterActivity;
    private ActivityRegisterBinding mBinding;

    public RegisterActivityViewmodel(RegisterActivity registerActivity, ActivityRegisterBinding
            binding) {
        mRegisterActivity = registerActivity;
        mBinding = binding;
    }

    public void getCode() {
        String phone = mBinding.etPhone.getText().toString().trim();
        if (validatePhone(phone)) {
            OkHttpUtils
                    .post()
                    .url(UrlsOrKeys.GETCODE)
                    .addParams("phone", URLEncoder.encode(phone))
                    .tag(mRegisterActivity)
                    .build()
                    .execute(new MyGetCodeCallback());
        }
    }

    public void register() {
        String phone = mBinding.etPhone.getText().toString().trim();
        String verification = mBinding.etVerification.getText().toString().trim();
        String password = mBinding.etPassword.getText().toString().trim();
        String name = mBinding.etName.getText().toString().trim();
        if (validateUser(phone, verification, password, name)) {//前端验证
            mRegisterActivity.showLoadingDialog();
            OkHttpUtils
                    .post()
                    .url(UrlsOrKeys.REGISTER)
                    .addParams("phone", phone)
                    .addParams("verifynum", verification)
                    .addParams("password", password)
                    .addParams("nickname", name)
                    .tag(mRegisterActivity)
                    .build()
                    .execute(new MyCallback());
        }
    }

    private boolean validatePhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            mBinding.tilPhone.setErrorEnabled(true);
            mBinding.tilPhone.setError("手机号不能为空");
            return false;
        } else {
            mBinding.tilPhone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUser(String phone, String verification, String password, String name) {
        boolean isAccessPhone = false;
        boolean isAccessPass = false;
        if (TextUtils.isEmpty(phone)) {
            mBinding.tilPhone.setErrorEnabled(true);
            mBinding.tilPhone.setError("手机号不能为空");
            isAccessPhone = false;
        } else {
            mBinding.tilPhone.setErrorEnabled(false);
            isAccessPhone = true;
        }
        if (TextUtils.isEmpty(verification)) {
            mBinding.tilVerification.setErrorEnabled(true);
            mBinding.tilVerification.setError("验证码不能为空");
            isAccessPass = false;
        } else {
            mBinding.tilVerification.setErrorEnabled(false);
            isAccessPass = true;
        }
        if (TextUtils.isEmpty(password)) {
            mBinding.tilPassword.setErrorEnabled(true);
            mBinding.tilPassword.setError("密码不能为空");
            isAccessPass = false;
        } else {
            mBinding.tilPassword.setErrorEnabled(false);
            isAccessPass = true;
        }
        if (TextUtils.isEmpty(name)) {
            mBinding.tilName.setErrorEnabled(true);
            mBinding.tilName.setError("名字不能为空");
            isAccessPass = false;
        } else {
            mBinding.tilName.setErrorEnabled(false);
            isAccessPass = true;
        }
        return isAccessPhone & isAccessPass;
    }

    private class MyGetCodeCallback extends Callback<RegisterBean> {

        @Override
        public RegisterBean parseNetworkResponse(Response response) throws Exception {
            RegisterBean bean = new Gson().fromJson(response.body().string(), RegisterBean
                    .class);
            return bean;
        }

        @Override
        public void onError(Call call, Exception e) {
            L.i(mRegisterActivity.TAG, "数据加载错误");
            mRegisterActivity.showToast("网络请求异常");
        }

        @Override
        public void onResponse(RegisterBean response) {
            if (response.code == 1001 || response.code==2000) {//手机号已经被注册
                mBinding.tilPhone.setErrorEnabled(true);
                mBinding.tilPhone.setError(response.error);
            }else{
                mBinding.tilPhone.setErrorEnabled(false);
                mRegisterActivity.showToast("验证码已发送");
            }
        }
    }

    private class MyCallback extends Callback<RegisterBean> {

        @Override
        public RegisterBean parseNetworkResponse(Response response) throws Exception {
            RegisterBean bean = new Gson().fromJson(response.body().string(), RegisterBean
                    .class);
            return bean;
        }

        @Override
        public void onError(Call call, Exception e) {
            L.i(mRegisterActivity.TAG, "数据加载错误");
            mRegisterActivity.dismissLoadingDialog();
        }

        @Override
        public void onResponse(RegisterBean response) {
            mRegisterActivity.dismissLoadingDialog();
            //注册成功
            L.i(mRegisterActivity.TAG, "返回码：" + response.code + "");
            if (response.code == 0) {//注册成功
                mRegisterActivity.showToast("注册成功");
                mRegisterActivity.finish();
            } else if (response.code == 1003 || response.code == 1002) {//验证码错误
                mBinding.tilVerification.setErrorEnabled(true);
                mBinding.tilVerification.setError(response.error);
            } else if (response.code == 2000) {
                mBinding.tilPhone.setErrorEnabled(true);
                mBinding.tilPhone.setError(response.error);
            }
        }
    }
}
