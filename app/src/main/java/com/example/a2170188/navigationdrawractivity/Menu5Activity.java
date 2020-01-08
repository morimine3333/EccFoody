package com.example.a2170188.navigationdrawractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;

public class Menu5Activity{

    FrameLayout frame;
    View view;

    private static final String TAG = "Menu1Activity";

    Menu5Activity() {}

    Menu5Activity(FrameLayout frame, View view) {
        this.frame = frame;
        this.view = view;
    }

    public void change() {
        Button button5 = view.findViewById(R.id.button5);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Menu5Activity.this, "ログアウトボタン", Toast.LENGTH_LONG).show();
                logout();
                //ログイン画面への遷移の処理を追加
                MyApplication.getAppContext().startActivity(new Intent(MyApplication.getAppContext(), loginActivity.class));
            }
        });

        Button button6 = view.findViewById(R.id.button6);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Menu5Activity.this, "退会ボタン", Toast.LENGTH_LONG).show();
                delete();
                //ログイン画面への遷移の処理を追加
            }
        });
    }

    private void logout() {
        //ログアウト
        FirebaseAuth.getInstance().signOut();
//        Toast.makeText(Menu5Activity.this, "ログアウト完了", Toast.LENGTH_LONG).show();
    }

    private void delete() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(Menu5Activity.this, "退会完了", Toast.LENGTH_LONG).show();
                            //ユーザーの関連情報(CloudFirestore, CloudStorage)の情報を削除
                        }
                    }
                });
    }
}
