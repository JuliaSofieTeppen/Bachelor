package com.julia.bachelor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.julia.bachelor.helperClass.Beholdning;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Honning;
import com.julia.bachelor.helperClass.SalgFactory;
import com.julia.bachelor.helperClass.SalgTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_ALLSALG = "AllSalg";

    static ArrayList<Honning> Honning = new ArrayList<>();
    static ArrayList<Beholdning> Beholdning = new ArrayList<>();
    static ArrayList<SalgTemplate> AllSalg = new ArrayList<>();

    EditText dato, som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende;
    List<EditText> verdier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
         */
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public Context shareContext(){
        return this;
    }

    void setArrays(ArrayList<Beholdning> BeholdningList, ArrayList<SalgTemplate> allSalg,
                   ArrayList<Honning> HonningList) {
        Beholdning.addAll(BeholdningList);
        AllSalg.addAll(allSalg);
        Honning.addAll(HonningList);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, HovedsideFragment.newInstance(position + 1, Beholdning, Honning, AllSalg))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                break;
            case 2:
                RapportFragment myf = RapportFragment.newInstance(AllSalg);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, myf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case 3:
                AddBeholdningFragment fragment = AddBeholdningFragment.newInstance();
                FragmentTransaction fragmentt = getFragmentManager().beginTransaction();
                fragmentt.replace(R.id.container, fragment);
                fragmentt.addToBackStack(null);
                fragmentt.commit();
                break;

            case 4:
                PriserFragment prifragment = PriserFragment.newInstance(1);
                FragmentTransaction prifragmentt = getFragmentManager().beginTransaction();
                prifragmentt.replace(R.id.container, prifragment);
                prifragmentt.addToBackStack(null);
                prifragmentt.commit();
                break;

            case 5:
                SettingFragment insfragment = SettingFragment.newInstance();
                FragmentTransaction insfragmentt = getFragmentManager().beginTransaction();
                insfragmentt.replace(R.id.container, insfragment);
                insfragmentt.addToBackStack(null);
                insfragmentt.commit();
                break;
            case 6:
                Intent intent = new Intent(this, PdfCreatorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_ALLSALG, AllSalg);
                intent.putExtra(KEY_BUNDLE, bundle);
                startActivity(intent);
        }
    }

    public void lagre(View v) {
        dato = findViewById(R.id.Bdato);
        som1kg = findViewById(R.id.Bsom1kg);
        som05kg = findViewById(R.id.Bsom05kg);
        som025kg = findViewById(R.id.Bsom025kg);
        lyng1kg = findViewById(R.id.Blyn1kg);
        lyng05kg = findViewById(R.id.Blyn05kg);
        lyng025kg = findViewById(R.id.Blyn025kg);
        ingf05kg = findViewById(R.id.Binf05kg);
        ingf025kg = findViewById(R.id.Binf025kg);
        flytende = findViewById(R.id.Bflyt);
        verdier = new ArrayList<>(Arrays.asList(som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende));

        int tell = 0;
        if (Tools.checkDate(dato.getText().toString())) {
            for (EditText verdi : verdier) {
                if (verdi.getText().toString().equals("")) {
                    verdi.setText("0");
                } else {
                    tell++;
                }
            }
            if (tell == 0) {
                Toast.makeText(this, "Legg til minst et produkt", Toast.LENGTH_SHORT).show();
            } else {
                insertIntoDB();
                Toast.makeText(this, "Beholdning lagret", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }

    void insertIntoDB() {
        Database.executeOnDB("http://www.honningbier.no/PHP/SalgIn.php/?Dato=" + dato.getText().toString());
        Database.executeOnDB("http://www.honningbier.no/PHP/BeholdningIn.php/?" + getStringBeholdning() + "&Dato=" + dato.getText().toString());
    }

    private String getStringBeholdning() {
        String[] strings = {"Sommer", "SommerH", "SommerK", "Lyng", "LyngH", "LyngK", "IngeferH", "IngeferK", "Flytende"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]).append("=").append(verdier.get(i).getText().toString()).append("&");
        }
        return sb.toString();
    }

    public List<Honning> getHonningTyper() {
        return Honning;
    }

    public ArrayList<Beholdning> getBeholdning(){
        return Beholdning;
    }

    void fetch() {
        FetchDataTask task = new FetchDataTask();
        String[] urls = {
                "http://www.honningbier.no/PHP/AnnetOut.php",
                "http://www.honningbier.no/PHP/BeholdningOut.php",
                "http://www.honningbier.no/PHP/BondensMarkedOut.php",
                "http://www.honningbier.no/PHP/HjemmeOut.php",
                "http://www.honningbier.no/PHP/HonningOut.php",
                "http://www.honningbier.no/PHP/VideresalgOut.php"
        };
        task.execute(urls);
        try {
            task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class FetchDataTask extends AsyncTask<String, Integer, String> {
        Integer progress;
        ArrayList<SalgTemplate> AllSalg = new ArrayList<>();
        ArrayList<Beholdning> BeholdningList = new ArrayList<>();
        ArrayList<Honning> HonningList = new ArrayList<>();

        @Override
        protected String doInBackground(String... urls) {
            // Get strings from bufferedReader.
            String nextLine;
            try {
                for (String url1 : urls) {
                    StringBuilder output = new StringBuilder();
                    URL url = new URL(url1);
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
                    bufferedReader.close();
                    conn.disconnect();
                    if (url1.equalsIgnoreCase(urls[0]) || url1.equalsIgnoreCase(urls[2]) || url1.equalsIgnoreCase(urls[3]) || url1.equalsIgnoreCase(urls[5])) {
                        setSaleValues(output.toString(), url1);
                    } else if (url1.equalsIgnoreCase(urls[1]) ) {
                        setBeholdnigValues(output.toString());
                    } else {
                        setHoneyValues(output.toString());
                    }
                }
                return "Done!";
            } catch (Exception e) {
                return "Noe gikk feil: " + e.toString();
            }
        }

        void setSaleValues(String output, String url) {
            try {
                // Convert string to JSONArray containing JSONObjects.
                JSONArray jsonArray = new JSONArray(output);
                for (int i = 0; i < jsonArray.length(); i++) {
                    SalgFactory factory = new SalgFactory();
                    SalgTemplate salgObject = factory.getSalgObject(url);
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    salgObject.set_ID(jsonobject.getLong("ID"));
                    if (!(salgObject instanceof BondensMarked))
                        salgObject.setKunde(jsonobject.getString("Kunde"));
                    salgObject.setDato(jsonobject.getString("Dato"));
                    salgObject.setVarer(jsonobject.getString("Varer"));
                    salgObject.setBelop(jsonobject.getInt("Belop"));
                    if (!(salgObject instanceof BondensMarked))
                        salgObject.setBetaling(jsonobject.getString("Betaling"));
                    if (salgObject instanceof com.julia.bachelor.helperClass.Videresalg)
                        salgObject.setMoms(jsonobject.getDouble("Moms"));
                    AllSalg.add(salgObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        void setBeholdnigValues(String output) {
            try {
                // Convert string to JSONArray containing JSONObjects.
                JSONArray jsonArray = new JSONArray(output);
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
                    BeholdningList.add(beholdning);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        void setHoneyValues(String output) {
            try {
                // Convert string to JSONArray containing JSONObjects.
                JSONArray jsonArray = new JSONArray(output);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Honning honning = new Honning();
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    honning.set_ID(jsonobject.getLong("ID"));
                    honning.setType(jsonobject.getString("Type"));
                    honning.setHjemmePris(jsonobject.getInt("HjemmePris"));
                    honning.setBondensMarkedPris(jsonobject.getInt("BMPris"));
                    honning.setFakturaPris(jsonobject.getInt("FakturaPris"));
                    HonningList.add(honning);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity mainActivity = new MainActivity();
            mainActivity.setArrays(BeholdningList, AllSalg, HonningList);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate();
            this.progress = progress[0];
        }
    }
}

