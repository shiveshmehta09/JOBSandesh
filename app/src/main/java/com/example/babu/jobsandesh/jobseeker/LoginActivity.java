package com.example.babu.jobsandesh.jobseeker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.babu.jobsandesh.R;

public class LoginActivity extends AppCompatActivity
{
    EditText jobseekUsername,jobseekPass;
    Button jobseek_login,jobseek_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        jobseekUsername = (EditText)findViewById(R.id.etseek_username);
        jobseekPass = (EditText)findViewById(R.id.editseekpassword);
        jobseek_login = (Button)findViewById(R.id.bt_login);
        jobseek_register = (Button)findViewById(R.id.bt_registerpage);

        jobseek_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, JobseekNavActivity.class);
                startActivity(intent);

            }
        });
        jobseek_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JobseekRegisterActivity.class);
                        startActivity(intent);
            }
        });
    }
}
