package com.example.babu.jobsandesh.employer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.babu.jobsandesh.R;

public class Employer_LoginActivity extends AppCompatActivity {
    EditText empUsername,empPass;
    Button emp_login,emp_register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer__login);
        empUsername = (EditText)findViewById(R.id.etemp_username);
        empPass = (EditText)findViewById(R.id.etemp_pass);
        emp_login = (Button)findViewById(R.id.btemp_login);
        emp_register = (Button)findViewById(R.id.btemp_register);


        emp_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Employer_LoginActivity.this,Employer_LoginActivity.class);
                startActivity(intent);
            }
        });
        emp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Employer_LoginActivity.this, employerRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
