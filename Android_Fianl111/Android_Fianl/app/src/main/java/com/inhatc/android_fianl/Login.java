package com.inhatc.android_fianl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    ImageView captcha;
    EditText edtCode;
    Button btnCode;
    Button movepage;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registerNow);
        movepage = findViewById(R.id.btn_login3);
        captcha =  findViewById(R.id.captchaImageView);

        GeneratorCaptcha.resetCaptcha();
        captcha.setImageBitmap(GeneratorCaptcha.getCaptchaImage());

        edtCode = findViewById(R.id.edtCode);

        btnCode = findViewById(R.id.btnCode);
        btnCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GeneratorCaptcha.resetCaptcha();
                captcha.setImageBitmap(GeneratorCaptcha.getCaptchaImage());
            }
        });
        //페이지 이동
        movepage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),adminpw.class);
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this,"Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this,"Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!edtCode.getText().toString().equals(GeneratorCaptcha.getCaptchaCode())){
                    Toast.makeText(Login.this,"code login", Toast.LENGTH_SHORT).show();
                    GeneratorCaptcha.resetCaptcha();
                    captcha.setImageBitmap(GeneratorCaptcha.getCaptchaImage());
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("UID", task.getResult().getUser().getUid());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}