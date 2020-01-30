package com.example.a2170188.navigationdrawractivity;

import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class Menu2Activity {
    private CharSequence[] tabTitle = {"行きたい", "行った", "フォロー", "フォロワー"};
    private static final String TAG = "Menu2Activity";

    public void change(View view) {
        //https://firespeed.org/diary.php?diary=kenz-1426
        //https://wasnot.hatenablog.com/entry/2013/04/20/220534
        //2回目以降はそもそもgetItemが動かない
        //MyApplication.getMainActivity().getSupportFragmentManager()
//        this.getChildFragmentManager();
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(MyApplication.getMainActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, "getItem: " + position);

                switch (position) {
                    case 0:
                        return new Main1Fragment();
                    case 1:
                        return new Main2Fragment();
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

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(tabTitle.length);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}