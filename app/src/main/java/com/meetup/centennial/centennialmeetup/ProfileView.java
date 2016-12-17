package com.meetup.centennial.centennialmeetup;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import static com.meetup.centennial.centennialmeetup.R.id.add;
import static com.meetup.centennial.centennialmeetup.R.id.edit_fname;

public class ProfileView extends AppCompatActivity {
    SQLiteDatabase dba;


    public String Uid;

    public static TextView centId,fname,lname,pos,email,hobby,dob,program,campus,department,editfname,editlname,editaddress,editemail,editdob,edithobby,editprog,editorigin,editcampus;

    Button btnAddFriend, btnSendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        Intent t = getIntent();

        Uid = t.getExtras().getString("Uid");


        centId = (TextView) findViewById(R.id.tv_id);
        fname = (TextView) findViewById(R.id.tv_fn);
        lname = (TextView) findViewById(R.id.tv_ln);
        email = (TextView) findViewById(R.id.tv_email);
        dob = (TextView) findViewById(R.id.tv_dob);
        hobby = (TextView) findViewById(R.id.tv_hobby);
        pos = (TextView) findViewById(R.id.tv_pos);
        program = (TextView) findViewById(R.id.tv_program);
        campus = (TextView) findViewById(R.id.tv_campus);
        department = (TextView) findViewById(R.id.tv_dept);
        editfname = (TextView) findViewById(R.id.edit_fname);
        editlname = (TextView) findViewById(R.id.edit_lname);
        editaddress = (TextView) findViewById(R.id.edit_address);
        editemail = (TextView) findViewById(R.id.edit_email);
        editdob = (TextView) findViewById(R.id.edit_dob);
        editprog = (TextView) findViewById(R.id.edit_program);
        editcampus = (TextView) findViewById(R.id.edit_campus);
        editorigin = (TextView) findViewById(R.id.edit_origin);
        edithobby = (TextView) findViewById(R.id.edit_hobby);

        ////////////////---------------- Viranch ----------------////////////////////
        /*final DatabaseManager db = new DatabaseManager(this);

        Cursor cursor = db.getUser("User","300853316");
        cursor.moveToFirst();

        centId.setText(cursor.getString(0));
        fname.setText(cursor.getString(1));
        lname.setText(cursor.getString(2));
        email.setText(cursor.getString(3));
        dob.setText(cursor.getString(4));
        hobby.setText(cursor.getString(5));
        String pas = cursor.getString(6);
        program.setText(cursor.getString(7));
        department.setText(cursor.getString(8));
        campus.setText(cursor.getString(9));
        pos.setText(cursor.getString(10));

        if(!cursor.isClosed())
        {
            cursor.close();
        }*/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        JSONObject json = null;
        try {
            String url = "http://10.0.2.2:8000/centennialmeetup/getprofile?centennialid=" + Uid;
            URL getprofileAPI = new URL(url);
            HttpURLConnection client = null;
            client = (HttpURLConnection) getprofileAPI.openConnection();
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
                    json = new JSONObject(sb.toString());
                    System.out.println(json);
                }
                br.close();
            }

            centId.setText(json.getString("centennialid"));
            fname.setText(json.getString("firstname"));
            lname.setText(json.getString("lastname"));
            email.setText(json.getString("email"));
            dob.setText(json.getString("dateofbirth"));
            hobby.setText(json.getString("hobby"));
            program.setText(json.getString("program"));
            department.setText(json.getString("department"));
            campus.setText(json.getString("campus"));
            pos.setText(json.getString("profession"));

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ////////////////////////----------------------------------////////////////////////////

        editfname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String head = "First Name";
                String value = fname.getText().toString();
                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

                startActivity(intent);

            }
        });
        editlname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = "Last Name";
                String value = lname.getText().toString();

                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

            }
        });
        editaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = "Email";
                String value = email.getText().toString();

                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

                startActivity(intent);

            }
        });
        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = "Date Of Birth";
                String value = dob.getText().toString();

                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

                startActivity(intent);

            }
        });
        editdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = "Hobby";
                String value = hobby.getText().toString();

                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

                startActivity(intent);

            }
        });
        edithobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String head = "Position";
                String value = pos.getText().toString();

                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

                startActivity(intent);

            }
        });
        editprog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = "Program";
                String value = program.getText().toString();

                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

                startActivity(intent);

            }
        });
        editorigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = "Department";
                String value = department.getText().toString();

                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

                startActivity(intent);

            }
        });
        editcampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = "Campus";
                String value = campus.getText().toString();

                Intent intent = new Intent(ProfileView.this, EditProfile.class);
                intent.putExtra("texthead", head);
                intent.putExtra("value", value);

                startActivity(intent);

            }
        });
    }
/*
//        btnAddFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //dba.addFriend(intUserId,current_user_id);

            }
        });
*/

        public void sendAText(View view){
                Intent intent = new Intent(ProfileView.this,SendSMSActivity.class);
                intent.putExtra("phonenum","5556");
                startActivity(intent);
        };


        //Check UpdateRecord Method in database manager and create onClick method for Updating and Saving profile
        //Code for updating is similar to the code provided above    // }
    }
