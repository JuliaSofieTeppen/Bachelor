package com.julia.bachelor;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

class Database {

    static void executeOnDB(String url) {
        ExecuteOnDB task = new ExecuteOnDB();
        task.execute(url);
    }

    //opens http connection to honningbier.no
    private static class ExecuteOnDB extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL myUrl = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
                if (con.getResponseCode() != 200) {
                    throw new RuntimeException("Failed: HTTP error code: " + con.getResponseCode());
                }
                con.disconnect();
            } catch (Exception e) {
                return e.toString();
            }
            return "Done";
        }
    }
}