//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import java.util.ArrayDeque;
import java.util.Deque;

//https://qiita.com/ksugawara61/items/31e72681fff7d6d390e9
//どこからでもcontexなどを取れるようにするソースファイル

public class MyApplication extends Application {
    private static Context context;
    private static LayoutInflater inflater;
    private static FrameLayout frameLayout;
    //スタック
    private static Deque<View> deque = new ArrayDeque<>();
    private static InputMethodManager inputMethodManager;
    private static Resources resources;


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
        MyApplication.deque.push(view);
    }

    public static void setResources() {
        resources = MyApplication.getAppContext().getResources();
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
        return MyApplication.deque.pop();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    //getResourcesがオーバーライド判定くらう
    public static Resources getR() {
        return resources;
    }
}
