package com.julia.bachelor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;



public class SkalBliInnstillinger extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_SECTION_NUMBER = "section_number";
    ConstraintLayout enkg;
    ConstraintLayout halvkg;
    ConstraintLayout kvartkg;
    ConstraintLayout Ingf05kg;
    ConstraintLayout Ingf025kg;
    ConstraintLayout flyt;
    ConstraintLayout endreverdier;
    ConstraintLayout endremoms;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText ferdigprodukt;
    EditText ikkeferdig;

    public SkalBliInnstillinger() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SkalBliInnstillinger newInstance(int sectionNumber) {
        SkalBliInnstillinger fragment = new SkalBliInnstillinger();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_skal_bli_innstillinger, container, false);

        enkg = rootView.findViewById(R.id.som1kg);
        halvkg = rootView.findViewById(R.id.som05kg);
        kvartkg = rootView.findViewById(R.id.som025kg);
        Ingf05kg = rootView.findViewById(R.id.ingf05kg);
        Ingf025kg = rootView.findViewById(R.id.ingf025kg);
        flyt = rootView.findViewById(R.id.flyt);
        endreverdier = rootView.findViewById(R.id.endrepris);
        endremoms = rootView.findViewById(R.id.endremoms);
        ferdigprodukt = rootView.findViewById(R.id.ferdigprodukt);
        ikkeferdig = rootView.findViewById(R.id.ikkeferdig);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        editor = sharedPreferences.edit();


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
                ConstraintLayout exe = rootView.findViewById(R.id.extend);
                extend(exe);
            }
        });

        halvkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend2);
                extend(exe);
            }
        });
        kvartkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend3);
                extend(exe);
            }
        });


        Ingf05kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend7);
                extend(exe);
            }
        });
        Ingf025kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend8);
                extend(exe);
            }
        });
        flyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend9);
                extend(exe);
            }
        });
        return rootView;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    //TODO skal kunne endre verdier.
    public void momslagre(View view){
        if(ferdigprodukt.getText().toString().equals("")||ikkeferdig.getText().toString().equals("")) {
            Toast.makeText(this.getContext(), "Sett inn verdier i feltene", Toast.LENGTH_SHORT).show();
        }else{
            editor.putInt("ferdigprodukt", Integer.parseInt(ferdigprodukt.getText().toString()));
            editor.putInt("ikkeferdig", Integer.parseInt(ikkeferdig.getText().toString()));
            editor.apply();
            Toast.makeText(this.getContext(), "Moms lagret", Toast.LENGTH_SHORT).show();
        }
    }
    public void extend(ConstraintLayout exe){
        if(exe.isShown()){
            exe.setVisibility(View.GONE);
        }else {
            exe.setVisibility(View.VISIBLE);
        }
    }
}
