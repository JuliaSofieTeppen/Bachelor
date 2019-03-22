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

import java.util.ArrayList;
import java.util.List;

public class Hovedside extends Fragment {
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
    TextView info, navn;
    List arraylist;
    ArrayList<Beholdning> beholdnings;
    ArrayList<Salg> salg;
    ArrayList<Honning> honning;

    public Hovedside() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Hovedside newInstance(int sectionNumber, ArrayList<Beholdning> Beholdning, ArrayList<Salg> Salg, ArrayList<Honning> Honning) {
        Hovedside fragment = new Hovedside();
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
        final View rootView = inflater.inflate(R.layout.hovedside, container, false);
        addbutton = rootView.findViewById(R.id.addbutton);
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
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KEY_HONNING, honning);
                            Intent myIntent = new Intent(rootView.getContext(), BmSalg.class);
                            myIntent.putExtra(KEY_BUNDLE, bundle); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else if (item.getTitle().equals("Hjemme salg")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KEY_HONNING, honning);
                            Intent myIntent = new Intent(rootView.getContext(), HjemmeSalg.class);
                            myIntent.putExtra(KEY_BUNDLE, bundle); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else if (item.getTitle().equals("Videre salg")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KEY_HONNING, honning);
                            Intent myIntent = new Intent(rootView.getContext(), FakturaSalg.class);
                            myIntent.putExtra(KEY_BUNDLE, bundle); //Optional parameters
                            rootView.getContext().startActivity(myIntent);
                        } else {
                            Intent myIntent = new Intent(rootView.getContext(), SalgAnnet.class);
                            rootView.getContext().startActivity(myIntent);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        try {
            beholdnings = (ArrayList<Beholdning>) (getArguments().getSerializable(KEY_BEHOLDNING));
            salg = (ArrayList<Salg>) (getArguments().getSerializable(KEY_BEHOLDNINGUT));
            honning = (ArrayList<Honning>) (getArguments().getSerializable(KEY_HONNING));
            info.setText(setValueString());
            navn.setText(setNameString());
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

    String buildNameString() {
        if (beholdnings == null || salg == null || honning == null || honning.size() == 0)
            return "Error getting content";
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < honning.size(); i++) {
                sb.append(honning.get(i).getType()).append("\n");
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    String buildValueString() {
        if (beholdnings == null || salg == null || honning == null || honning.size() == 0)
            return "Error getting content";
        StringBuilder sb = new StringBuilder();
        try{
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
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    Beholdning findCurrentBeholdning() {
        // TODO make this method find the newest Beholdning object
        try {
            Beholdning beholdning= beholdnings.get(beholdnings.size()-1);
            for(Beholdning beholdninger : beholdnings){
                if(greaterThan(beholdning, beholdninger)){

                }
            }
            return beholdning;
        }catch (IndexOutOfBoundsException e){
            Toast.makeText(this.getContext(), "Internett ikke tilkoblet", Toast.LENGTH_SHORT).show();
            addbutton.setVisibility(View.GONE);
        }
        return null;
    }
    boolean greaterThan(Beholdning current, Beholdning next){
        String[] dateCurrent = current.getDato().split("\\.");
        String[] dateNext = next.getDato().split("\\.");
        if(dateCurrent.length!=3 || dateNext.length!=3) throw new IndexOutOfBoundsException();
        if(Integer.parseInt(dateCurrent[0]) <= Integer.parseInt(dateNext[0])){
            return true;
        }else if(Integer.parseInt(dateCurrent[1]) <= Integer.parseInt(dateNext[1])){
            return true;
        }else if(Integer.parseInt(dateCurrent[2]) < Integer.parseInt(dateNext[2])){
            return true;
        }
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Main) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}