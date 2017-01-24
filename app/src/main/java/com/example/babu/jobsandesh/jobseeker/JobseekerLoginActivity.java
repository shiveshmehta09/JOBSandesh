package com.example.babu.jobsandesh.jobseeker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.babu.jobsandesh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class JobseekerLoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText seeker_username,seeker_password;

    Button login_button,button_throw_registerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseeker_login);

        seeker_username = (EditText)findViewById(R.id.etseek_username);
        seeker_password = (EditText)findViewById(R.id.editseekpassword);

        login_button = (Button)findViewById(R.id.bt_login);

        button_throw_registerActivity = (Button)findViewById(R.id.bt_registerpage);

        login_button.setOnClickListener(this);
        button_throw_registerActivity.setOnClickListener(this);
/*login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, JobseekNavActivity.class);
                startActivity(intent);
            }
        });
        button_throw_registerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JobseekRegisterActivity.class);
                        startActivity(intent);
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_login)
        {
            new BackgroundJobseekerLogin(this).execute(seeker_username.getText().toString(),seeker_password.getText().toString());
        }else
        {
            Intent intent=new Intent(this,JobseekRegisterActivity.class);
            startActivity(intent);
        }
    }
}

class BackgroundJobseekerLogin extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;

    BackgroundJobseekerLogin(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String register_url = "http://192.168.43.59/job_alert_app/jobseeker/login.php";
        try {
            String str_seeker_username = params[0],
                    str_seeker_password = params[1];
            URL url = new URL(register_url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username_by_post", "UTF-8") + "=" + URLEncoder.encode(str_seeker_username, "UTF-8")
                    + "&"
                    + URLEncoder.encode("password_by_post", "UTF-8") + "=" + URLEncoder.encode(str_seeker_password, "UTF-8");

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
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        alertDialog.setCancelable(true);
    }

    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
        String username = null;
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray arr_res = jsonObject.getJSONArray("result");

                for (int i = 0; i < result.length(); i++) {
                    JSONObject r = arr_res.getJSONObject(i);
                    /*String  id = r.getString("id"),
                            email = r.getString("email_id"),
                            password = r.getString("password");*/

                    if(r.getString("username")!=null) {
                        username = r.getString("username");
                    }else
                    {
                        username=null;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent in = new Intent(context, JobseekNavActivity.class);
            if(username!=null)
            {
                in.putExtra("name", username);
                context.startActivity(in);
            }
            else
            {
                Toast.makeText(context, "REGISTER to krle.....", Toast.LENGTH_SHORT).setMargin(40,20);
            }

        }
    }
}