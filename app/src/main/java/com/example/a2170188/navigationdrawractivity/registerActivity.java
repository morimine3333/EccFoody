package com.example.a2170188.navigationdrawractivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.opencensus.internal.StringUtils;

//新規登録画面
public class registerActivity extends AppCompatActivity {

    private static final String TAG = "registerActivity";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //新規登録ボタンを押したとき会員登録
        findViewById(R.id.newbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touroku();
            }
        });
    }

    public void touroku() {
        //メアドとパスワード取得
        final EditText emailText = findViewById(R.id.editText2);
        final EditText passwordText = findViewById(R.id.editText3);
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        //https://qiita.com/HALU5071/items/640652de9e31d4bbdbeb
        if(!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // サインインに成功し、サインインしたユーザーの情報でUIを更新する
                                Toast.makeText(registerActivity.this, "作成成功",
                                        Toast.LENGTH_SHORT).show();
//                                FirebaseUser user = mAuth.getCurrentUser();
                                finish();
                            } else {
                                //サインインに失敗した場合、ユーザーにメッセージを表示します。
                                Toast.makeText(registerActivity.this, "作成失敗",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(registerActivity.this, "入力不備",
                    Toast.LENGTH_SHORT).show();
        }


    }
}
