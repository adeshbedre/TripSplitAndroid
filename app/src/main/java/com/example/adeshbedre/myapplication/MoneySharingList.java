package com.example.adeshbedre.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoneySharingList extends AppCompatActivity {
    public static final String MY_TAG = "custorm";
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_sharing_list);
        final Toolbar.LayoutParams lparams = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        mLayout = (LinearLayout) findViewById(R.id.linear);

        if (getIntent().getExtras() != null) {
            for(String a : getIntent().getExtras().getStringArrayList("list")) {
                Log.i("=======","Data " + a);
                TextView textView = new TextView(this);
                textView.setText(a);
                textView.setLayoutParams(lparams);
                Log.i(MY_TAG, "returning something.. hopefully");
                mLayout.addView(textView);
            }
        }

    }
}
