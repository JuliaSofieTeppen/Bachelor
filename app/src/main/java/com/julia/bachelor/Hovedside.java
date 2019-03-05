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
    TextView totaltext, info;
    List arraylist;
    ArrayList<Beholdning> beholdning;
    ArrayList<BeholdningUt> beholdningUt;
    ArrayList<Honning> honning;
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
    @Override @SuppressWarnings("unchecked")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.hovedside, container, false);
        addbutton = rootView.findViewById(R.id.addbutton);
        totaltext = rootView.findViewById(R.id.totaltext);
        info = rootView.findViewById(R.id.Info);
        arraylist = new ArrayList();
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

        try {
            beholdning = (ArrayList<Beholdning>) (getArguments().getSerializable("beholdning"));
            beholdningUt = (ArrayList<BeholdningUt>) (getArguments().getSerializable("beholdning"));
            honning = (ArrayList<Honning>) getArguments().getSerializable("HonningType");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        info.setText(buildString());
        return rootView;
    }

    String buildString(){
        if(beholdning==null) return "Error getting content";
        StringBuilder sb = new StringBuilder();
        Beholdning beholdning = findCurrentBeholdning();
        sb.append(beholdning.getDato()).append("\n").append(honning.get(0).getType()).append("   Antall: ").append(beholdning.getSommer()).append("\n");
        sb.append(honning.get(1).getType()).append("   Antall: ").append(beholdning.getSommerH()).append("\n");
        sb.append(honning.get(2).getType()).append("   Antall: ").append(beholdning.getSommerK()).append("\n");
        sb.append(honning.get(3).getType()).append("   Antall: ").append(beholdning.getLyng()).append("\n");
        sb.append(honning.get(4).getType()).append("   Antall: ").append(beholdning.getLyngH()).append("\n");
        sb.append(honning.get(5).getType()).append("   Antall: ").append(beholdning.getLyngK()).append("\n");
        sb.append(honning.get(6).getType()).append("   Antall: ").append(beholdning.getIngeferH()).append("\n");
        sb.append(honning.get(7).getType()).append("   Antall: ").append(beholdning.getIngeferK()).append("\n");
        sb.append(honning.get(8).getType()).append("   Antall: ").append(beholdning.getFlytende()).append("\n");
        return sb.toString();
    }

    Beholdning findCurrentBeholdning(){
        // TODO make this method find the newest Beholdning object
        return beholdning.get(0);
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