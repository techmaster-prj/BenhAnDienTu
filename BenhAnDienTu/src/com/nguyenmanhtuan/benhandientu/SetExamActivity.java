package com.nguyenmanhtuan.benhandientu;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.nguyenmanhtuan.utils.DatabaseHandler;
import com.nguyenmanhtuan.utils.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SetExamActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
    private Locale  myLocale;
    CheckBox                    cbLiver, cbBlood, cbUrine;
    Button                      btCheck, btSetExam;
    String                      email          = null;
//    String                      liver           = null;
//    String                      blood           = null;
//    String                      urine           = null;
    String liver,blood,urine;
    String networkwarning;
    String examwarning;
    String examcreate;
    String examconfirm;
    String examliver;
    String examblood;
    String examurine;
    String check;
    String load;
    String contact;
    
    JSONParser                  jParser         = new JSONParser();
    private static String       url_create_exam = "http://192.168.56.1/emr_connect/create_exam.php";
    private static final String KEY_SUCCESS     = "success";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setexam);
        cbLiver = (CheckBox) findViewById(R.id.cbLiver);
        cbBlood = (CheckBox) findViewById(R.id.cbBlood);
        cbUrine = (CheckBox) findViewById(R.id.cbUrine);
        btCheck = (Button) findViewById(R.id.btCheck);
        btSetExam = (Button) findViewById(R.id.btSetExam);
        liver = "no";
        blood = "no";
        urine = "no";
        
        networkwarning = getResources().getString(R.string.networkwarning);
        
        check = getResources().getString(R.string.check);
        load = getResources().getString(R.string.load);
        contact = getResources().getString(R.string.contact);
        examcreate = getResources().getString(R.string.examcreate);
        examconfirm = getResources().getString(R.string.examconfirm);
        examwarning = getResources().getString(R.string.examwarning);
        examliver = getResources().getString(R.string.examliver);
        examblood = getResources().getString(R.string.examblood);
        examurine = getResources().getString(R.string.examurine);
      
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user = db.getUserDetails();
        email = user.get("email");
        
        cbLiver.setOnCheckedChangeListener(this);
        cbBlood.setOnCheckedChangeListener(this);
        cbUrine.setOnCheckedChangeListener(this);
        btCheck.setOnClickListener(this);
        btSetExam.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btCheck:
                if (liver.equals("no") && blood.equals("no") && urine.equals("no")) {
                    Toast.makeText(SetExamActivity.this, examwarning, Toast.LENGTH_LONG)
                            .show();
                } else if (liver.equals("yes") && blood.equals("yes") && urine.equals("yes")) {
                    Toast.makeText(SetExamActivity.this, examconfirm +"\n" + examliver + "\n" +examblood + "\n" +examurine,
                            Toast.LENGTH_LONG).show();
                } else if (liver.equals("yes") && blood.equals("yes")) {
                    Toast.makeText(SetExamActivity.this,  examconfirm +"\n" + examliver + "\n" +examblood,
                            Toast.LENGTH_LONG).show();
                } else if (blood.equals("yes") && urine.equals("yes")) {
                    Toast.makeText(SetExamActivity.this, examconfirm + "\n" +examblood + "\n" +examurine,
                            Toast.LENGTH_LONG).show();
                } else if (liver.equals("yes") && urine.equals("yes")) {
                    Toast.makeText(SetExamActivity.this, examconfirm +"\n" + examliver + "\n" +examurine,
                            Toast.LENGTH_LONG).show();
                } else if (liver.equals("yes")) {
                    Toast.makeText(SetExamActivity.this, examconfirm +"\n" + examliver , Toast.LENGTH_LONG).show();
                } else if (blood.equals("yes")) {
                    Toast.makeText(SetExamActivity.this, examconfirm +"\n" +examblood , Toast.LENGTH_LONG).show();
                } else if (urine.equals("yes")) {
                    Toast.makeText(SetExamActivity.this, examconfirm +"\n" +examurine, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btSetExam:
                if (liver.equals("no") && blood.equals("no") && urine.equals("no")) {
                    Toast.makeText(SetExamActivity.this, examwarning, Toast.LENGTH_LONG)
                            .show();
                }else{
                    new NetCheck().execute();
                }
                
                break;
        
        }
    }
    
    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
        switch (arg0.getId()) {
            case R.id.cbLiver:
                if (isChecked) {
                    liver = "yes";
                } else {
                    liver = "no";
                }
                break;
            case R.id.cbBlood:
                if (isChecked) {
                    blood = "yes";
                } else {
                    blood = "no";
                }
                break;
            case R.id.cbUrine:
                if (isChecked) {
                    urine = "yes";
                } else {
                    urine = "no";
                }
                break;
        }
        
    }
    
    private class NetCheck extends AsyncTask<String, String, Boolean> {
        private ProgressDialog nDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(SetExamActivity.this);
            nDialog.setTitle(check);
            nDialog.setMessage(load+"...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }
        
        /**
         * Gets current device state and checks for working internet connection
         * by trying Google.
         **/
        @Override
        protected Boolean doInBackground(String... args) {
            
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;
            
        }
        
        @Override
        protected void onPostExecute(Boolean th) {
            
            if (th == true) {
                nDialog.dismiss();
                new ProcessSetExam().execute();
            } else {
                nDialog.dismiss();
                Toast.makeText(getApplicationContext(), networkwarning, Toast.LENGTH_SHORT).show();
                
            }
        }
    }
    
    class ProcessSetExam extends AsyncTask<String, String, String> {
        private ProgressDialog      pDialog;
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SetExamActivity.this);
            pDialog.setMessage(examcreate +"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("liver", liver));
            params.add(new BasicNameValuePair("blood", blood));
            params.add(new BasicNameValuePair("urine", urine));
            
            // getting JSON Object
            // Note that create exam url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_create_exam, "POST", params);
            
            // check log cat fro response
            Log.d("Create Response", json.toString());
            
            // check for success tag
            try {
                int success = json.getInt(KEY_SUCCESS);
                
                if (success == 1) {
                    // successfully created exam
                    Intent i = new Intent(getApplicationContext(), AllRecordsActivity.class);
                    startActivity(i);
                    
                    // closing this screen
                    finish();
                } else {
                    // failed to create exam
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
        
    }
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SetExamActivity.class);
        startActivity(refresh);
    }
    
}
