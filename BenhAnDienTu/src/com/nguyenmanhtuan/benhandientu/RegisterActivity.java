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
import android.view.inputmethod.InputBinding;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.nguyenmanhtuan.utils.DatabaseHandler;
import com.nguyenmanhtuan.utils.UserFunctions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class RegisterActivity extends Activity {
    private Locale        myLocale;
    /**
     * JSON Response node names.
     **/
    
    private static String KEY_SUCCESS     = "success";
    private static String KEY_UID         = "uid";
    private static String KEY_FIRSTNAME   = "fname";
    private static String KEY_LASTNAME    = "lname";
    private static String KEY_USERNAME    = "uname";
    private static String KEY_EMAIL       = "email";
    private static String KEY_ADDRESS     = "address";
    private static String KEY_PHONENUMBER = "phonenumber";
    private static String KEY_BIRTHYEAR   = "birthyear";
    private static String KEY_CREATED_AT  = "created_at";
    private static String KEY_ERROR       = "error";
    
    /**
     * Defining layout items.
     **/
    
    EditText              inputFirstName;
    EditText              inputLastName;
    EditText              inputUsername;
    EditText              inputEmail;
    EditText              inputPassword;
    EditText              inputAddress;
    EditText              inputPhoneNumber;
    EditText              inputBirthYear;
    Button                btnRegister;
    TextView              registerErrorMsg;
    
    String                usernamewarning;
    String                emptyfieldwarning;
    String                register;
    String                registersuccess;
    String                registererror;
    String                loadinfor;
    String                userexist;
    String                invalidemailid;
    String                networkwarning;
    String                invalid;
    String                check;
    String                load;
    String                contact;
    String                getdata;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        /**
         * Defining all layout items
         **/
        inputFirstName = (EditText) findViewById(R.id.fname);
        inputLastName = (EditText) findViewById(R.id.lname);
        inputUsername = (EditText) findViewById(R.id.uname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pword);
        inputAddress = (EditText) findViewById(R.id.address);
        inputPhoneNumber = (EditText) findViewById(R.id.phonenumber);
        inputBirthYear = (EditText) findViewById(R.id.birthyear);
        
        btnRegister = (Button) findViewById(R.id.register);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        
        /**
         * Button which Switches back to the login screen on clicked
         **/
        usernamewarning = getResources().getString(R.string.usernamewarning);
        emptyfieldwarning = getResources().getString(R.string.emptyfieldwarning);
        register = getResources().getString(R.string.register);
        registersuccess = getResources().getString(R.string.registersuccess);
        registererror = getResources().getString(R.string.registererror);
        loadinfor = getResources().getString(R.string.loadinfor);
        userexist = getResources().getString(R.string.userexists);
        invalidemailid = getResources().getString(R.string.invalidemailid);
        
        networkwarning = getResources().getString(R.string.networkwarning);
        invalid = getResources().getString(R.string.invalid);
        check = getResources().getString(R.string.check);
        load = getResources().getString(R.string.load);
        contact = getResources().getString(R.string.contact);
        getdata = getResources().getString(R.string.getdata);
        
        Button login = (Button) findViewById(R.id.bktologin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
            
        });
        
        /**
         * Register Button click event.
         * A Toast is set to alert when the fields are empty.
         * Another toast is set to alert Username must be 5 characters.
         **/
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if ((!inputUsername.getText().toString().equals(""))
                        && (!inputPassword.getText().toString().equals(""))
                        && (!inputFirstName.getText().toString().equals(""))
                        && (!inputLastName.getText().toString().equals(""))
                        && (!inputEmail.getText().toString().equals(""))
                        && (!inputAddress.getText().toString().equals(""))
                        && (!inputPhoneNumber.getText().toString().equals(""))
                        && (!inputBirthYear.getText().toString().equals(""))) {
                    if (inputUsername.getText().toString().length() > 4) {
                        NetAsync(view);
                        
                    } else {
                        Toast.makeText(getApplicationContext(), usernamewarning, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), emptyfieldwarning, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    /**
     * Async Task to check whether internet connection is working
     **/
    
    private class NetCheck extends AsyncTask<String, String, Boolean> {
        private ProgressDialog nDialog;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(RegisterActivity.this);
            nDialog.setMessage(load + "...");
            nDialog.setTitle(check);
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }
        
        @Override
        protected Boolean doInBackground(String... args) {
            
            /**
             * Gets current device state and checks for working internet
             * connection by trying Google.
             **/
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
                registerErrorMsg.setText(networkwarning);
            }
        }
    }
    
    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {
        
        /**
         * Defining Process dialog
         **/
        private ProgressDialog pDialog;
        
        String                 email, password, fname, lname, uname, address, phonenumber, birthyear;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            inputUsername = (EditText) findViewById(R.id.uname);
            inputPassword = (EditText) findViewById(R.id.pword);
            fname = inputFirstName.getText().toString();
            lname = inputLastName.getText().toString();
            email = inputEmail.getText().toString();
            uname = inputUsername.getText().toString();
            password = inputPassword.getText().toString();
            address = inputAddress.getText().toString();
            phonenumber = inputPhoneNumber.getText().toString();
            birthyear = inputBirthYear.getText().toString();
            
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setTitle(contact);
            pDialog.setMessage(register + " ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        @Override
        protected JSONObject doInBackground(String... args) {
            
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.registerUser(fname, lname, email, uname, password, address, phonenumber, birthyear);
            
            return json;
            
        }
        
        @Override
        protected void onPostExecute(JSONObject json) {
            /**
             * Checks for success message.
             **/
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    registerErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS);
                    
                    String red = json.getString(KEY_ERROR);
                    
                    if (Integer.parseInt(res) == 1) {
                        pDialog.setTitle(getdata);
                        pDialog.setMessage(loadinfor);
                        
                        registerErrorMsg.setText(registersuccess);
                        
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");
                        
                        /**
                         * Removes all the previous data in the SQlite database
                         **/
                        
                        UserFunctions logout = new UserFunctions();
                        logout.logoutUser(getApplicationContext());
                        db.addUser(json_user.getString(KEY_FIRSTNAME), json_user.getString(KEY_LASTNAME),
                                json_user.getString(KEY_EMAIL), json_user.getString(KEY_USERNAME),
                                json_user.getString(KEY_UID), json_user.getString(KEY_ADDRESS),
                                json_user.getString(KEY_PHONENUMBER), json_user.getString(KEY_BIRTHYEAR),
                                json_user.getString(KEY_CREATED_AT));
                        /**
                         * Stores registered data in SQlite Database
                         * Launch Registered screen
                         **/
                        
                        Intent registered = new Intent(getApplicationContext(), RegisteredActivity.class);
                        
                        /**
                         * Close all views before launching Registered screen
                         **/
                        registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();
                        startActivity(registered);
                        
                        finish();
                    }
                    
                    else if (Integer.parseInt(red) == 2) {
                        pDialog.dismiss();
                        registerErrorMsg.setText(userexist);
                    } else if (Integer.parseInt(red) == 3) {
                        pDialog.dismiss();
                        registerErrorMsg.setText(invalidemailid);
                    }
                    
                }
                
                else {
                    pDialog.dismiss();
                    
                    registerErrorMsg.setText(registererror);
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
        Intent refresh = new Intent(this, RegisterActivity.class);
        startActivity(refresh);
    }
}
