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
        Database db = new Database();
        // TODO Add correct Url here
        String url = "";
        db.executeOnDB(url);
    }


}
