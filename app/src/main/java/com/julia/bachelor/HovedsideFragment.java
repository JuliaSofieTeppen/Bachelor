package com.julia.bachelor;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.julia.bachelor.helperClass.BeholdningTemplate;
import com.julia.bachelor.helperClass.Honning;
import com.julia.bachelor.helperClass.Salg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HovedsideFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String KEY_BEHOLDNING = "Beholdning";
    private static final String KEY_BEHOLDNINGUT = "Salg";
    private static final String KEY_HONNING = "Honning";
    private static final String KEY_BUNDLE = "Bundle";

    Button addbutton;
    TextView info, navn, dato;
    List arraylist;
    ArrayList<BeholdningTemplate> beholdnings;
    ArrayList<BeholdningTemplate> salg;
    ArrayList<Honning> honning;
    Salg beholdningUt;

    public HovedsideFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HovedsideFragment newInstance(int sectionNumber, ArrayList<BeholdningTemplate> Beholdning, ArrayList<BeholdningTemplate> Salg, ArrayList<Honning> Honning) {
        HovedsideFragment fragment = new HovedsideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(KEY_BEHOLDNING, Beholdning);
        args.putSerializable(KEY_BEHOLDNINGUT, Salg);
        args.putSerializable(KEY_HONNING, Honning);
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
        arraylist = new ArrayList();
        dato = rootView.findViewById(R.id.dato);

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
            beholdnings = (ArrayList<BeholdningTemplate>) (getArguments().getSerializable(KEY_BEHOLDNING));
            salg = (ArrayList<BeholdningTemplate>) (getArguments().getSerializable(KEY_BEHOLDNINGUT));
            honning = (ArrayList<Honning>) (getArguments().getSerializable(KEY_HONNING));
            info.setText(setValueString());
            navn.setText(setNameString());
            dato.setText(findCurrentBeholdning(beholdnings).getDato());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    String setNameString() {
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

    String setValueString() {
        BeholdningTemplate beholdning = null;
        try {
            beholdning = findCurrentBeholdning(beholdnings);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return beholdning == null ? ":/" :
                (beholdning.getSommer() - beholdningUt.getSommer()) + "\n" +
                        (beholdning.getSommerH() - beholdningUt.getSommerH()) + " \n" +
                        (beholdning.getSommerK() - beholdningUt.getSommerK()) + " \n" +
                        (beholdning.getLyng() - beholdningUt.getLyng()) + " \n" +
                        (beholdning.getLyngH() - beholdningUt.getLyngH()) + " \n" +
                        (beholdning.getLyngK() - beholdningUt.getLyngK()) + " \n" +
                        (beholdning.getIngeferH() - beholdningUt.getIngeferH()) + " \n" +
                        (beholdning.getIngeferK() - beholdningUt.getIngeferK()) + " \n" +
                        (beholdning.getFlytende() - beholdningUt.getFlytende()) + " \n";
    }

    BeholdningTemplate findCurrentBeholdning(ArrayList<BeholdningTemplate> beholdning) {
        BeholdningTemplate current = null;
        beholdningUt = null;
        try {
            current = beholdning.get(beholdning.size() - 1);
            //beholdningUt = salg.get(salg.size() - 1);
            for (int i = 0; i < beholdning.size(); i++) {
                if (greaterThan(current, beholdning.get(i))) {
                    current = beholdning.get(i);
                }
            }
            return current;
        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(this.getContext(), "Internett ikke tilkoblet", Toast.LENGTH_SHORT).show();
            addbutton.setVisibility(View.GONE);
        }
        // TODO check if its possible to do this better
        if (current == null) throw new NullPointerException("Possible problem with connection");
        return current;
    }

    boolean greaterThan(BeholdningTemplate current, BeholdningTemplate next) {
        ArrayList<String> dates = new ArrayList<>(Arrays.asList(current.getDato(), next.getDato()));
        Collections.sort(dates);
        return !dates.get(1).equals(current.getDato());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}