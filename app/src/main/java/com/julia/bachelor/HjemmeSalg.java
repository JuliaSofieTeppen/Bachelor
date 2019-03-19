package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HjemmeSalg extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_HONNING = "Honning";
    static ArrayList<Honning> honningtype;
    String betalingsmetode;
    List<Integer> telling;
    TextView oversikt, oversikttall, total;
    EditText kundenavn;
    Spinner betaling;
    int kr;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hjemme_salg);
        kr = 0;
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        oversikt = findViewById(R.id.oversikt);
        oversikttall = findViewById(R.id.oversikttall);
        betaling = findViewById(R.id.betalingsmetode);
        kundenavn = findViewById(R.id.kundenavn);
        total = findViewById(R.id.total);
        telling = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        honningtype = (ArrayList<Honning>) bundle.getSerializable(KEY_HONNING);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.betalingsmetode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        betaling.setAdapter(adapter);
        betaling.setOnItemSelectedListener(this);
        setTelling();
    }


    String getVarer() {
        StringBuilder varer = new StringBuilder();
        for (int i = 0; i < honningtype.size(); i++) {
            if (!telling.get(i).equals(0)) {
                varer.append(honningtype.get(i).get_ID()).append("-").append(telling.get(i)).append(",");
            }
        }
        return varer.toString();
    }

    String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        Date now = new Date();
        return dateFormat.format(now);
    }

    void insertValues() {
        Database.executeOnDB("http://www.honningbier.no/PHP/HjemmeIn.php/?Kunde=" + kundenavn.getText().toString() +
                "&Dato=" + getDate() + "&Varer=" + getVarer() + "&Belop=" + kr + "&Betaling=" + betalingsmetode);
    }

    void setTelling() {
        for (int c = 0; c < honningtype.size(); c++) {
            telling.add(0);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        betalingsmetode = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        goback();
        return true;
    }

    public void add1Sommerkg(View view) {
        telling.set(0, telling.get(0) + 1);
        kr = kr + honningtype.get(0).getHjemmePris();
        setText();
    }

    public void add05sommerkg(View view) {
        telling.set(1, telling.get(1) + 1);
        kr = kr + honningtype.get(1).getHjemmePris();
        setText();
    }

    public void add025sommerkg(View view) {
        telling.set(2, telling.get(2) + 1);
        kr = kr + honningtype.get(2).getHjemmePris();
        setText();
    }

    public void add1lyngkg(View view) {
        telling.set(3, telling.get(3) + 1);
        kr = kr + honningtype.get(3).getHjemmePris();
        setText();
    }

    public void add05lyngkg(View view) {
        telling.set(4, telling.get(4) + 1);
        kr = kr + honningtype.get(4).getHjemmePris();
        setText();
    }

    public void add025lyngkg(View view) {
        telling.set(5, telling.get(5) + 1);
        kr = kr + honningtype.get(5).getHjemmePris();
        setText();
    }

    public void add05ingfkg(View view) {
        telling.set(6, telling.get(6) + 1);
        kr = kr + honningtype.get(6).getHjemmePris();
        setText();
    }

    public void add025ingfkg(View view) {
        telling.set(7, telling.get(7) + 1);
        kr = kr + honningtype.get(7).getHjemmePris();
        setText();
    }

    public void addflytende(View view) {
        telling.set(8, telling.get(8) + 1);
        kr = kr + honningtype.get(8).getHjemmePris();
        setText();
    }

    public void setText() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbtall = new StringBuilder();
        for (int i = 0; i < honningtype.size(); i++) {
            if (!telling.get(i).equals(0)) {
                sb.append(honningtype.get(i).getType()).append("\n");
                sbtall.append("x").append(telling.get(i)).append("\n");
            }
        }
        oversikt.setText(sb.toString());
        oversikttall.setText(sbtall.toString());
        String text = kr + "kr";
        total.setText(text);
    }

    public void slettliste(View v) {
        for (int i = 0; i < telling.size(); i++) {
            telling.set(i, 0);
        }
        kr = 0;
        setText();
    }

    public void selg(View view) {
        if (kundenavn.getText().toString().equals("")) {
            Toast.makeText(this, "Skriv inn navn på kunde", Toast.LENGTH_SHORT).show();
        } else {
            if (!(oversikt.getText().toString().equals(""))) {
                // TODO update beholdning
                insertValues();
                Toast.makeText(this, "Hjemmesalg lagret", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Handlekurven er tom", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        goback();
    }

    public void goback() {
        if (ValueInField()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(HjemmeSalg.this);
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
        for (int i = 0; i < telling.size(); i++) {
            if (!(telling.get(i).equals(0))) {
                return true;
            }
        }
        return false;
    }
}
