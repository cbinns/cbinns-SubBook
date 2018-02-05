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
import android.widget.AdapterView;
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
import java.text.NumberFormat;
import java.util.ArrayList;


public class HomeScreenActivity extends AppCompatActivity {
    private ListView oldSubscriptionsList;
    private SubscriptionListAdapter adapter;
    private ArrayList<Subscription> subscriptionsList;
    private static final String FILENAME="subscriptions.sav";
    private TextView textTotalCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        textTotalCharge = (TextView) findViewById(R.id.chargeTotalText);

        oldSubscriptionsList = (ListView) findViewById(R.id.oldSubscriptionsList);
        Button addButton = (Button) findViewById(R.id.addSubscriptionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddScreen();            // read this to go to add new sub activity
                adapter.notifyDataSetChanged();
                saveInFile();                 // make persistent
            }
        });

        oldSubscriptionsList.setAdapter(adapter);
        oldSubscriptionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launchSubscriptionDetails(i);
            }
        });
    }

    private void launchAddScreen() {
        startActivity(new Intent(this, AddSubscriptionActivity.class));
    }

    private void launchSubscriptionDetails(int position){
        Intent subscriptionDetailsIntent = new Intent(this, SubscriptionDetailsActivity.class);
        subscriptionDetailsIntent.putExtra("position", position);
        startActivity(subscriptionDetailsIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new SubscriptionListAdapter(this, subscriptionsList);
        oldSubscriptionsList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadFromFile();
        adapter.notifyDataSetChanged();

        // Calculate and display Total Monthly Charge
        double totalCharge=0;
        for (Subscription subscription: subscriptionsList){
            String valueString = subscription.getCharge().replace("$","");
            double value =  Double.parseDouble(valueString);
            totalCharge += value;
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String money = formatter.format(totalCharge);
        textTotalCharge.setText("Total charge: "+ money);
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
