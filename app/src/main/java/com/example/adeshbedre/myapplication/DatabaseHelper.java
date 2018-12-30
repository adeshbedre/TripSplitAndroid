package com.example.adeshbedre.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.*;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.*;
/**
 * Created by adesh bedre on 12/5/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String MY_TAG = "custorm";
    public static final String DATABASE_NAME = "Trips.db";
    public static final String TABLE_NAME1 = "TRIP_TABLE";
    public static final String TABLE_NAME2 = "MEMBERS_TABLE";
    public static final String TABLE_NAME3 = "ITEM_TABLE";
    public static final String COL_1 = "TRIP_ID";
    public static final String COL_2 = "TRIP_NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.i(MY_TAG,"sdffsdf db ");
        SQLiteDatabase db = this.getWritableDatabase();
       // insertData("","","");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(MY_TAG,"creating db ");
        db.execSQL("create table " + TABLE_NAME1 +" (TRIP_ID INTEGER PRIMARY KEY AUTOINCREMENT,TRIPNAME TEXT)");
        db.execSQL("create table " + TABLE_NAME2 +" (MEMBER_ID INTEGER PRIMARY KEY AUTOINCREMENT,TRIP_ID INTEGER ,MEMBER_NAME TEXT)");
        db.execSQL("create table " + TABLE_NAME3 +" (ITEMS_ID INTEGER PRIMARY KEY AUTOINCREMENT,MEMBER_ID INTEGER ,ITEMNAME TEXT,NAME TEXT,COST INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

   /* @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(db);
    }*/

    public boolean insertData(List<EditText> members, String tripName) {

        int id = getSequenceValue();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COL_1,name);
        contentValues.put("TRIPNAME",tripName);
        contentValues.put("TRIP_ID",id);
        long result = db.insert(TABLE_NAME1,null ,contentValues);

        Log.i(MY_TAG,"the result is "+result);

        for(EditText ed : members)
        {
            int memberSeq = getMemberSequenceValue();
            Log.i(MY_TAG,"the ids names are "+memberSeq);
            Log.i(MY_TAG,"the ids names are "+ed.getText().toString());
            String member  = ed.getText().toString();
            contentValues = new ContentValues();
            contentValues.put("TRIP_ID",id);
            contentValues.put("MEMBER_ID",memberSeq);
            contentValues.put("MEMBER_NAME",member);
            result = db.insert(TABLE_NAME2,null ,contentValues);
            Log.i(MY_TAG,"the result is "+result);
        }


        Cursor res = getAllData();


        StringBuffer buffer = new StringBuffer();
        Log.i(MY_TAG,"the buffer is  "+res.toString());
        while (res.moveToNext()) {
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("TRIPNAME :"+ res.getString(1)+"\n");
//            buffer.append("MEMBERNAME :"+ res.getString(2)+"\n");
            Log.i(MY_TAG,"the res is  "+buffer.toString()+"\n\n\n");
        }


        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME1,null);


        StringBuffer buffer = new StringBuffer();
        Log.i(MY_TAG,"in get all data is  "+res.toString());
       /* while (res.moveToNext()) {
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("TRIPNAME :"+ res.getString(1)+"\n");
            buffer.append("MEMBERNAME :"+ res.getString(2)+"\n");
            Log.i(MY_TAG,"the res is  "+buffer.toString()+"\n\n\n");
        }*/
        return res;
    }

   /* public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME1, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME1, "ID = ?",new String[] {id});
    }
*/

    public int getSequenceValue() {
        int sequence = 0;
        Log.i(MY_TAG,"in sequence");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SEQ FROM SQLITE_SEQUENCE WHERE name='"+TABLE_NAME1+"'",null);
        StringBuffer buffer = new StringBuffer();
        Log.i(MY_TAG,"in get all data is  "+res);
        while (res.moveToNext()) {
            sequence = res.getInt(0);
            Log.i(MY_TAG,"sequence value isssssss "+res.getInt(0));

            //buffer.append("the next value in the sequnce is  :"+ res.getString(0)+"\n");
        }
        Log.i(MY_TAG,"sequence value is "+buffer.toString());
        return sequence+1;
    }



    public int getMemberSequenceValue() {
        int sequence = 0;
        Log.i(MY_TAG,"in sequence");
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor res = db.rawQuery("SELECT SEQ FROM SQLITE_SEQUENCE WHERE name='"+TABLE_NAME2+"'",null);            SELECT last_insert_rowid()
        Cursor res = db.rawQuery("SELECT MAX(MEMBER_ID) from "+TABLE_NAME2,null);
        while (res.moveToNext()) {
            sequence = res.getInt(0);
            Log.i(MY_TAG,"sequence value isssssss "+res.getInt(0));

            //buffer.append("the next value in the sequnce is  :"+ res.getString(0)+"\n");
        }
        Log.i(MY_TAG,"sequence value is "+sequence);
        return sequence+1;
    }


    public ArrayList getTripMembers(String tripId) {
        ArrayList<Member> list = new ArrayList<Member>();
        Log.i(MY_TAG,"DatabaseHelper====>getTripMembers() tripId is  "+tripId);
        SQLiteDatabase db = this.getWritableDatabase();
        // tripName="C CName";
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2+" m,"+TABLE_NAME1+" t where t.TRIP_ID = m.TRIP_ID and t.TRIP_ID='"+tripId+"'",null);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            Member member = new Member();
            buffer.append("MEMBER_ID :"+ res.getInt(0)+"\n");
            buffer.append("TRIP_ID :"+ res.getInt(1)+"\n");
            buffer.append("MEMBER_NAME :"+ res.getString(2)+"\n\n");
            member.setId(res.getInt(0));
            member.setName(res.getString(2));
            list.add(member);
        }
        Log.i(MY_TAG,"the res is  \n"+buffer.toString()+"\n\n\n");

        return list;
    }

    //ITEMS_ID INTEGER PRIMARY KEY AUTOINCREMENT,MEMBER_ID TEXT ,ITEMNAME TEXT,NAME TEXT,COST INTEGER
    public void insertItemsBought(String itemName,String itemCost,int memberId)
    {
        Log.i(MY_TAG,"start of insertItemsbouth method");
        SQLiteDatabase db = this.getWritableDatabase();
       // db.execSQL("create table " + TABLE_NAME3 +" (ITEMS_ID INTEGER PRIMARY KEY AUTOINCREMENT,MEMBER_ID INTEGER ,ITEMNAME TEXT,NAME TEXT,COST INTEGER)");
        int id = getIdSequenceValue();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(COL_1,name);
        contentValues.put("ITEMS_ID",id);
        contentValues.put("MEMBER_ID",memberId);
        contentValues.put("ITEMNAME",itemName);
        contentValues.put("COST",itemCost);
        long result = db.insert(TABLE_NAME3,null ,contentValues);
        Log.i(MY_TAG,"end of insertItemsbouth method "+result);
    }

    public int getIdSequenceValue() {
        int sequence = 0;
        Log.i(MY_TAG,"in item sequence");
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor res = db.rawQuery("SELECT SEQ FROM SQLITE_SEQUENCE WHERE name='"+TABLE_NAME2+"'",null);            SELECT last_insert_rowid()
        Cursor res = db.rawQuery("SELECT MAX(ITEMS_ID) from "+TABLE_NAME3,null);
        while (res.moveToNext()) {
            sequence = res.getInt(0);
            Log.i(MY_TAG,"sequence value isssssss "+res.getInt(0));

            //buffer.append("the next value in the sequnce is  :"+ res.getString(0)+"\n");
        }
        Log.i(MY_TAG,"sequence value is "+sequence);
        return sequence+1;
    }
