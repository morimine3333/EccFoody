package com.example.a2170188.navigationdrawractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SearchResults extends AppCompatActivity {
    private String text;
    private String genre;


    SearchResults() { }

    SearchResults(String text, String genre) {
        this.text = text;
        this.genre = genre;
    }

    public void change() {

        LayoutInflater inflater = MyApplication.getInflater();
        FrameLayout frame = MyApplication.getFrameLayout();
        View view = inflater.inflate(R.layout.activity_searchresults, frame, false);

        TextView textView =  view.findViewById(R.id.searchTextView1);
        textView.setText(genre);

        //店名
        TextView searchstorename01 = view.findViewById(R.id.searchstorename01);
        TextView searchstorename02 = view.findViewById(R.id.searchstorename02);
        TextView searchstorename03 = view.findViewById(R.id.searchstorename03);
        TextView searchstorename04 = view.findViewById(R.id.searchstorename04);
        TextView searchstorename05 = view.findViewById(R.id.searchstorename05);
        TextView searchstorename06 = view.findViewById(R.id.searchstorename06);
        TextView searchstorename07 = view.findViewById(R.id.searchstorename07);
        TextView searchstorename08 = view.findViewById(R.id.searchstorename08);
        TextView searchstorename09 = view.findViewById(R.id.searchstorename09);
        TextView searchstorename10 = view.findViewById(R.id.searchstorename10);

        List<TextView> searchstorenamelist = Arrays.asList(searchstorename01,
                searchstorename02,searchstorename03,searchstorename04,
                searchstorename05,searchstorename06,searchstorename07,
                searchstorename08,searchstorename09,searchstorename10);

        //写真
        ImageView searchImage01 = view.findViewById(R.id.searchImage01);
        ImageView searchImage02 = view.findViewById(R.id.searchImage02);
        ImageView searchImage03 = view.findViewById(R.id.searchImage03);
        ImageView searchImage04 = view.findViewById(R.id.searchImage04);
        ImageView searchImage05 = view.findViewById(R.id.searchImage05);
        ImageView searchImage06 = view.findViewById(R.id.searchImage06);
        ImageView searchImage07 = view.findViewById(R.id.searchImage07);
        ImageView searchImage08 = view.findViewById(R.id.searchImage08);
        ImageView searchImage09 = view.findViewById(R.id.searchImage09);
        ImageView searchImage10 = view.findViewById(R.id.searchImage10);
        ImageView searchImage11 = view.findViewById(R.id.searchImage11);
        ImageView searchImage12 = view.findViewById(R.id.searchImage12);
        ImageView searchImage13 = view.findViewById(R.id.searchImage13);
        ImageView searchImage14 = view.findViewById(R.id.searchImage14);
        ImageView searchImage15 = view.findViewById(R.id.searchImage15);
        ImageView searchImage16 = view.findViewById(R.id.searchImage16);
        ImageView searchImage17 = view.findViewById(R.id.searchImage17);
        ImageView searchImage18 = view.findViewById(R.id.searchImage18);
        ImageView searchImage19 = view.findViewById(R.id.searchImage19);
        ImageView searchImage20 = view.findViewById(R.id.searchImage20);

        List<ImageView> searchimglist = Arrays.asList(searchImage01,
                searchImage02,searchImage03,searchImage04,searchImage05,
                searchImage06,searchImage07,searchImage08,searchImage09,
                searchImage10,searchImage11,searchImage12,searchImage13,
                searchImage14,searchImage15,searchImage16,searchImage17,
                searchImage18,searchImage19,searchImage20);

        Database2 comments = new Database2("comments");

        comments.search(MyApplication.getAppContext(),text,genre,searchstorenamelist,searchimglist);

        frame.removeAllViews();
        frame.addView(view);
    }
}
