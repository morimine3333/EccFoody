//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

//https://knowledge.moshimore.jp/entry/android_tablayout_viewpager

public class FavoriteActivity extends AppCompatActivity {
    private CharSequence[] tabTitle = {"行った", "行きたい", "フォロー", "フォロワー"};

    private static final String TAG = "FavoriteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_main);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, "getItem: " + position);

                switch (position) {
                    case 0:
                        return new Main1Fragment();
                    case 1:
                        return new Main3Fragment();
                    case 2:
                        return new Main3Fragment();
                    case 3:
                        return new Main4Fragment();
                    default:
                        return null;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitle[position];
            }

            @Override
            public int getCount() {
                return tabTitle.length;
            }
        };
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(tabTitle.length);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
