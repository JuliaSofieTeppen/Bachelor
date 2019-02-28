package com.julia.bachelor;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Rapport extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    static ArrayList<Beholdning> Beholdning;
    static ArrayList<BeholdningUt> BeholdningUt;
    TextView Som, SomH, SomK, Lyng, LyngH, LyngK, IngH, IngK, Flyt;
    ListView listView;
    private ArrayList<String> salgliste; //for now, må endres til objekter så vi kan putte inn objekter ordenlig.


    public Rapport() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Rapport newInstance(int sectionNumber) {
        Rapport fragment = new Rapport();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rapport, container, false);

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


        try {
            Beholdning = (ArrayList<Beholdning>) (getArguments().getSerializable("beholdning"));
            BeholdningUt = (ArrayList<BeholdningUt>) (getArguments().getSerializable("salg"));
            // TODO do not use get(0) must find the right object here..
            String tmpString=Integer.toString(Beholdning.get(0).getSommer() - BeholdningUt.get(0).getSommer());
            Som.setText(tmpString);
            tmpString=Integer.toString(Beholdning.get(0).getSommerH() - BeholdningUt.get(0).getSommerH());
            SomH.setText(tmpString);
            tmpString=Integer.toString(Beholdning.get(0).getSommerK() - BeholdningUt.get(0).getSommerK());
            SomK.setText(tmpString);
            tmpString=Integer.toString(Beholdning.get(0).getLyng() - BeholdningUt.get(0).getLyng());
            Lyng.setText(tmpString);
            tmpString=Integer.toString(Beholdning.get(0).getLyngH() - BeholdningUt.get(0).getLyngH());
            LyngH.setText(tmpString);
            tmpString=Integer.toString(Beholdning.get(0).getLyngK() - BeholdningUt.get(0).getLyngK());
            LyngK.setText(tmpString);
            tmpString=Integer.toString(Beholdning.get(0).getIngeferH() - BeholdningUt.get(0).getIngeferH());
            IngH.setText(tmpString);
            tmpString=Integer.toString(Beholdning.get(0).getIngeferK() - BeholdningUt.get(0).getIngeferK());
            IngK.setText(tmpString);
            tmpString=Integer.toString(Beholdning.get(0).getFlytende() - BeholdningUt.get(0).getFlytende());
            Flyt.setText(tmpString);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return rootView;
    }

}