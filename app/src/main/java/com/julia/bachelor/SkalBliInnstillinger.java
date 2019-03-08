package com.julia.bachelor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class SkalBliInnstillinger extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_SECTION_NUMBER = "section_number";
    ConstraintLayout enkg,halvkg,kvartkg,Ingf05kg,Ingf025kg,flyt,endreverdier,endremoms;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText ferdigprodukt;
    EditText ikkeferdig;
    Button momslagre;

    public SkalBliInnstillinger() {}

    // TODO: Rename and change types and number of parameters
    public static SkalBliInnstillinger newInstance(int sectionNumber) {
        SkalBliInnstillinger fragment = new SkalBliInnstillinger();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_skal_bli_innstillinger, container, false);
        enkg = rootView.findViewById(R.id.enkg);
        halvkg = rootView.findViewById(R.id.halvkg);
        kvartkg = rootView.findViewById(R.id.kvartkg);
        Ingf05kg = rootView.findViewById(R.id.ingf05kg);
        Ingf025kg = rootView.findViewById(R.id.ingf025kg);
        flyt = rootView.findViewById(R.id.flyt);
        endreverdier = rootView.findViewById(R.id.endrepris);
        endremoms = rootView.findViewById(R.id.endremoms);
        ferdigprodukt = rootView.findViewById(R.id.ferdigprodukt);
        ikkeferdig = rootView.findViewById(R.id.ikkeferdig);
        momslagre = rootView.findViewById(R.id.momslagre);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        editor = sharedPreferences.edit();
        momslagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ferdigprodukt.getText().toString().equals("")||ikkeferdig.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Sett inn verdier i feltene", Toast.LENGTH_SHORT).show();
                }else{
                    editor.putInt("ferdigprodukt", Integer.parseInt(ferdigprodukt.getText().toString()));
                    editor.putInt("ikkeferdig", Integer.parseInt(ikkeferdig.getText().toString()));
                    editor.apply();
                    Toast.makeText(getContext(), "Moms lagret", Toast.LENGTH_SHORT).show();
                }
            }
        });
        endreverdier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.bigextend);
                extend(exe);
            }
        });
        endremoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.bigextend2);
                extend(exe);
            }
        });
        enkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.enkgextend);
                extend(exe);
            }
        });
        halvkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.halvkgextend);
                extend(exe);
            }
        });
        kvartkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.kvartkgextend);
                extend(exe);
            }
        });
        Ingf05kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.ingfhalvkgextend);
                extend(exe);
            }
        });
        Ingf025kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.ingfkvartkgextend);
                extend(exe);
            }
        });
        flyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.flytextend);
                extend(exe);
            }
        });
        return rootView;
    }

    public void extend(ConstraintLayout exe){
        if(exe.isShown()){
            exe.setVisibility(View.GONE);
        }else {
            exe.setVisibility(View.VISIBLE);
        }
    }
}
