package com.inhatc.android_fianl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class adminpw extends AppCompatActivity {
    Button adminbutton;
    EditText textView;
    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin);
        adminbutton = findViewById(R.id.adminlogin);
        textView = findViewById(R.id.passwordEditText);
        adminbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            if(textView.getText().toString().equals("1234")){
                Intent intent = new Intent(adminpw.this, ListActivity.class);
                intent.putExtra("email", "admin");
                intent.putExtra("UID", "adminUID");
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"비밀번호가 틀립니다",Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}
//
//movepage.setOnClickListener(new View.OnClickListener(){
//@Override
//public void onClick(View view) {
//        Intent intent = new Intent(getApplicationContext(),adminpw.class);
//        startActivity(intent);
//        }
//        });