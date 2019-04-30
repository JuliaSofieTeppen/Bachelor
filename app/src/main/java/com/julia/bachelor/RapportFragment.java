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

import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.SortedObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class RapportFragment extends Fragment {
    private static final String KEY_ALLSALG = "AllSalg";
    private static final String KEY_OBJECT = "Object";
    private static final String KEY_BUNDLE = "Bundle";
    ListView listView;
    Spinner datoer, salgtyper;
    ArrayList<SalgTemplate> Salg, dynamicList;
    ArrayList<String> salgliste;
    Beregninger beregninger;

    public RapportFragment() {}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RapportFragment newInstance(ArrayList<SalgTemplate> salg) {
        RapportFragment fragment = new RapportFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_ALLSALG, salg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rapport, container, false);
        listView = rootView.findViewById(R.id.salgitems);
        datoer = rootView.findViewById(R.id.dagmånedår);
        salgtyper = rootView.findViewById(R.id.salgtyper);
        Salg = (ArrayList<SalgTemplate>) getArguments().getSerializable(KEY_ALLSALG);
        beregninger = new Beregninger(this.getContext());
        dynamicList = new ArrayList<>();
        salgliste = new ArrayList<>();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.datoer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        datoer.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.Salg, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salgtyper.setAdapter(adapter);

        datoer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectdagmanedar(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        salgtyper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectsalgtyper(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_OBJECT, (Serializable) dynamicList.get(position));
                Intent myIntent = new Intent(RapportFragment.this.getContext(), DetailsActivity.class);
                myIntent.putExtra(KEY_BUNDLE, bundle);
                startActivity(myIntent);
            }
        });
        return rootView;
    }

    private void selectdagmanedar(int position) {
        switch (position) {
            case 0:
                selectsalgtyper(salgtyper.getSelectedItemPosition());
                sortDay();
                break;
            case 1:
                selectsalgtyper(salgtyper.getSelectedItemPosition());
                sortMonth();
                break;
            case 2:
                selectsalgtyper(salgtyper.getSelectedItemPosition());
                sortYear();
                break;
        }
    }
    public void sortDay(){

    }

    private void selectsalgtyper(int posisjon) {
        switch (posisjon) {
            case 0:
                sorterpaalle();
                break;
            case 1:
                sorterpaBondensMarked();
                break;
            case 2:
                sorterpaHjemme();
                break;
            case 3:
                sorterpavideresalg();
                break;
            case 4:
                sorterpaAnnet();
                break;

        }
    }

    // using Dynamic list
    void updateSalgliste() {
        StringBuilder sb = new StringBuilder();
        salgliste.clear();
        for (SalgTemplate salg : dynamicList) {
            sb.append(salg.getDato()).append("   ").append(salg.getKunde()).append("  Beløp: ").append(salg.getBelop());
            salgliste.add(sb.toString());
            sb.delete(0, sb.length());
        }
    }

    @SuppressWarnings("unchecked")
    void updateListView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, salgliste);
        listView.setAdapter(arrayAdapter);
    }

    public void sorterpaBondensMarked() {
        dynamicList = beregninger.separateBondensMarked(Salg);
        updateSalgliste();
        updateListView();
    }

    public void sorterpaHjemme() {
        dynamicList = beregninger.separateHjemme(Salg);
        updateSalgliste();
        updateListView();
    }

    public void sorterpavideresalg() {
        dynamicList = beregninger.separateVideresalg(Salg);
        updateSalgliste();
        updateListView();
    }

    public void sorterpaAnnet() {
        dynamicList = beregninger.separateAnnet(Salg);
        updateSalgliste();
        updateListView();
    }

    public void sorterpaalle() {
        dynamicList.clear();
        dynamicList.addAll(Salg);
        updateSalgliste();
        updateListView();
    }

    void sortMonth() {
        HashMap<String, SortedObjects> sorted = new HashMap<>();
        for (int i = 0; i < dynamicList.size(); i++) {
            String date;
            SalgTemplate salg = dynamicList.get(i);
            date = getMonth(salg.getDato());
            // sorted is a hash map where i use the date as the key. date is a string
            if (!sorted.containsKey(date)) {
                SortedObjects sortedObjects = new SortedObjects(true);
                sortedObjects.add(dynamicList.get(i));
                sortedObjects.setDato(date);
                sorted.put(date, sortedObjects);
            } else {
                SortedObjects d = sorted.get(date);
                if (d != null) d.add(dynamicList.get(i));
            }
        }
        dynamicList.clear();
        dynamicList.addAll(sorted.values());
        salgliste.clear();
        updateSalgliste();
        updateListView();
    }

    void sortYear() {
        HashMap<String, SortedObjects> sorted = new HashMap<>();
        for (int i = 0; i < dynamicList.size(); i++) {
            String date;
            SalgTemplate salg = dynamicList.get(i);
            date = getYear(salg.getDato());
            // sorted is a hash map where i use the date as the key. date is a string
            if (!sorted.containsKey(date)) {
                SortedObjects sortedObjects = new SortedObjects(false);
                sortedObjects.add(dynamicList.get(i));
                sortedObjects.setDato(date);
                sorted.put(date, sortedObjects);
            } else {
                SortedObjects d = sorted.get(date);
                if (d != null) d.add(dynamicList.get(i));
            }
        }
        dynamicList.clear();
        dynamicList.addAll(sorted.values());
        salgliste.clear();
        updateSalgliste();
        updateListView();
    }

    String getMonth(String date) {
        // yyyy.MM.dd
        String[] dates = date.split("-");
        return dates[0] + "-" + dates[1];
    }

    String getYear(String date) {
        // yyyy.MM.dd
        String[] dates = date.split("-");
        return dates[0];
    }
}
