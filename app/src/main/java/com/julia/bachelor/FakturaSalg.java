package com.julia.bachelor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakturaSalg extends Activity {

    Spinner Betalingmetode;
    EditText moms;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktura_salg);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        db = new Database();
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
        verdier = new ArrayList<>(Arrays.asList(moms,som1kg,som05kg,som025kg,lyng1kg,lyng05kg,lyng025kg,ingf05kg,ingf025kg,flytende));

    }
    public void lagre(View v){
        Toast.makeText(this, dato.getText().toString(), Toast.LENGTH_SHORT).show();
        if(checkDate(dato.getText().toString())){
            for(EditText verdi : verdier){
                if(verdi.getText().toString().equals("")){
                    verdi.setText("0");
                }
            }
            //TODO send alle verdier til databasen
            //  db.executeOnDB("www.honningbier.no/PHP/Beholdning.php/?");

        }else{
            Toast.makeText(this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed(){
        goback();
    }
    public void goback(){
        for(EditText verdi : verdier){
            if(!verdi.getText().toString().equals("")){
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation( , Gravity.CENTER, 0, 0);
            }
        }

    }



    public boolean checkDate(String date){
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
            goback();
        return true;
    }
}
