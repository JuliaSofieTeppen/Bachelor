package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BmSalg extends Activity {

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
    Database db;
    static List<Honning> honningtyper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm_salg);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        db = new Database();
        dato = findViewById(R.id.BMSdato);
        som1kg = findViewById(R.id.BMSsom1kg);
        som05kg = findViewById(R.id.BMSsom05kg);
        som025kg = findViewById(R.id.BMSsom025kg);
        lyng1kg = findViewById(R.id.kundenavn);
        lyng05kg = findViewById(R.id.BMSlyn05kg);
        lyng025kg = findViewById(R.id.BMSlyn025kg);
        ingf05kg = findViewById(R.id.BMSingf05kg);
        ingf025kg = findViewById(R.id.BMSingf025kg);
        flytende = findViewById(R.id.BMSflyt);
        verdier = new ArrayList<>(Arrays.asList(som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende));
        db.getHonningType();
    }


    public void lagre(View v) {
        int tell = 0;
        if (checkDate(dato.getText().toString())) {
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
                //TODO legg til verdier

                insertValues();

                Toast.makeText(this, "Bondens marked salg lagret", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    public boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    @Override
    public void onBackPressed() {
        goback();
    }

    public void goback() {
        //TODO check fields before poppopen skal syntes.
        //android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FakturaSalg.this,R.style.AlertDialog);
        final AlertDialog.Builder builder = new AlertDialog.Builder(BmSalg.this);
        builder.setMessage("Vil du gå tilbake?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    void setHonningtyper(ArrayList<Honning> type){
        honningtyper = type;
    }

    public String getVarer() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < honningtyper.size(); i++) {
            stringBuilder.append(honningtyper.get(i).get_ID()).append("-").append(verdier.get(i).getText().toString()).append(",");
        }
        return stringBuilder.toString();
    }

    void insertValues() {
        db.executeOnDB("http://www.honningbier.no/PHP/BondensMarkedIn.php/?Dato=" + dato.getText().toString() +
                "&Varer=" + getVarer() + "&Belop=" + getbelop());
    }

    int getbelop(){
        int total=0;
        for(int i = 0; i < verdier.size(); i++){
            total += Integer.parseInt(verdier.get(i).getText().toString()) * honningtyper.get(i).getBondensMarkedPris();
        }
        return total;
    }
}
