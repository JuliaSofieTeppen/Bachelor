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

import java.util.ArrayList;

public class Rapport extends Fragment {
    private static final String KEY_ALLSALG = "AllSalg";
    ListView listView;
    Spinner datoer;
    Spinner salgtyper;
    static StringBuilder sb;

    public Rapport() {
    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Rapport newInstance(ArrayList<Object> salg) {
        Rapport fragment = new Rapport();
        Bundle args = new Bundle();
        args.putSerializable(KEY_ALLSALG,salg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rapport, container, false);

        ArrayList<Object> Salg = (ArrayList<Object>) getArguments().getSerializable(KEY_ALLSALG);
        listView = rootView.findViewById(R.id.salgitems);
        ArrayList<String> salgliste = new ArrayList<>();

        datoer = rootView.findViewById(R.id.dagmånedår);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.datoer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        datoer.setAdapter(adapter);

        salgtyper = rootView.findViewById(R.id.salgtyper);
        ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(this.getContext(), R.array.Salg, android.R.layout.simple_spinner_item);
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salgtyper.setAdapter(sadapter);

        sb = new StringBuilder();
        if(Salg != null){
            for(int i = 0; i < Salg.size(); i++) {
                if (Salg.get(i) instanceof BondensMarked) {
                    BondensMarked bm = (BondensMarked) Salg.get(i);
                    sb.append(bm.getDato()).append("   ").append(bm.getBelop());
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
        }else {
            sb.append("Beklager kan ikke laste innhold.");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
        //---------------------------------------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO sett inn position der fordi ...fordi
                Intent i = new Intent(getContext(),SalgItem.class );
                startActivity(i);
            }
        });
        return rootView;
    }
}