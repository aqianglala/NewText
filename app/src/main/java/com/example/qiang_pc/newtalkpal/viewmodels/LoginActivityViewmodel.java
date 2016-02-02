package com.example.qiang_pc.newtalkpal.viewmodels;

import android.text.TextUtils;

import com.example.qiang_pc.newtalkpal.activity.LoginActivity;
import com.example.qiang_pc.newtalkpal.bean.EventMessage;
import com.example.qiang_pc.newtalkpal.bean.LoginBean;
import com.example.qiang_pc.newtalkpal.databinding.ActivityLoginBinding;
import com.example.qiang_pc.newtalkpal.utils.AESUtils;
import com.example.qiang_pc.newtalkpal.utils.L;
import com.example.qiang_pc.newtalkpal.utils.SPUtils;
import com.example.qiang_pc.newtalkpal.utils.UrlsOrKeys;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.simple.eventbus.EventBus;

import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/2/2.
 */
public class LoginActivityViewmodel {
    private LoginActivity mLoginActivity;
    private ActivityLoginBinding mBinding;

    public LoginActivityViewmodel(LoginActivity loginActivity , ActivityLoginBinding binding){
        mLoginActivity=loginActivity;
        mBinding=binding;
    }

    public void login() {
        String phone = mBinding.etPhone.getText().toString().trim();
        String password = mBinding.etPassword.getText().toString().trim();
        if(validateUser(phone,password)){
            mLoginActivity.showLoadingDialog();
            OkHttpUtils
                    .post()
                    .url(UrlsOrKeys.LOGIN)
                    .addParams("phone", URLEncoder.encode(phone))
                    .addParams("password", URLEncoder.encode(password))
                    .tag(mLoginActivity)
                    .build()
                    .execute(new MyCallback());
        }else{
            mLoginActivity.showToast("用户名或密码不能为空");
        }
    }

    private boolean validateUser(String phone, String password) {
        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
            return false;
        }
        return true;
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
            L.i(mLoginActivity.TAG,"数据加载错误");
            mLoginActivity.dismissLoadingDialog();
        }

        @Override
        public void onResponse(LoginBean response) {
            mLoginActivity.dismissLoadingDialog();
            L.i(mLoginActivity.TAG,"token："+response.getData().getToken());
            //验证是否登录成功
            if(response.getCode()==0){//登录成功

                try {
                    SPUtils.put(mLoginActivity,UrlsOrKeys.TOKEN,AESUtils.encrypt(UrlsOrKeys.seed,
                            response.getData().getToken()));
                    SPUtils.put(mLoginActivity,UrlsOrKeys.PHONE,response.getData().getUser().getPhone());
                    SPUtils.put(mLoginActivity,UrlsOrKeys.NICKNAME,response.getData().getUser()
                            .getNickname());
                    String headimgurl = response.getData().getUser().getHeadimgurl();
                    if(!TextUtils.isEmpty(headimgurl)){
                        SPUtils.put(mLoginActivity,UrlsOrKeys.HEADIMGURL,response.getData().getUser()
                                .getHeadimgurl());
                    }
                    mLoginActivity.showToast("登录成功");
                    EventBus.getDefault().post(new EventMessage("登录成功"),"update_order_list");
                    //如果登录，则把用户信息保存到sharedPreference中
                    mLoginActivity.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {//登录失败
                mLoginActivity.showToast("登录失败");
            }
        }
    }
}
