//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Main2Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        List<String> list = new ArrayList<>();

//        MyAdapter1 adapter = new MyAdapter1(new String[] {"one", "two", "three", "four", "five" , "six" , "seven" , "eight" , "nine" , "ten"},
//                new String[] {"1", "b", "c", "d", "e" , "f" , "g" , "h" , "i" , "j"},
//                new String[] {"あ", "い", "う", "え", "お" , "か" , "き" , "く" , "け" , "こ"},
//                new String[] {"one", "two", "three", "four", "five" , "six" , "seven" , "eight" , "nine" , "ten"},
//                new String[] {"one", "two", "three", "four", "five" , "six" , "seven" , "eight" , "nine" , "ten"});
//
//        rv.setAdapter(adapter);
//
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        //1.current.userからお気に入りリスト取得
        //2.お気に入りリスト分だけ中で繰り返す
        Database2 db2 = new Database2("dafsdf");

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        db2.went(user.getUid(), rv);

        return rootView;
    }
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//    }
}
