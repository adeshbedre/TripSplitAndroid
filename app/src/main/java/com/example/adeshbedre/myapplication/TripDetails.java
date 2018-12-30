package com.example.adeshbedre.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TripDetails extends AppCompatActivity {

    public static final String MY_TAG = "custom";
    private RelativeLayout mLayout;
    private EditText mEditText;
    private Button mButton;
    private int count = 0;
    DatabaseHelper myDb;
    List<EditText> allEds = new ArrayList<EditText>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLayout = (RelativeLayout) findViewById(R.id.relative);
        Log.i(MY_TAG, "near");
        myDb = new DatabaseHelper(this);
        Log.i(MY_TAG, "here");
        //Get the bundle
        Bundle bundle = getIntent().getExtras();

//Extract the data…
       // String stuff = bundle.getString("TripName");
        //Log.i(MY_TAG, "The trip name is "+stuff);
        //mEditText = (EditText) findViewById(R.id.memberText2);
        //mButton = (Button) findViewById(R.id.button);
       // mButton.setOnClickListener(onClick());
        TextView textView = new TextView(this);
        textView.setText("New text");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClick());
    }


    public void insertIntoDb(View v) {
        Log.i(MY_TAG, "button click");

        EditText tripNameText = (EditText) findViewById(R.id.tripNameText);
        EditText memberText1 = (EditText) findViewById(R.id.memberText1);
        EditText memberText2 = (EditText) findViewById(R.id.memberText2);
        EditText memberText3 = (EditText) findViewById(R.id.memberText3);
        EditText memberText4 = (EditText) findViewById(R.id.memberText4);
        for(EditText ed : allEds)
        {
            Log.i(MY_TAG,"the ids names are "+ed.getText().toString());
        }
        myDb.insertData(allEds,tripNameText.toString());
      //  Log.i(MY_TAG, "button click" + tripNameText.getText().toString() + "     " + memberText1.getText().toString() + memberText2.getText().toString() + memberText3.getText().toString() + memberText4.getText().toString());
        //showAlert(v);

    }



    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout.addView(createNewTextView("dfsdf"));
            }
        };
    }


    private TextView createNewTextView(String text) {
        final Toolbar.LayoutParams lparams = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        final TextView textView = new TextView(this);
        final EditText editText = new EditText(this);
        allEds.add(editText);
        textView.setLayoutParams(lparams);
        editText.setLayoutParams(lparams);
        editText.setText("New text: " + count);
        textView.setText("Trip Member "+count);
        count++;
        return editText;
    }


}
