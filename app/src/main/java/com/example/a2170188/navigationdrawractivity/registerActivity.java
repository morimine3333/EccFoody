//作成者:盛

package com.example.a2170188.navigationdrawractivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        //判定
        if(email.isEmpty()) {
            Toast.makeText(registerActivity.this, "メールアドレスを入力してください。", Toast.LENGTH_LONG).show();
        } else if(password.isEmpty()) {
            Toast.makeText(registerActivity.this, "パスワードを入力してください。", Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // サインインに成功し、サインインしたユーザーの情報でUIを更新する
                                Toast.makeText(registerActivity.this, "作成成功",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                                String firstName = ((TextView)findViewById(R.id.nameText)).getText().toString();
                                String lastName = ((TextView)findViewById(R.id.nameText2)).getText().toString();

                                Database2 database2 = new Database2();
                                database2.shinki(firstName, lastName, user.getUid());


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
                                Toast.makeText(registerActivity.this, "作成失敗",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
