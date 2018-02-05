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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Edit the details of an existing subscription
 *
 * @author Carolyn Binns
 */

public class EditSubscriptionDetailsActivity extends AppCompatActivity {
    private Button saveButton;
    private ArrayList<Subscription> subscriptionsList;
    private static final String FILENAME="subscriptions.sav";
    int position;
    private EditText newName;
    private EditText newCharge;
    private DatePicker newDatePicker;
    private EditText newComment;
    private Date anotherDate;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription_details);

        newName = (EditText) findViewById(R.id.newName);
        newCharge = (EditText) findViewById(R.id.newCharge);
        newDatePicker = (DatePicker) findViewById(R.id.newDate);
        newComment = (EditText) findViewById(R.id.newComment);

        // get position of Subscription in list
        Intent detailsIntent = getIntent();
        Bundle bundle= detailsIntent.getExtras();
        position = (int) bundle.get("position");

        loadFromFile();
        final Subscription subscription = subscriptionsList.get(position);

        // set value fields to old values
        newName.setText(subscription.getName());
        newCharge.setText(subscription.getCharge().replace('$',' '));
        newComment.setText(subscription.getComment());

        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(subscription.getDate());
        }catch (java.text.ParseException e){
            this.date = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        newDatePicker.updateDate(year, month, day);

        // on save get new values and save to file
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newName.getText().toString();
                String charge = newCharge.getText().toString();
                String comment = newComment.getText().toString();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, newDatePicker.getYear());
                cal.set(Calendar.MONTH, newDatePicker.getMonth());
                cal.set(Calendar.DATE, newDatePicker.getDayOfMonth());

                // set new date
                // TODO: cant be in the future?
                anotherDate=cal.getTime();
                subscription.setDate(new SimpleDateFormat("yyyy-MM-dd").format(anotherDate));

                try{
                    subscription.setName(name);
                }catch(Exception e ){
                    Context context = view.getContext();
                    CharSequence text = "Name must be between 0 and 20 characters";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                // set monthly charge, rounds to 2 decimal points
                try {
                    Double chargeDouble = Double.parseDouble(charge);
                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
                    String money = formatter.format(chargeDouble);
                    subscription.setCharge(money);
                }catch(Exception e){
                    Context context = view.getContext();
                    CharSequence text = "Must enter a charge";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                // set comment, may be empty string
                try{
                    subscription.setComment(comment);
                }catch(Exception e){
                    Context context = view.getContext();
                    CharSequence text = "Comment must be less than 30 characters";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                if (!name.equals("") && !charge.equals("") && !name.replaceAll("\\s+","").equals("")) {
                    // add the subscription to the list and return to home screen
                    Context context = view.getContext();
                    CharSequence text = "Changes saved";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    saveInFile();
                    finish();               //return to SubscriptionDetailsActivity

                } else {
                    Context context = view.getContext();
                    CharSequence text = "Name/Charge can not be whitespace";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

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
