package com.example.a2170188.navigationdrawractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Menu1Activity extends AppCompatActivity {

    //登録アクティビティの onCreate メソッドで、FirebaseAuth オブジェクトの共有インスタンスを取得します。
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

        // Firebase Authを初期化する
        mAuth = FirebaseAuth.getInstance();

        Button button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu1Activity.this, Menu5Activity.class);
                startActivity(intent);
                Toast.makeText(Menu1Activity.this, "マイページ遷移", Toast.LENGTH_LONG).show();
            }
        });
    }

    //onCreateの後にはしる
    @Override
    public void onStart() {
        super.onStart();
        // ユーザーがサインインしているかどうか（null以外）を確認
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // 認証済み
        } else {
            // 未認証
            //ログイン画面に遷移する
            Intent intent = new Intent(Menu1Activity.this, loginActivity.class);
            startActivity(intent);
        }
    }
}