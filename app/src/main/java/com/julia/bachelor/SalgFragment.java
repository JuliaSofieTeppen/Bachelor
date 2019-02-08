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
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class SalgFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    Button addbutton;
    ConstraintLayout som1kg;
    ConstraintLayout som05kg;
    ConstraintLayout som025kg;
    ConstraintLayout lyng1kg;
    ConstraintLayout lyng05kg;
    ConstraintLayout lyng025kg;
    ConstraintLayout Ingf05kg;
    ConstraintLayout Ingf025kg;
    ConstraintLayout flyt;
    TextView totaltext;
    TextView gjnsnitttext;
    TextView kg1txtsom;
    TextView kg05txtsom;
    TextView kg025txtsom;
    List arraylist;

    public SalgFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SalgFragment newInstance(int sectionNumber) {
        SalgFragment fragment = new SalgFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_salg, container, false);

        addbutton = rootView.findViewById(R.id.addbutton);

        som1kg = rootView.findViewById(R.id.som1kg);
        som05kg = rootView.findViewById(R.id.som05kg);
        som025kg = rootView.findViewById(R.id.som025kg);
        lyng1kg = rootView.findViewById(R.id.lyng1kg);
        lyng05kg = rootView.findViewById(R.id.lyng05kg);
        lyng025kg = rootView.findViewById(R.id.lyng025kg);
        Ingf05kg = rootView.findViewById(R.id.ingf05kg);
        Ingf025kg = rootView.findViewById(R.id.ingf025kg);
        flyt = rootView.findViewById(R.id.flyt);

        totaltext = rootView.findViewById(R.id.totaltext);
        gjnsnitttext = rootView.findViewById(R.id.gjnsnitttext);
        kg1txtsom = rootView.findViewById(R.id.kg1txtsom);
        kg05txtsom = rootView.findViewById(R.id.kg05txtsom);
        kg025txtsom = rootView.findViewById(R.id.kg025txtsom);


        arraylist = new ArrayList();


        som1kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend);
                extend(exe);
            }
        });

        som05kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend2);
                extend(exe);
            }
        });
        som025kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend3);
                extend(exe);
            }
        });

        lyng1kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend4);
                extend(exe);
            }
        });
        lyng05kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend5);
                extend(exe);
            }
        });
        lyng025kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = rootView.findViewById(R.id.extend6);
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

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(rootView.getContext() , addbutton);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Bondens Marked")){
                            Intent myIntent = new Intent(rootView.getContext(), BmSalg.class);
                            myIntent.putExtra("BM", 1); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        }else if(item.getTitle().equals("Hjemme salg")){
                            Intent myIntent = new Intent(rootView.getContext(), HjemmeSalg.class);
                            myIntent.putExtra("Hjemme", 1); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        }else if(item.getTitle().equals("Videre salg")){
                            Intent myIntent = new Intent(rootView.getContext(), FakturaSalg.class);
                            myIntent.putExtra("Videresalg", 1); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        }else{
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

    public void extend(ConstraintLayout exe){
        if(exe.isShown()){
            exe.setVisibility(View.GONE);
        }else {
            exe.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Main) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void setTotalGjennomsnitt(){
        //totaltext.setText(totalsum(arraylist));
        //gjnsnitttext.setText(gjensnitt(arraylist));

    }
    public void setSommerhonning(){
        //kg1txtsom.setText(getSumArray(0));
        //kg05txtsom.setText(getSumArray(1));
        //kg025txtsom.setText(getSumArray(2));

    }
    public void setLynghonning(){

    }
    public void setAnnet(){

    }
}