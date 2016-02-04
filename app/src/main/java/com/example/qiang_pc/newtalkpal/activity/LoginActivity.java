package com.example.qiang_pc.newtalkpal.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.databinding.ActivityLoginBinding;
import com.example.qiang_pc.newtalkpal.global.BaseActivity;
import com.example.qiang_pc.newtalkpal.viewmodels.LoginActivityViewmodel;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private LoginActivityViewmodel viewmodel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ActivityLoginBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_login);

        viewmodel = new LoginActivityViewmodel(this, binding);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void register(View view){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void login(View view){
        viewmodel.login();
    }


}

