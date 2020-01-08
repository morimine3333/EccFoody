package com.example.a2170188.navigationdrawractivity;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.widget.Toolbar;

import java.lang.ref.WeakReference;

//コンストラクタをいじったactivityを開始しようとするとインスタンス生成出来ないと言われる
////https://ja.stackoverflow.com/questions/4257/activity%E3%82%92%E7%B6%99%E6%89%BF%E3%81%97%E3%81%A6%E3%81%84%E3%81%AA%E3%81%84%E3%82%AF%E3%83%A9%E3%82%B9%E3%81%8B%E3%82%89%E7%94%BB%E9%9D%A2%E9%81%B7%E7%A7%BB%E3%82%92%E8%A1%8C%E3%81%86
//    //https://riptutorial.com/ja/android/example/14069/weakreference%E3%81%A8%E3%81%97%E3%81%A6%E3%82%A2%E3%82%AF%E3%83%86%E3%82%A3%E3%83%93%E3%83%86%E3%82%A3%E3%82%92%E6%B8%A1%E3%81%97%E3%81%A6%E3%83%A1%E3%83%A2%E3%83%AA%E3%83%AA%E3%83%BC%E3%82%AF%E3%82%92%E5%9B%9E%E9%81%BF%E3%81%99%E3%82%8B
//    Activity activity;
//    WeakReference<Activity> weakReference;
//      this
//      new WeakReference<Activity>(this)
//      activity.startActivity(new Intent(activity, loginActivity.class));
//      weakReference.get().startActivity(new Intent(weakReference.get(), loginActivity.class));

public class MainActivity extends AppCompatActivity {
    public static final int sub = 1001;

    //Log.d用
    private static final String TAG = "MainActivity";

    //登録アクティビティの onCreate メソッドで、FirebaseAuth オブジェクトの共有インスタンスを取得します。
    private FirebaseAuth mAuth;

//모리군은 최고로 섹시합니다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt);

        //Toolbarに文字を設定
        //https://teratail.com/questions/97280
        String title = "ECC FOODY";
        Toolbar toolbar = (Toolbar) findViewById(R.id.TLHeader);
        toolbar.setTitle(title);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);

        TextView email_tv = (TextView) header.findViewById(R.id.nav_money_input);
        TextView name_tv = (TextView) header.findViewById(R.id.nav_tag);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_money_input:
                        Toast.makeText(MainActivity.this, "nav_money_input", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_tag:
                        Toast.makeText(MainActivity.this, "nav_tag", Toast.LENGTH_SHORT).show();
                    case R.id.nav_tool:
                        Toast.makeText(MainActivity.this, "nav_tool", Toast.LENGTH_SHORT).show();
                    case R.id.nav_calendar:
                        Toast.makeText(MainActivity.this, "nav_calendar", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        //画面下部のボタンのリスナー
        ImageButton ImageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton1.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(0);
            }
        });
        ImageButton ImageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton2.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(1);
            }
        });
        ImageButton ImageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton3.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(2);
            }
        });
        ImageButton ImageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton4.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(3);
            }
        });
        ImageButton ImageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        ImageButton5.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(4);
            }


        });


        //アプリ起動時の認証処理
        // Firebase Authを初期化する
        mAuth = FirebaseAuth.getInstance();

        // ユーザーがサインインしているかどうか（null以外）を確認
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // 未認証
            //ログイン画面に遷移する
            MyApplication.getAppContext().startActivity(new Intent(MyApplication.getAppContext(), loginActivity.class));
//            startActivity(new Intent(MainActivity.this, loginActivity.class));
//            activity.startActivity(new Intent(activity, loginActivity.class));
//            weakReference.get().startActivity(new Intent(weakReference.get(), loginActivity.class));
        }

        //アプリ起動時の画面遷移(ホーム画面)
        changeView(0);
    }

    private void changeView(int index) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        FrameLayout frame = (FrameLayout) findViewById(R.id.frame_layout);
        if (frame.getChildCount() > 0) {
            frame.removeViewAt(0);
        }

        View view = null;
        //確認コード
        Log.d(TAG, "changeView index: " + index);

        switch (index) {
            case 0:
                view = inflater.inflate(R.layout.activity_menu1, frame, false);
                Menu1Activity menu1Activity = new Menu1Activity(frame, view);
                menu1Activity.change();
                break;
            case 1:
                view = inflater.inflate(R.layout.activity_menu2, frame, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.activity_menu3, frame, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.activity_menu4, frame, false);
                break;
            case 4:
                view = inflater.inflate(R.layout.activity_menu5, frame, false);
                Menu5Activity menu5Activity = new Menu5Activity(frame, view);
                menu5Activity.change();
                break;
        }

        if (view != null) {
            Log.d(TAG, "changeView removeAllViews");
            frame.removeAllViews();
            frame.addView(view);
        }
    }
}
