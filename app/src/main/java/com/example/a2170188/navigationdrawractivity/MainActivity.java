package com.example.a2170188.navigationdrawractivity;


import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class MainActivity extends AppCompatActivity {
    public static final int sub = 1001;

    private Menu1Activity menu1Activity = new Menu1Activity();
    private Menu2Activity menu2Activity = new Menu2Activity();
    private Menu3Activity menu3Activity = new Menu3Activity();
    private Menu4Activity menu4Activity = new Menu4Activity();
    private Menu5Activity menu5Activity = new Menu5Activity();
    private MapsActivity mapsActivity = new MapsActivity();

    //Log.d用
    private static final String TAG = "MainActivity";


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
                break;
        }

        if (view != null) {
//            Log.d(TAG, "changeView removeAllViews");
//            frame.removeAllViews();
            frame.addView(view);
            Intent intent = new Intent(this, Menu2Activity.class);
            startActivity(intent);
        }
    }

}
