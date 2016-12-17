package com.meetup.centennial.centennialmeetup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText edId, edFname, edLname, edEmail, edPassword, edConfirmpass, edDob, edHobby, edProgram;
    RadioGroup rbProfession;
    Spinner spinDepartment, spinCampus;
    Button signupButton;
    RadioButton rbChosen;
    TextView loginLink;
    private Pattern pattern;
    private Matcher matcher;
    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final DBAdapter db = new DBAdapter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edId = (EditText) findViewById(R.id.edCentenniald);
        edFname = (EditText) findViewById(R.id.edFName);
        edLname = (EditText) findViewById(R.id.edLName);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edTextPassword);
        edConfirmpass = (EditText) findViewById(R.id.edTextCPassword);
        edDob = (EditText) findViewById(R.id.edDOB);
        edHobby = (EditText) findViewById(R.id.edTextHobby);
        edProgram = (EditText) findViewById(R.id.edProgram);
        spinDepartment = (Spinner) findViewById(R.id.spinDepartment);
        spinCampus = (Spinner) findViewById(R.id.spinCampus);
        rbProfession = (RadioGroup) findViewById(R.id.rgProfession);
        int selected = rbProfession.getCheckedRadioButtonId();
        rbChosen = (RadioButton) findViewById(selected);
        loginLink = (TextView) findViewById(R.id.link_login);
        signupButton = (Button) findViewById(R.id.btn_signup);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.open();

                signup();
                //db.close();

            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signup(){
        Log.d(TAG, "Signup");

        if (!validate() /*&& !dateValidate(edDob.getText().toString())*/) {
            onSignupFailed();
            return;
        } else
        {
            signupButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                    R.style.AppTheme);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            String id = ((EditText) findViewById(R.id.edCentenniald)).getText().toString();
            String fname = ((EditText) findViewById(R.id.edFName)).getText().toString();
            String lname = ((EditText) findViewById(R.id.edLName)).getText().toString();
            String email = ((EditText) findViewById(R.id.edEmail)).getText().toString();
            String date = ((EditText) findViewById(R.id.edDOB)).getText().toString();
            String hobby = ((EditText) findViewById(R.id.edTextHobby)).getText().toString();
            String password = ((EditText) findViewById(R.id.edTextPassword)).getText().toString();
            String program = ((EditText) findViewById(R.id.edProgram)).getText().toString();
            String department = ((Spinner) findViewById(R.id.spinDepartment)).getSelectedItem().toString();
            String campus = ((Spinner) findViewById(R.id.spinCampus)).getSelectedItem().toString();
            String profession = rbChosen.getText().toString();

            //////////////--------- Viranch -------------------////////////////////
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);


            try{
                URL registerAPI = new URL("http://10.0.2.2:8000/centennialmeetup/register");
                HttpURLConnection client = null;
                client = (HttpURLConnection) registerAPI.openConnection();
                client.setRequestMethod("POST");
                client.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                client.setDoOutput(true);
                client.setDoInput(true);

                String Postdata = new String();
                Postdata = Postdata + "centennialid=" + id + "&";
                Postdata = Postdata + "firstname=" + fname + "&";
                Postdata = Postdata + "lastname=" + lname + "&";
                Postdata = Postdata + "email=" + email + "&";
                Postdata = Postdata + "dateofbirth=" + date + "&";
                Postdata = Postdata + "hobby=" + hobby + "&";
                Postdata = Postdata + "password=" + password + "&";
                Postdata = Postdata + "program=" + program + "&";
                Postdata = Postdata + "department=" + department + "&";
                Postdata = Postdata + "campus=" + campus + "&";
                Postdata = Postdata + "profession=" + profession + "&";
                Postdata = Postdata + "phone=5556";

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
                    Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println(client.getResponseMessage());
                }

            } catch(MalformedURLException error) {
                //Handles an incorrectly entered URL
            } catch (IOException e) {
                e.printStackTrace();
            }


            /////////////////---------------------------------------//////////////////////////////
            //DBAdapter db = new DBAdapter(this);
            //db.open();

            //boolean flag = db.insertUser((Integer.parseInt(id)), fname, lname, email, date, hobby, password,program, department, campus, profession);
            //db.close();
            //if (flag) {
            //    Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
            //}

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success
                            onSignupSuccess();
                            // onSignupFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);
            // TODO: Implement your own signup logic here.

        }
    }
    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        edId.setText("");
        edFname.setText("");
        edLname.setText("");
        edEmail.setText("");
        edHobby.setText("");
        edPassword.setText("");
        edConfirmpass.setText("");
        edProgram.setText("");
        edDob.setText("");
        edId.setText("");

        setResult(RESULT_OK, null);

    }


    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String id = ((EditText) findViewById(R.id.edCentenniald)).getText().toString();
        String fname = ((EditText) findViewById(R.id.edFName)).getText().toString();
        String lname = ((EditText) findViewById(R.id.edLName)).getText().toString();
        String email = ((EditText) findViewById(R.id.edEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.edTextPassword)).getText().toString();
        String confirmpass = ((EditText) findViewById(R.id.edTextCPassword)).getText().toString();
        String date = ((EditText) findViewById(R.id.edDOB)).getText().toString();
        String hobby = ((EditText) findViewById(R.id.edTextHobby)).getText().toString();
        String program = ((EditText) findViewById(R.id.edProgram)).getText().toString();
        String department = ((Spinner) findViewById(R.id.spinDepartment)).getSelectedItem().toString();
        String campus = ((Spinner) findViewById(R.id.spinCampus)).getSelectedItem().toString();
        String profession = rbChosen.getText().toString();

        if (id.isEmpty() || id.length() < 9) {
            edId.setError("At least 9 digits");
            valid = false;
        } else {
            edId.setError(null);
        }


        if (fname.isEmpty() || fname.length() < 3) {
            edFname.setError("At least 3 characters");
            valid = false;
        } else {
            edFname.setError(null);
        }
        if (lname.isEmpty() || lname.length() < 3) {
            edLname.setError("At least 3 characters");
            valid = false;
        } else {
            edLname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            edEmail.setError(null);
        }
        if (date.isEmpty()) {
            edDob.setError("Field can not be empty");
            valid = false;
        } else {
            edDob.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edPassword.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edPassword.setError(null);
        }
        if (confirmpass.isEmpty() || !confirmpass.equals(password)) {
            edConfirmpass.setError("Password does not match");
            valid = false;
        } else {
            edConfirmpass.setError(null);
        }
        if (hobby.isEmpty()) {
            edHobby.setError("Enter your hobbies");
            valid = false;
        } else {
            edHobby.setError(null);
        }
        if (program.isEmpty()) {
            edProgram.setError("Enter your program ");
            valid = false;
        } else {
            edProgram.setError(null);
        }
        if (department.isEmpty() || department.equals("Select your department")) {
            Toast.makeText(RegisterActivity.this, "Please select your department", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (campus.isEmpty() || campus.equals("Select your campus")) {
            Toast.makeText(RegisterActivity.this, "Please select your campus", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (rbProfession.getCheckedRadioButtonId() == -1) {
            Toast.makeText(RegisterActivity.this, "Please select your profession", Toast.LENGTH_LONG).show();
            valid = false;
        }


        return valid;
    }
}


