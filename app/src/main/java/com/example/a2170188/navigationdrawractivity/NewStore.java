//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class NewStore extends AppCompatActivity {

    //Log.d用
    private static final String TAG = "NewStore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newstore);

        //住所を座標に変換するために必要
        final Geocoder gcoder = new Geocoder(this, Locale.getDefault());

        //Toast.make用
        final Context context = this;

        //Activity終了用
        final Activity activity = this;

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //エラー文初期化
                TextView storeError = findViewById(R.id.storeError);
                TextView telError = findViewById(R.id.telError);
                TextView streetAddressError = findViewById(R.id.streetAddressError);
                TextView budgetError = findViewById(R.id.budgetError);
                storeError.setText("");
                telError.setText("");
                streetAddressError.setText("");
                budgetError.setText("");

                //ジャンル
                Spinner genreSpinner = (Spinner)findViewById(R.id.genre);
                String genre = genreSpinner.getSelectedItem().toString();

                //店名
                String storeName = ((TextView)findViewById(R.id.storeName)).getText().toString();
                if(storeName.isEmpty()) {
                    storeError.setText("店名を入力してください");
                    return;
                }

                //https://qiita.com/helloyuki_/items/8fbb5b889299a27ed805
                //電話番号(正規表現必要)
                String tel = ((TextView) findViewById(R.id.tel)).getText().toString();

                if(!tel.matches("^0\\d-\\d{4}-\\d{4}$")) {
                    telError.setText("電話番号を正しく入力して下さい");
                    Log.d("new", "Error");
                    return;
                }

                //https://k-sugi.sakura.ne.jp/it_synthesis/android/3878/
                //住所(GeoPointに変換)
                GeoPoint geoPoint;
                String streetAddress = ((TextView)findViewById(R.id.streetAddress)).getText().toString();

                if(streetAddress.isEmpty()) {
                    streetAddressError.setText("住所を入力してください");
                    return;
                }

                int maxResults = 1;
                List<Address> lstAddr;

                // 位置情報の取得
                try {
                    lstAddr = gcoder.getFromLocationName(streetAddress, maxResults);

                    if (lstAddr != null && lstAddr.size() > 0) {
                        // 緯度・経度取得
                        Address addr = lstAddr.get(0);
                        double latitude = addr.getLatitude();
                        double longitude = addr.getLongitude();

                        geoPoint = new GeoPoint(latitude, longitude);
                    } else {
                        streetAddressError.setText("住所を正しく入力して下さい");
                        return;
                    }
                } catch (IOException ioe) {
                    Log.d(TAG, ioe.getMessage());
                    return;
                }

                //予算(閉店時間の場合はどうするのか)
                //昼予算
                Spinner lunchSpinner = (Spinner)findViewById(R.id.lunchBudget);
                String lunchBudget = lunchSpinner.getSelectedItem().toString();
                //夜予算
                Spinner dinnerSpinner = (Spinner)findViewById(R.id.dinnerBudget);
                String dinnerBudget = dinnerSpinner.getSelectedItem().toString();

                if(lunchBudget.equals("未指定") || dinnerBudget.equals("未指定")) {
                    budgetError.setText("予算を指定してください");
                    return;
                } else {
                    if(lunchBudget.equals("営業時間外")) {
                        lunchBudget = "0";
                    }

                    if(dinnerBudget.equals("営業時間外")) {
                        dinnerBudget = "0";
                    }

                    lunchBudget = lunchBudget.replaceAll("[^0-9]", "");
                    dinnerBudget = dinnerBudget.replaceAll("[^0-9]", "");

                }

                //開店時間
                Spinner startHHSpinner = (Spinner)findViewById(R.id.startHH);
                Spinner startMMSpinner = (Spinner)findViewById(R.id.startMM);
                String startHH = startHHSpinner.getSelectedItem().toString();
                String startMM = startMMSpinner.getSelectedItem().toString();
                String startTime = startHH + ":" + startMM;
                //閉店時間
                Spinner endHHSpinner = (Spinner)findViewById(R.id.endHH);
                Spinner endMMSpinner = (Spinner)findViewById(R.id.endMM);
                String endHH = endHHSpinner.getSelectedItem().toString();
                String endMM = endMMSpinner.getSelectedItem().toString();
                String endTime = endHH + ":" + endMM;
                //営業時間
                String businessHours = startTime + "～" + endTime;

                //定休日
                CheckBox[] checkBoxes = {findViewById(R.id.Mon), findViewById(R.id.Tue), findViewById(R.id.Wed),
                                        findViewById(R.id.Thu), findViewById(R.id.Fri), findViewById(R.id.Sat),
                                        findViewById(R.id.Sun)};

                Map<String, Boolean> weekMap = new HashMap<>();
                weekMap.put("月曜日", checkBoxes[0].isChecked());
                weekMap.put("火曜日", checkBoxes[1].isChecked());
                weekMap.put("水曜日", checkBoxes[2].isChecked());
                weekMap.put("木曜日", checkBoxes[3].isChecked());
                weekMap.put("金曜日", checkBoxes[4].isChecked());
                weekMap.put("土曜日", checkBoxes[5].isChecked());
                weekMap.put("日曜日", checkBoxes[6].isChecked());


                Database2 db2 = new Database2("stores");
                db2.newStore(genre, storeName, tel, geoPoint, lunchBudget, dinnerBudget, businessHours, weekMap,
                        context, activity);
            }
        });

    }

}
