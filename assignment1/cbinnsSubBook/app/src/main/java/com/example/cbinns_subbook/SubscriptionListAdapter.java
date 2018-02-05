package com.example.cbinns_subbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abinsi on 1/28/2018.
 */

public class SubscriptionListAdapter extends ArrayAdapter<Subscription> {
    public SubscriptionListAdapter(Context context, ArrayList<Subscription> subscriptions) {
        super(context,0, subscriptions);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Subscription subscription = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_subscriptions, parent, false);
        }
        // Lookup view for data population
        TextView nameTextView = (TextView) convertView.findViewById(R.id.listNameText);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.listDateText);
        TextView chargeTextView = (TextView) convertView.findViewById(R.id.listChargeText);

        nameTextView.setText(subscription.getName());
        dateTextView.setText(subscription.getDate());
        chargeTextView.setText(subscription.getCharge());


        return convertView;
    }


}
