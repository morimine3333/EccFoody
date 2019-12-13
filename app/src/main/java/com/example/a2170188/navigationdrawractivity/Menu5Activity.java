package com.example.a2170188.navigationdrawractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu5);

        Button button5 = findViewById(R.id.button5);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu5Activity.this, "ログアウトボタン", Toast.LENGTH_LONG).show();
                logout();
            }
        });

        Button button6 = findViewById(R.id.button6);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu5Activity.this, "退会ボタン", Toast.LENGTH_LONG).show();
                delete();
            }
        });
    }

    private void logout() {
        //ログアウト
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(Menu5Activity.this, "ログアウト完了", Toast.LENGTH_LONG).show();
    }

    private void delete() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Menu5Activity.this, "退会完了", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
