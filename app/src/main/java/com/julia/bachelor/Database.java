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
    private static List<Annet> Annet = new ArrayList<>();
    private static List<Beholdning> Beholdning = new ArrayList<>();
    private static List<BondensMarked> BM = new ArrayList<>();
    private static List<Hjemme> Hjemme = new ArrayList<>();
    private static ArrayList<Honning> Honning = new ArrayList<>();

    void executeOnDB(String url){
        ExecuteOnDB task = new ExecuteOnDB();
        task.execute(url);
    }

    List<Annet> getAnnetValues(){
        AnnetTask task = new AnnetTask();
        // TODO set url for annet
        task.execute("");
        return Annet;
    }

    List<Beholdning> getBeholdning(){
        BeholdningTask task = new BeholdningTask();
        // TODO set url for beholdning
        task.execute("");
        return Beholdning;
    }

    List<BondensMarked> getBMValues(){
        BondensMTask task = new BondensMTask();
        // TODO set correct url
        task.execute("");
        return BM;
    }

    List<Hjemme> getHjemmeValues(){
        HjemmeTask task = new HjemmeTask();
        // TODO find url for hjemme
        task.execute("");
        return Hjemme;
    }

    List<Honning> getHonningType(){
        HonningTask task = new HonningTask();
        // TODO Url for Honning
        task.execute("http://www.honningbier.no/PHP/HonningOut.php");
        return Honning;
    }

    private static class AnnetTask extends AsyncTask<String, Void, String> {
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
                        Annet annet = new Annet();
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        annet.set_ID(jsonobject.getLong("_ID"));
                        annet.setKunde(jsonobject.getString("Kunde"));
                        annet.setDato(jsonobject.getString("Dato"));
                        annet.setVarer(jsonobject.getString("Varer "));
                        annet.setBelop(jsonobject.getInt("Belop"));
                        annet.setBetaling(jsonobject.getString("Betaling")) ;
                        Annet.add(annet);
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

    private static class BeholdningTask extends AsyncTask<String, Void, String> {
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
                        Beholdning beholdning = new Beholdning();
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        beholdning.set_ID(jsonobject.getLong("_ID"));
                        beholdning.setSommer(jsonobject.getInt("Sommer"));
                        beholdning.setSommerH(jsonobject.getInt("SommerHalv"));
                        beholdning.setSommerK(jsonobject.getInt("SommerKvart"));
                        beholdning.setLyng(jsonobject.getInt("Lyng"));
                        beholdning.setLyngH(jsonobject.getInt("LyngHalv"));
                        beholdning.setLyngK(jsonobject.getInt("LyngKvart"));
                        beholdning.setIngeferH(jsonobject.getInt("IngeferHalv"));
                        beholdning.setIngeferK(jsonobject.getInt("IngeferKvart"));
                        beholdning.setFlytende(jsonobject.getInt("Flytende"));
                        beholdning.setDato(jsonobject.getString("Dato"));
                        Beholdning.add(beholdning);
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

    private static class BondensMTask extends AsyncTask<String, Void, String> {
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
                        bondensMarked.setVarer(jsonobject.getString("Varer "));
                        bondensMarked.setBelop(jsonobject.getInt("Belop"));
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

    private static class HjemmeTask extends AsyncTask<String, Void, String> {
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
                        Hjemme hjemme = new Hjemme();
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        hjemme.set_ID(jsonobject.getLong("_ID"));
                        hjemme.setKunde(jsonobject.getString("Kunde"));
                        hjemme.setDato(jsonobject.getString("Dato"));
                        hjemme.setVarer(jsonobject.getString("Varer "));
                        hjemme.setBelop(jsonobject.getInt("Belop"));
                        hjemme.setBetaling(jsonobject.getString("Betaling"));
                        Hjemme.add(hjemme);
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

    private static class HonningTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // Get strings from bufferedReader.
            String nextLine;
            StringBuilder output = new StringBuilder();
            Honning = new ArrayList<>();
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
                        Honning honning = new Honning();
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        honning.set_ID(jsonobject.getLong("ID"));
                        honning.setType(jsonobject.getString("Type"));
                        honning.setStorrelse(jsonobject.getDouble("Storrelse"));
                        honning.setHjemmePris(jsonobject.getInt("HjemmePris"));
                        honning.setBondensMarkedPris(jsonobject.getInt("BMPris"));
                        honning.setFakturaPris(jsonobject.getInt("FakturaPris"));
                        Honning.add(honning);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "Done!";
            } catch (Exception e) {
                return "Noe gikk feil: " + e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            HjemmeSalg hjemmeSalg = new HjemmeSalg();
            hjemmeSalg.setArrays(Honning);
            Main main = new Main();
            main.setArrays(Honning);
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