//ITEMS_ID INTEGER PRIMARY KEY AUTOINCREMENT,MEMBER_ID INTEGER ,ITEMNAME TEXT,NAME TEXT,COST INTEGER
    public ArrayList<ItemsVO> getItemsAndCost(String tripId) {
        ArrayList<ItemsVO> list = new ArrayList<ItemsVO>();
        Log.i(MY_TAG,"DatabaseHelper====>getItemsAndCost() tripId is  "+tripId);
        SQLiteDatabase db = this.getWritableDatabase();
        // tripName="C CName";
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2+" m,"+TABLE_NAME1+" t,"+TABLE_NAME3+ " i where t.TRIP_ID = m.TRIP_ID and m.MEMBER_ID = i.MEMBER_ID and  t.TRIP_ID='"+tripId+"'",null);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            ItemsVO itemsVO = new ItemsVO();
            buffer.append("member name index value is  :"+ res.getColumnIndex("MEMBER_NAME")+"\n");
            buffer.append("member name is  :"+ res.getString(res.getColumnIndex("MEMBER_NAME"))+"\n");
            buffer.append("cost is :"+ res.getInt(res.getColumnIndex("COST"))+"\n");
            buffer.append("item name is  :"+ res.getString(res.getColumnIndex("ITEMNAME"))+"\n\n");
            buffer.append("ITEMS_ID  is  :"+ res.getString(res.getColumnIndex("ITEMS_ID"))+"\n\n");
            itemsVO.setCost(res.getInt(res.getColumnIndex("COST")));
            itemsVO.setItemName(res.getString(res.getColumnIndex("ITEMNAME")));
            itemsVO.setMemberName(res.getString(res.getColumnIndex("MEMBER_NAME")));
            itemsVO.setItemId(res.getInt(res.getColumnIndex("ITEMS_ID")));
            list.add(itemsVO);
        }
        Log.i(MY_TAG,"the res is  \n"+buffer.toString()+"\n\n\n");
        return list;
    }

    ArrayList<String> calculatePayableAmount(ArrayList<ItemsVO> list,ArrayList<String> memberNames)
    {
        System.out.println("The size of membernames is "+memberNames);
        int paid[] = new int[memberNames.size()],amountPaid,equalAmount = 0,n;
        int i = 0,sum = 0;
        int bufferAmount = 0;
        LinkedHashMap<String,Integer> hm = new LinkedHashMap<String,Integer>();
        System.out.println(" the memberNames are "+memberNames);
        System.out.println(" the list vo is  "+list);

        for (ItemsVO itemsVO: list)
        {
            System.out.println("***************");
            System.out.println("items vo === "+itemsVO.getItemName() );
            System.out.println("items vo === "+itemsVO.getCost() );
            System.out.println("items vo === "+itemsVO.getMemberName() );

            if(hm.containsKey(itemsVO.getMemberName()))
            {
                int prevCost = hm.get(itemsVO.getMemberName());
                int totalCost = prevCost + itemsVO.getCost();
                hm.put(itemsVO.getMemberName(),totalCost);
                System.out.println("prev cost = "+prevCost);
                System.out.println("present cost = "+itemsVO.getCost());
                System.out.println("total  = "+totalCost);
                System.out.println("the name is "+list.get(i).getMemberName());
                System.out.println("***************");
            }
            else
            {
                hm.put(itemsVO.getMemberName(),itemsVO.getCost());
            }

        }
        for (String name :memberNames) {


        if(!hm.containsKey(name))
        {
            System.out.println("hm does not contain "+name);
            hm.put(name,0);
        }
        }
        System.out.println("hm -----> "+hm);
        ArrayList<Integer> ar = new ArrayList<Integer>(hm.values());
        System.out.println("array list members is "+ar);
        System.out.println("array size members is "+ar.size());
        System.out.println("hashmap size "+hm.size());

        for (i = 0 ; i < hm.size() ; i++)
        {
            paid[i] = ar.get(i);
            sum += paid[i];
            System.out.println(i+" -----> "+paid[i]);
        }
        n = hm.size();
        System.out.println("The no of members are "+n);
        equalAmount = sum/n;
        bufferAmount = sum%n;
        System.out.println("The bufferAmount  "+bufferAmount);

        int amountGraph [][] = new int [n][n];
            int oweAmountArray[] = new int[n];
            System.out.println("each should pay rs "+equalAmount);
            for( i = 0 ; i < n ; i++)
            {
                oweAmountArray[i] = equalAmount - paid[i];
                System.out.println(oweAmountArray[i]);
            }



            for( i = 0 ; i < n ; i++)
            {
                amountPaid = paid[i];
                if(oweAmountArray[i] > 0)
                {
                    System.out.println("\n\n\n"+Arrays.toString(oweAmountArray)+"\n\n");
                    int oweAmount = oweAmountArray[i];
                    System.out.println("the owed amount is "+oweAmount);
                    for(int j = 0 ; j < n ; j++)
                    {
                        if(oweAmountArray[j]<0)
                        {
                            int requireAmount = oweAmountArray[j];
                            oweAmount = oweAmountArray[i];
                            System.out.println(j+" requires Rs"+requireAmount);
                            System.out.println("(oweAmount+requireAmount) = "+(oweAmount+requireAmount));
                            if((oweAmount+requireAmount) > 0)
                            {
                                System.out.println((oweAmount+requireAmount)+"> 0");
                                oweAmountArray[i] = oweAmount + requireAmount;
                                System.out.println(i+"  "+oweAmountArray[i]);
                                oweAmountArray[j] = 0;
                                System.out.println(j+"  "+oweAmountArray[j]);
                                amountGraph[i][j] =  Math.abs(requireAmount);

                            }
                            else if((oweAmount+requireAmount) < 0)
                            {
                                System.out.println((oweAmount+requireAmount)+"< 0");
                                oweAmountArray[i] = 0;
                                System.out.println(i+"  "+oweAmountArray[i]);
                                oweAmountArray[j] = oweAmount + requireAmount;
                                System.out.println(j+"  "+oweAmountArray[j]);
                                amountGraph[i][j] = oweAmount ;
                                break;
                            }
                            else if((oweAmount+requireAmount) == 0)
                            {
                                System.out.println((oweAmount+requireAmount)+"< 0");
                                oweAmountArray[i] = 0;
                                System.out.println(i+"  "+oweAmountArray[i]);
                                oweAmountArray[j] = 0;
                                System.out.println(j+"  "+oweAmountArray[j]);
                                amountGraph[i][j] = oweAmount ;
                                break;
                            }

                        }
                    }
                }
            }

            System.out.println("\n\n\n\n");
            for( i = 0 ; i < n ; i++)
            {
                System.out.println(oweAmountArray[i]);
            }



        ArrayList<String> finalList = new ArrayList<String>();
        System.out.println("\n\n\n\n");
        for( i = 0 ; i < n ; i++)
        {
            for(int j = 0 ; j < n ;j++)
                if(amountGraph[i][j] >0)
                {
                    System.out.println((new ArrayList<String>(hm.keySet()).get(i))+" owes "+(new ArrayList<String>(hm.keySet()).get(j))+" Rs "+amountGraph[i][j]);
                    String st = (new ArrayList<String>(hm.keySet()).get(i))+" owes "+(new ArrayList<String>(hm.keySet()).get(j))+" Rs "+amountGraph[i][j];
                    finalList.add(st);
                }
        }
        //bufferAmount
        if(bufferAmount != 0)
        {
            String st = "The buffer amount is "+bufferAmount;
            finalList.add(st);

        }

        return finalList;


        }
//(ITEMS_ID INTEGER PRIMARY KEY AUTOINCREMENT,MEMBER_ID INTEGER ,ITEMNAME TEXT,NAME TEXT,COST INTEGER)");

    public boolean updateMemberItem(String items_id,String member_id,String itemName,String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("ITEMS_ID",items_id);
        contentValues.put("MEMBER_ID",member_id);
        contentValues.put("ITEMNAME",itemName);
        //contentValues.put("NAME",name);
        contentValues.put("COST",cost);
        db.update(TABLE_NAME3, contentValues, "ITEMS_ID = ?",new String[] { items_id });
        return true;
    }


}
