package com.inhatc.android_fianl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();

    EditText title_et, content_et;
    Button reg_button;

    String loginEmail = "";
    String loginUID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        loginEmail = getIntent().getStringExtra("email");
        loginUID = getIntent().getStringExtra("UID");
        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);
        reg_button = findViewById(R.id.reg_button);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db;
                FirebaseDatabase mfire = FirebaseDatabase.getInstance();
                db = mfire.getReference("board");
                UUID uid = UUID.randomUUID();
                HashMap<String, Object> board = new HashMap<>();
                board.put("title", title_et.getText().toString());
                board.put("content", content_et.getText().toString());
                board.put("writer", loginEmail);

                db.child(loginUID).child(uid.toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot != null && dataSnapshot.exists()) {
                                db.child(loginUID).child(uid.toString()).updateChildren(board);
                            } else {
                                db.child(loginUID).child(uid.toString()).setValue(board);
                            }
                            Toast.makeText(getApplicationContext(), "게시글 등록완료", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e(TAG, "Error getting data", task.getException());
                        }
                    }
                });
            }
        });

    }
}