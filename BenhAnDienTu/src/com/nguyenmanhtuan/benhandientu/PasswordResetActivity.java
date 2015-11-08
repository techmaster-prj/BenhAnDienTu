package com.nguyenmanhtuan.benhandientu;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.nguyenmanhtuan.utils.UserFunctions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class PasswordResetActivity extends Activity {
    private Locale  myLocale;
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR   = "error";
    
    EditText              email;
    TextView              alert;
    Button                resetpass;
    String check;
    String load;
    String networkwarning;
    String contact;
    String getdata;
    String emailconfirm;
    String emailinvalid;
    String emailerror;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_passwordreset);
        check = getResources().getString(R.string.check);
        load = getResources().getString(R.string.load);
        networkwarning = getResources().getString(R.string.networkwarning);
        contact = getResources().getString(R.string.contact);
        getdata = getResources().getString(R.string.getdata);
        emailconfirm = getResources().getString(R.string.emailconfirm);
        emailinvalid = getResources().getString(R.string.emailinvalid);
        emailerror = getResources().getString(R.string.emailerror);
        
        Button login = (Button) findViewById(R.id.bktolog);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
            
        });
        
        email = (EditText) findViewById(R.id.forpas);
        alert = (TextView) findViewById(R.id.alert);
        resetpass = (Button) findViewById(R.id.respass);
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                NetAsync(view);
                
            }
            
        });
    }
    
    private class NetCheck extends AsyncTask<String, String, Boolean>
    
    {
        private ProgressDialog nDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(PasswordResetActivity.this);
            nDialog.setMessage(load +"...");
            nDialog.setTitle(check);
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }
        
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
                new ProcessRegister().execute();
            } else {
                nDialog.dismiss();
                alert.setText("Error in Network Connection");
            }
        }
    }
    
    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {
        
        private ProgressDialog pDialog;
        
        String                 forgotpassword;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            forgotpassword = email.getText().toString();
            
            pDialog = new ProgressDialog(PasswordResetActivity.this);
            pDialog.setTitle(contact);
            pDialog.setMessage(getdata + "...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        @Override
        protected JSONObject doInBackground(String... args) {
            
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.forPass(forgotpassword);
            return json;
            
        }
        
        @Override
        protected void onPostExecute(JSONObject json) {
            /**
             * Checks if the Password Change Process is sucesss
             **/
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    alert.setText("");
                    String res = json.getString(KEY_SUCCESS);
                    String red = json.getString(KEY_ERROR);
                    
                    if (Integer.parseInt(res) == 1) {
                        pDialog.dismiss();
                        alert.setText(emailconfirm);
                        
                    } else if (Integer.parseInt(red) == 2) {
                        pDialog.dismiss();
                        alert.setText(emailinvalid);
                    } else {
                        pDialog.dismiss();
                        alert.setText(emailerror);
                    }
                    
                }
            } catch (JSONException e) {
                e.printStackTrace();
                
            }
        }
    }
    
    public void NetAsync(View view) {
        new NetCheck().execute();
    }
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, PasswordResetActivity.class);
        startActivity(refresh);
    }
}
