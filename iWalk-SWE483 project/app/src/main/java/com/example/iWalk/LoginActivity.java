package com.example.iWalk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView back;
    EditText etEmail,etPass;
    Button btnLogin;
    DBHelper DB;
    String passwordFromDatabase;
    String nameFromDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        back = findViewById(R.id.back);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPass);
        back.setOnClickListener(view -> finish());
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("datastore", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                DB=new DBHelper(LoginActivity.this);
                String email= etEmail.getText().toString();
                String password=etPass.getText().toString();
                Cursor passwordData= DB.getPassword(email);
                if(passwordData.getCount()==0){
                    Toast.makeText(LoginActivity.this, "Wrong email or password, please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                while(passwordData.moveToNext()){
                    nameFromDatabase = passwordData.getString(0);
                    passwordFromDatabase = passwordData.getString(1);
                    editor.putString("Name",  nameFromDatabase);
                    editor.apply();
                }
                if(password.equals(passwordFromDatabase)){
                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else
                    Toast.makeText(LoginActivity.this, "Login not successful", Toast.LENGTH_SHORT).show();

        }
        });

    }


}
