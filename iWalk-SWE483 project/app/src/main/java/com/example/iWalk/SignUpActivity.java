package com.example.iWalk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    TextView back;
    EditText etName,etPass,etEmail,etPass1;
    Button btnSignup;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signup);
        back = findViewById(R.id.back);
        etName = findViewById(R.id.etName);
        etPass = findViewById(R.id.etPass);
        etPass1 = findViewById(R.id.etPass1);
        etEmail = findViewById(R.id.etEmail);
        btnSignup = findViewById(R.id.btnSignup);
        back.setOnClickListener(view -> finish());
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(etName)) {
                    Toast t = Toast.makeText(SignUpActivity.this, "You must enter first name to register!", Toast.LENGTH_SHORT);
                    t.show();
                    etName.setError("Name should not be empty");
                }
                if(isEmpty(etPass)){
                    etPass.setError("Password must be not empty");
                }
                if(isEmpty(etPass1)){
                    etPass1.setError("Password must be not empty");
                }
                if(isEmpty(etEmail)){
                    etEmail.setError("Email should be filled");
                }
                if(validate(etEmail.toString())){
                    etEmail.setError("Enter valid email address");
                }
                DB=new DBHelper(SignUpActivity.this);
                String name = etName.getText().toString();
                String password=etPass.getText().toString();
                String email= etEmail.getText().toString();
                Boolean checkinsertdata= DB.insertUserData(name,password,email);
                if(checkinsertdata==true) {
                    Toast.makeText(SignUpActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }
                    else
                    Toast.makeText(SignUpActivity.this, "Signup not successful", Toast.LENGTH_SHORT).show();
            }
        });

            }
            boolean isEmpty(EditText text) {
                CharSequence str = text.getText().toString();
                return TextUtils.isEmpty(str);
            }

            public  final Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

            public  boolean validate(String emailStr) {
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
                return matcher.find();
            }
        }
