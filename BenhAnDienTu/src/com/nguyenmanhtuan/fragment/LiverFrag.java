package com.nguyenmanhtuan.fragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nguyenmanhtuan.benhandientu.R;
import com.nguyenmanhtuan.benhandientu.RecordDetailActivity1;
import com.nguyenmanhtuan.utils.JSONParser;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//@SuppressLint("ValidFragment")
public class LiverFrag extends Fragment {
    TextView                    tvfHbsab, tvHbsag, tvHbeab, tvHbeag, tvHbcab;
    View                        liver;
    JSONParser                  jParser            = new JSONParser();
    String                      pid;
    private static final String url_record_detials = "http://192.168.0.105/emr_connect/get_record_detail.php";
    
    private static final String KEY_SUCCESS        = "success";
    private static final String KEY_RECORD         = "record";
    private static final String KEY_HBSAB          = "hbsab";
    private static final String KEY_HBSAG          = "hbsag";
    private static final String KEY_HBEAB          = "hbeab";
    private static final String KEY_HBEAG          = "hbeag";
    private static final String KEY_HBCAB          = "hbcab";
    
    JSONArray                   record;
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
       // pid = this.getArguments().getString("pid");
        liver = inflater.inflate(R.layout.frag_liver, container, false);               
        tvfHbsab = (TextView) liver.findViewById(R.id.tvfHbsab);
        //initialize();

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        RecordDetailActivity1 record = new RecordDetailActivity1();
        pid = record.getPid();
        //pid = ((RecordDetailActivity1)getActivity()).getPid();
        tvfHbsab.setText(pid);
        //new NetCheck().execute();
        return liver;
    }
    
    
   


    private void initialize() {
        tvfHbsab = (TextView) liver.findViewById(R.id.tvfHbsab);
        tvHbsag = (TextView) liver.findViewById(R.id.tvfHbsag);
        tvHbeab = (TextView) liver.findViewById(R.id.tvfHbeab);
        tvHbeag = (TextView) liver.findViewById(R.id.tvfHbeag);
        tvHbcab = (TextView) liver.findViewById(R.id.tvfHbcab);
        
    }
    
    private class NetCheck extends AsyncTask<String, String, Boolean> {
        private ProgressDialog nDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setTitle("Checking Network");
            nDialog.setMessage("Loading..");
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
            
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                Toast.makeText(getActivity().getApplicationContext(), "Error in Network Connection", Toast.LENGTH_LONG)
                        .show();
                
            }
        }
    }
    
    private class ProcessRecordDetail extends AsyncTask<String, String, String> {
        
        private ProgressDialog pDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            pDialog = new ProgressDialog(getActivity());
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Showing ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        @Override
        protected String doInBackground(String... args) {
            getActivity().runOnUiThread(new Runnable() {
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
                            
                             tvfHbsab = (TextView)
                             liver.findViewById(R.id.tvfHbsab);
                             tvHbsag = (TextView)
                             liver.findViewById(R.id.tvfHbsag);
                             tvHbeab = (TextView)
                             liver.findViewById(R.id.tvfHbeab);
                             tvHbeag = (TextView)
                             liver.findViewById(R.id.tvfHbeag);
                             tvHbcab = (TextView)
                             liver.findViewById(R.id.tvfHbcab);
                            
                            // display record data in EditText
                            tvfHbsab.setText(c.getString(KEY_HBSAB));
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
    
}
