package com.julia.bachelor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.julia.bachelor.helperClass.BeholdningTemplate;
import com.julia.bachelor.helperClass.Honning;
import com.julia.bachelor.helperClass.SalgTemplate;

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

    static ArrayList<SalgTemplate> Annet;
    static ArrayList<BeholdningTemplate> Beholdning;
    static ArrayList<BeholdningTemplate> Salg;
    static ArrayList<SalgTemplate> Bm;
    static ArrayList<SalgTemplate> Hjemme;
    static ArrayList<Honning> Honning;
    static ArrayList<SalgTemplate> Videresalg;

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

        }, 6000);
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
}
