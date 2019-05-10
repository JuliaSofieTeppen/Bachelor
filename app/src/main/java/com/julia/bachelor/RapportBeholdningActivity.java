package com.julia.bachelor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.julia.bachelor.helperClass.Beholdning;

import java.util.ArrayList;

public class RapportBeholdningActivity extends Activity {
    private static final String KEY_BEHOLD = "Behold";
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_BEHOLDOBJECT = "BeholdObject";
    ListView listView;
    ArrayList<String> beholdninglist;
    ArrayList<Beholdning> beholdningArrayList;

    @Override @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_beholdning);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
            beholdningArrayList = (ArrayList<Beholdning>) bundle.getSerializable(KEY_BEHOLD);
            beholdninglist = new ArrayList<>();
            listView = findViewById(R.id.beholdlist);

            makelist();

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, beholdninglist);
            listView.setAdapter(arrayAdapter);

            //when clicked on an item in the listener, open new activity
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_BEHOLDOBJECT, beholdningArrayList.get(position));
                    Intent myIntent = new Intent(RapportBeholdningActivity.this, BeholdningItemActivity.class);
                    myIntent.putExtra(KEY_BUNDLE, bundle);
                    startActivity(myIntent);
                }
            });
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    void makelist(){
        StringBuilder sb = new StringBuilder();
        beholdninglist.clear();
        for (Beholdning beholdning : beholdningArrayList) {
            sb.append(beholdning.getDato()).append("   ");
            beholdninglist.add(sb.toString());
            sb.delete(0, sb.length());
        }
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
}
