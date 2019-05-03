package com.julia.bachelor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.julia.bachelor.helperClass.Beholdning;
import com.julia.bachelor.helperClass.BeholdningTemplate;
import com.julia.bachelor.helperClass.SalgTemplate;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RapportBeholdning extends Activity {
    private static final String KEY_BEHOLD = "Behold";
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_BEHOLDOBJECT = "BeholdObject";
    ListView listView;
    ArrayList<String> beholdninglist;
    ArrayList<Beholdning> beholdningArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_beholdning);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        beholdningArrayList = (ArrayList<Beholdning>) bundle.getSerializable(KEY_BEHOLD);
        beholdninglist = new ArrayList<>();
        listView = findViewById(R.id.beholdlist);

        makelist();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, beholdninglist);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_BEHOLDOBJECT, beholdningArrayList.get(position));
                Intent myIntent = new Intent(RapportBeholdning.this, BeholdningItem.class);
                myIntent.putExtra(KEY_BUNDLE, bundle);
                startActivity(myIntent);
            }
        });
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
