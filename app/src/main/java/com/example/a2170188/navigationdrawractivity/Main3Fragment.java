//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Main3Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        //値変える
        MyAdapter2 adapter = new MyAdapter2(new String[] {"one", "two", "three", "four", "five" , "six" , "seven" , "eight" , "nine" , "ten"},
                new int[] {R.drawable.img_1,R.drawable.img_1,R.drawable.img_1,R.drawable.img_1,R.drawable.img_1,R.drawable.img_1,R.drawable.img_1,R.drawable.img_1,R.drawable.img_1,R.drawable.img_1});

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//    }
}
