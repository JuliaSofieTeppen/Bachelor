package com.julia.bachelor;

import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    private static final String KEY_ANNET = "Annet";
    private static final String KEY_BEHOLDNING = "Beholdning";
    private static final String KEY_BEHOLDNINGUT = "Salg";
    private static final String KEY_BONDENSMARKED = "Bondensmarked";
    private static final String KEY_HJEMME = "Hjemme";
    private static final String KEY_HONNING = "Honning";
    private static final String KEY_VIDERESALG = "Videresalg";
    private static final String KEY_BUNDLE = "Bundle";

    static ArrayList<Honning> Honning;
    static ArrayList<Annet> Annet;
    static ArrayList<Hjemme> Hjemme;
    static ArrayList<BondensMarked> Bm;
    static ArrayList<Videresalg> Videresalg;
    static ArrayList<Beholdning> Beholdning;
    static ArrayList<Salg> Salg;
    static ArrayList<Object> AllSalg;

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
        /*
         * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
         */
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }
    @SuppressWarnings("unchecked")
    void setArrays(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        Honning = (ArrayList<Honning>) bundle.getSerializable(KEY_HONNING);
        Annet = (ArrayList<Annet>) bundle.getSerializable(KEY_ANNET);
        Hjemme = (ArrayList<Hjemme>) bundle.getSerializable(KEY_HJEMME);
        Bm = (ArrayList<BondensMarked>) bundle.getSerializable(KEY_BONDENSMARKED);
        Videresalg = (ArrayList<Videresalg>) bundle.getSerializable(KEY_VIDERESALG);
        Beholdning = (ArrayList<Beholdning>) bundle.getSerializable(KEY_BEHOLDNING);
        Salg = (ArrayList<Salg>) bundle.getSerializable(KEY_BEHOLDNINGUT);
        if(Videresalg==null || Hjemme==null || Annet==null || Bm==null) return;
        setSalg();
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        setArrays();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, Hovedside.newInstance(position + 1, Beholdning, Salg, Honning))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                break;
            case 2:
                Rapport myf = Rapport.newInstance(1, AllSalg);
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
        Database.executeOnDB("http://www.honningbier.no/PHP/SalgIn.php/?Dato=" + dato.getText().toString());
        Database.executeOnDB("http://www.honningbier.no/PHP/BeholdningIn.php/?" + getBeholdning() + "&Dato=" + dato.getText().toString());
    }

    private String getBeholdning(){
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

    private void setSalg(){
        AllSalg = new ArrayList<>();
        AllSalg.addAll(Bm);
        AllSalg.addAll(Hjemme);
        AllSalg.addAll(Videresalg);
        AllSalg.addAll(Annet);
    }
}
