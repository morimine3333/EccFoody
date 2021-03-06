//作成者:盛
package com.example.a2170188.navigationdrawractivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//ログイン画面
public class loginActivity extends AppCompatActivity {

    //タグの定数を定義 使っているクラス名?
    private static final String TAG = "loginActivity";

    //REQUEST_CODEは遷移先のアクティビティに対して一意に定まっていれば任意の整数でよい
    //整数の引数は、リクエストを識別する「要求コード」です。結果の Intent を受け取ると、
    // アプリが正常に結果を識別し、その処理方法を決定することができるように、コールバックが同じ要求コードを提供します
    private static final int RC_SIGN_IN = 1;

    //登録アクティビティの onCreate メソッドで、FirebaseAuth オブジェクトの共有インスタンスを取得します。
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase Authを初期化する
        mAuth = FirebaseAuth.getInstance();

        final Button sinki = findViewById(R.id.sinkibutton);
        final Button login = findViewById(R.id.loginbutton);

        //ログインボタン
        // ボタンに OnClickListener インターフェースを実装する
        login.setOnClickListener(new View.OnClickListener() {

            // クリック時に呼ばれるメソッド
            @Override
            public void onClick(View view) {
                //enterを押したときキーボードを閉じる処理
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                login();
//                Toast.makeText(loginActivity.this, "ログインボタン", Toast.LENGTH_LONG).show();
            }
        });

        //新規登録ボタン
        // ボタンに OnClickListener インターフェースを実装する
        sinki.setOnClickListener(new View.OnClickListener() {

            // クリック時に呼ばれるメソッド
            @Override
            public void onClick(View view) {
                //新規登録画面に遷移
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
//                Toast.makeText(loginActivity.this, "新規登録ボタン", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void login() {
        //メアドとパスワード取得
        final EditText idText = findViewById(R.id.idText);
        final EditText passwordText = findViewById(R.id.passwordText);
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();

        //判定
        if(id.isEmpty()) {
            Toast.makeText(loginActivity.this, "メールアドレスを入力して下さい", Toast.LENGTH_LONG).show();
        } else if(password.isEmpty()) {
            Toast.makeText(loginActivity.this, "パスワードを入力して下さい", Toast.LENGTH_LONG).show();
        } else {


            //ログイン判定
            mAuth.signInWithEmailAndPassword(id, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // サインインに成功し、サインインしたユーザーの情報でUIを更新する
                                Toast.makeText(loginActivity.this, "ログインに成功しました",
                                        Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = mAuth.getCurrentUser();
                                //ホーム画面へ遷移する
                                finish();

                                //再ログイン時の再起動処理
                                //https://www.hohog.net/activity_reload/
                                Intent intent = MyApplication.getMainActivity().getIntent();
                                overridePendingTransition(0, 0);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                finish();

                                overridePendingTransition(0, 0);
                                startActivity(intent);
                            } else {
                                //サインインに失敗した場合、ユーザーにメッセージを表示します。
                                Toast.makeText(loginActivity.this, "ログインに失敗しました",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // 戻るボタン押下
    @Override
    public void onBackPressed() {
        // 何もしない
    }
}
