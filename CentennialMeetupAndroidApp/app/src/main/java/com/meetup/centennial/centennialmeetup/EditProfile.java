package com.meetup.centennial.centennialmeetup;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    private TextView lable ;
    private EditText value;
    private Button update;
    DatabaseManager db ;
    ProfileView obj ;
  public  static ContentValues values;
    public static  String heading,editValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.4));

       heading = getIntent().getStringExtra("texthead");
        editValue = getIntent().getStringExtra("value");

        lable = (TextView)findViewById(R.id.heading);
        value = (EditText)findViewById(R.id.value);
        update = (Button)findViewById(R.id.but_update);
        lable.setText(heading);
        value.setText(editValue);






            update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // String updated_value =   value.getText().toString();


                //adding into database
              //  values.put("centennial_id",updated_value);
                if(heading.equals("First Name"))
                {

                    db.updateRecord(values,"User","firstname",value.getText().toString());
                }
                else if(heading.equals("Last Name"))
                {
                    db.updateRecord(values,"User","lastname",value.getText().toString());

                }
                else if(heading.equals("Email"))
                {
                    db.updateRecord(values,"User","emailid",value.getText().toString());

                }
                else if(heading.equals("Date Of Birth"))
                {
                    db.updateRecord(values,"User","dateofbirth",value.getText().toString());

                }


                else if(heading.equals("Hobby"))
                {
                    db.updateRecord(values,"User","hobby",value.getText().toString());

                }
                else if(heading.equals("Position"))
                {
                    db.updateRecord(values,"User","position",value.getText().toString());

                }
                else if(heading.equals("Program"))
                {
                    db.updateRecord(values,"User","program",value.getText().toString());

                }
                else if(heading.equals("department"))
                {
                    db.updateRecord(values,"User","department",value.getText().toString());

                }
                else if(heading.equals("Campus"))
                {
                    db.updateRecord(values,"User","campus",value.getText().toString());

                }

                else
                {
                    Toast.makeText(EditProfile.this,"Invalid Data",Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
