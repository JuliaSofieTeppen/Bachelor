package com.julia.bachelor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Innstillinger extends Activity {
    ConstraintLayout enkg;
    ConstraintLayout halvkg;
    ConstraintLayout kvartkg;
    ConstraintLayout Ingf05kg;
    ConstraintLayout Ingf025kg;
    ConstraintLayout flyt;
    ConstraintLayout endreverdier;
    ConstraintLayout endremoms;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText ferdigprodukt;
    EditText ikkeferdig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_innstillinger);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        enkg = findViewById(R.id.som1kg);
        halvkg = findViewById(R.id.som05kg);
        kvartkg = findViewById(R.id.som025kg);
        Ingf05kg = findViewById(R.id.ingf05kg);
        Ingf025kg = findViewById(R.id.ingf025kg);
        flyt = findViewById(R.id.flyt);
        endreverdier = findViewById(R.id.endrepris);
        endremoms = findViewById(R.id.endremoms);
        ferdigprodukt = findViewById(R.id.ferdigprodukt);
        ikkeferdig = findViewById(R.id.ikkeferdig);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();


        endreverdier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.bigextend);
                extend(exe);
            }
        });
        endremoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.bigextend2);
                extend(exe);
            }
        });
        enkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.extend);
                extend(exe);
            }
        });

        halvkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.extend2);
                extend(exe);
            }
        });
        kvartkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.extend3);
                extend(exe);
            }
        });


        Ingf05kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe =findViewById(R.id.extend7);
                extend(exe);
            }
        });
        Ingf025kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.extend8);
                extend(exe);
            }
        });
        flyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.extend9);
                extend(exe);
            }
        });
    }

    public void extend(ConstraintLayout exe){
        if(exe.isShown()){
            exe.setVisibility(View.GONE);
        }else {
            exe.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return true;
    }
    //TODO skal kunne endre verdier.
    public void momslagre(View view){
        if(ferdigprodukt.getText().toString().equals("")||ikkeferdig.getText().toString().equals("")) {
            Toast.makeText(this, "Sett inn verdier i feltene", Toast.LENGTH_SHORT).show();
        }else{
            editor.putInt("ferdigprodukt", Integer.parseInt(ferdigprodukt.getText().toString()));
            editor.putInt("ikkeferdig", Integer.parseInt(ikkeferdig.getText().toString()));
            editor.apply();
            Toast.makeText(this, "Moms lagret", Toast.LENGTH_SHORT).show();
        }
    }
}
