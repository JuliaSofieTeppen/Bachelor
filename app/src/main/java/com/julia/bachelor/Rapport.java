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
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Rapport extends Fragment {
    private static final String KEY_ALLSALG = "AllSalg";
    ListView listView;
    Spinner datoer;
    Spinner salgtyper;
    static StringBuilder sb;
    ArrayList<Object> Salg;
    ArrayList<String> salgliste;
    Beregninger beregninger;

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

        listView = rootView.findViewById(R.id.salgitems);
        salgliste = new ArrayList<>();

        datoer = rootView.findViewById(R.id.dagmånedår);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.datoer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        datoer.setAdapter(adapter);

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
                //TODO sett inn position der fordi ...fordi
                Intent i = new Intent(getContext(), SalgItem.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    private void selectdagmånedår() {
        switch (datoer.getSelectedItemPosition()) {
            case 1:
                //sorter på dag
                break;
            case 2:
                //sorter på måned
                break;
            case 3:
                //sorter på år
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
        ArrayList<BondensMarked> bmlist = beregninger.separateBondensMarked(Salg);
        salgliste.removeAll(salgliste);

        for (int i = 0; i < bmlist.size(); i++) {
            BondensMarked bm = bmlist.get(i);
            sb.append(bm.getDato()).append("   ").append("BM").append("  Beløp: ").append(bm.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpåHjemme() {
        sb = new StringBuilder();
        ArrayList<Hjemme> bmlist = beregninger.separateHjemme(Salg);
        salgliste.removeAll(salgliste);

        for (int i = 0; i < bmlist.size(); i++) {
            Hjemme bm = bmlist.get(i);
            sb.append(bm.getDato()).append("   ").append(bm.getKunde()).append("  Beløp: ").append(bm.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpåvideresalg() {
        sb = new StringBuilder();
        ArrayList<Videresalg> bmlist = beregninger.separateVideresalg(Salg);
        salgliste.removeAll(salgliste);

        for (int i = 0; i < bmlist.size(); i++) {
            Videresalg bm = bmlist.get(i);
            sb.append(bm.getDato()).append("   ").append(bm.getKunde()).append("  Beløp: ").append(bm.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpåAnnet() {
        sb = new StringBuilder();
        ArrayList<Annet> bmlist = beregninger.separateAnnet(Salg);
        salgliste.removeAll(salgliste);

        for (int i = 0; i < bmlist.size(); i++) {
            Annet bm = bmlist.get(i);
            sb.append(bm.getDato()).append("   ").append(bm.getKunde()).append("  Beløp: ").append(bm.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpåalle() {
        sb = new StringBuilder();
        salgliste.removeAll(salgliste);
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
