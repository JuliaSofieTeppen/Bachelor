package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
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
    ScrollView layout;

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
        layout = findViewById(R.id.scroll);
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
        //TODO check fields before poppopen skal syntes.
                //android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FakturaSalg.this,R.style.AlertDialog);
                final AlertDialog.Builder builder = new AlertDialog.Builder(FakturaSalg.this);
                builder.setMessage("Vil du gå tilbake?");
                builder.setCancelable(true);
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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
