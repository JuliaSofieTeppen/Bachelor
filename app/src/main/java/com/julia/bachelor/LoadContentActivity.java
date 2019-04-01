package com.julia.bachelor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.Beholdning;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.Honning;
import com.julia.bachelor.helperClass.Salg;
import com.julia.bachelor.helperClass.Videresalg;

import java.util.ArrayList;

public class LoadContentActivity extends Activity {
    private static final String KEY_ANNET = "Annet";
    private static final String KEY_BEHOLDNING = "Beholdning";
    private static final String KEY_BEHOLDNINGUT = "BeholdningUt";
    private static final String KEY_BONDENSMARKED = "Bondensmarked";
    private static final String KEY_HJEMME = "Hjemme";
    private static final String KEY_HONNING = "Honning";
    private static final String KEY_VIDERESALG = "Videresalg";
    private static final String KEY_BUNDLE = "Bundle";

    static ArrayList<com.julia.bachelor.helperClass.Annet> Annet;
    static ArrayList<com.julia.bachelor.helperClass.Beholdning> Beholdning;
    static ArrayList<com.julia.bachelor.helperClass.Salg> Salg;
    static ArrayList<BondensMarked> Bm;
    static ArrayList<com.julia.bachelor.helperClass.Hjemme> Hjemme;
    static ArrayList<com.julia.bachelor.helperClass.Honning> Honning;
    static ArrayList<Videresalg> Videresalg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_content);
        if (getActionBar() != null) getActionBar().hide();
        Database.getAnnetValues();
        Database.getBeholdningValues();
        Database.getBeholdningUtValues();
        Database.getBMValues();
        Database.getHjemmeValues();
        Database.getHonningType();
        Database.getVideresalgValues();
        ImageView bee = findViewById(R.id.LoadingBeeImage);
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
                Intent intent = new Intent(LoadContentActivity.this, MainActivity.class);
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
                finish();
            }

        }, 3000);
    }

    public void setAnnet(ArrayList<Annet> annet) {
        Annet = annet;
    }

    public void setBeholdning(ArrayList<Beholdning> beholdnings) {
        Beholdning = beholdnings;
    }

    public void setBeholdningUt(ArrayList<Salg> salgs) {
        Salg = salgs;
    }

    public void setBM(ArrayList<BondensMarked> bondensMarkeds) {
        Bm = bondensMarkeds;
    }

    public void setHjemme(ArrayList<Hjemme> hjemmes) {
        Hjemme = hjemmes;
    }

    public void setHonning(ArrayList<Honning> honning) {
        Honning = honning;
    }

    public void setVideresalg(ArrayList<com.julia.bachelor.helperClass.Videresalg> videresalg) {
        Videresalg = videresalg;
    }
}