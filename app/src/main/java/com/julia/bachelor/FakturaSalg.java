package com.julia.bachelor;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class FakturaSalg extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktura_salg);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
            this.finish();
        return true;
    }
}
