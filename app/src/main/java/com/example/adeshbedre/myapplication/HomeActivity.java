package com.example.adeshbedre.myapplication;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import android.view.LayoutInflater;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import java.util.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity{
    public static final String MY_TAG = "custorm";
    public DatabaseHelper db = null;
    List<TripNameId> tripNameidLi = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);
        Log.i(MY_TAG,"in create");
        Cursor res = db.getAllData();//Trips
        StringBuffer buffer = new StringBuffer();
        List<String> tripList = new ArrayList<String>();
        tripNameidLi = new ArrayList<TripNameId>();
        Log.i(MY_TAG,"the buffer is  "+res.toString());
        while (res.moveToNext()) {
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("TRIPNAME :"+ res.getString(1)+"\n");
//            buffer.append("MEMBERNAME :"+ res.getString(2)+"\n");

            TripNameId tripNameId = new TripNameId();
            tripNameId.setTripId(res.getString(0));
            tripNameId.setTripName(res.getString(1));
            tripNameidLi.add(tripNameId);

            String var = res.getString(1);
            tripList.add(var);
            Log.i(MY_TAG,"the res is  "+buffer.toString()+"\n\n\n");
        }
        if( tripList.size() == 0)
        {
            ListView lv = (ListView) findViewById(R.id.Trips);
            tripList.add("NO TRIPS AVALABLE... PLEASE CLICK ON + BUTTON TO ADD NEW TRIPS");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    R.layout.custom_text_view,
                    tripList );

            lv.setAdapter(arrayAdapter);
        }
        else
        {
            ListView lv = (ListView) findViewById(R.id.Trips);
            //Log.i(MY_TAG,"the trip is   "+lv.toString());
            for(String s : tripList)
            {
                Log.i(MY_TAG,"s is "+s);
            }
            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    R.layout.custom_text_view,
                    tripList );

            lv.setAdapter(arrayAdapter);
            lv.setOnItemClickListener(new OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.i(MY_TAG,"comin inside this onclick thing ");
                    TripNameId item = (TripNameId) tripNameidLi.get(i);

                    //  String selectedItem = item.get("Id").toString();
                    Log.i(MY_TAG,"yyyyyy "+item.getTripName());
                    Log.i(MY_TAG,"yyyyyy "+item.getTripId());

                    Intent intent;
                    intent = new Intent(HomeActivity.this,AddItemsBought.class);
                    Bundle bundle = new Bundle();

//Add your data to bundle
                    bundle.putString("TripName", item.getTripName());
                    bundle.putString("TripId", item.getTripId());
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
        }


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xml.
        */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonCheck(View v) {
        Log.i(MY_TAG,"button click");
        // db.getTripMembers("Fine");
        Intent intent;
        intent = new Intent(HomeActivity.this,TripNameMembersAdd.class);

        startActivity(intent);
        //showAlert(v);

    }



  /*  public void showAlert(View v)
    {
        View view = (LayoutInflater.from(HomeActivity.this)).inflate(R.layout.user_input,null);
        Log.i(MY_TAG,"in the show alreat method");
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.user_input);
        alertBuilder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(MY_TAG,"the written value is "+userInput.getText());
                    }
                });
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }*/
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Home Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }



}
