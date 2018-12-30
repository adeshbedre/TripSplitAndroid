package com.example.adeshbedre.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.widget.*;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;


public class TripNameMembersAdd extends AppCompatActivity {

    public static final String MY_TAG = "custom";
    private LinearLayout mLayout;
    List<EditText> allEds = new ArrayList<EditText>();
    private int count = 0;
    DatabaseHelper myDb;
    Dialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_name_members_add);
        myDb = new DatabaseHelper(this);
        mLayout = (LinearLayout) findViewById(R.id.memberLayout);
        Log.i(MY_TAG, "gettin clicked"+mLayout);
        Button button = (Button)findViewById(R.id.AddMember);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                addMemberTextBox();

            }
        } );
    }
    private void addMemberTextBox() {
        //createAlert();
        Log.i(MY_TAG, "gettin clicked");
        final Toolbar.LayoutParams lparams = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        final EditText editText = new EditText(this);
        allEds.add(editText);
        textView.setLayoutParams(lparams);
        editText.setLayoutParams(lparams);
        //editText.setText("New text: " + count);
        textView.setText("Trip Member "+count);
        count++;
        Log.i(MY_TAG, "returning something.. hopefully");
        mLayout.addView(textView);
        mLayout.addView(editText);

    }



    public void submit(View v) {
        Log.i(MY_TAG, "coming to submit event");

        EditText tripNameText = (EditText) findViewById(R.id.TripName);
       /* EditText memberText1 = (EditText) findViewById(R.id.memberText1);
        EditText memberText2 = (EditText) findViewById(R.id.memberText2);
        EditText memberText3 = (EditText) findViewById(R.id.memberText3);
        EditText memberText4 = (EditText) findViewById(R.id.memberText4);*/
      //  Log.i(MY_TAG,"the trip name is "+tripNameText.getText().toString());
        for(EditText ed : allEds)
        {
            Log.i(MY_TAG,"the ids names are "+ed.getText().toString());
        }
        //myDb.insertData("","","");
        //  Log.i(MY_TAG, "button click" + tripNameText.getText().toString() + "     " + memberText1.getText().toString() + memberText2.getText().toString() + memberText3.getText().toString() + memberText4.getText().toString());
        //showAlert(v);
        myDb.insertData(allEds,tripNameText.getText().toString());
        Intent intent;
        intent = new Intent(TripNameMembersAdd.this,HomeActivity.class);
        startActivity(intent);
    }

    public void createAlert()
    {
        View view = (LayoutInflater.from(TripNameMembersAdd.this)).inflate(R.layout.user_input,null);
        //spinner = (Spinner)findViewById(R.id.spinner);
        //Log.i(MY_TAG,"the value of the spinner is "+spinner);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TripNameMembersAdd.this);
        alertBuilder.setView(view);
        final EditText itemName = (EditText) view.findViewById(R.id.itemName);
        final EditText itemCost = (EditText) view.findViewById(R.id.itemCost);
        final Spinner memberName = (Spinner) view.findViewById(R.id.memberList);

        alertBuilder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int memberId = 0;
                        Log.i(MY_TAG,"the name of the item is "+itemName.getText());//memberList
                        Log.i(MY_TAG,"the cost id  is "+itemCost.getText());
                        Log.i(MY_TAG,"the member  is "+memberName.getSelectedItem().toString());
       /*                 for(Member member : memberList)
                        {
                            if(member.getName().equals(memberName.getSelectedItem().toString()))
                            {
                                memberId = member.getId();
                                break;
                            }
                        }
                        Log.i(MY_TAG,"the id of the member is "+memberId);//memberList
                        db.insertItemsBought(itemName.getText().toString(),itemCost.getText().toString(),memberId);
                        itemInfo.clear();
                        itemVOList = db.getItemsAndCost(tripId);
                        for(ItemsVO itemVo: itemVOList)
                        {
                            itemInfo.add(itemVo.getItemName()+"   "+itemVo.getCost()+"----->"+itemVo.getMemberName());
                        }
                        //itemInfo.add(itemInfo);
                        //itemInfo.add("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        arrayAdapter.notifyDataSetChanged();*/
                    }
                });


        dialog = alertBuilder.create();
        // setContentView(R.layout.user_input);
        dialog.show();
    }
}
