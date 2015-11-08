package com.nguyenmanhtuan.benhandientu;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class RecordDetailActivity extends Activity {
    
    private Locale  myLocale;
    TextView                    tvHbsab, tvHbsag, tvHbeab, tvHbeag, tvHbcab;
    // Progress Dialog
    
    // JSON parser class
    JSONParser                  jParser            = new JSONParser();
    String                      pid;
    String networkwarning;
    String invalid;
    String check;
    String show;
    String contact;
    String load;
    private static final String url_record_detials = "http://192.168.56.1/emr_connect/get_record_detail.php";
    
    private static final String KEY_SUCCESS        = "success";
    private static final String KEY_RECORD         = "record";
    private static final String KEY_HBSAB          = "hbsab";
    private static final String KEY_HBSAG          = "hbsag";
    private static final String KEY_HBEAB          = "hbeab";
    private static final String KEY_HBEAG          = "hbeag";
    private static final String KEY_HBCAB          = "hbcab";
    
    JSONArray                   record;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorddetail);
        initialize();
        Intent i = getIntent();
        pid = i.getStringExtra("pid");
        // tvHbsab.setText(pid);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        networkwarning = getResources().getString(R.string.networkwarning);
        invalid = getResources().getString(R.string.invalid);
        check = getResources().getString(R.string.check);
        show = getResources().getString(R.string.show);
        contact = getResources().getString(R.string.contact);
        load = getResources().getString(R.string.load);
        new NetCheck().execute();
        
    }
    
    private void initialize() {
        tvHbsab = (TextView) findViewById(R.id.tvHbsab);
        tvHbsag = (TextView) findViewById(R.id.tvHbsag);
        tvHbeab = (TextView) findViewById(R.id.tvHbeab);
        tvHbeag = (TextView) findViewById(R.id.tvHbeag);
        tvHbcab = (TextView) findViewById(R.id.tvHbcab);
        
    }
    
    private class NetCheck extends AsyncTask<String, String, Boolean> {
        private ProgressDialog nDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(RecordDetailActivity.this);
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
                new ProcessRecordDetail().execute();
            } else {
                nDialog.dismiss();
                Toast.makeText(getApplicationContext(), networkwarning, Toast.LENGTH_LONG).show();
                
            }
        }
    }
    
    private class ProcessRecordDetail extends AsyncTask<String, String, String> {
        
        private ProgressDialog pDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            pDialog = new ProgressDialog(RecordDetailActivity.this);
            pDialog.setTitle(contact);
            pDialog.setMessage(show + "...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        @Override
        protected String doInBackground(String... args) {
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success key
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));
                        
                        // getting record details by making HTTP request
                        // Note that record details url will use GET request
                        JSONObject json = jParser.makeHttpRequest(url_record_detials, "GET", params);
                        
                        // check your log for json response
                        Log.d("Record Detail", json.toString());
                        
                        // json success key
                        success = json.getInt(KEY_SUCCESS);
                        if (success == 1) {
                            // successfully received record details
                            record = json.getJSONArray(KEY_RECORD); // JSON
                                                                    // Array
                            
                            JSONObject c = record.getJSONObject(0);
                            
//                            tvHbsab = (TextView) findViewById(R.id.tvHbsab);
//                            tvHbsag = (TextView) findViewById(R.id.tvHbsag);
//                            tvHbeab = (TextView) findViewById(R.id.tvHbeab);
//                            tvHbeag = (TextView) findViewById(R.id.tvHbeag);
//                            tvHbcab = (TextView) findViewById(R.id.tvHbcab);
                            
                            // display record data in EditText
                            tvHbsab.setText(c.getString(KEY_HBSAB));
                            tvHbsag.setText(c.getString(KEY_HBSAG));
                            tvHbeab.setText(c.getString(KEY_HBEAB));
                            tvHbeag.setText(c.getString(KEY_HBEAG));
                            tvHbcab.setText(c.getString(KEY_HBCAB));
                            
                        } else {
                            // record with pid not found
                        }
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            
            return null;
        }
        
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            
        }
        
    }
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, RecordDetailActivity.class);
        startActivity(refresh);
    }
}
