package com.example.a2170188.navigationdrawractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

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
        //いいねランキング
        //店名
        TextView rankstorename1 = view.findViewById(R.id.rankstorename1);
        TextView rankstorename2 = view.findViewById(R.id.rankstorename2);
        TextView rankstorename3 = view.findViewById(R.id.rankstorename3);
        TextView rankstorename4 = view.findViewById(R.id.rankstorename4);
        TextView rankstorename5 = view.findViewById(R.id.rankstorename5);
        TextView rankstorename6 = view.findViewById(R.id.rankstorename6);
        TextView rankstorename7 = view.findViewById(R.id.rankstorename7);
        TextView rankstorename8 = view.findViewById(R.id.rankstorename8);
        TextView rankstorename9 = view.findViewById(R.id.rankstorename9);
        TextView rankstorename10 = view.findViewById(R.id.rankstorename10);

        List<TextView> rankstorenamelist= Arrays.asList(rankstorename1,
                rankstorename2,rankstorename3,rankstorename4,rankstorename5,
                rankstorename6,rankstorename7,rankstorename8,rankstorename9,
                rankstorename10);

        //テキスト
        TextView ranktext01 = view.findViewById((R.id.ranktext01));
        TextView ranktext02 = view.findViewById((R.id.ranktext02));
        TextView ranktext03 = view.findViewById((R.id.ranktext03));
        TextView ranktext04 = view.findViewById((R.id.ranktext04));
        TextView ranktext05 = view.findViewById((R.id.ranktext05));
        TextView ranktext06 = view.findViewById((R.id.ranktext06));
        TextView ranktext07 = view.findViewById((R.id.ranktext07));
        TextView ranktext08 = view.findViewById((R.id.ranktext08));
        TextView ranktext09 = view.findViewById((R.id.ranktext09));
        TextView ranktext10 = view.findViewById((R.id.ranktext10));

        List<TextView> ranktextlist= Arrays.asList(ranktext01,
                ranktext02, ranktext03,ranktext04,ranktext05,
                ranktext06,ranktext07,ranktext08,ranktext09,
                ranktext10);

        //写真
        ImageView rankimg01 = view.findViewById(R.id.rankimg01);
        ImageView rankimg02 = view.findViewById(R.id.rankimg02);
        ImageView rankimg03 = view.findViewById(R.id.rankimg03);
        ImageView rankimg04 = view.findViewById(R.id.rankimg04);
        ImageView rankimg05 = view.findViewById(R.id.rankimg05);
        ImageView rankimg06 = view.findViewById(R.id.rankimg06);
        ImageView rankimg07 = view.findViewById(R.id.rankimg07);
        ImageView rankimg08 = view.findViewById(R.id.rankimg08);
        ImageView rankimg09 = view.findViewById(R.id.rankimg09);
        ImageView rankimg10 = view.findViewById(R.id.rankimg10);
        ImageView rankimg11 = view.findViewById(R.id.rankimg11);
        ImageView rankimg12 = view.findViewById(R.id.rankimg12);
        ImageView rankimg13 = view.findViewById(R.id.rankimg13);
        ImageView rankimg14 = view.findViewById(R.id.rankimg14);
        ImageView rankimg15 = view.findViewById(R.id.rankimg15);
        ImageView rankimg16 = view.findViewById(R.id.rankimg16);
        ImageView rankimg17 = view.findViewById(R.id.rankimg17);
        ImageView rankimg18 = view.findViewById(R.id.rankimg18);
        ImageView rankimg19 = view.findViewById(R.id.rankimg19);
        ImageView rankimg20 = view.findViewById(R.id.rankimg20);

        List<ImageView> rankimglist= Arrays.asList(rankimg01,
                rankimg02,rankimg03,rankimg04,rankimg05,rankimg06,
                rankimg07,rankimg08,rankimg09,rankimg10,rankimg11,
                rankimg12,rankimg13,rankimg14,rankimg15,rankimg16,
                rankimg17,rankimg18,rankimg19,rankimg20);

        //アイコン
        ImageView rankicon01 = view.findViewById(R.id.rankicon01);
        ImageView rankicon02 = view.findViewById(R.id.rankicon02);
        ImageView rankicon03 = view.findViewById(R.id.rankicon03);
        ImageView rankicon04 = view.findViewById(R.id.rankicon04);
        ImageView rankicon05 = view.findViewById(R.id.rankicon05);
        ImageView rankicon06 = view.findViewById(R.id.rankicon06);
        ImageView rankicon07 = view.findViewById(R.id.rankicon07);
        ImageView rankicon08 = view.findViewById(R.id.rankicon08);
        ImageView rankicon09 = view.findViewById(R.id.rankicon09);
        ImageView rankicon10 = view.findViewById(R.id.rankicon10);


        List<ImageView> rankiconlist =Arrays.asList(rankicon01,
                rankicon02,rankicon03,rankicon04,rankicon05,
                rankicon06,rankicon07,rankicon08,rankicon09,
                rankicon10);

        //性
        TextView rankfirstname01 = view.findViewById(R.id.rankfirstname01);
        TextView rankfirstname02 = view.findViewById(R.id.rankfirstname02);
        TextView rankfirstname03 = view.findViewById(R.id.rankfirstname03);
        TextView rankfirstname04 = view.findViewById(R.id.rankfirstname04);
        TextView rankfirstname05 = view.findViewById(R.id.rankfirstname05);
        TextView rankfirstname06 = view.findViewById(R.id.rankfirstname06);
        TextView rankfirstname07 = view.findViewById(R.id.rankfirstname07);
        TextView rankfirstname08 = view.findViewById(R.id.rankfirstname08);
        TextView rankfirstname09 = view.findViewById(R.id.rankfirstname09);
        TextView rankfirstname10 = view.findViewById(R.id.rankfirstname10);

        List<TextView> rankfirstnamelist = Arrays.asList(rankfirstname01,
                rankfirstname02,rankfirstname03,rankfirstname04,rankfirstname05,
                rankfirstname06,rankfirstname07,rankfirstname08,rankfirstname09,
                rankfirstname10);

        //名
        TextView ranklastname01 = view.findViewById(R.id.ranklastname01);
        TextView ranklastname02 = view.findViewById(R.id.ranklastname02);
        TextView ranklastname03 = view.findViewById(R.id.ranklastname03);
        TextView ranklastname04 = view.findViewById(R.id.ranklastname04);
        TextView ranklastname05 = view.findViewById(R.id.ranklastname05);
        TextView ranklastname06 = view.findViewById(R.id.ranklastname06);
        TextView ranklastname07 = view.findViewById(R.id.ranklastname07);
        TextView ranklastname08 = view.findViewById(R.id.ranklastname08);
        TextView ranklastname09 = view.findViewById(R.id.ranklastname09);
        TextView ranklastname10 = view.findViewById(R.id.ranklastname10);

        List<TextView> ranklastnamelist = Arrays.asList(ranklastname01,
                ranklastname02,ranklastname03,ranklastname04,ranklastname05,
                ranklastname06,ranklastname07,ranklastname08,ranklastname09,
                ranklastname10);

        Database2 comments = new Database2("comments");
        comments.top10(MyApplication.getAppContext(), rankstorenamelist,ranktextlist,rankimglist,rankfirstnamelist,ranklastnamelist,rankiconlist);


        //新着投稿
        //アイコン
        ImageView newicon01 = view.findViewById(R.id.newicon01);
        ImageView newicon02 = view.findViewById(R.id.newicon02);
        ImageView newicon03 = view.findViewById(R.id.newicon03);
        ImageView newicon04 = view.findViewById(R.id.newicon04);
        ImageView newicon05 = view.findViewById(R.id.newicon05);
        ImageView newicon06 = view.findViewById(R.id.newicon06);
        ImageView newicon07 = view.findViewById(R.id.newicon07);
        ImageView newicon08 = view.findViewById(R.id.newicon08);
        ImageView newicon09 = view.findViewById(R.id.newicon09);
        ImageView newicon10 = view.findViewById(R.id.newicon10);

        List<ImageView> iconlist = Arrays.asList(newicon01,
                newicon02,newicon03,newicon04,newicon05,
                newicon06,newicon07,newicon08,newicon09,
                newicon10);

        //性
        TextView newfirstname01 = view.findViewById(R.id.newfirstname01);
        TextView newfirstname02 = view.findViewById(R.id.newfirstname02);
        TextView newfirstname03 = view.findViewById(R.id.newfirstname03);
        TextView newfirstname04 = view.findViewById(R.id.newfirstname04);
        TextView newfirstname05 = view.findViewById(R.id.newfirstname05);
        TextView newfirstname06 = view.findViewById(R.id.newfirstname06);
        TextView newfirstname07 = view.findViewById(R.id.newfirstname07);
        TextView newfirstname08 = view.findViewById(R.id.newfirstname08);
        TextView newfirstname09 = view.findViewById(R.id.newfirstname09);
        TextView newfirstname10 = view.findViewById(R.id.newfirstname10);

        List<TextView> firstnamelist = Arrays.asList(newfirstname01,
                newfirstname02,newfirstname03,newfirstname04,newfirstname05,
                newfirstname06,newfirstname07,newfirstname08,newfirstname09,
                newfirstname10);

        //名
        TextView newlastname01 = view.findViewById(R.id.newlastname01);
        TextView newlastname02 = view.findViewById(R.id.newlastname02);
        TextView newlastname03 = view.findViewById(R.id.newlastname03);
        TextView newlastname04 = view.findViewById(R.id.newlastname04);
        TextView newlastname05 = view.findViewById(R.id.newlastname05);
        TextView newlastname06 = view.findViewById(R.id.newlastname06);
        TextView newlastname07 = view.findViewById(R.id.newlastname07);
        TextView newlastname08 = view.findViewById(R.id.newlastname08);
        TextView newlastname09 = view.findViewById(R.id.newlastname09);
        TextView newlastname10 = view.findViewById(R.id.newlastname10);

        List<TextView> lastnamelist = Arrays.asList(newlastname01,
                newlastname02,newlastname03,newlastname04,newlastname05,
                newlastname06,newlastname07,newlastname08,newlastname09,
                newlastname10);

        //店名
        TextView newstorename01 = view.findViewById(R.id.newstorename01);
        TextView newstorename02 = view.findViewById(R.id.newstorename02);
        TextView newstorename03 = view.findViewById(R.id.newstorename03);
        TextView newstorename04 = view.findViewById(R.id.newstorename04);
        TextView newstorename05 = view.findViewById(R.id.newstorename05);
        TextView newstorename06 = view.findViewById(R.id.newstorename06);
        TextView newstorename07 = view.findViewById(R.id.newstorename07);
        TextView newstorename08 = view.findViewById(R.id.newstorename08);
        TextView newstorename09 = view.findViewById(R.id.newstorename09);
        TextView newstorename10 = view.findViewById(R.id.newstorename10);

        List<TextView> storenamelist = Arrays.asList(newstorename01,
                newstorename02,newstorename03,newstorename04,newstorename05,
                newstorename06,newstorename07,newstorename08,newstorename09,
                newstorename10);

        //日付
        TextView newTime01 = view.findViewById(R.id.newTime01);
        TextView newTime02 = view.findViewById(R.id.newTime02);
        TextView newTime03 = view.findViewById(R.id.newTime03);
        TextView newTime04 = view.findViewById(R.id.newTime04);
        TextView newTime05 = view.findViewById(R.id.newTime05);
        TextView newTime06 = view.findViewById(R.id.newTime06);
        TextView newTime07 = view.findViewById(R.id.newTime07);
        TextView newTime08 = view.findViewById(R.id.newTime08);
        TextView newTime09 = view.findViewById(R.id.newTime09);
        TextView newTime10 = view.findViewById(R.id.newTime10);

        List<TextView> timelist = Arrays.asList(newTime01,
                newTime02,newTime03,newTime04,newTime05,
                newTime06,newTime07,newTime08,newTime09,
                newTime10);

        //投稿の説明
        TextView newText01 = view.findViewById(R.id.newText01);
        TextView newText02 = view.findViewById(R.id.newText02);
        TextView newText03 = view.findViewById(R.id.newText03);
        TextView newText04 = view.findViewById(R.id.newText04);
        TextView newText05 = view.findViewById(R.id.newText05);
        TextView newText06 = view.findViewById(R.id.newText06);
        TextView newText07 = view.findViewById(R.id.newText07);
        TextView newText08 = view.findViewById(R.id.newText08);
        TextView newText09 = view.findViewById(R.id.newText09);
        TextView newText10 = view.findViewById(R.id.newText10);

        List<TextView> textlist = Arrays.asList(newText01,
                newText02,newText03,newText04,newText05,
                newText06,newText07,newText08,newText09,
                newText10);

        //写真
        ImageView newImage01 = view.findViewById(R.id.newImage01);
        ImageView newImage02 = view.findViewById(R.id.newImage02);
        ImageView newImage03 = view.findViewById(R.id.newImage03);
        ImageView newImage04 = view.findViewById(R.id.newImage04);
        ImageView newImage05 = view.findViewById(R.id.newImage05);
        ImageView newImage06 = view.findViewById(R.id.newImage06);
        ImageView newImage07 = view.findViewById(R.id.newImage07);
        ImageView newImage08 = view.findViewById(R.id.newImage08);
        ImageView newImage09 = view.findViewById(R.id.newImage09);
        ImageView newImage10 = view.findViewById(R.id.newImage10);
        ImageView newImage11 = view.findViewById(R.id.newImage11);
        ImageView newImage12 = view.findViewById(R.id.newImage12);
        ImageView newImage13 = view.findViewById(R.id.newImage13);
        ImageView newImage14 = view.findViewById(R.id.newImage14);
        ImageView newImage15 = view.findViewById(R.id.newImage15);
        ImageView newImage16 = view.findViewById(R.id.newImage16);
        ImageView newImage17 = view.findViewById(R.id.newImage17);
        ImageView newImage18 = view.findViewById(R.id.newImage18);
        ImageView newImage19 = view.findViewById(R.id.newImage19);
        ImageView newImage20 = view.findViewById(R.id.newImage20);

        List<ImageView> imglist = Arrays.asList(newImage01,
                newImage02,newImage03,newImage04,newImage05,
                newImage06,newImage07,newImage08,newImage09,
                newImage10,newImage11,newImage12,newImage13,
                newImage14,newImage15,newImage16,newImage17,
                newImage18,newImage19,newImage20);

        comments.new10(MyApplication.getAppContext(),timelist,storenamelist,textlist,imglist,firstnamelist,lastnamelist,iconlist);





        //ここから追加
        Spinner spinner =(Spinner)view.findViewById(R.id.spinner);
        int idx = spinner.getSelectedItemPosition();
        String item = (String)spinner.getSelectedItem();
//
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item =(String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//

        //検索結果画面へ遷移する
        Button button = (Button) view.findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MyApplication.getInputMethodManager().hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                //検索バーに入力されている文字を取得
                EditText edittext = view.findViewById(R.id.text);
                String text =  edittext.getText().toString();

                //選択されているジャンルを取得
                Spinner spinner = view.findViewById(R.id.spinner);
                String genre =(String)spinner.getSelectedItem();

                SearchResults searchResults = new SearchResults(text, genre);
                searchResults.change();
            }
        });
//

        //店舗詳細画面へ遷移する
//        LinearLayout layout =(LinearLayout) view.findViewById(R.id.test00);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplication(),Test2.class);
//                //  ImageView icon01 = view.findViewById()(R.id.newicon01);
//                TextView newfirstname01 = view.findViewById(R.id.newfirstname01);
//
//                String firstname = newfirstname01.getText().toString();
//
//                intent.putExtra("firstname",firstname);
//
//                startActivity(intent);
//
//            }
//        });
    }
}