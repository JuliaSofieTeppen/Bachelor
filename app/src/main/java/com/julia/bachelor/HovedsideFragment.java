package com.julia.bachelor;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HovedsideFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    static ArrayList<Beholdning> Beholdning;
    static ArrayList<BeholdningUt> BeholdningUt;
    Button addbutton;
    TextView Som, SomH, SomK, Lyng, LyngH, LyngK, IngH, IngK, Flyt;

    public HovedsideFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HovedsideFragment newInstance(int sectionNumber) {
        HovedsideFragment fragment = new HovedsideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hovedside_fragment, container, false);
        addbutton = rootView.findViewById(R.id.button);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), AddBeholdning.class);
                myIntent.putExtra("addbeholdning", 1); //Optional parameters
                getContext().startActivity(myIntent);
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