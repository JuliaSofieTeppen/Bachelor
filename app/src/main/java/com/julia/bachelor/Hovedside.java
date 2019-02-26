package com.julia.bachelor;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Hovedside extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    Button addbutton;
    TextView totaltext;
    List arraylist;
    ListView listView;
    private ArrayList<String> salgliste; //for now, må endres til objekter så vi kan putte inn objekter ordenlig.

    public Hovedside() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Hovedside newInstance(int sectionNumber) {
        Hovedside fragment = new Hovedside();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.rapport_fragment, container, false);

        addbutton = rootView.findViewById(R.id.addbutton);

        totaltext = rootView.findViewById(R.id.totaltext);
        arraylist = new ArrayList();

        //add salg variabler i listen
        listView = rootView.findViewById(R.id.salgitems);
        salgliste = new ArrayList<>();
        salgliste.add("1234.1.1 salg 500kr");
        salgliste.add("8832.2.2 salg 377kr");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1,salgliste);
        listView.setAdapter(arrayAdapter);
        //---------------------------------------

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO sett inn position der fordi ...fordi
                Intent i = new Intent(getContext(),SalgItem.class );
                startActivity(i);
            }
        });
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(rootView.getContext(), addbutton);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Bondens Marked")) {
                            Intent myIntent = new Intent(rootView.getContext(), BmSalg.class);
                            myIntent.putExtra("BM", 1); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else if (item.getTitle().equals("Hjemme salg")) {
                            Intent myIntent = new Intent(rootView.getContext(), HjemmeSalg.class);
                            myIntent.putExtra("Hjemme", 1); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else if (item.getTitle().equals("Videre salg")) {
                            Intent myIntent = new Intent(rootView.getContext(), FakturaSalg.class);
                            myIntent.putExtra("Videresalg", 1); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else {
                            Intent myIntent = new Intent(rootView.getContext(), SalgAnnet.class);
                            myIntent.putExtra("Salgannet", 1); //Optional parameters
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

    public void extend(ConstraintLayout exe) {
        if (exe.isShown()) {
            exe.setVisibility(View.GONE);
        } else {
            exe.setVisibility(View.VISIBLE);
        }
    }


    public void setTotalGjennomsnitt() {
        //totaltext.setText(totalsum(arraylist));
        //gjnsnitttext.setText(gjensnitt(arraylist));

    }

    public void setSommerhonning() {
        //kg1txtsom.setText(getSumArray(0));
        //kg05txtsom.setText(getSumArray(1));
        //kg025txtsom.setText(getSumArray(2));

    }

    public void setLynghonning() {

    }

    public void setAnnet() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Main) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}