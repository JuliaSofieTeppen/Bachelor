package com.julia.bachelor;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddBeholdning extends Activity {

    EditText dato;
    EditText som1kg;
    EditText som05kg;
    EditText som025kg;
    EditText lyng1kg;
    EditText lyng05kg;
    EditText lyng025kg;
    EditText ingf05kg;
    EditText ingf025kg;
    EditText flytende;
    List<EditText> verdier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beholdning);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Database.getHonningType();
        dato = findViewById(R.id.Bdato);
        som1kg = findViewById(R.id.Bsom1kg);
        som05kg = findViewById(R.id.Bsom05kg);
        som025kg = findViewById(R.id.Bsom025kg);
        lyng1kg = findViewById(R.id.Blyn1kg);
        lyng05kg = findViewById(R.id.Blyn05kg);
        lyng025kg = findViewById(R.id.Blyn025kg);
        ingf05kg = findViewById(R.id.Binf05kg);
        ingf025kg = findViewById(R.id.Binf025kg);
        flytende = findViewById(R.id.Bflyt);
        verdier = new ArrayList<>(Arrays.asList(som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    public void lagre(View v) {
        int tell = 0;
        if (Beregninger.checkDate(dato.getText().toString())) {
            for (EditText verdi : verdier) {
                if (verdi.getText().toString().equals("")) {
                    verdi.setText("0");
                } else {
                    tell++;
                }
            }
            if (tell == 0) {
                Toast.makeText(this, "Legg til minst et produkt", Toast.LENGTH_SHORT).show();
            } else {
                insertIntoDB();
                Toast.makeText(this, "Beholdning lagret", Toast.LENGTH_SHORT).show();
                finish();
            }

        } else {
            Toast.makeText(this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }
    String getBeholdning(){
        String[] strings = {"Sommer","SommerH","SommerK","Lyng","LyngH","LyngK","IngeferH","IngeferK","Flytende"};
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < strings.length; i++){
            sb.append(strings[i]).append("=").append(verdier.get(i).getText().toString()).append("&");
        }
        return sb.toString();
    }

    void insertIntoDB(){
        Database.executeOnDB("http://www.honningbier.no/PHP/SalgIn.php/?Dato=" + dato.getText().toString());
        Database.executeOnDB("http://www.honningbier.no/PHP/BeholdningIn.php/?" + getBeholdning() + "&Dato=" + dato.getText().toString());
    }
}
