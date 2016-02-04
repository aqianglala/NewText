package com.example.qiang_pc.newtalkpal.utils;

import com.example.qiang_pc.newtalkpal.global.BaseApplication;

/**
 * Created by admin on 2016/2/4.
 */
public class HasLoginUtils {

    public static String getToken(){
        String token =null;
        try {
            token = AESUtils.decrypt(UrlsOrKeys.seed, (String) SPUtils.get(BaseApplication.getInstance(),
                    UrlsOrKeys.TOKEN, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
