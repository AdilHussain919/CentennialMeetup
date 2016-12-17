package com.meetup.centennial.centennialmeetup;

import android.content.Intent;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText id,passwordText;
    Button loginButton;
    TextView signupLink;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        //final DBAdapter db = new DBAdapter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //db.open();

       // db.insertUser(300859921, "Satinder" ,"bangar" ,"xyz@email.co","260694","hobby" ,"password"," program"," department", "campus", "profession");
     //   db.close();
        loginButton = (Button)findViewById(R.id.btn_login) ;
        id = (EditText)findViewById(R.id.input_id);
        passwordText =(EditText)findViewById(R.id.input_password);
        signupLink = (TextView)findViewById(R.id.link_signup);
        //  ButterKnife.inject(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Oncick working", Toast.LENGTH_LONG).show();
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            Toast.makeText(LoginActivity.this, "Inside validations failed", Toast.LENGTH_LONG).show();
            onLoginFailed();

            return;
        } else {
            //Toast.makeText(LoginActivity.this, "validations good fetching", Toast.LENGTH_LONG).show();


            ///////////////------- Viranch ---------------/////////////////////////////
            //BAdapter db = new DBAdapter(this);
            String Id =id.getText().toString();
            String password = passwordText.getText().toString();

            /*db.open();
            Cursor cursor = db.getUser(Id, password);



            if  (cursor.moveToFirst())
            {
                cursor.moveToFirst();
                int c1 = cursor.getInt(cursor.getColumnIndex("centennialid"));
                String c2 = cursor.getString(cursor.getColumnIndex("password"));
                Toast.makeText(LoginActivity.this,"fethced"+c2, Toast.LENGTH_SHORT).show();*/
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);


            try{
                String url = "http://10.0.2.2:8000/centennialmeetup/login?centennialid=" + Id + "&password=" + password;
                URL loginAPI = new URL(url);
                HttpURLConnection client = null;
                client = (HttpURLConnection) loginAPI.openConnection();
                client.setRequestMethod("GET");
                client.setDoOutput(true);
                client.setDoInput(true);
                client.connect();

                StringBuilder sb = new StringBuilder();
                int HttpResult = client.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(client.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println("" + sb.toString());
                    if (sb.toString().contains("Incorrect password"))
                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                    else if (sb.toString().contains("Logged In")){


                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("UserId",id.getText().toString());
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "No user found!", Toast.LENGTH_LONG).show();
                    }


                } else {
                    System.out.println(client.getResponseMessage());
                    Toast.makeText(LoginActivity.this, "No user found!", Toast.LENGTH_LONG).show();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ////////////////////////----------------------------------////////////////////////////

        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }



    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = id.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty()) {
            id.setError("enter your centennial id");
            valid = false;
        } else {
            id.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}

