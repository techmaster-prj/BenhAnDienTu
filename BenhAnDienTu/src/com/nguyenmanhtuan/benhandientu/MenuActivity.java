package com.nguyenmanhtuan.benhandientu;



import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends Activity {
    private Locale  myLocale;
    String login;
    String setting;
    String help;
    String exit;
    String warning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_menulist);
        ListView menuList = (ListView) findViewById(R.id.ListView_Menu);
        final Context context = this;
        login = getResources().getString(R.string.menuloign);
        setting = getResources().getString(R.string.menusetting);
        help = getResources().getString(R.string.menuhelp);
        exit = getResources().getString(R.string.menuexit);
        warning = getResources().getString(R.string.exitwarning);
        String[] menus = {login, setting, help, exit};

        //String[] menus = {"Login", "Setting", "Help", "Exit"};
                ;
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                R.layout.menu_item, menus);
        menuList.setAdapter(adapt);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View itemClicked,
                    int position, long id) {
                switch (position) {
                case 0:
                    Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(i);
                    break;
                case 1:
                    Intent i1 = new Intent(MenuActivity.this, SettingActivity.class);
                    startActivity(i1);
                    break;
                case 2:
                    Intent i2 = new Intent(MenuActivity.this, HelpActivity.class);
                    startActivity(i2);
                    break;
                case 3:                                          
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
             
                        // set title
                        alertDialogBuilder.setTitle(warning);
             
                        // set dialog message
                        alertDialogBuilder
                            //.setMessage("Click yes to exit!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    MenuActivity.this.finish();
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
                            break;
                        }
                    
                        
                

                // Note: if the list was built "by hand" the id could be used.
                // As-is, though, each item has the same id
//
//              TextView textView = (TextView) itemClicked;
//              String strText = textView.getText().toString();
//
//              if (strText.equalsIgnoreCase(getResources().getString(
//                      R.string.menu_patient_list))) {
//                  // Launch the Game Activity
//                  startActivity(new Intent(MainActivity.this,
//                          PatientList.class));
//              } else if (strText.equalsIgnoreCase(getResources().getString(
//                      R.string.menu_language))) {
//                  // Launch the Help Activity
//                  startActivity(new Intent(MainActivity.this,
//                          Help.class));
//              } else if (strText.equalsIgnoreCase(getResources().getString(
//                      R.string.menu_language))) {
//                  // Launch the Settings Activity
//                  startActivity(new Intent(MainActivity.this,
//                          Setting.class));
//              } else if (strText.equalsIgnoreCase(getResources().getString(
//                      R.string.menu_exit))) {
//                  finish();
//                  // Launch the Scores Activity
//                  
//              }

            }
        });

    }
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MenuActivity.class);
        startActivity(refresh);
    }
}
