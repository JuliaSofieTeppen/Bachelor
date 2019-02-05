package com.julia.bachelor;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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

        getActionBar().setDisplayHomeAsUpEnabled(true);
        betaling = findViewById(R.id.sabetalingsmetode);
        AntBifolk = findViewById(R.id.antbifolk);
        AntVoks = findViewById(R.id.antvoks);
        AntPollenering = findViewById(R.id.antpollenering);
        AntDronninger = findViewById(R.id.antdronninger);
        KundeNavn = findViewById(R.id.sakundenavn);
        Dato = findViewById(R.id.sadato);
        Pris = findViewById(R.id.sapris);

        verdier = new ArrayList<>(Arrays.asList(AntBifolk,AntVoks,AntPollenering,AntDronninger));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.betalingsmetode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        betaling.setAdapter(adapter);
        betaling.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        betalingsmetode = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public boolean checkDate(String date){
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    public void SAlagre(View view){
        if(checkDate(Dato.getText().toString())){
            if(!(Pris.getText().toString().equals("") || KundeNavn.getText().toString().equals("")) ) {
                for (EditText verdi : verdier) {
                    if (verdi.getText().toString().equals("")) {
                        verdi.setText("0");
                    }
                }

                //TODO send alle verdier til databasen
                //  db.executeOnDB("www.honningbier.no/PHP/Beholdning.php/?");
                Toast.makeText(this, "Annet salg lagret", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Kundens navn eller Pris er ikke fyllt", Toast.LENGTH_SHORT).show();
            }
    }else{
        Toast.makeText(this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
    }
    }

}
