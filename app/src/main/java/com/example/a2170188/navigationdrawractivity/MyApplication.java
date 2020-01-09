package com.example.a2170188.navigationdrawractivity;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

//https://qiita.com/ksugawara61/items/31e72681fff7d6d390e9

public class MyApplication extends Application {
    private static Context context;
    private static LayoutInflater inflater;
    private static FrameLayout frameLayout;
    private static View view;
    private static InputMethodManager inputMethodManager;


    /**
     * アプリケーションの起動時に呼び出される
     */
    @Override
    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    /**
     *アプリケーション終了時に呼び出される
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static void setInflater(LayoutInflater inflater) {
        MyApplication.inflater = inflater;
    }

    public static void setFrameLayout(FrameLayout frameLayout) {
        MyApplication.frameLayout = frameLayout;
    }

    public static void setInputMethodManager(InputMethodManager inputMethodManager) {
        MyApplication.inputMethodManager = inputMethodManager;
    }

    public static void setView(View view) {
        MyApplication.view = view;
    }

    public static LayoutInflater getInflater() {
        return MyApplication.inflater;
    }

    public static FrameLayout getFrameLayout() {
        return MyApplication.frameLayout;
    }

    public static InputMethodManager getInputMethodManager() {
        return MyApplication.inputMethodManager;
    }

    public static View getView() {
        return MyApplication.view;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
