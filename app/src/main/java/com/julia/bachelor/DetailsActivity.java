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
import com.julia.bachelor.helperClass.SortedObjects;
import com.julia.bachelor.helperClass.Videresalg;

public class DetailsActivity extends Activity {
    private static final String KEY_OBJECT = "Object";
    private static final String KEY_BUNDLE = "Bundle";
    static Object object;
    TextView total, betalingkroner, Kundenavn, solgteprodukter, navnetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salg_item);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);

        total = findViewById(R.id.total);
        betalingkroner = findViewById(R.id.betalingkroner);
        Kundenavn = findViewById(R.id.kundenavn);
        solgteprodukter = findViewById(R.id.solgteprodukter);
        navnetext = findViewById(R.id.navnetext);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        object = bundle.getSerializable(KEY_OBJECT);

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
            setHjemmesalgVerdier(hm.getVarer());
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
            int[] betaling = sortedObjects.getBetaling();
            text = Integer.toString(betaling[0]) + "kr\n\n" + Integer.toString(betaling[1]) + "kr";
            betalingkroner.setText(text);
            text = "Periode: " + sortedObjects.getDato();
            Kundenavn.setText(text);
        } else {
            Toast.makeText(this, "Noe gikk galt", Toast.LENGTH_SHORT).show();
        }
    }

    public void setVerdier(String verdilinje) {
        String text = "";
        String[] verdier = verdilinje.split(",");
        for (String aVerdier : verdier) {
            String[] verd = aVerdier.split("-");
            text += verd[1] + "\n\n";
        }
        solgteprodukter.setText(text);
    }

    public void setHjemmesalgVerdier(String verdilinje) {
        String[] verdier = verdilinje.split(",");
        int[] a = new int[9];
        int o = 0;
        String text = "";
        for (String aVerdier : verdier) {
            String[] s = verdier[o].split("-");
            a[Integer.parseInt(s[0]) - 1] += Integer.parseInt(s[1]);
            o++;
        }
        for (int anA : a) {
            text += Integer.toString(anA) + "\n\n";
        }
        solgteprodukter.setText(text);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }
}
