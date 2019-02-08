package com.julia.bachelor;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.internal.ParcelableSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import org.parceler.Parcels;

import java.io.Serializable;
import java.io.SerializablePermission;
import java.util.ArrayList;
import java.util.List;

public class Main extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    static ArrayList<Honning> honningtype;
    static Database database;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database();
        database.getHonningType();
        honningtype = new ArrayList<>();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, SalgFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                BeholdningFragment myf = new BeholdningFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, myf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                Bundle bundle = new Bundle();
                bundle.putParcelable("params", Parcels.wrap(honningtype));
                PriserFragment fragment = new PriserFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentt = getFragmentManager().beginTransaction();
                fragmentt.replace(R.id.container, fragment);
                fragmentt.addToBackStack(null);
                fragmentt.commit();
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                Intent myIntent = new Intent(Main.this, Innstillinger.class);
                myIntent.putExtra("Innstillinger", 1); //Optional parameters
                Main.this.startActivity(myIntent);

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
    public void setArrays(ArrayList<Honning> type){
        honningtype = type;
    }

}
