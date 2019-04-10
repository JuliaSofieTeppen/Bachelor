package com.julia.bachelor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BeholdningTemplate;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.Honning;
import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.Videresalg;

import java.util.ArrayList;

public class LoadContentActivity extends Fragment {
    private static final String KEY_ANNET = "Annet";
    private static final String KEY_BEHOLDNING = "Beholdning";
    private static final String KEY_BEHOLDNINGUT = "BeholdningUt";
    private static final String KEY_BONDENSMARKED = "Bondensmarked";
    private static final String KEY_HJEMME = "Hjemme";
    private static final String KEY_HONNING = "Honning";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String KEY_VIDERESALG = "Videresalg";
    private static final String KEY_BUNDLE = "Bundle";

    static ArrayList<SalgTemplate> Annet;
    static ArrayList<BeholdningTemplate> Beholdning;
    static ArrayList<BeholdningTemplate> Salg;
    static ArrayList<SalgTemplate> Bm;
    static ArrayList<SalgTemplate> Hjemme;
    static ArrayList<Honning> Honning;
    static ArrayList<SalgTemplate> Videresalg;

    public LoadContentActivity(){

    }
    public static LoadContentActivity newInstance(int sectionNumber) {
        LoadContentActivity fragment = new LoadContentActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_load_content, container, false);
        Database.getAnnetValues();
        Database.getBeholdningValues();
        Database.getBeholdningUtValues();
        Database.getBMValues();
        Database.getHjemmeValues();
        Database.getHonningType();
        Database.getVideresalgValues();
        ImageView bee = rootView.findViewById(R.id.LoadingBeeImage);
        bee.setBackgroundResource(R.drawable.animation);
        AnimationDrawable anim = (AnimationDrawable) bee.getBackground();
        anim.start();
        // Flying bee ....
        //  \ / />/>
        // (^_^)( || ||)>
        //   /// \\\
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadContentActivity.this.getContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_HONNING, Honning);
                bundle.putSerializable(KEY_ANNET, Annet);
                bundle.putSerializable(KEY_BEHOLDNING, Beholdning);
                bundle.putSerializable(KEY_BEHOLDNINGUT, Salg);
                bundle.putSerializable(KEY_BONDENSMARKED, Bm);
                bundle.putSerializable(KEY_HJEMME, Hjemme);
                bundle.putSerializable(KEY_VIDERESALG, Videresalg);
                intent.putExtra(KEY_BUNDLE, bundle);
                LoadContentActivity.this.startActivity(intent);
            }

        }, 6000);
        return rootView;
    }

    public void setAnnet(ArrayList<SalgTemplate> annet) {
        Annet = annet;
    }

    public void setBeholdning(ArrayList<BeholdningTemplate> beholdnings) {
        Beholdning = beholdnings;
    }

    public void setBeholdningUt(ArrayList<BeholdningTemplate> salgs) {
        Salg = salgs;
    }

    public void setBM(ArrayList<SalgTemplate> bondensMarkeds) {
        Bm = bondensMarkeds;
    }

    public void setHjemme(ArrayList<SalgTemplate> hjemmes) {
        Hjemme = hjemmes;
    }

    public void setHonning(ArrayList<Honning> honning) {
        Honning = honning;
    }

    public void setVideresalg(ArrayList<SalgTemplate> videresalg) {
        Videresalg = videresalg;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
