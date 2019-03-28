package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SalgAnnet extends Activity implements AdapterView.OnItemSelectedListener {
    String betalingsmetode;
    Spinner betaling;
    EditText AntBifolk;
    EditText AntVoks;
    EditText AntPollenering;
    EditText AntDronninger;
    EditText KundeNavn;
    EditText Dato;
    EditText Pris;
    ArrayList<EditText> verdier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salg_annet);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        betaling = findViewById(R.id.sabetalingsmetode);
        AntBifolk = findViewById(R.id.antbifolk);
        AntVoks = findViewById(R.id.antvoks);
        AntPollenering = findViewById(R.id.antpollenering);
        AntDronninger = findViewById(R.id.antdronninger);
        KundeNavn = findViewById(R.id.sakundenavn);
        Dato = findViewById(R.id.sadato);
        Pris = findViewById(R.id.sapris);

        verdier = new ArrayList<>(Arrays.asList(AntBifolk, AntVoks, AntPollenering, AntDronninger));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.betalingsmetode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        betaling.setAdapter(adapter);
        betaling.setOnItemSelectedListener(this);

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();
        Dato.setText(dateFormat.format(date));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        goback();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        betalingsmetode = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    public void SAlagre(View view) {
        int tell = 0;
        if (checkDate(Dato.getText().toString())) {
            if (!(Pris.getText().toString().equals("") || KundeNavn.getText().toString().equals(""))) {
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
                    Database.executeOnDB("http://www.honningbier.no/PHP/AnnetIn.php/?Kunde="+KundeNavn.getText().toString()+
                            "&Dato=" + Dato.getText().toString() + "&Varer=" + getVarer() + "&Belop=" + Pris.getText().toString()+
                            "&Betaling=" + betaling.getSelectedItem().toString());
                    Toast.makeText(this, "Annet salg lagret", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(this, "Kundens navn eller Pris er ikke fyllt", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }

    public String getVarer() {
        String[] names = {"Bifolk","Voks","Pollinering","Dronninger"};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < verdier.size(); i++) {
            if (verdier.get(i).getText().toString().equals("")) {
            }else{
                stringBuilder.append(names[i] + "-" + verdier.get(i).getText().toString()+",");
            }
        }
        return stringBuilder.toString();
    }


    @Override
    public void onBackPressed() {
        goback();
    }

    public void goback() {
        if (ValueInField()) {
            //android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FakturaSalg.this,R.style.AlertDialog);
            final AlertDialog.Builder builder = new AlertDialog.Builder(SalgAnnet.this);
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

    public boolean ValueInField() {
        for (EditText verdi : verdier) {
            if (!(verdi.getText().toString().equals(""))) {
                return true;
            }
        }
        return false;
    }


}
