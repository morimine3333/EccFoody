package com.example.a2170188.navigationdrawractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Menu2Activity extends AppCompatActivity {
    FrameLayout frame;
    View view;

    Menu2Activity() {

    }

    Menu2Activity(FrameLayout frame, View view) {
        this.frame = frame;
        this.view = view;
    }

    public void change() {
        TextView textView = view.findViewById(R.id.textView2);
        textView.setText("fasfdsa");
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_menu2);
//    }
}

//public class Menu2Activity {
//    FrameLayout frame;
//    View view;
//
//
//    public Menu2Activity(FrameLayout frame, View view) {
//        TextView textView = view.findViewById(R.id.textView2);
//        textView.setText("aaaaa");
//        frame.addView(view);
//    }
//
//    public View getView() {
//        return view;
//    }
//}
