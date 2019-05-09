package com.julia.bachelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.SortedObjects;
import com.julia.bachelor.helperClass.Videresalg;

public class DetailsActivity extends Activity {
    private static final String KEY_OBJECT = "Object";
    private static final String KEY_BUNDLE = "Bundle";
    static SalgTemplate object;
    TextView total, betalingkroner, Kundenavn, solgteprodukter, navnetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salg_item);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        total = findViewById(R.id.total);
        Kundenavn = findViewById(R.id.kundenavn);
        navnetext = findViewById(R.id.navnetext);
        betalingkroner = findViewById(R.id.betalingkroner);
        solgteprodukter = findViewById(R.id.solgteprodukter);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        object = (SalgTemplate) bundle.getSerializable(KEY_OBJECT);
        String text;

        if(object != null) {

            text = Integer.toString(object.getBelop());
            total.setText(text);
            setVerdier(object.getVarer());
            if(object instanceof SortedObjects){
                SortedObjects sortedObjects = (SortedObjects) object;
                text = "Periode: " + sortedObjects.getDato();
                Kundenavn.setText(text);
                text = sortedObjects.getBetalings()[0] + "kr\n\n" + sortedObjects.getBetalings()[1] + "kr";
                betalingkroner.setText(text);

            }else {
                text = object.getKunde();
                Kundenavn.setText(text);
                text = object.getBetaling().equals("Kort") ? "0 kr \n \n" + object.getBelop() + " kr" : object.getBelop() + " kr \n \n0 kr";
                betalingkroner.setText(text);
            }
        }

        /*
        if (object instanceof BondensMarked) {
            BondensMarked bm = (BondensMarked) object;
            text = Integer.toString(bm.getBelop());
            total.setText(text);
            text = "Bondens Marked";
            Kundenavn.setText(text);
            setVerdier(bm.getVarer());
        } else if (object instanceof Hjemme) {
            Hjemme hm = (Hjemme) object;
            text = Integer.toString(hm.getBelop());
            total.setText(text);
            if (hm.getBetaling().equals("Kort")) {
                text = "0 kr \n \n" + hm.getBelop() + " kr";
                betalingkroner.setText(text);
            } else {
                text = hm.getBelop() + " kr \n \n0 kr";
                betalingkroner.setText(text);
            }
            Kundenavn.setText(hm.getKunde());
            setVerdier(hm.getVarer());
        } else if (object instanceof Videresalg) {
            Videresalg vi = (Videresalg) object;
            text = Integer.toString(vi.getBelop());
            total.setText(text);
            if (vi.getBetaling().equals("Kontant")) {
                text = vi.getBelop() + " kr \n \n0 kr";
                betalingkroner.setText(text);
            } else {
                text = "0 kr \n \n" + vi.getBelop() + " kr";
                betalingkroner.setText(text);
            }
            Kundenavn.setText(vi.getKunde());
            setVerdier(vi.getVarer());
        } else if (object instanceof Annet) {
            Annet an = (Annet) object;
            text = Integer.toString(an.getBelop());
            total.setText(text);
            if (an.getBetaling().equals("Kontant")) {
                text = an.getBelop() + " kr \n \n0 kr";
                betalingkroner.setText(text);
            } else {
                text = "0 kr \n \n" + an.getBelop() + " kr";
                betalingkroner.setText(text);
            }
            navnetext.setText("Bifolk\n\nVoks\n\nPollinering\n\nDronninger");
            setVerdier(an.getVarer());
        } else if (object instanceof SortedObjects) {
            SortedObjects sortedObjects = (SortedObjects) object;
            text = Integer.toString(sortedObjects.getBelop());
            total.setText(text);
            int[] betaling = sortedObjects.getBetalings();
            text = betaling[0] + "kr\n\n" + betaling[1] + "kr";
            betalingkroner.setText(text);
            text = "Periode: " + sortedObjects.getDato();
            Kundenavn.setText(text);
            setVerdier(sortedObjects.getVarer());
        } else {
            Toast.makeText(this, "Noe gikk galt", Toast.LENGTH_SHORT).show();
        }
        */
    }

    public void setVerdier(String verdilinje) {
        StringBuilder text = new StringBuilder();
        String[] verdier = verdilinje.split(",");
        for (String aVerdier : verdier) {
            String[] verd = aVerdier.split("-");
            text.append(verd[1]).append("\n\n");
        }
        solgteprodukter.setText(text.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    public void delete(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
        builder.setMessage("Vil virkelig slette dette salget?");
        builder.setCancelable(true);
        builder.setNegativeButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity main = new MainActivity();
                if(object instanceof SortedObjects)
                    Toast.makeText(DetailsActivity.this, "Kan ikke slette sortert Salg", Toast.LENGTH_SHORT).show();
                if(object instanceof Hjemme) {
                    Database.executeOnDB("http://www.honningbier.no/PHP/HjemmeDelete.php/?ID=" + object.get_ID());
                } else if(object instanceof Videresalg)
                    Database.executeOnDB("http://www.honningbier.no/PHP/VideresalgDelete.php/?ID=" + object.get_ID());
                else if(object instanceof BondensMarked)
                    Database.executeOnDB("http://www.honningbier.no/PHP/BondensMarkedDelete.php/?ID=" + object.get_ID());
                else if(object instanceof Annet)
                    Database.executeOnDB("http://www.honningbier.no/PHP/AnnetDelete.php/?ID=" + object.get_ID());
                main.fetch();
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
