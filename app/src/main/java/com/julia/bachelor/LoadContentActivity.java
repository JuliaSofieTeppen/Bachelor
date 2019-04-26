package com.julia.bachelor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class LoadContentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_content);
        if(getActionBar()!=null) getActionBar().hide();
        ImageView bee = findViewById(R.id.LoadingBeeImage);
        bee.setBackgroundResource(R.drawable.animation);
        AnimationDrawable anim = (AnimationDrawable) bee.getBackground();
        anim.start();
        // Flying bee ....
        //  \ / />/>
        // (^_^)( || ||)>
        //   /// \\\

        MainActivity main = new MainActivity();
        if (!MainActivity.AllSalg.isEmpty()) {
            MainActivity.AllSalg.clear();
            MainActivity.Beholdning.clear();
            MainActivity.Honning.clear();
        }
        main.fetch();
        Intent intent = new Intent(LoadContentActivity.this, MainActivity.class);
        LoadContentActivity.this.startActivity(intent);
        finish();
    }
}
