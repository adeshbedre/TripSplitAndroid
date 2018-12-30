package com.example.adeshbedre.myapplication;

/**
 * Created by adesh bedre on 1/1/2017.
 */
//ITEMS_ID INTEGER PRIMARY KEY AUTOINCREMENT,MEMBER_ID TEXT ,ITEMNAME TEXT,NAME TEXT,COST INTEGER

public class ItemsVO {
    String itemName;
    int cost;
    int itemId;
    String memberName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
