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
import android.widget.Spinner;
import android.widget.Toast;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.Videresalg;

import java.io.Serializable;
import java.util.ArrayList;

public class Rapport extends Fragment {
    private static final String KEY_ALLSALG = "AllSalg";
    private static final String KEY_OBJECT = "Object";
    private static final String KEY_BUNDLE = "Bundle";
    ListView listView;
    Spinner datoer;
    Spinner salgtyper;
    static StringBuilder sb;
    ArrayList<Object> Salg;
    ArrayList<String> salgliste;
    Beregninger beregninger;
    ArrayList<Object> dynamicList;

    public Rapport() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Rapport newInstance(ArrayList<Object> salg) {
        Rapport fragment = new Rapport();
        Bundle args = new Bundle();
        args.putSerializable(KEY_ALLSALG, salg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rapport, container, false);

        beregninger = new Beregninger(this.getContext());
        Salg = (ArrayList<Object>) getArguments().getSerializable(KEY_ALLSALG);
        dynamicList = new ArrayList<>();
        dynamicList.addAll(Salg);
        listView = rootView.findViewById(R.id.salgitems);
        salgliste = new ArrayList<>();

        datoer = rootView.findViewById(R.id.dagmånedår);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.datoer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        datoer.setAdapter(adapter);

        datoer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectdagmånedår(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        salgtyper = rootView.findViewById(R.id.salgtyper);
        ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(this.getContext(), R.array.Salg, android.R.layout.simple_spinner_item);
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salgtyper.setAdapter(sadapter);
        salgtyper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectsalgtyper(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---------------------------------------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_OBJECT,(Serializable) dynamicList.get(position));
                Intent myIntent = new Intent(Rapport.this.getContext(), SalgItem.class);
                myIntent.putExtra(KEY_BUNDLE, bundle);
                startActivity(myIntent);
            }
        });
        return rootView;
    }

    private void selectdagmånedår(int position) {
        switch (position) {
            case 0:
                Toast.makeText(this.getContext(),"sorter på dag", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this.getContext(),"sorter på måned", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this.getContext(),"sorter på år", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void selectsalgtyper(int posisjon) {
            switch (posisjon) {
                case 0:
                    sorterpåalle();
                    break;
                case 1:
                    sorterpåBondensMarked();
                    break;
                case 2:
                    sorterpåHjemme();
                    break;
                case 3:
                    sorterpåvideresalg();
                    break;
                case 4:
                    sorterpåAnnet();
                    break;

        }
    }

    public void sorterpåBondensMarked() {
        sb = new StringBuilder();
        dynamicList = beregninger.separateBondensMarked(Salg);
        salgliste.clear();

        for (int i = 0; i < dynamicList.size(); i++) {
            BondensMarked bm = (BondensMarked) dynamicList.get(i);
            sb.append(bm.getDato()).append("   ").append("BM").append("  Beløp: ").append(bm.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpåHjemme() {
        sb = new StringBuilder();
        dynamicList = beregninger.separateHjemme(Salg);
        salgliste.clear();

        for (int i = 0; i < dynamicList.size(); i++) {
            Hjemme bm = (Hjemme)dynamicList.get(i);
            sb.append(bm.getDato()).append("   ").append(bm.getKunde()).append("  Beløp: ").append(bm.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpåvideresalg() {
        sb = new StringBuilder();
        dynamicList = beregninger.separateVideresalg(Salg);
        salgliste.clear();

        for (int i = 0; i < dynamicList.size(); i++) {
            Videresalg bm = (Videresalg) dynamicList.get(i);
            sb.append(bm.getDato()).append("   ").append(bm.getKunde()).append("  Beløp: ").append(bm.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpåAnnet() {
        sb = new StringBuilder();
        dynamicList = beregninger.separateAnnet(Salg);
        salgliste.clear();

        for (int i = 0; i < dynamicList.size(); i++) {
            Annet bm =(Annet) dynamicList.get(i);
            sb.append(bm.getDato()).append("   ").append(bm.getKunde()).append("  Beløp: ").append(bm.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpåalle() {
        sb = new StringBuilder();
        dynamicList.clear();
        dynamicList.addAll(Salg);
        salgliste.clear();
        if (Salg != null) {
            for (int i = 0; i < Salg.size(); i++) {
                if (Salg.get(i) instanceof BondensMarked) {
                    BondensMarked bm = (BondensMarked) Salg.get(i);
                    sb.append(bm.getDato()).append("   ").append("BM").append("  Beløp: ").append(bm.getBelop());
                    salgliste.add(sb.toString());
                    sb.delete(0, sb.length());
                } else if (Salg.get(i) instanceof Hjemme) {
                    Hjemme hjemme = (Hjemme) Salg.get(i);
                    sb.append(hjemme.getDato()).append("   ").append(hjemme.getKunde()).append("  Beløp: ").append(hjemme.getBelop());
                    salgliste.add(sb.toString());
                    sb.delete(0, sb.length());
                } else if (Salg.get(i) instanceof Videresalg) {
                    Videresalg videresalg = (Videresalg) Salg.get(i);
                    sb.append(videresalg.getDato()).append("   ").append(videresalg.getKunde()).append("  Beløp: ").append(videresalg.getBelop());
                    salgliste.add(sb.toString());
                    sb.delete(0, sb.length());
                } else if (Salg.get(i) instanceof Annet) {
                    Annet annet = (Annet) Salg.get(i);
                    sb.append(annet.getDato()).append("   ").append(annet.getKunde()).append("  Beløp: ").append(annet.getBelop());
                    salgliste.add(sb.toString());
                    sb.delete(0, sb.length());
                } else {
                    sb.append("Beklager noe gikk galt  :(");
                    salgliste.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }
        } else {
            sb.append("Beklager kan ikke laste innhold.");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }
}
