package com.julia.bachelor;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HjemmeSalg extends Activity implements AdapterView.OnItemSelectedListener {
    String betalingsmetode;
    List<Integer> telling;
    TextView oversikt;
    EditText kundenavn;
    TextView oversikttall;
    Database database;
    TextView total;
    Spinner betaling;
    int kr = 0;
    static List<Honning> honningtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hjemme_salg);
        database = new Database();
        database.getHonningType();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        oversikt = findViewById(R.id.oversikt);
        oversikttall = findViewById(R.id.oversikttall);
        betaling = findViewById(R.id.betalingsmetode);
        kundenavn = findViewById(R.id.kundenavn);
        total = findViewById(R.id.total);
        telling = new ArrayList<>();
        setTelling();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.betalingsmetode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        betaling.setAdapter(adapter);
        betaling.setOnItemSelectedListener(this);
    }

    public String reverseDate(String date){
        String[] strings = date.split("\\.");
        date="";
        for(int i=strings.length-1;i>=0;i--){
            date += strings[i];
            date += i >= 1 ? "." : "";
        }
        return date;
    }

    void setTelling(){
        for(int c = 0; c < 9; c++){
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
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return true;
    }
    public void add1Sommerkg(View view){
        telling.set(0, telling.get(0) +1);
        kr = kr + honningtype.get(0).getHjemmePris();
        setText();
    }
    public void add05sommerkg(View view){
        telling.set(1,telling.get(1)+1);
        kr = kr + honningtype.get(1).getHjemmePris();
        setText();
    }
    public void add025sommerkg(View view){
        telling.set(2,telling.get(2)+1);
        kr = kr + honningtype.get(2).getHjemmePris();
        setText();
    }
    public void add1lyngkg(View view){
        telling.set(3,telling.get(3)+1);
        kr = kr + honningtype.get(3).getHjemmePris();
        setText();
    }
    public void add05lyngkg(View view){
        telling.set(4,telling.get(4)+1);
        kr = kr + honningtype.get(4).getHjemmePris();
        setText();
    }
    public void add025lyngkg(View view){
        telling.set(5,telling.get(5)+1);
        kr = kr + honningtype.get(5).getHjemmePris();
        setText();
    }
    public void add05ingfkg(View view){
        telling.set(6,telling.get(6)+1);
        kr = kr + honningtype.get(6).getHjemmePris();
        setText();
    }
    public void add025ingfkg(View view){
        telling.set(7,telling.get(7)+1);
        kr = kr + honningtype.get(7).getHjemmePris();
        setText();
    }
    public void addflytende(View view){
        telling.set(8,telling.get(8)+1);
        kr = kr + honningtype.get(8).getHjemmePris();
        setText();
    }
    //TODO hvordan kan vi legge til flere verdier/ slette honningtyper (dynamisk oppsett)
    // case, for dynamisk oppsett

    public void setText(){
        StringBuilder sb = new StringBuilder();
        StringBuilder sbtall = new StringBuilder();
        for( int i = 0; i < honningtype.size(); i ++){
            if(!telling.get(i).equals(0)){
                sb.append(honningtype.get(i).getType() + "\n");
                sbtall.append("x" + telling.get(i) +"\n");
            }
        }
        oversikt.setText(sb.toString());
        oversikttall.setText(sbtall.toString());
        total.setText(kr + "kr");
    }
    public void setArrays(ArrayList<Honning> type){
        honningtype = type;
    }
    public void slettliste(View v){
        for(int i = 0; i < telling.size(); i++){
            telling.set(i,0);
        }
        setText();
    }

    public void selg(View view){
        if(kundenavn.getText().toString() == ""){
            Toast.makeText(this,"Skriv inn navn på kunde", Toast.LENGTH_SHORT).show();
        }else{
            kundenavn.getText();
        }

    }

}
