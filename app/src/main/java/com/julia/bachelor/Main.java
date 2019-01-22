package com.julia.bachelor;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class Main extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
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
                PriserFragment fragment = new PriserFragment();
                FragmentTransaction fragmentt = getFragmentManager().beginTransaction();
                fragmentt.replace(R.id.container, fragment);
                fragmentt.addToBackStack(null);
                fragmentt.commit();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        Button addbutton;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_salg, container, false);
            addbutton = rootView.findViewById(R.id.addbutton);

            addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(rootView.getContext() , addbutton);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(rootView.getContext(), "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                            if(item.getTitle().equals("Bondens Marked")){
                                Intent myIntent = new Intent(rootView.getContext(), HjemmeSalg.class);
                                myIntent.putExtra("Hjemme", 1); //Optional parameters
                                rootView.getContext().startActivity(myIntent);
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Main) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class BeholdningFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public BeholdningFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static BeholdningFragment newInstance(int sectionNumber) {
            BeholdningFragment fragment = new BeholdningFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_beholdning, container, false);
            return rootView;
        }

    }
    public static class PriserFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PriserFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PriserFragment newInstance(int sectionNumber) {
            PriserFragment fragment = new PriserFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_priser, container, false);
            return rootView;
        }
    }

}
