package com.julia.bachelor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.ArrayList;

public class LoadContent extends Activity {
    static ArrayList<Honning> Honningtype;
    static ArrayList<Annet> Annet;
    static ArrayList<Hjemme> Hjemme;
    static ArrayList<BondensMarked> Bm;
    static ArrayList<Videresalg> Videresalg;
    static ArrayList<Beholdning> Beholdning;
    static ArrayList<BeholdningUt> BeholdningUt;
    static public boolean notFinished = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_content);
        Database database;
        database = new Database();
        database.getHonningType();
        database.getAnnetValues();
        database.getBeholdningValues();
        database.getBeholdningUtValues();
        database.getBMValues();
        database.getHjemmeValues();
        database.getVideresalgValues();

        ImageView rocketImage = (ImageView) findViewById(R.id.LoadingBeeImage);
        rocketImage.setBackgroundResource(R.drawable.animation);
        AnimationDrawable anim = (AnimationDrawable) rocketImage.getBackground();
        anim.start();

        // Flying bee ....
        //  \ / />/>
        // (^_^)( || ||)>
        //   /// \\\
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(LoadContent.this, Main.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Honningtype", Honningtype);
                bundle.putSerializable("Annet", Annet);
                bundle.putSerializable("Beholdning",Beholdning);
                bundle.putSerializable("BeholdningUt", BeholdningUt);
                bundle.putSerializable("BondensMarked", Bm);
                bundle.putSerializable("Hjemme", Hjemme);
                bundle.putSerializable("Videresalg", Videresalg);
                intent.putExtra("ThaBundle",bundle);
                LoadContent.this.startActivity(intent);
                finish();
            }

        },2000);
    }
    public void setAnnet(ArrayList<Annet> annet) { Annet = annet; }

    public void setBeholdning(ArrayList<Beholdning> beholdnings){ Beholdning = beholdnings; }

    public void setBeholdningUt(ArrayList<BeholdningUt> beholdningUts){ BeholdningUt = beholdningUts;}

    public void setBM(ArrayList<BondensMarked> bondensMarkeds){ Bm = bondensMarkeds; }

    public void setHjemme(ArrayList<Hjemme> hjemmes){ Hjemme = hjemmes; }

    public void setHonning(ArrayList<Honning> type){ Honningtype = type; }

    public void setVideresalg(ArrayList<com.julia.bachelor.Videresalg> videresalg) { Videresalg = videresalg; notFinished = false;}
}
