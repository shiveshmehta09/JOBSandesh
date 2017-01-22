package com.example.babu.jobsandesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.babu.jobsandesh.employer.Employer_LoginActivity;
import com.example.babu.jobsandesh.jobseeker.LoginActivity;


public class MainActivity extends AppCompatActivity
{
    Button jobseek,emp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jobseek = (Button)findViewById(R.id.bt_jobseek);
        emp = (Button)findViewById(R.id.bt_emp);
        jobseek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Employer_LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
