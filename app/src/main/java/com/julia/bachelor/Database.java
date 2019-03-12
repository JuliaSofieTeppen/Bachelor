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

class Database {
    private static ArrayList<Annet> Annet = new ArrayList<>();
    private static ArrayList<Beholdning> Beholdning = new ArrayList<>();
    private static ArrayList<Salg> Salg = new ArrayList<>();
    private static ArrayList<BondensMarked> BM = new ArrayList<>();
    private static ArrayList<Hjemme> Hjemme = new ArrayList<>();
    private static ArrayList<Honning> Honning = new ArrayList<>();
    private static ArrayList<Videresalg> Videresalg = new ArrayList<>();

    static void executeOnDB(String url) {
        ExecuteOnDB task = new ExecuteOnDB();
        task.execute(url);
    }

    // TODO add method for updating BeholdningUt

    static void getAnnetValues() {
        AnnetTask task = new AnnetTask();
        task.execute("http://www.honningbier.no/PHP/AnnetOut.php");
    }

    static void getBeholdningValues() {
        BeholdningTask task = new BeholdningTask();
        task.execute("http://www.honningbier.no/PHP/BeholdningOut.php");
    }

    static void getBeholdningUtValues() {
        BeholdningUtTask task = new BeholdningUtTask();
        task.execute("http://www.honningbier.no/PHP/SalgOut.php");
    }

    static void getBMValues() {
        BondensMTask task = new BondensMTask();
        task.execute("http://www.honningbier.no/PHP/BondensMarkedOut.php");
    }

    static void getHjemmeValues() {
        HjemmeTask task = new HjemmeTask();
        task.execute("http://www.honningbier.no/PHP/HjemmeOut.php");
    }

    static void getHonningType() {
        HonningTask task = new HonningTask();
        task.execute("http://www.honningbier.no/PHP/HonningOut.php");
    }

    static void getVideresalgValues(){
        VideresalgTask task = new VideresalgTask();
        task.execute("http://www.honningbier.no/PHP/VideresalgOut.php");
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
                        annet.set_ID(jsonobject.getLong("ID"));
                        annet.setKunde(jsonobject.getString("Kunde"));
                        annet.setDato(jsonobject.getString("Dato"));
                        annet.setVarer(jsonobject.getString("Varer"));
                        annet.setBelop(jsonobject.getInt("Belop"));
                        annet.setBetaling(jsonobject.getString("Betaling"));
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LoadContent load = new LoadContent();
            load.setAnnet(Annet);
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
                        beholdning.set_ID(jsonobject.getLong("ID"));
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LoadContent load = new LoadContent();
            load.setBeholdning(Beholdning);
        }
    }

    private static class BeholdningUtTask extends AsyncTask<String, Void, String> {
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
                            Salg salg = new Salg();
                            JSONObject jsonobject = jsonArray.getJSONObject(i);
                            salg.set_ID(jsonobject.getLong("ID"));
                            salg.setSommer(jsonobject.getInt("Sommer"));
                            salg.setSommerH(jsonobject.getInt("SommerHalv"));
                            salg.setSommerK(jsonobject.getInt("SommerKvart"));
                            salg.setLyng(jsonobject.getInt("Lyng"));
                            salg.setLyngH(jsonobject.getInt("LyngHalv"));
                            salg.setLyngK(jsonobject.getInt("LyngKvart"));
                            salg.setIngeferH(jsonobject.getInt("IngeferHalv"));
                            salg.setIngeferK(jsonobject.getInt("IngeferKvart"));
                            salg.setFlytende(jsonobject.getInt("Flytende"));
                            salg.setDato(jsonobject.getString("Dato"));
                            Salg.add(salg);
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
            LoadContent load = new LoadContent();
            load.setBeholdningUt(Salg);
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
                        bondensMarked.set_ID(jsonobject.getLong("ID"));
                        bondensMarked.setDato(jsonobject.getString("Dato"));
                        bondensMarked.setVarer(jsonobject.getString("Varer"));
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LoadContent load = new LoadContent();
            load.setBM(BM);
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
                        hjemme.set_ID(jsonobject.getLong("ID"));
                        hjemme.setKunde(jsonobject.getString("Kunde"));
                        hjemme.setDato(jsonobject.getString("Dato"));
                        hjemme.setVarer(jsonobject.getString("Varer"));
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LoadContent load = new LoadContent();
            load.setHjemme(Hjemme);
        }
    }

    private static class HonningTask extends AsyncTask<String, Void, String> {
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
            LoadContent load = new LoadContent();
            load.setHonning(Honning);
        }
    }

    private static class VideresalgTask extends AsyncTask<String, Void, String> {
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
                        Videresalg videresalg = new Videresalg();
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        videresalg.set_ID(jsonobject.getLong("ID"));
                        videresalg.setKunde(jsonobject.getString("Kunde"));
                        videresalg.setDato(jsonobject.getString("Dato"));
                        videresalg.setVarer(jsonobject.getString("Varer"));
                        videresalg.setBelop(jsonobject.getInt("Belop"));
                        videresalg.setBetaling(jsonobject.getString("Betaling"));
                        videresalg.setMoms(jsonobject.getDouble("Moms"));
                        Videresalg.add(videresalg);
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
            LoadContent load = new LoadContent();
            load.setVideresalg(Videresalg);
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