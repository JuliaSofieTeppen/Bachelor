package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.julia.bachelor.helperClass.Beholdning;
import com.julia.bachelor.helperClass.Honning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BmSalgActivity extends Activity {
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_HONNING = "Honning";
    private static final String KEY_BEHOLD = "Behold";
    static List<Honning> honningtyper;
    static Beholdning behold;
    EditText dato, som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende;
    List<EditText> verdier;

    @Override @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm_salg);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
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
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        try {
            behold = (Beholdning) bundle.getSerializable(KEY_BEHOLD);
            honningtyper = (ArrayList<Honning>) bundle.getSerializable(KEY_HONNING);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        dato.setText(Tools.getDate());
    }

    public void lagre(View v) {
        int tell = 0;
        if (Tools.checkDate(dato.getText().toString())) {
            for (EditText verdi : verdier) {
                if (verdi.getText().toString().equals("") || verdi.getText().toString().equals("0")) {
                    verdi.setText("0");

                } else {
                    tell++;
                }
            }
            if (tell == 0) {
                Toast.makeText(this, "Legg til minst et produkt", Toast.LENGTH_SHORT).show();
            } else {
                if(!checkbehold()){ addAlert();
                }else {
                    insertValues();
                    Toast.makeText(this, "Bondens marked salg lagret", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        } else {
            Toast.makeText(this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        goback();
        return true;
    }

    @Override
    public void onBackPressed() {
        goback();
    }

    public void goback() {
        if (ValueInField()) {
            //android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(VideresalgActivity.this,R.style.AlertDialog);
            final AlertDialog.Builder builder = new AlertDialog.Builder(BmSalgActivity.this);
            builder.setMessage("Vil du gå tilbake?");
            builder.setCancelable(true);
            builder.setNegativeButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("Nei", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            finish();
        }
    }

    public void addAlert(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(BmSalgActivity.this);
        builder.setMessage("Du prøver å selge ett større antall honning enn antall resterende i beholdningen. \n Vil du fortsette?");
        builder.setCancelable(true);
        builder.setNegativeButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertValues();
                Toast.makeText(BmSalgActivity.this, "Bondens marked salg lagret", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setPositiveButton("Nei", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean ValueInField() {
        for (EditText verdi : verdier) {
            if (!(verdi.getText().toString().equals(""))) {
                return true;
            }
        }
        return false;
    }

    public String getVarer() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < honningtyper.size(); i++) {
            if (i == verdier.size()) break;
            stringBuilder.append(honningtyper.get(i).get_ID()).append("-").append(verdier.get(i).getText().toString()).append(",");
        }
        return stringBuilder.toString();
    }
    public boolean checkbehold(){
        if (checkValue(behold.getSommer(),Integer.parseInt(verdier.get(0).getText().toString()))) return false;
        if (checkValue(behold.getSommerH(),Integer.parseInt(verdier.get(1).getText().toString()))) return false;
        if (checkValue(behold.getSommerK(),Integer.parseInt(verdier.get(2).getText().toString()))) return false;
        if (checkValue(behold.getLyng(),Integer.parseInt(verdier.get(3).getText().toString()))) return false;
        if (checkValue(behold.getLyngH(),Integer.parseInt(verdier.get(4).getText().toString()))) return false;
        if (checkValue(behold.getLyngK(),Integer.parseInt(verdier.get(5).getText().toString()))) return false;
        if (checkValue(behold.getIngeferH(),Integer.parseInt(verdier.get(6).getText().toString()))) return false;
        if (checkValue(behold.getIngeferK(),Integer.parseInt(verdier.get(7).getText().toString()))) return false;
        return !checkValue(behold.getFlytende(), Integer.parseInt(verdier.get(8).getText().toString()));
    }
    public boolean checkValue(int behold, int verdi){
        if(verdi == 0){
            return false;
        }
        return behold < verdi;
    }
    void insertValues() {
        Database.executeOnDB("http://www.honningbier.no/PHP/BondensMarkedIn.php/?Dato=" + dato.getText().toString() +
                "&Varer=" + getVarer() + "&Belop=" + getbelop());
    }

    int getbelop() {
        int total = 0;
        for (int i = 0; i < verdier.size(); i++) {
            total += Integer.parseInt(verdier.get(i).getText().toString()) * honningtyper.get(i).getBondensMarkedPris();
        }
        return total;
    }
}