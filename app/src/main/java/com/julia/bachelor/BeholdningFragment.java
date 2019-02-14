package com.julia.bachelor;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class BeholdningFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    static ArrayList<Beholdning> Beholdning;
    static ArrayList<BeholdningUt> BeholdningUt;
    Button addbutton;

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

    @Override @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beholdning, container, false);
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        return rootView;
    }

}