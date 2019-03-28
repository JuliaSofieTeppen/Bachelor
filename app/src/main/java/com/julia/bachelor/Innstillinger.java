package com.julia.bachelor;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Innstillinger extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_SECTION_NUMBER = "section_number";
    ConstraintLayout enkg, halvkg, kvartkg, Ingf05kg, Ingf025kg, flyt, endreverdier, endremoms;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText ferdigprodukt, ikkeferdig;
    Button momslagre;
    EditText enkghjemme,halvkghjemme,kvartkghjemme,enkgbm,halvkgbm,kvartkgbm,enkgfak,halvkgfak,kvartkgfak,
            ingfhalvkghjemme,ingfkvartkghjemme,ingfhalvkgbm,ingfkvartkgbm,ingfhalvkgfak,ingfkvartkgfak,
            flythjemme,flytbm,flytfak;
    List<Honning> honningtype;
    List<EditText> BMverdier;
    List<EditText> HjemmeVerdier;
    List<EditText> FakturaVerdier;

    public Innstillinger() {
    }

    // TODO: Rename and change types and number of parameters
    public static Innstillinger newInstance(int sectionNumber) {
        Innstillinger fragment = new Innstillinger();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_skal_bli_innstillinger, container, false);
        enkghjemme = rootView.findViewById(R.id.enkghjemme);
        halvkghjemme = rootView.findViewById(R.id.halvkghjemme);
        kvartkghjemme = rootView.findViewById(R.id.kvartkghjemme);
        enkgbm = rootView.findViewById(R.id.enkgbm);
        halvkgbm = rootView.findViewById(R.id.halvkgbm);
        kvartkgbm = rootView.findViewById(R.id.kvartkgbm);
        enkgfak = rootView.findViewById(R.id.enkgfak);
        halvkgfak = rootView.findViewById(R.id.halvkgfak);
        kvartkgfak = rootView.findViewById(R.id.kvartkgfak);
        enkg = rootView.findViewById(R.id.enkg);
        halvkg = rootView.findViewById(R.id.halvkg);
        kvartkg = rootView.findViewById(R.id.kvartkg);
        ingfhalvkghjemme = rootView.findViewById(R.id.ingfhalvkghjemme);
        ingfhalvkgbm = rootView.findViewById(R.id.ingfhalvkgbm);
        ingfhalvkgfak = rootView.findViewById(R.id.ingfhalvkgfak);
        ingfkvartkghjemme = rootView.findViewById(R.id.ingfkvartkghjemme);
        ingfkvartkgbm = rootView.findViewById(R.id.ingfkvartkgbm);
        ingfkvartkgfak = rootView.findViewById(R.id.ingfkvartkgfak);
        flythjemme = rootView.findViewById(R.id.flythjemme);
        flytbm = rootView.findViewById(R.id.flytbm);
        flytfak = rootView.findViewById(R.id.flytfak);
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

        BMverdier = new ArrayList<>(Arrays.asList(enkgbm,halvkgbm,kvartkgbm,ingfhalvkgbm,ingfkvartkgbm,flytbm));
        HjemmeVerdier = new ArrayList<>(Arrays.asList(enkghjemme,halvkghjemme,kvartkghjemme,ingfhalvkghjemme,ingfkvartkghjemme,flythjemme));
        FakturaVerdier = new ArrayList<>(Arrays.asList(enkgfak,halvkgfak,kvartkgfak,ingfhalvkgfak,ingfkvartkgfak,flytfak));

        Main main = new Main();
        honningtype = main.Fåhonningtyper();

        try {
            if (honningtype != null) {
                String s;
                for (int i = 0; i < BMverdier.size(); i++) {
                    s = Integer.toString(honningtype.get(i+3).getBondensMarkedPris());
                    BMverdier.get(i).setText(s);
                }
                for (int i = 0; i < HjemmeVerdier.size(); i++) {
                    s= Integer.toString(honningtype.get(i+3).getHjemmePris());
                    HjemmeVerdier.get(i).setText(s);
                }
                for (int i = 0; i < FakturaVerdier.size(); i++) {
                    s = Integer.toString(honningtype.get(i+3).getFakturaPris());
                    FakturaVerdier.get(i).setText(s);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        momslagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ferdigprodukt.getText().toString().equals("") || ikkeferdig.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Sett inn verdier i feltene", Toast.LENGTH_SHORT).show();
                } else {
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

    public void extend(ConstraintLayout exe) {
        if (exe.isShown()) {
            exe.setVisibility(View.GONE);
        } else {
            exe.setVisibility(View.VISIBLE);
        }
    }
    public void lagre(View view){

    }

    public boolean checkifchanged(){
        for(int i = 0; i<HjemmeVerdier.size();i++){
            HjemmeVerdier.get(i).getText().toString();
        }
        return false;
    }
}
