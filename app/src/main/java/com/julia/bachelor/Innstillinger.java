package com.julia.bachelor;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MenuItem;
import android.view.View;

public class Innstillinger extends Activity {
    ConstraintLayout som1kg;
    ConstraintLayout som05kg;
    ConstraintLayout som025kg;
    ConstraintLayout lyng1kg;
    ConstraintLayout lyng05kg;
    ConstraintLayout lyng025kg;
    ConstraintLayout Ingf05kg;
    ConstraintLayout Ingf025kg;
    ConstraintLayout flyt;
    ConstraintLayout endreverdier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_innstillinger);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        som1kg = findViewById(R.id.som1kg);
        som05kg = findViewById(R.id.som05kg);
        som025kg = findViewById(R.id.som025kg);
        lyng1kg = findViewById(R.id.lyng1kg);
        lyng05kg = findViewById(R.id.lyng05kg);
        lyng025kg = findViewById(R.id.lyng025kg);
        Ingf05kg = findViewById(R.id.ingf05kg);
        Ingf025kg = findViewById(R.id.ingf025kg);
        flyt = findViewById(R.id.flyt);
        endreverdier = findViewById(R.id.endreverdier);

        endreverdier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.bigextend);
                extend(exe);
            }
        });
        som1kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.extend);
                extend(exe);
            }
        });

        som05kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout exe = findViewById(R.id.extend2);
                extend(exe);
            }
        });
        som025kg.setOnClickListener(new View.OnClickListener() {
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
}
