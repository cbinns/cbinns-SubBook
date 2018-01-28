package com.example.cbinns_subbook;

import java.util.ArrayList;

/**
 * Created by Abinsi on 1/27/2018.
 */

public class SubscriptionList {
    private ArrayList<Subscription> subsList;

    public SubscriptionList(ArrayList<Subscription> subsList) {
        this.subsList = subsList;
    }

    public ArrayList<Subscription> getSubsList() {
        return subsList;
    }

    public void setSubsList(ArrayList<Subscription> subsList) {
        this.subsList = subsList;
    }
}
