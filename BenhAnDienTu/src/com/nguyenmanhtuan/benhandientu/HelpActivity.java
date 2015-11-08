package com.nguyenmanhtuan.benhandientu;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HelpActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Read raw file into string and populate TextView
        InputStream iFile = getResources().openRawResource(R.raw.medhelp);
        try {
            TextView tv = (TextView) findViewById(R.id.textView2);
            String strFile = inputStreamToString(iFile);
            tv.setText(strFile);
        } catch (Exception e) {
            Log.e("Medical report Quiz Log", "InputStreamToString failure", e);
        }

    }

    /**
     * Converts an input stream to a string
     * 
     * @param is
     *            The {@code InputStream} object to read from
     * @return A {@code String} object representing the string for of the input
     * @throws IOException
     *             Thrown on read failure from the input
     */
    public String inputStreamToString(InputStream is) throws IOException {
        StringBuffer sBuffer = new StringBuffer();
        DataInputStream dataIO = new DataInputStream(is);
        String strLine = null;

        while ((strLine = dataIO.readLine()) != null) {
            sBuffer.append(strLine + "\n");
        }

        dataIO.close();
        is.close();

        return sBuffer.toString();
    }
}
