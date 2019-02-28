package com.julia.bachelor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SkalBliAddBeholdning extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
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
    Database db;

    public SkalBliAddBeholdning() {
        // Required empty public constructor
    }


    public static SkalBliAddBeholdning newInstance(int sectionNumber) {
        SkalBliAddBeholdning fragment = new SkalBliAddBeholdning();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_skal_bli_add_beholdning, container, false);


        db = new Database();
        db.getHonningType();
        dato = rootView.findViewById(R.id.Bdato);
        som1kg = rootView.findViewById(R.id.Bsom1kg);
        som05kg = rootView.findViewById(R.id.Bsom05kg);
        som025kg = rootView.findViewById(R.id.Bsom025kg);
        lyng1kg = rootView.findViewById(R.id.Blyn1kg);
        lyng05kg = rootView.findViewById(R.id.Blyn05kg);
        lyng025kg = rootView.findViewById(R.id.Blyn025kg);
        ingf05kg = rootView.findViewById(R.id.Binf05kg);
        ingf025kg = rootView.findViewById(R.id.Binf025kg);
        flytende = rootView.findViewById(R.id.Bflyt);
        verdier = new ArrayList<>(Arrays.asList(som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende));

        return rootView;
    }

}
