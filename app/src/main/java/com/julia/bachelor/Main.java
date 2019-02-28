package com.julia.bachelor;

import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;

public class Main extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    static ArrayList<Honning> Honningtype;
    static ArrayList<Annet> Annet;
    static ArrayList<Hjemme> Hjemme;
    static ArrayList<BondensMarked> Bm;
    static ArrayList<Videresalg> Videresalg;
    static ArrayList<Beholdning> Beholdning;
    static ArrayList<BeholdningUt> BeholdningUt;

    static Database database;

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
        Honningtype = new ArrayList<>();
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
                .replace(R.id.container, RapportFragment.newInstance(position + 1))
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
                HovedsideFragment myf = HovedsideFragment.newInstance(1);
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

            case 4:
                /*mTitle = getString(R.string.title_section4);
                bundle = new Bundle();
                bundle.putSerializable("params", Honningtype);
                PriserFragment fragment = new PriserFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentt = getFragmentManager().beginTransaction();
                fragmentt.replace(R.id.container, fragment);
                fragmentt.addToBackStack(null);
                fragmentt.commit();*/
                break;

            case 5:
                SkalBliInnstillinger insfragment = new SkalBliInnstillinger();
                FragmentTransaction insfragmentt = getFragmentManager().beginTransaction();
                insfragmentt.replace(R.id.container, insfragment);
                insfragmentt.addToBackStack(null);
                insfragmentt.commit();

        }
    }
    public void setAnnet(ArrayList<com.julia.bachelor.Annet> annet) {
        Annet = annet;
    }

    public void setBeholdning(ArrayList<Beholdning> beholdnings){
        Beholdning = beholdnings;
    }

    public void setBM(ArrayList<BondensMarked> bondensMarkeds){
        Bm = bondensMarkeds;
    }

    public void setHjemme(ArrayList<Hjemme> hjemmes){
        Hjemme = hjemmes;
    }

    public void setHonning(ArrayList<Honning> type){
        Honningtype = type;
    }

    public void setVideresalg(ArrayList<com.julia.bachelor.Videresalg> videresalg) { Videresalg = videresalg; }
}
