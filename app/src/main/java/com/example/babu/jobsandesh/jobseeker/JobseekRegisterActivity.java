package com.example.babu.jobsandesh.jobseeker;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.babu.jobsandesh.R;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class JobseekRegisterActivity extends AppCompatActivity
{
    EditText seeker_name,
            seeker_username,
            seeker_password,
            seeker_confirm_password,
            seeker_email;

    Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseek_register);

        seeker_name = (EditText) findViewById(R.id.et_seeker_name);
        seeker_username = (EditText) findViewById(R.id.et_seeker_user_name);
        seeker_password = (EditText) findViewById(R.id.et_seeker_password);
        seeker_confirm_password = (EditText) findViewById(R.id.et_seeker_confirm_password);
        seeker_email = (EditText) findViewById(R.id.et_seeker_email);

        register_button = (Button)findViewById(R.id.btn_seek_register);

     // register_button.setOnClickListener(this);

    register_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {

            String str_seeker_name = seeker_name.getText().toString(),
                    str_seeker_username = seeker_username.getText().toString(),
                    str_seeker_password = seeker_password.getText().toString(),
                    str_seeker_confirm_password = seeker_confirm_password.getText().toString(),
                    str_seeker_email = seeker_email.getText().toString();

            if (str_seeker_password.equals(str_seeker_confirm_password)) {
                new JobseekerRegisterBackground(JobseekRegisterActivity.this).execute(
                        str_seeker_name,
                        str_seeker_username,
                        str_seeker_password,
                        str_seeker_email
                );
            }
            else
            {
                Toast.makeText(JobseekRegisterActivity.this, "PASSWORD should be SAME", Toast.LENGTH_SHORT).show();
            }

        }
    });
}

class JobseekerRegisterBackground extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;

    JobseekerRegisterBackground(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        alertDialog.setCancelable(true);
    }

    @Override
    protected String doInBackground(String... params) {
        String register_url = "http://192.168.43.59/job_alert_app/jobseeker/register.php";
        try {

            String str_seeker_name = params[0],
                    str_seeker_username = params[1],
                    str_seeker_password = params[2],
                    str_seeker_email = params[3];

            URL url = new URL(register_url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("name_by_post", "UTF-8") + "=" + URLEncoder.encode(str_seeker_name, "UTF-8")
                    + "&"
                    + URLEncoder.encode("username_by_post", "UTF-8") + "=" + URLEncoder.encode(str_seeker_username, "UTF-8")
                    + "&"
                    + URLEncoder.encode("password_by_post", "UTF-8") + "=" + URLEncoder.encode(str_seeker_password, "UTF-8")
                    + "&"
                    + URLEncoder.encode("email_by_post", "UTF-8") + "=" + URLEncoder.encode(str_seeker_email, "UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {

                result += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return "Successfully Registered";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(String result) {
        String checkwith_me = "Not able To REGISTER";
        if (result.equals(checkwith_me)) {
            alertDialog.setMessage(checkwith_me);
            alertDialog.show();
        } else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
    }
}