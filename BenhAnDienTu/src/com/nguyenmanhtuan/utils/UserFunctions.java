package com.nguyenmanhtuan.utils;


import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;


public class UserFunctions {

    private JSONParser jsonParser;

    //URL of the PHP API
    private static String loginURL = "http://192.168.56.1/emr_connect/index.php";
    private static String registerURL = "http://192.168.56.1/emr_connect/index.php";
    private static String forpassURL = "http://192.168.56.1/emr_connect/index.php";
    private static String chgpassURL = "http://192.168.56.1/emr_connect/index.php";


    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String forpass_tag = "forpass";
    private static String chgpass_tag = "chgpass";


    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * Function to Login
     **/

    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
        return json;
    }

    /**
     * Function to change password
     **/

    public JSONObject chgPass(String newpas, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", chgpass_tag));

        params.add(new BasicNameValuePair("newpas", newpas));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.makeHttpRequest(chgpassURL,"POST", params);
        return json;
    }





    /**
     * Function to reset the password
     **/

    public JSONObject forPass(String forgotpassword){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", forpass_tag));
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.makeHttpRequest(forpassURL,"POST", params);
        return json;
    }






     /**
      * Function to  Register
      **/
    public JSONObject registerUser(String fname, String lname, String email, String uname, String password, String address, String phonenumber, String birthyear){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("fname", fname));
        params.add(new BasicNameValuePair("lname", lname));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("uname", uname));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("address", address));
        params.add(new BasicNameValuePair("phonenumber", phonenumber));
        params.add(new BasicNameValuePair("birthyear", birthyear));
        JSONObject json = jsonParser.makeHttpRequest(registerURL,"POST", params);
        return json;
    }


    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }

}

