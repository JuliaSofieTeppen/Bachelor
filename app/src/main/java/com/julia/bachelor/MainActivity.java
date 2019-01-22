package com.julia.bachelor;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void InsertIntoDB(){
        executeOnDB task = new executeOnDB();
        task.execute("");
    }

    private class executeOnDB extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL myUrl = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
                if(con.getResponseCode() != 200) {
                    throw new RuntimeException("Failed: HTTP error code: " + con.getResponseCode());
                }
                con.disconnect();
            }catch (Exception e){
                return e.toString();
            }
            return "works";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_SHORT).show();
        }
    }
}
