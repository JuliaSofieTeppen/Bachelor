package com.julia.bachelor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BeholdningTemplate;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.Honning;
import com.julia.bachelor.helperClass.Salg;
import com.julia.bachelor.helperClass.Videresalg;

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

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String KEY_ANNET = "Annet";
    private static final String KEY_BEHOLDNING = "Beholdning";
    private static final String KEY_BEHOLDNINGUT = "BeholdningUt";
    private static final String KEY_BONDENSMARKED = "Bondensmarked";
    private static final String KEY_HJEMME = "Hjemme";
    private static final String KEY_HONNING = "Honning";
    private static final String KEY_VIDERESALG = "Videresalg";
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_ALLSALG = "AllSalg";

    static ArrayList<com.julia.bachelor.helperClass.Honning> Honning;
    static ArrayList<com.julia.bachelor.helperClass.Annet> Annet;
    static ArrayList<com.julia.bachelor.helperClass.Hjemme> Hjemme;
    static ArrayList<BondensMarked> Bm;
    static ArrayList<com.julia.bachelor.helperClass.Videresalg> Videresalg;
    static ArrayList<com.julia.bachelor.helperClass.BeholdningTemplate> Beholdning;
    static ArrayList<com.julia.bachelor.helperClass.BeholdningTemplate> Salg;
    static ArrayList<Object> AllSalg;

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
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @SuppressWarnings("unchecked")
    void setArrays() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        Honning = (ArrayList<Honning>) bundle.getSerializable(KEY_HONNING);
        Annet = (ArrayList<Annet>) bundle.getSerializable(KEY_ANNET);
        Hjemme = (ArrayList<Hjemme>) bundle.getSerializable(KEY_HJEMME);
        Bm = (ArrayList<BondensMarked>) bundle.getSerializable(KEY_BONDENSMARKED);
        Videresalg = (ArrayList<Videresalg>) bundle.getSerializable(KEY_VIDERESALG);
        Beholdning = (ArrayList<BeholdningTemplate>) bundle.getSerializable(KEY_BEHOLDNING);
        Salg = (ArrayList<BeholdningTemplate>) bundle.getSerializable(KEY_BEHOLDNINGUT);
        if (Videresalg == null || Hjemme == null || Annet == null || Bm == null) return;
        setSalg();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        setArrays();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, HovedsideFragment.newInstance(position + 1, Beholdning, Salg, Honning))
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
                SettingFragment insfragment = SettingFragment.newInstance(2);
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
        if (checkDate(dato.getText().toString())) {
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
        Database.executeOnDB("http://www.honningbier.no/PHP/BeholdningIn.php/?" + getBeholdning() + "&Dato=" + dato.getText().toString());
    }

    private String getBeholdning() {
        String[] strings = {"Sommer", "SommerH", "SommerK", "Lyng", "LyngH", "LyngK", "IngeferH", "IngeferK", "Flytende"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]).append("=").append(verdier.get(i).getText().toString()).append("&");
        }
        return sb.toString();
    }

    public boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    public List<Honning> getHonningTyper() {
        return Honning;
    }

    private void setSalg() {
        AllSalg = new ArrayList<>();
        AllSalg.addAll(Bm);
        AllSalg.addAll(Hjemme);
        AllSalg.addAll(Videresalg);
        AllSalg.addAll(Annet);
    }

    void fetch(){
        FetchDataTask task = new FetchDataTask();
        String[] urls = {
                "http://www.honningbier.no/PHP/AnnetOut.php",
                "http://www.honningbier.no/PHP/BeholdningOut.php",
                "http://www.honningbier.no/PHP/SalgOut.php",
                "http://www.honningbier.no/PHP/BondensMarkedOut.php",
                "http://www.honningbier.no/PHP/HjemmeOut.php",
                "http://www.honningbier.no/PHP/HonningOut.php",
                "http://www.honningbier.no/PHP/VideresalgOut.php"
        };
        task.execute(urls);
    }

    private static class FetchDataTask extends AsyncTask<String, int[], String> {
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
    }
}
