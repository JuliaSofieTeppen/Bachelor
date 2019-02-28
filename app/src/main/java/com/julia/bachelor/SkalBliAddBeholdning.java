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

    public void lagre(View v) {
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
                Toast.makeText(this.getContext(), "Legg til minst et produkt", Toast.LENGTH_SHORT).show();
            } else {
                insertIntoDB();
                Toast.makeText(this.getContext(), "Beholdning lagret", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this.getContext(), "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }

    String getBeholdning(){
        String[] strings = {"Sommer","SommerH","SommerK","Lyng","LyngH","LyngK","IngeferH","IngeferK","Flytende"};
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < strings.length; i++){
            sb.append(strings[i]).append("=").append(verdier.get(i).getText().toString()).append("&");
        }
        return sb.toString();
    }

    void insertIntoDB(){
        db.executeOnDB("http://www.honningbier.no/PHP/SalgIn.php/?Dato=" + dato.getText().toString());
        db.executeOnDB("http://www.honningbier.no/PHP/BeholdningIn.php/?" + getBeholdning() + "&Dato=" + dato.getText().toString());
    }

    public boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }
}
