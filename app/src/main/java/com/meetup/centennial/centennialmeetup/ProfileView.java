package com.meetup.centennial.centennialmeetup;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ProfileView extends AppCompatActivity {
    SQLiteDatabase dba;

    private static final String tables[]={"tbl_users","tbl_friends"};
    //
    private static final String tableCreatorString[] ={"CREATE TABLE tbl_users (user_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "centennial_id TEXT, firstname TEXT, lastname TEXT, address TEXT, email TEXT, DOB TEXT, hobby TEXT, program TEXT, origin TEXT, campus TEXT);",
            "CREATE TABLE tbl_friends (user_id INTEGER, friend_id INTEGER);"};
    public static TextView centId,fname,lname,email,address,hobby,dob,program,campus,origin,editPro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        centId = (TextView)findViewById(R.id.tv_id);
        fname  = (TextView)findViewById(R.id.tv_fn);
        lname = (TextView)findViewById(R.id.tv_ln);
        address  = (TextView)findViewById(R.id.tv_add);
        email = (TextView)findViewById(R.id.tv_email);
        dob  = (TextView)findViewById(R.id.tv_dob);
        hobby = (TextView)findViewById(R.id.tv_hobby);
        program  = (TextView)findViewById(R.id.tv_program);
        campus = (TextView)findViewById(R.id.tv_campus);
        origin = (TextView)findViewById(R.id.tv_origin) ;
        editPro = (TextView)findViewById(R.id.edit_fname);
        dba=openOrCreateDatabase("Clinic.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);
        final DatabaseManager db = new DatabaseManager(this);
        //db.createDatabase(getApplicationContext());
        db.dbInitialize( tables,tableCreatorString);
        final String fields[] = {"centennial_id","firstname","lastname","address","email","dob","hobby","program","origin","campus"};
        final String record[] = new String[10];
        //Edit Text Fields
        //Add references to edit text fields bellow

        // Handle Save button
        // Add reference to add User button here
//retrieving info from edit text fields and putting into record array
        record[0]="User1";
        record[1]="amnsdns";
        record[2]="sdsadadas";
        record[3]="dsadasdsa";
        record[4]="sdsadsads";
        record[5]="sdsadsads";
        record[6]="sdsadsd";
        record[7]="sdsadsadsa";
        record[8]="sdsadsdsd";
        record[9]="dsadsadsadd";
        //checking if added
        Log.d("First Name: ",record[0]);
        ContentValues values=new ContentValues();
        //adding into database
        values.put("centennial_id",record[0]);
        values.put("firstname",record[1]);
        values.put("lastname",record[2]);
        values.put("address",record[3]);
        values.put("email",record[4]);
        values.put("dob",record[5]);
        values.put("hobby",record[6]);
        values.put("program",record[7]);
        values.put("origin",record[8]);
        values.put("campus",record[9]);
        db.addRecord(values, "tbl_users",fields,record);

        //dba.insert("tbl_users",null,values);
        //dba.close();
        //    db.getStudentById(1);
        Cursor cursor = db.getUser("tbl_users","User1");
        cursor.moveToFirst();
        centId.setText(cursor.getString(1));
        fname.setText(cursor.getString(2));
        lname.setText(cursor.getString(3));
        address.setText(cursor.getString(4));
        email.setText(cursor.getString(5));
        dob.setText(cursor.getString(6));
       hobby.setText(cursor.getString(7));
        program.setText(cursor.getString(8));
        origin.setText(cursor.getString(9));
        campus.setText(cursor.getString(10));

        if(!cursor.isClosed())
        {
            cursor.close();
        }

        editPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileView.this,EditProfile.class);
                startActivity(intent);

            }
        });


        //Check UpdateRecord Method in database manager and create onClick method for Updating and Saving profile
        //Code for updating is similar to the code provided above    // }
    }
}