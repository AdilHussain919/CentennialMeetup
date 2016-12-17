package com.meetup.centennial.centennialmeetup;

import android.content.Intent;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    String columnname=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final DBAdapter db = new DBAdapter(this);
        ImageButton searchBtn=(ImageButton) findViewById(R.id.imageButton);

        //TEXT BOX WITH ARGUMENT (FIRSTNAME)
        final EditText searcharg=(EditText)findViewById(R.id.editText);

        //TEXT BOX WITH ADDITIONAL ARGUMENT
        final EditText secondargument=(EditText)findViewById(R.id.edittextArgument2);


        /*********INSERTING SAMPLE DATA****//////
        /*db.open();
        db.insertUser(1,"Paul","Smith","abc@gmail","Nov2,2013","Cricket","password","3249","ICET","Progress","Student");
        db.insertUser(2,"Paula","Stuart","xyz@gmail","Nov6,1998","GYM","password","3298","Business","Morningside","Faculty");
        db.insertUser(3,"Adil","Hussain","def@gmail","Nov6,1990","Table Tennis","password","3288","DENTAL","Morningside","Faculty");
        db.insertUser(4,"Satinderjit","Bangar","sbngr@gmail","June26 1994","GYM","password","3948","ICEt","Progress","Student");
        db.insertUser(5,"Paul","Stuart","xyz@gmail","Nov6,1998","GYM","password","3298","Business","Morningside","Faculty");
        db.close();*/

        try{
            String url2 = "http://10.0.2.2:8000/centennialmeetup/getallusers";
            URL getuserAPI = new URL(url2);
            HttpURLConnection client1 = null;
            client1 = (HttpURLConnection) getuserAPI.openConnection();
            client1.setRequestMethod("GET");
            client1.setDoOutput(true);
            client1.setDoInput(true);
            client1.connect();

            StringBuilder sb1 = new StringBuilder();
            int HttpResult1 = client1.getResponseCode();
            if (HttpResult1 == HttpURLConnection.HTTP_OK) {
                BufferedReader br1 = new BufferedReader(
                        new InputStreamReader(client1.getInputStream(), "utf-8"));
                String line1 = null;
                while ((line1 = br1.readLine()) != null) {
                    sb1.append(line1 + "\n");
                }
                br1.close();
                JSONObject json = new JSONObject(sb1.toString());
                db.open();
                List<String> list = new ArrayList<String>();
                JSONArray array = json.getJSONArray("data");
                for(int i = 0 ; i < array.length() ; i++){
                    db.insertUser(i + 1,
                            array.getJSONObject(i).getString("firstname"),
                            array.getJSONObject(i).getString("lastname"),
                            array.getJSONObject(i).getString("emailida"),
                            array.getJSONObject(i).getString("dateofbirth"),
                            array.getJSONObject(i).getString("hobby"),
                            array.getJSONObject(i).getString("password"),
                            array.getJSONObject(i).getString("program"),
                            array.getJSONObject(i).getString("department"),
                            array.getJSONObject(i).getString("campus"),
                            array.getJSONObject(i).getString("profession"));
                }
                db.close();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

/****************GETTING THE COLUMN NAME FOR THE SECOND ARGUMENT*////////////////
        final Spinner colname=(Spinner)findViewById(R.id.spinner);

        colname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                String columnname=colname.getSelectedItem().toString();
                //Toast.makeText(getBaseContext(),columnname,Toast.LENGTH_LONG).show();
                setColoumnname(columnname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        /********Search Function invoked on click*/////
        searchBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {

                //SETTING COLUMNNAME ON THE BASIS OF SELECTION FROM SPINNER
                switch(columnname)
                {
                    case "Department":
                        columnname="department";
                       // Toast.makeText(getApplicationContext(),"INSIDE DEPT",Toast.LENGTH_LONG).show();
                        break;
                    case "Last Name":
                        columnname="lastname";
                        break;
                    case "Campus":
                        columnname="campus";
                        break;
                    default:
                        columnname="default";
                        break;

                }

                //SETTING SECOND ARGUMENT
                String searcharg2=secondargument.getText().toString();

                //SETTING FORST ARGUMENT
                String argument1=searcharg.getText().toString();


                if(!argument1.equals("(Enter First name)") ||argument1!=null  )
                {

                   // Toast.makeText(getBaseContext(),"arg2 is"+searcharg2,Toast.LENGTH_LONG).show();
                    db.open();
                    final CustomAdapter customAdapter;
                    Cursor cursor = db.searchfunction(argument1,searcharg2,columnname);
                    customAdapter = new CustomAdapter(SearchActivity.this, cursor, 0);
                    ListView searchedList = (ListView) findViewById(R.id.searchresults);
                    String[] items = {""};
                    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(items));
                    if (cursor.moveToFirst()) {

                        do {
                            arrayList.add((cursor.getString(cursor.getColumnIndex("firstname"))));
                          //  Toast.makeText(getApplicationContext(), "" + arrayList, Toast.LENGTH_SHORT).show();
                        } while (cursor.moveToNext());

                    } else {
                        Toast.makeText(getApplicationContext(), "No Result Found", Toast.LENGTH_LONG).show();
                    }
                    db.close();


                    /**StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                    StrictMode.setThreadPolicy(policy);

                    JSONObject json = null;
                    try{
                        String url = "";
                        if (searcharg2.equals("Second Search Keyword(Optional)") ||searcharg2 ==null)
                            url = "http://10.0.2.2:8000/centennialmeetup/search?arg1=" + argument1;

                        else
                            url = "http://10.0.2.2:8000/centennialmeetup/search?arg1=" + argument1 + "&arg2=" + searcharg2 +"&columnname=" + columnname;

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

                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                    searchedList.setAdapter(customAdapter);
                    searchedList.setAdapter(customAdapter);
                    searchedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            String selectedSetting =customAdapter.getid();
                            //////////////--------- Viranch -------------------////////////////////

                            Intent t =getIntent();

                            String Userid = t.getExtras().getString("Uid");

                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);


                            try{
                                URL addfriendAPI = new URL("http://10.0.2.2:8000/centennialmeetup/addfriend");
                                HttpURLConnection client = null;
                                client = (HttpURLConnection) addfriendAPI.openConnection();
                                client.setRequestMethod("POST");
                                client.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                                client.setDoOutput(true);
                                client.setDoInput(true);

                                String Postdata = new String();
                                Postdata = Postdata + "otherusersname=" + selectedSetting + "&";
                                Postdata = Postdata + "centennialid=" + Userid;

                                //Postdata = URLEncoder.encode(Postdata,"UTF-8");
                                byte[] postData       = Postdata.getBytes( StandardCharsets.UTF_8 );

                                try( DataOutputStream wr = new DataOutputStream( client.getOutputStream())) {
                                    wr.write( postData );
                                    wr.flush();
                                    wr.close();
                                }

                                //display what returns the POST request

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
                                    Toast.makeText(getApplicationContext(), selectedSetting + " added to Friends", Toast.LENGTH_LONG).show();
                                } else {
                                    System.out.println(client.getResponseMessage());
                                }

                            } catch(MalformedURLException error) {
                                //Handles an incorrectly entered URL
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            /////////////////---------------------------------------//////////////////////////////


                        }
                    });

                }else
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Search Argument",Toast.LENGTH_LONG).show();
                    db.close();
                }
            }

        });


        /*CREATE ON CLICK LISTNERS ON THE ITEMS OF THE LIST GENERATED TO GET TO USER PROFILE  HERE *///



    }


    /*TO ACCESS THE columnname as it was declared final *////
    public void setColoumnname(String arg)
    {

       // Toast.makeText(getApplicationContext(),"SETTING GLOBAL COLUMN NAME",Toast.LENGTH_LONG).show();
        columnname=arg;
    }
    public String getColumnname()
    {
        return columnname;
    }



}
