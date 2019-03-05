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

import java.util.ArrayList;
import java.util.List;

public class Hovedside extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String KEY_BEHOLDNING = "Beholdning";
    private static final String KEY_BEHOLDNINGUT = "BeholdningUt";
    private static final String KEY_HONNING = "Honning";
    private static final String KEY_BUNDLE = "Bundle";
    Button addbutton;
    TextView totaltext, info, navn;
    List arraylist;
    ArrayList<Beholdning> beholdning;
    ArrayList<BeholdningUt> beholdningUt;
    ArrayList<Honning> honning;

    public Hovedside() {
    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Hovedside newInstance(int sectionNumber, ArrayList<Beholdning> Beholdning, ArrayList<BeholdningUt> BeholdningUt, ArrayList<Honning> Honning) {
        Hovedside fragment = new Hovedside();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(KEY_BEHOLDNING, Beholdning);
        args.putSerializable(KEY_BEHOLDNINGUT, BeholdningUt);
        args.putSerializable(KEY_HONNING , Honning);
        fragment.setArguments(args);
        return fragment;
    }
    @Override @SuppressWarnings("unchecked")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.hovedside, container, false);
        addbutton = rootView.findViewById(R.id.addbutton);
        totaltext = rootView.findViewById(R.id.totaltext);
        setTotalGjennomsnitt();
        info = rootView.findViewById(R.id.Info);
        navn = rootView.findViewById(R.id.navn);
        arraylist = new ArrayList();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(rootView.getContext(), addbutton);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Bondens Marked")) {
                            Intent myIntent = new Intent(rootView.getContext(), BmSalg.class);
                            myIntent.putExtra("BM", 1); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else if (item.getTitle().equals("Hjemme salg")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KEY_HONNING,honning);
                            Intent myIntent = new Intent(rootView.getContext(), HjemmeSalg.class);
                            myIntent.putExtra(KEY_BUNDLE, bundle); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else if (item.getTitle().equals("Videre salg")) {
                            Intent myIntent = new Intent(rootView.getContext(), FakturaSalg.class);
                            myIntent.putExtra("Videresalg", 1); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else {
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
        try {
            beholdning = (ArrayList<Beholdning>) (getArguments().getSerializable(KEY_BEHOLDNING));
            beholdningUt = (ArrayList<BeholdningUt>) (getArguments().getSerializable(KEY_BEHOLDNINGUT));
            honning = (ArrayList<Honning>) (getArguments().getSerializable(KEY_HONNING));
            info.setText(setValueString());
            navn.setText(setNameString());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return rootView;
    }
    String setNameString(){
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
    String setValueString(){
        Beholdning beholdning = findCurrentBeholdning();
        return beholdning.getSommer() + "\n" +
                beholdning.getSommerH() + " \n" +
                beholdning.getSommerK() + " \n" +
                beholdning.getLyng() + " \n" +
                beholdning.getLyngH() + " \n" +
                beholdning.getLyngK() + " \n" +
                beholdning.getIngeferH() + " \n" +
                beholdning.getIngeferK() + " \n" +
                beholdning.getFlytende() + " \n";
    }

    String buildNameString(){
        if(beholdning==null || beholdningUt==null || honning==null || honning.size()==0) return "Error getting content";
        StringBuilder sb = new StringBuilder();
        try {
            for(int i = 0; i < honning.size(); i++){
                sb.append(honning.get(i).getType()).append("\n");
            }
        }catch (IndexOutOfBoundsException e){e.printStackTrace();}
        return sb.toString();
    }
    String buildValueString(){
        if(beholdning==null || beholdningUt==null || honning==null || honning.size()==0) return "Error getting content";
        StringBuilder sb = new StringBuilder();
        try {
            Beholdning beholdning = findCurrentBeholdning();
            sb.append(beholdning.getSommer()).append("\n");
            sb.append(beholdning.getSommerH()).append("\n");
            sb.append(beholdning.getSommerK()).append("\n");
            sb.append(beholdning.getLyng()).append("\n");
            sb.append(beholdning.getLyngH()).append("\n");
            sb.append(beholdning.getLyngK()).append("\n");
            sb.append(beholdning.getIngeferH()).append("\n");
            sb.append(beholdning.getIngeferK()).append("\n");
            sb.append(beholdning.getFlytende()).append("\n");
        }catch (IndexOutOfBoundsException e){e.printStackTrace();}
        return sb.toString();
    }

    Beholdning findCurrentBeholdning(){
        // TODO make this method find the newest Beholdning object
        return beholdning.get(0);
    }
    // TODO need math class here..
    public void setTotalGjennomsnitt() {
        totaltext.setText(R.string.toto);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Main) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}