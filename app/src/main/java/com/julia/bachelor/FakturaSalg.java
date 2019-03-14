package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakturaSalg extends Activity implements AdapterView.OnItemSelectedListener {
    static List<Honning> honningtyper;
    Spinner betaling;
    String betalingsmetode;
    Spinner moms;
    EditText navn, dato, som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende;
    List<EditText> verdier;
    ScrollView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktura_salg);
        Database.getHonningType();
        if(getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        navn = findViewById(R.id.FSnavn);
        moms = findViewById(R.id.FSmoms);
        dato = findViewById(R.id.FSdato);
        som1kg = findViewById(R.id.FSsom1kg);
        som05kg = findViewById(R.id.FSsom05kg);
        som025kg = findViewById(R.id.FSsom025kg);
        lyng1kg = findViewById(R.id.FSlyng1kg);
        lyng05kg = findViewById(R.id.FSlyng05kg);
        lyng025kg = findViewById(R.id.FSlyng025kg);
        ingf05kg = findViewById(R.id.FSingf05kg);
        ingf025kg = findViewById(R.id.FSingf025kg);
        flytende = findViewById(R.id.FSflyt);
        layout = findViewById(R.id.scroll);
        betaling = findViewById(R.id.FSbetalmet);
        verdier = new ArrayList<>(Arrays.asList(som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setBetalingsmetodespinner();
        setMomsspinner(sharedPreferences);

    }

    private void setBetalingsmetodespinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.betalingsmetode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        betaling.setAdapter(adapter);
        betaling.setOnItemSelectedListener(this);
    }

    private void setMomsspinner(SharedPreferences sh) {
        Integer[] momsarray = {sh.getInt("ferdigprodukt", 15), sh.getInt("ikkeferdig", 25)};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, momsarray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moms.setAdapter(adapter);
        moms.setOnItemSelectedListener(this);
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
                insertValues();
                Toast.makeText(this, "Videre salg lagret", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }

    private String getVarer() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < honningtyper.size(); i++) {
            stringBuilder.append(honningtyper.get(i).get_ID()).append("-").append(verdier.get(i).getText().toString()).append(",");
        }
        return stringBuilder.toString();
    }

    void insertValues() {
        Database.executeOnDB("http://www.honningbier.no/PHP/VideresalgIn.php/?Kunde=" + navn.getText().toString() +
                "&Dato=" + dato.getText().toString() +
                "&Varer=" + getVarer() + "&Belop=" + getbelop() + "&Betaling=" + betaling.getSelectedItem().toString() + "&Moms=" + moms.getSelectedItem().toString());
    }

    int getbelop() {
        int total = 0;
        for (int i = 0; i < verdier.size(); i++) {
            total += Integer.parseInt(verdier.get(i).getText().toString()) * honningtyper.get(i).getBondensMarkedPris();
        }
        return total;
    }

    @Override
    public void onBackPressed() {
        goback();
    }

    public void goback() {
        if (ValueInField()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(FakturaSalg.this);
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
        }else{
            finish();
        }
    }

    // TODO might move it to another class since this method is needed in several classes.
    public boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
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
    public boolean ValueInField(){
        for(EditText verdi : verdier){
            if(!(verdi.getText().toString().equals(""))){
                return true;
            }
        }
        return false;
    }
}
