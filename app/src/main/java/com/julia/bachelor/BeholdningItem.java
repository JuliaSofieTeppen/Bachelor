package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.julia.bachelor.helperClass.Beholdning;

public class BeholdningItem extends Activity {
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_BEHOLDOBJECT = "BeholdObject";
    Beholdning beholdning;
    TextView dato, verdier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beholdning_item);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);

        dato = findViewById(R.id.beholddato);
        verdier = findViewById(R.id.beholdverdier);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        beholdning = (Beholdning) bundle.getSerializable(KEY_BEHOLDOBJECT);

        dato.setText(beholdning.getDato());
        verdier.setText(addVerdier());

    }

    public String addVerdier(){
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

    public void delete(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(BeholdningItem.this);
        builder.setMessage("Vil virkelig slette denne beholdningen?");
        builder.setCancelable(true);
        builder.setNegativeButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity main = new MainActivity();
                Database database = new Database();
                database.executeOnDB("http://www.honningbier.no/PHP/BeholdningDelete.php/?ID=" + beholdning.get_ID());
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
    }
}
