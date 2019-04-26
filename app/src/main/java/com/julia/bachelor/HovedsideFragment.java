package com.julia.bachelor;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.Beholdning;
import com.julia.bachelor.helperClass.BeholdningTemplate;
import com.julia.bachelor.helperClass.Honning;
import com.julia.bachelor.helperClass.SalgTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HovedsideFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String KEY_ALLSALG = "AllSalg";
    private static final String KEY_BEHOLDNING = "Beholdning";
    private static final String KEY_HONNING = "Honning";
    private static final String KEY_BUNDLE = "Bundle";
    static ArrayList<BeholdningTemplate> beholdnings;
    static ArrayList<SalgTemplate> AllSalg;
    static ArrayList<Honning> honning;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    Button addbutton;
    TextView info, navn, dato;

    public HovedsideFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HovedsideFragment newInstance(int sectionNumber, ArrayList<BeholdningTemplate> Salg, ArrayList<Honning> Honning, ArrayList<SalgTemplate> AllSalg) {
        HovedsideFragment fragment = new HovedsideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(KEY_BEHOLDNING, Salg);
        args.putSerializable(KEY_HONNING, Honning);
        args.putSerializable(KEY_ALLSALG, AllSalg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_hovedside, container, false);
        addbutton = rootView.findViewById(R.id.addbutton);
        info = rootView.findViewById(R.id.Info);
        navn = rootView.findViewById(R.id.navn);
        dato = rootView.findViewById(R.id.dato);
        mSwipeRefreshLayout = rootView.findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                final MainActivity main = new MainActivity();
                AllSalg.clear();
                honning.clear();
                beholdnings.clear();
                main.fetch();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        beholdnings = main.getBeholdning();
                        info.setText(setValueString());
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(rootView.getContext(), addbutton);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Bondens Marked")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KEY_HONNING, honning);
                            Intent myIntent = new Intent(rootView.getContext(), BmSalgActivity.class);
                            myIntent.putExtra(KEY_BUNDLE, bundle); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else if (item.getTitle().equals("Hjemmesalg")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KEY_HONNING, honning);
                            Intent myIntent = new Intent(rootView.getContext(), HjemmesalgActivity.class);
                            myIntent.putExtra(KEY_BUNDLE, bundle);
                            rootView.getContext().startActivity(myIntent);
                        } else if (item.getTitle().equals("Videresalg")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KEY_HONNING, honning);
                            Intent myIntent = new Intent(rootView.getContext(), VideresalgActivity.class);
                            myIntent.putExtra(KEY_BUNDLE, bundle);
                            rootView.getContext().startActivity(myIntent);
                        } else {
                            Intent myIntent = new Intent(rootView.getContext(), AnnetSalgActivity.class);
                            rootView.getContext().startActivity(myIntent);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        try {
            ArrayList<BeholdningTemplate> beholdningTemplates = (ArrayList<BeholdningTemplate>) (getArguments().getSerializable(KEY_BEHOLDNING));
            if (beholdningTemplates != null) {
                beholdnings = Beregninger.separateBeholdning(beholdningTemplates);
            }
            honning = (ArrayList<Honning>) (getArguments().getSerializable(KEY_HONNING));
            AllSalg = (ArrayList<SalgTemplate>) (getArguments().getSerializable(KEY_ALLSALG));
            info.setText(setValueString());
            navn.setText(setNameString());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private String setNameString() {
        return honning.get(0).getType() + "\n" +
                honning.get(1).getType() + "\n" +
                honning.get(2).getType() + "\n" +
                honning.get(3).getType() + "\n" +
                honning.get(4).getType() + "\n" +
                honning.get(5).getType() + "\n" +
                honning.get(6).getType() + "\n" +
                honning.get(7).getType() + "\n" +
                honning.get(8).getType() + "\n";
    }

    private String setValueString() {
        BeholdningTemplate beholdning = null;
        try {
            beholdning = CalculateBeholdning();
            dato.setText(beholdning.getDato());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return beholdning == null ? "" :
                beholdning.getSommer() + "\n" +
                        beholdning.getSommerH() + " \n" +
                        beholdning.getSommerK() + " \n" +
                        beholdning.getLyng() + " \n" +
                        beholdning.getLyngH() + " \n" +
                        beholdning.getLyngK() + " \n" +
                        beholdning.getIngeferH() + " \n" +
                        beholdning.getIngeferK() + " \n" +
                        beholdning.getFlytende() + " \n";
    }
    
    BeholdningTemplate CalculateBeholdning() {
        BeholdningTemplate beholdning = findCurrentBeholdning(Beregninger.separateBeholdning(beholdnings));
        ArrayList<SalgTemplate> period = salesInPeriod(beholdning.getDato());
        int[] amount = new int[9];
        try {
            for (SalgTemplate salg : period) {
                if(salg instanceof Annet) continue;
                String[] pairs = salg.getVarer().split(",");
                for (int i = 0; i < amount.length; i++) {
                    String[] value = pairs[i].split("-");
                    amount[i] += Integer.parseInt(value[1]);
                }
            }
            beholdning.setSommer(beholdning.getSommer() - amount[0]);
            beholdning.setSommerH(beholdning.getSommerH() - amount[1]);
            beholdning.setSommerK(beholdning.getSommerK() - amount[2]);
            beholdning.setLyng(beholdning.getLyng() - amount[3]);
            beholdning.setLyngH(beholdning.getLyngH() - amount[4]);
            beholdning.setLyngK(beholdning.getLyngK() - amount[5]);
            beholdning.setIngeferH(beholdning.getIngeferH() - amount[6]);
            beholdning.setIngeferK(beholdning.getIngeferK() - amount[7]);
            beholdning.setFlytende(beholdning.getFlytende() - amount[8]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return beholdning;
    }

    private BeholdningTemplate findCurrentBeholdning(ArrayList<BeholdningTemplate> beholdning) {
        Beholdning current = null;
        try {
            current = (Beholdning) beholdning.get(beholdning.size() - 1);
            for (int i = 0; i < beholdning.size(); i++) {
                if (current.getDato().compareTo(beholdning.get(i).getDato()) <= 0) {
                    current = (Beholdning) beholdning.get(i);
                }
            }
            return current;
        } catch (IndexOutOfBoundsException e) {
            addbutton.setVisibility(View.GONE);
        }
        if (current == null) throw new NullPointerException("Possible problem with connection");
        return Beholdning.copy(current);
    }

    private ArrayList<SalgTemplate> salesInPeriod(String start) {
        String end = Beregninger.getDate();
        ArrayList<SalgTemplate> period = new ArrayList<>();
        for (SalgTemplate sale : AllSalg) {
            String dato = sale.getDato();
            if (start.compareTo(dato) < 0 && end.compareTo(dato) >= 0) {
                period.add(sale);
            }
        }
        return period;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}