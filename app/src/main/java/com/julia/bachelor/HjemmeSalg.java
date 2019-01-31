package com.julia.bachelor;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HjemmeSalg extends Activity {
    GridLayout varer;
    String text;
    List<Honning> honningtype;
    List<Integer> telling;
    TextView oversikt;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hjemme_salg);

    getActionBar().setDisplayHomeAsUpEnabled(true);

        honningtype = database.getHonningType();
        telling = new ArrayList<>();
        for(int c = 0; c < honningtype.size(); c++){
            telling.add(0);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return true;
    }
    public void add1Sommerkg(View view){
        telling.set(0, telling.get(0) +1);
        setText();
    }
    public void add05sommerkg(View view){
        telling.set(1,telling.get(1)+1);
        setText();
    }
    public void add025sommerkg(View view){
        telling.set(2,telling.get(2)+1);
        setText();
    }
    public void add1lyngkg(View view){
        telling.set(3,telling.get(3)+1);
        setText();
    }
    public void add05lyngkg(View view){
        telling.set(4,telling.get(4)+1);
        setText();
    }
    public void add025lyngkg(View view){
        telling.set(5,telling.get(5)+1);
        setText();
    }
    public void add05ingfkg(View view){
        telling.set(6,telling.get(6)+1);
        setText();
    }
    public void add025ingfkg(View view){
        telling.set(7,telling.get(7)+1);
        setText();
    }
    public void addflytende(View view){
        telling.set(8,telling.get(8)+1);
        setText();
    }
    //TODO hvordan kan vi legge til flere verdier/ slette honningtyper (dynamisk oppsett)
    // case, for dynamisk oppsett

    public void setText(){
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < telling.size(); i ++){
            if(!telling.get(i).equals(0)){
                sb.append(honningtype.get(i).getType() + "            x" + telling.get(i) +"\n");
            }
        }
    }
}
