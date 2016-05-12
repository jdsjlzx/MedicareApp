package com.nuoxin.enterprise.util;

import android.app.Activity;
import android.content.Context;

import com.nuoxin.enterprise.ui.LoginActivity;

/**
 * Created by lzx on 15/12/5.
 */
public class UIHelper {

    /**
     * 显示登陆界面
     *
     * @param context
     */
    public static void showLoginActivity(Context context) {
        IntentUtil.startActivity(((Activity) context), LoginActivity.class);
    }
}
