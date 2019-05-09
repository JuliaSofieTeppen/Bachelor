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

import com.julia.bachelor.helperClass.Beholdning;
import com.julia.bachelor.helperClass.Honning;

import java.util.ArrayList;
import java.util.List;

public class HjemmesalgActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_HONNING = "Honning";
    private static final String KEY_BEHOLD = "Behold";
    private static final String LavBeholdningWarning = "Du prøver å selge ett større antall honning enn antall resterende i beholdningen.";
    static ArrayList<Honning> honningtype;
    static Beholdning behold;
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
        behold = (Beholdning) bundle.getSerializable(KEY_BEHOLD);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.betalingsmetode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        betaling.setAdapter(adapter);
        betaling.setOnItemSelectedListener(this);
        setTelling();
    }

    String getVarer() {
        StringBuilder varer = new StringBuilder();
        for (int i = 0; i < honningtype.size(); i++) {
            varer.append(honningtype.get(i).get_ID()).append("-").append(telling.get(i)).append(",");
        }
        return varer.toString();
    }

    void insertValues() {
        Database.executeOnDB("http://www.honningbier.no/PHP/HjemmeIn.php/?Kunde=" + kundenavn.getText().toString() +
                "&Dato=" + Tools.getDate() + "&Varer=" + getVarer() + "&Belop=" + kr + "&Betaling=" + betalingsmetode);
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
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getSommer() <= telling.get(0))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(0, telling.get(0) + 1);
            kr = kr + honningtype.get(0).getHjemmePris();
            setText();
        }
    }

    public void add05sommerkg(View view) {
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getSommerH() <= telling.get(1))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(1, telling.get(1) + 1);
            kr = kr + honningtype.get(1).getHjemmePris();
            setText();
        }
    }

    public void add025sommerkg(View view) {
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getSommerK() <= telling.get(2))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(2, telling.get(2) + 1);
            kr = kr + honningtype.get(2).getHjemmePris();
            setText();
        }
    }

    public void add1lyngkg(View view) {
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getLyng() <= telling.get(3))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(3, telling.get(3) + 1);
            kr = kr + honningtype.get(3).getHjemmePris();
            setText();
        }
    }

    public void add05lyngkg(View view) {
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getLyngH() <= telling.get(4))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(4, telling.get(4) + 1);
            kr = kr + honningtype.get(4).getHjemmePris();
            setText();
        }
    }

    public void add025lyngkg(View view) {
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getLyngK() <= telling.get(5))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(5, telling.get(5) + 1);
            kr = kr + honningtype.get(5).getHjemmePris();
            setText();
        }
    }

    public void add05ingfkg(View view) {
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getIngeferH() <= telling.get(6))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(6, telling.get(6) + 1);
            kr = kr + honningtype.get(6).getHjemmePris();
            setText();
        }
    }

    public void add025ingfkg(View view) {
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getIngeferK() <= telling.get(7))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(7, telling.get(7) + 1);
            kr = kr + honningtype.get(7).getHjemmePris();
            setText();
        }
    }

    public void addflytende(View view) {
        if (honningtype != null) {
            if (honningtype.size() != 9) return;
            if(behold.getFlytende() <= telling.get(8))
                Toast.makeText(this,LavBeholdningWarning, Toast.LENGTH_LONG ).show();
            telling.set(8, telling.get(8) + 1);
            kr = kr + honningtype.get(8).getHjemmePris();
            setText();
        }
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(HjemmesalgActivity.this);
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
