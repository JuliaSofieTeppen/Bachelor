package com.julia.bachelor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
            text = Integer.toString(betaling[0]) + "kr\n\n" + Integer.toString(betaling[1]) + "kr";
            betalingkroner.setText(text);
            text = "Periode: " + sortedObjects.getDato();
            Kundenavn.setText(text);
            setVerdier(sortedObjects.getVarer());
        } else {
            Toast.makeText(this, "Noe gikk galt", Toast.LENGTH_SHORT).show();
        }
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
}
