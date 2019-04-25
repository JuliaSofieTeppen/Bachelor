package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.julia.bachelor.helperClass.Honning;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VideresalgActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_HONNING = "Honning";
    static ArrayList<Honning> honningtyper;
    Spinner betaling;
    String betalingsmetode;
    Spinner moms;
    EditText navn, dato, som1kg, som05kg, som025kg, lyng1kg, lyng05kg, lyng025kg, ingf05kg, ingf025kg, flytende;
    List<EditText> verdier;
    ScrollView layout;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktura_salg);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
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

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        honningtyper = (ArrayList<Honning>) bundle.getSerializable(KEY_HONNING);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setBetalingsmetodespinner();
        setMomsspinner(sharedPreferences);

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        Date date = new Date();
        dato.setText(dateFormat.format(date));

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
        if (Beregninger.checkDate(dato.getText().toString())) {
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(VideresalgActivity.this);
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

    public boolean ValueInField() {
        for (EditText verdi : verdier) {
            if (!(verdi.getText().toString().equals(""))) {
                return true;
            }
        }
        return false;
    }
}
