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
import com.julia.bachelor.helperClass.Videresalg;

public class SalgItem extends Activity {
    private static final String KEY_OBJECT = "Object";
    private static final String KEY_BUNDLE = "Bundle";
    static Object object;
    TextView total,betalingkroner,Kundenavn,solgteprodukter,navnetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salg_item);
        if(getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);

        total = findViewById(R.id.total);
        betalingkroner = findViewById(R.id.betalingkroner);
        Kundenavn = findViewById(R.id.kundenavn);
        solgteprodukter = findViewById(R.id.solgteprodukter);
        navnetext = findViewById(R.id.navnetext);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        object = bundle.getSerializable(KEY_OBJECT);


        if (object instanceof BondensMarked) {
           BondensMarked bm = (BondensMarked)object;
           total.setText(Integer.toString(bm.getBelop()));
           Kundenavn.setText("Bondens Marked");
           setVerdier(bm.getVarer());
        } else if (object instanceof Hjemme) {
            Hjemme hm = (Hjemme) object;
            total.setText(Integer.toString(hm.getBelop()));
            if(hm.getBetaling().equals("Kort")){
                betalingkroner.setText("0 kr \n \n" + hm.getBelop()+" kr");
            }else{
                betalingkroner.setText(hm.getBelop()+" kr \n \n0 kr");
            }
            Kundenavn.setText(hm.getKunde());
            setHjemmesalgVerdier(hm.getVarer());
        } else if (object instanceof Videresalg) {
            Videresalg vi = (Videresalg) object;
            total.setText(Integer.toString(vi.getBelop()));
            if(vi.getBetaling().equals("Kontant")){
                betalingkroner.setText(vi.getBelop()+" kr \n \n0 kr");
            }else{
                betalingkroner.setText("0 kr \n \n" + vi.getBelop()+" kr");
            }
            Kundenavn.setText(vi.getKunde());
            setVerdier(vi.getVarer());
        } else if (object instanceof Annet) {
            Annet an = (Annet)object;
            total.setText(Integer.toString(an.getBelop()));
            if(an.getBetaling().equals("Kontant")){
                betalingkroner.setText(an.getBelop()+" kr \n \n0 kr");
            }else{
                betalingkroner.setText("0 kr \n \n" + an.getBelop()+" kr");
            }
            navnetext.setText("Bifolk\n\nVoks\n\nPollinering\n\nDronninger");
            setVerdier(an.getVarer());
        } else {
            Toast.makeText(this,"Noe gikk galt", Toast.LENGTH_SHORT).show();
        }
    }

    public void setVerdier(String verdilinje){
        String text = "";
        String[] verdier = verdilinje.split(",");
        for(int i = 0; i < verdier.length; i++){
            String[] verd = verdier[i].split("-");
            text += verd[1] + "\n\n";
        }
        solgteprodukter.setText(text);
    }
    public void setHjemmesalgVerdier(String verdilinje){
        String[] verdier = verdilinje.split(",");
        int[]a = new int[9];
        int o = 0;
        String text = "";
        for(int i = 0; i < verdier.length; i++){
            String[] s = verdier[o].split("-");
            a[Integer.parseInt(s[0])-1] += Integer.parseInt(s[1]);
            o++;
        }
        for(int i = 0; i < a.length; i++){
            text += Integer.toString(a[i]) + "\n\n";
        }
        solgteprodukter.setText(text);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }
}
