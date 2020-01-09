package com.example.a2170188.navigationdrawractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;
public class PostScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postscreen);

        Intent intent =getIntent();
        String firstname =intent.getStringExtra("firstname");
        TextView textView = findViewById(R.id.resultfirstname);
        textView.setText(firstname);

        //アイコン
        ImageView resulticon = findViewById(R.id.resulticon);



        //性
        TextView resultfirstname = findViewById(R.id.resultfirstname);
        String text = resultfirstname.getText().toString();

        //名
        TextView resultlastname = findViewById(R.id.resultlastname);

        //投稿日
        TextView resultTime = findViewById(R.id.resultTime);

        //店名
        Button resultstorename = findViewById(R.id.resultstorename);

        //投稿の説明
        TextView resultText = findViewById(R.id.resultText);

        //写真
        ImageView resultImage01 = findViewById(R.id.resultImage01);
        ImageView resultImage02 = findViewById(R.id.resultImage02);

        Database2 comments = new Database2("comments");








    }
}
