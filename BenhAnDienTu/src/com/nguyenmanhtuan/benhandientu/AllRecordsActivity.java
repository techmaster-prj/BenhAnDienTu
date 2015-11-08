package com.nguyenmanhtuan.benhandientu;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.nguyenmanhtuan.utils.DatabaseHandler;
import com.nguyenmanhtuan.utils.JSONParser;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AllRecordsActivity extends Activity implements OnItemClickListener {
    final Context context = this;
    private Locale              myLocale;
    ListView                    lv;
    
    // Creating JSON Parser object
    JSONParser                  jParser         = new JSONParser();
    
    List<Map<String, String>>   recordsList;
    
    // url to get all products list
    private static String       url_all_records = "http://192.168.56.1/emr_connect/get_all_records.php";
    
    // JSON Node names
    private static final String KEY_SUCCESS     = "success";
    private static final String KEY_RECORDS     = "records";
    private static final String KEY_LIVER       = "liver";
    private static final String KEY_BLOOD       = "blood";
    private static final String KEY_URINE       = "urine";
    private static final String KEY_PID         = "pid";
    
    // products JSONArray
    JSONArray                   records         = null;
    String                      email           = null;
    
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrecords);
        
        recordsList = new ArrayList<Map<String, String>>();
        
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        HashMap<String, String> user = new HashMap<String, String>();
        user = db.getUserDetails();
        
        lv = (ListView) findViewById(R.id.listrecord);
        
        email = user.get("email");
        final TextView login = (TextView) findViewById(R.id.textwelcome);
        login.setText("Welcome  " + user.get("fname"));
        final TextView lname = (TextView) findViewById(R.id.lname);
        lname.setText(user.get("lname"));
        new NetCheck().execute();
        lv.setOnItemClickListener(this);
    }
    
    private class NetCheck extends AsyncTask<String, String, Boolean> {
        private ProgressDialog nDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(AllRecordsActivity.this);
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
                new ProcessShowAllRecords().execute();
            } else {
                nDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error in Network Connection", Toast.LENGTH_LONG).show();
                
            }
        }
    }
    
    private class ProcessShowAllRecords extends AsyncTask<String, String, String> {
        
        private ProgressDialog pDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            pDialog = new ProgressDialog(AllRecordsActivity.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Showing ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        @Override
        protected String doInBackground(String... args) {
            
            // Check for success key
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", email));
                
                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jParser.makeHttpRequest(url_all_records, "GET", params);
                
                // check your log for json response
                Log.d("All records", json.toString());
                
                // json success key
                success = json.getInt(KEY_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    records = json.getJSONArray(KEY_RECORDS); // JSON
                                                              // Array
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject c = records.getJSONObject(i);
                        
                        // Storing each json item in variable
                        String pid = c.getString(KEY_PID);
                        String liver = c.getString(KEY_LIVER);
                        String blood = c.getString(KEY_BLOOD);
                        String urine = c.getString(KEY_URINE);
                        Map<String, String> item = new HashMap<String, String>();
                        
                        // adding each child node to HashMap key => value
                        item.put(KEY_PID, pid);
                        item.put(KEY_LIVER, liver);
                        item.put(KEY_BLOOD, blood);
                        item.put(KEY_URINE, urine);
                        recordsList.add(item);
                        
                    }
                } else {
                    AllRecordsActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    
                    // set title
                    alertDialogBuilder.setTitle("Have no record");
         
                    // set dialog message
                    alertDialogBuilder
                        .setMessage("Want to choose an examination!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                              Intent i = new Intent(AllRecordsActivity.this, SetExamActivity.class);
                              // Closing all previous activities
                              i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(i);
                                
                            }
                          })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
         
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
         
                        // show it
                        alertDialog.show();
                        }
                    });
                    
//                    Intent i = new Intent(getApplicationContext(), SetExamActivity.class);
//                    // Closing all previous activities
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);
                    
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all record
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     */
                    SimpleAdapter adapter = new SimpleAdapter(AllRecordsActivity.this, recordsList,
                            R.layout.list_records, new String[] { KEY_PID, KEY_LIVER, KEY_BLOOD, KEY_URINE },
                            new int[] { R.id.tvPid, R.id.tvEmail, R.id.tvLname, R.id.tvUrine });
                    // updating listview
                    lv.setAdapter(adapter);
                }
            });
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
        String pid = ((TextView) view.findViewById(R.id.tvPid)).getText().toString();
        // Bundle bundle = new Bundle(); //
        // bundle.putString("pid", pid); //
        
/*         Intent intent = new Intent(getApplicationContext(), RecordDetailActivity.class);
         intent.putExtra("pid", pid);
*/        Intent intent = new Intent(getApplicationContext(), RecordDetailActivity1.class);
        intent.putExtra("pid", pid); //
        
        startActivity(intent);
    }
    
    // private class Holder {
    // // public LinearLayout root = null;
    // public TextView liver = null;
    // public TextView blood = null;
    // public TextView urine = null;
    // public TextView pid = null;
    // }
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, AllRecordsActivity.class);
        startActivity(refresh);
    }
}
