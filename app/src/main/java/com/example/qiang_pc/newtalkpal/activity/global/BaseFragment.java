package com.example.qiang_pc.newtalkpal.activity.global;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.qiang_pc.newtalkpal.activity.utils.Utils;

/**
 * Created by admin on 2016/1/26.
 */
public abstract class BaseFragment extends Fragment{

    protected View rootView;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        if(rootView==null){
            rootView = inflater.inflate(getLayoutResID(), container, false);
            initView();
            initListener();
        }else{
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if(parent!=null){
                parent.removeView(rootView);
            }
        }
        //解决软键盘隐藏问题
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)
                        mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });
        return rootView;
    }

    /**
     * 在屏幕中央显示一个Toast
     * @param text
     */
    public void showToast(String text) {
        Utils.showToast(getActivity(), text);
    }

    public abstract int getLayoutResID();
    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();
}
