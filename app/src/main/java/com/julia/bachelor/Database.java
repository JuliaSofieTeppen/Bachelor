package com.julia.bachelor;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class Database {
    private static List<BondensMarked> BM = new ArrayList<>();


    void executeOnDB(String url) {
        ExecuteOnDB task = new ExecuteOnDB();
        task.execute(url);
    }

    List<BondensMarked> getBMValues() {
        BondensM task = new BondensM();
        // TODO set correct url
        task.execute("");
        return BM;
    }

    public List<Honning> getHonningTyper() {

        return null;
    }

    private static class BondensM extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // Get strings from bufferedReader.
            String nextLine;
            StringBuilder output = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
                }
                // Get the string containing values from db.
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                while ((nextLine = bufferedReader.readLine()) != null) {
                    output.append(nextLine);
                }
                conn.disconnect();
                try {
                    // Convert string to JSONArray containing JSONObjects.
                    JSONArray jsonArray = new JSONArray(output.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BondensMarked bondensMarked = new BondensMarked();
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        bondensMarked.set_ID(jsonobject.getLong("_ID"));
                        bondensMarked.setDato(jsonobject.getString("Dato"));
                        bondensMarked.setBelop(jsonobject.getInt("Belop"));
                        bondensMarked.setVarer(jsonobject.getString("Varer "));
                        BM.add(bondensMarked);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "Done!";
            } catch (Exception e) {
                return "Noe gikk feil: " + e.toString();
            }
        }
    }

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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
