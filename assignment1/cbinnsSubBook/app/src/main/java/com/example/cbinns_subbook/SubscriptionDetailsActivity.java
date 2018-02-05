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
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * SubscriptionDetailsActivity
 * View all details of a subscription that
 * is chosen from the home screen
 *
 * @author Carolyn Binns
 * @see Subscription
 * @see EditSubscriptionDetailsActivity
 *
 */
public class SubscriptionDetailsActivity extends AppCompatActivity {
    private TextView subscriptionNameTextView;
    private TextView subscriptionDateTextView;
    private TextView subscriptionChargeTextView;
    private TextView subscriptionCommentTextView;
    private ArrayList<Subscription> subscriptionsList;
    private static final String FILENAME="subscriptions.sav";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_details);

        initializeContent();            // set text fields to the subscriptions values

        // get position of Subscription in list to display
        Intent detailsIntent = getIntent();
        Bundle bundle= detailsIntent.getExtras();
        position = (int) bundle.get("position");

        // get subscription object at that index
        loadFromFile();
        final Subscription subscription = subscriptionsList.get(position);
        showSubscription(subscription);

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscriptionsList.remove(position);
                saveInFile();          // make persistent
                finish();              // navigate back to HomeScreenActivity
            }
        });

        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to EditSubscriptionActivity and send position
                Intent subscriptionDetailsIntent = new Intent(view.getContext(), EditSubscriptionDetailsActivity.class);
                subscriptionDetailsIntent.putExtra("position", position);
                startActivity(subscriptionDetailsIntent);
            }
        });
    }

    /**
     * OnResume load list of subscriptions from
     * file in case there were changes to the Subscriptions
     */
    @Override
    protected void onResume(){
        super.onResume();
        loadFromFile();
        final Subscription subscription = subscriptionsList.get(position);
        showSubscription(subscription);

    }

    /**
     * Find TextViews
     */
    private void initializeContent() {
        subscriptionNameTextView = (TextView) findViewById(R.id.nameField);
        subscriptionDateTextView = (TextView) findViewById(R.id.dateField);
        subscriptionChargeTextView = (TextView) findViewById(R.id.chargeField);
        subscriptionCommentTextView = (TextView) findViewById(R.id.commentField);
    }

    /**
     * Display name, charge, date and comment
     * for the selected subscription
     */
    private void showSubscription(Subscription subscription){
        subscriptionNameTextView.setText(subscription.getName());
        subscriptionDateTextView.setText(subscription.getDate());
        subscriptionChargeTextView.setText(subscription.getCharge());
        subscriptionCommentTextView.setText(subscription.getComment());
    }

    /*
    Taken with permission from Lonely Twitter for Cmput 301
    https://github.com/ta301-ks/lonelyTwitter/tree/w18TueLab3
    2018-02-01
    */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-23
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subscriptionsList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionsList = new ArrayList<Subscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /*
    Taken with permission from Lonely Twitter for Cmput 301
    https://github.com/ta301-ks/lonelyTwitter/tree/w18TueLab3
    2018-02-01
    */
    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subscriptionsList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
