package com.julia.bachelor;

import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    static ArrayList<Honning> Honningtype;
    static ArrayList<Annet> Annet;
    static ArrayList<Hjemme> Hjemme;
    static ArrayList<BondensMarked> Bm;
    static ArrayList<Videresalg> Videresalg;
    static ArrayList<Beholdning> Beholdning;
    static ArrayList<BeholdningUt> BeholdningUt;
    static ArrayList<Object> Salg;
    static Database database;

    EditText dato;
    EditText som1kg;
    EditText som05kg;
    EditText som025kg;
    EditText lyng1kg;
    EditText lyng05kg;
    EditText lyng025kg;
    EditText ingf05kg;
    EditText ingf025kg;
    EditText flytende;
    List<EditText> verdier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database();
        database.getHonningType();
        database.getAnnetValues();
        database.getBeholdningValues();
        database.getBMValues();
        database.getHjemmeValues();
        database.getVideresalgValues();
        /*
         * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
         */
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, Hovedside.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        Bundle bundle;
        switch (number) {
            case 1:
                CharSequence mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                bundle = new Bundle();
                bundle.putSerializable("beholdning", Beholdning);
                bundle.putSerializable("salg",BeholdningUt);
                Rapport myf = Rapport.newInstance(1);
                myf.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, myf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case 3:
                SkalBliAddBeholdning fragment = new SkalBliAddBeholdning();
                FragmentTransaction fragmentt = getFragmentManager().beginTransaction();
                fragmentt.replace(R.id.container, fragment);
                fragmentt.addToBackStack(null);
                fragmentt.commit();
                break;

            case 4:
                PriserFragment prifragment = new PriserFragment();
                FragmentTransaction prifragmentt = getFragmentManager().beginTransaction();
                prifragmentt.replace(R.id.container, prifragment);
                prifragmentt.addToBackStack(null);
                prifragmentt.commit();
                break;

            case 5:
                SkalBliInnstillinger insfragment = new SkalBliInnstillinger();
                FragmentTransaction insfragmentt = getFragmentManager().beginTransaction();
                insfragmentt.replace(R.id.container, insfragment);
                insfragmentt.addToBackStack(null);
                insfragmentt.commit();
                break;

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

    void insertIntoDB(){
        database.executeOnDB("http://www.honningbier.no/PHP/SalgIn.php/?Dato=" + dato.getText().toString());
        database.executeOnDB("http://www.honningbier.no/PHP/BeholdningIn.php/?" + getBeholdning() + "&Dato=" + dato.getText().toString());
    }

    String getBeholdning(){
        String[] strings = {"Sommer","SommerH","SommerK","Lyng","LyngH","LyngK","IngeferH","IngeferK","Flytende"};
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < strings.length; i++){
            sb.append(strings[i]).append("=").append(verdier.get(i).getText().toString()).append("&");
        }
        return sb.toString();
    }
    public boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    public void setAnnet(ArrayList<com.julia.bachelor.Annet> annet) { Annet = annet; }

    public void setBeholdning(ArrayList<Beholdning> beholdnings){ Beholdning = beholdnings; }

    public void setBM(ArrayList<BondensMarked> bondensMarkeds){ Bm = bondensMarkeds; }

    public void setHjemme(ArrayList<Hjemme> hjemmes){ Hjemme = hjemmes; }

    public void setHonning(ArrayList<Honning> type){ Honningtype = type; }

    public void setVideresalg(ArrayList<com.julia.bachelor.Videresalg> videresalg) { Videresalg = videresalg; }

    private void setSalg(){
        Salg.addAll(Bm);
        Salg.addAll(Hjemme);
        Salg.addAll(Videresalg);
        Salg.addAll(Annet);
    }
}
