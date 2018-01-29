package com.example.cbinns_subbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Abinsi on 1/28/2018.
 */

public class SubscriptionListAdapter extends ArrayAdapter<Subscription> {
    public SubscriptionListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = getListItemView(convertView, parent);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.listNameText);
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.listDateText);
        TextView chargeTextView = (TextView) listItemView.findViewById(R.id.listChargeText);

        Subscription subscription = getItem(position);
        nameTextView.setText(subscription.getName());
        dateTextView.setText(subscription.getDate());
        chargeTextView.setText(subscription.getCharge());

        return listItemView;

    }

    private View getListItemView(@Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        }
        return LayoutInflater.from(getContext()).inflate(R.layout.list_item_subscriptions, parent, false);
    }

}
