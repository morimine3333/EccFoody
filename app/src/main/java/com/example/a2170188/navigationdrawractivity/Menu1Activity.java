package com.example.a2170188.navigationdrawractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;

public class Menu1Activity {
    FrameLayout frame;
    View view;

    private static final String TAG = "Menu1Activity";

    Menu1Activity() {

    }

    Menu1Activity(FrameLayout frame, View view) {
        this.frame = frame;
        this.view = view;
    }

    public void change() {
        //消してホーム画面のソース追加
    }
}