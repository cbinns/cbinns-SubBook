/*
 * Copyright (c)  2018 Carolyn Binns
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
 * SubscriptionListAdapter
 *
 * Custom Adapter to allow for three
 * text fields: name, charge and date to be
 * displayed in the list view on home screen
 *
 * @author Carolyn Binns
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
