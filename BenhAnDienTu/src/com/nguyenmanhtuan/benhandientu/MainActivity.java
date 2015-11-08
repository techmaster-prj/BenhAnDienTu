package com.nguyenmanhtuan.benhandientu;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

import com.nguyenmanhtuan.utils.DatabaseHandler;
import com.nguyenmanhtuan.utils.UserFunctions;

public class MainActivity extends Activity {
    private Locale  myLocale;
    Button btnLogout;
    Button changepas;
    Button allRecords;
    Button setExam;
    String welcome;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        changepas = (Button) findViewById(R.id.btchangepass);
        btnLogout = (Button) findViewById(R.id.logout);
        allRecords = (Button) findViewById(R.id.btAllRecords);
        setExam = (Button) findViewById(R.id.btSetExam);
        welcome = getResources().getString(R.string.welcome);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        
        /**
         * Hashmap to load data from the Sqlite database
         **/
        HashMap<String, String> user = new HashMap<String, String>();
        user = db.getUserDetails();
        setExam.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent setexam= new Intent(getApplicationContext(), SetExamActivity.class);
                startActivity(setexam);
                
            }
        });
        allRecords.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent allrecord = new Intent(getApplicationContext(), AllRecordsActivity.class);
                startActivity(allrecord);
                
            }
        });
        
        /**
         * Change Password Activity Started
         **/
        changepas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                
                Intent chgpass = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                
                startActivity(chgpass);
            }
            
        });
        
        /**
         * Logout from the User Panel which clears the data in Sqlite database
         **/
        btnLogout.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View arg0) {
                
                UserFunctions logout = new UserFunctions();
                logout.logoutUser(getApplicationContext());
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                finish();
            }
        });
        /**
         * Sets user first name and last name in text view.
         **/
        final TextView login = (TextView) findViewById(R.id.textwelcome);
        login.setText(welcome  +" " + user.get("fname"));
        final TextView lname = (TextView) findViewById(R.id.lname);
        lname.setText(user.get("lname"));
        
    }
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }
}
