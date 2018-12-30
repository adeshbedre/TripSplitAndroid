package com.example.adeshbedre.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddItemsBought extends AppCompatActivity {
    public static final String MY_TAG = "custorm";
    public DatabaseHelper db = null;
    public Spinner spinner = null;
    ArrayList<Member> memberList = null;
    ArrayList<String> memberNames = null;
    ArrayAdapter<String> arrayAdapter = null;
    ArrayAdapter<String> spinnerArrayAdapter = null;
    Dialog dialog = null;
    Dialog modifyDialog = null;
    ListView lv = null;
    String tripName = "";
    String tripId = "";
    ArrayList<String> itemInfo = null;
    ArrayList<ItemsVO> itemVOList = null;
    String itemIdModify = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        memberNames = new ArrayList<String>();
        db = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items_bought);
        Intent intent = getIntent();
        tripName = intent.getStringExtra("TripName");
        tripId = intent.getStringExtra("TripId");
        Log.i(MY_TAG,"The name of the trip is "+tripName);
        Log.i(MY_TAG,"The name of the trip is "+tripId);
        memberList = db.getTripMembers(tripId);
        for(Member a : memberList)
        {
            Log.i(MY_TAG,a.getName()+"\n");
            memberNames.add(a.getName());
        }
        createAlert();
        createModifyAlert();
        lv = (ListView) findViewById(R.id.Trips);

        itemInfo = new ArrayList<String>();
        itemVOList = db.getItemsAndCost(tripId);

        if(itemVOList.size() == 0)
        {
            itemInfo.add("Please add new items to the list");
        }
        else
        {
            for(ItemsVO itemVo: itemVOList)
            {
                itemInfo.add(itemVo.getItemName()+"   "+itemVo.getCost()+"----->"+itemVo.getMemberName());
            }
        }

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                itemInfo );



        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.i(MY_TAG,"comin inside this onclick thing ");
                String item = (String) adapterView.getItemAtPosition(i);
                //  String selectedItem = item.get("Id").toString();
                Log.i(MY_TAG,"the position is  "+i);
                Log.i(MY_TAG,"the item id is  "+itemVOList.get(i).getItemName()+"     "+itemVOList.get(i).getItemId());
                itemIdModify = itemVOList.get(i).getItemId()+"";

                showAlertModify(itemVOList.get(i).getItemName(),itemVOList.get(i).getCost(),itemVOList.get(i).getMemberName(),itemVOList.get(i).getItemId(),view);
            }
        });
    }


    public void createAlert()
    {
        final View view = (LayoutInflater.from(AddItemsBought.this)).inflate(R.layout.user_input,null);
        //spinner = (Spinner)findViewById(R.id.spinner);
        //Log.i(MY_TAG,"the value of the spinner is "+spinner);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddItemsBought.this);
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
                        for(Member member : memberList)
                        {
                            if(member.getName().equals(memberName.getSelectedItem().toString()))
                            {
                                memberId = member.getId();
                                break;
                            }
                        }
                        Log.i(MY_TAG,"the id of the member is "+memberId);//memberList
                        db.insertItemsBought(itemName.getText().toString(),itemCost.getText().toString(),memberId);
                        synchronized (this)
                        {
                            itemInfo.clear();
//                            arrayAdapter.clear();
                            itemVOList = db.getItemsAndCost(tripId);
                            for(ItemsVO itemVo: itemVOList)
                            {
                                itemInfo.add(itemVo.getItemName()+"   "+itemVo.getCost()+"----->"+itemVo.getMemberName());
                            }
                            arrayAdapter.notifyDataSetChanged();

                        }

                        //itemInfo.add(itemInfo);
                        //itemInfo.add("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                        view.requestLayout();
                    }
                });


        dialog = alertBuilder.create();
        // setContentView(R.layout.user_input);
        dialog.hide();
    }
    public void showAlert(View v)
    {
        dialog.show();
        final EditText itemName = (EditText) dialog.findViewById(R.id.itemName);
        final EditText itemCost = (EditText) dialog.findViewById(R.id.itemCost);
        spinner = (Spinner)dialog.findViewById(R.id.memberList);
        System.out.println("the item object is "+itemName);
        itemCost.setText("");
        spinner.setSelection(0);
        itemName.setText("");

        spinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                memberNames );
        for(String a : memberNames)
        {
            Log.i(MY_TAG,a+"\n");

        }
        spinner = (Spinner)dialog.findViewById(R.id.memberList);
        Log.i(MY_TAG,"please dont be  "+spinner);

        spinner.setAdapter(spinnerArrayAdapter);


    }



    public void createModifyAlert()
    {
        View view = (LayoutInflater.from(AddItemsBought.this)).inflate(R.layout.user_input,null);
        //spinner = (Spinner)findViewById(R.id.spinner);
        //Log.i(MY_TAG,"the value of the spinner is "+spinner);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddItemsBought.this);
        alertBuilder.setView(view);
        final EditText itemName = (EditText) view.findViewById(R.id.itemName);
        final EditText itemCost = (EditText) view.findViewById(R.id.itemCost);
        final Spinner memberName = (Spinner) view.findViewById(R.id.memberList);

        alertBuilder.setCancelable(true)
                .setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int memberId = 0;
//itemIdModify
                        Log.i(MY_TAG,"the itemIdModify is "+itemIdModify);//memberList

                        Log.i(MY_TAG,"the name of the item is "+itemName.getText());//memberList
                        Log.i(MY_TAG,"the cost id  is "+itemCost.getText());
                        Log.i(MY_TAG,"the member  is "+memberName.getSelectedItem().toString());
                        for(Member member : memberList)
                        {
                            if(member.getName().equals(memberName.getSelectedItem().toString()))
                            {
                                memberId = member.getId();
                                break;
                            }
                        }
                        Log.i(MY_TAG,"the id of the member is "+memberId);//memberList
                        //db.insertItemsBought(itemName.getText().toString(),itemCost.getText().toString(),memberId);
                        db.updateMemberItem(itemIdModify,memberId+"",itemName.getText().toString(),itemCost.getText().toString());
                        itemInfo.clear();
                        itemVOList = db.getItemsAndCost(tripId);
                        for(ItemsVO itemVo: itemVOList)
                        {
                            itemInfo.add(itemVo.getItemName()+"   "+itemVo.getCost()+"----->"+itemVo.getMemberName());
                        }
                        //itemInfo.add(itemInfo);
                        //itemInfo.add("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        arrayAdapter.notifyDataSetChanged();
                    }
                });


        modifyDialog = alertBuilder.create();
        // setContentView(R.layout.user_input);
        modifyDialog.hide();
    }

    public void showAlertModify(String itemNameString,int cost,String member,int itemId,View view)
    {
        modifyDialog.show();
        spinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                memberNames );
        final EditText itemName = (EditText) modifyDialog.findViewById(R.id.itemName);
        final EditText itemCost = (EditText) modifyDialog.findViewById(R.id.itemCost);

        for(String a : memberNames)
        {
            Log.i(MY_TAG,a+"\n");

        }


        spinner = (Spinner)modifyDialog.findViewById(R.id.memberList);

        Log.i(MY_TAG,"the member  is "+member);


        Log.i(MY_TAG,"please dont be  "+spinner);
        Log.i(MY_TAG,"please dont be  "+itemName);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(memberNames.indexOf(member));
        itemName.setText(itemNameString);
        itemCost.setText(cost+"");

    }
    public void calculateAmount(View v)
    {
        for(ItemsVO itemVo: itemVOList)
        {
            Log.i(MY_TAG,itemVo.getItemName()+"   "+itemVo.getCost()+"----->"+itemVo.getMemberName());
        }
        ArrayList<String> finalList = db.calculatePayableAmount(itemVOList,memberNames);
        Intent intent;
        intent = new Intent(AddItemsBought.this,MoneySharingList.class);
        Bundle bundle = new Bundle();

        bundle.putStringArrayList("list", (ArrayList<String>)finalList);

        intent.putExtras(bundle);
        startActivity(intent);

    }
}
