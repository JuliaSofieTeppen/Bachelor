package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.julia.bachelor.helperClass.Beholdning;

public class BeholdningItemActivity extends Activity {
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_BEHOLDOBJECT = "BeholdObject";
    Beholdning beholdning;
    TextView dato, verdier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beholdning_item);
        //makes return button in application
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);

        dato = findViewById(R.id.beholddato);
        verdier = findViewById(R.id.beholdverdier);

        //gets values from previous activity
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        beholdning = (Beholdning) bundle.getSerializable(KEY_BEHOLDOBJECT);

        //inserts values and date into Textview
        if (beholdning != null) dato.setText(beholdning.getDato());
        verdier.setText(addVerdier());
    }

    //returns all honey values from beholdning
    public String addVerdier() {
        return beholdning.getSommer() + "\n\n" + beholdning.getSommerH() + "\n\n" + beholdning.getSommerK() + "\n\n"
                + beholdning.getLyng() + "\n\n" + beholdning.getLyngH() + "\n\n" + beholdning.getLyngK() + "\n\n" +
                beholdning.getIngeferH() + "\n\n" + beholdning.getIngeferK() + "\n\n" + beholdning.getFlytende();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    //makes popup when click on delete button
    public void delete(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BeholdningItemActivity.this);
        builder.setMessage("Vil virkelig slette denne beholdningen?");
        builder.setCancelable(true);
        builder.setNegativeButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //when clicked "ja" on popup, value is deleted from database
                Database.executeOnDB("http://www.honningbier.no/PHP/BeholdningDelete.php/?ID=" + beholdning.get_ID());
                finish();
            }
        });
        //when clicked "nei" return back to previous screen
        builder.setPositiveButton("Nei", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
