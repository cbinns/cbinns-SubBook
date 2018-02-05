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

public class SubscriptionDetailsActivity extends AppCompatActivity {
    public static final String DETAIL_SUBSCRIPTION = "SubscriptionDetailsActivity.subscription";

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


        Intent detailsIntent = getIntent();
        Bundle bundle= detailsIntent.getExtras();

        position = (int) bundle.get("position");

        loadFromFile();
        final Subscription subscription = subscriptionsList.get(position);
        showSubscription(subscription);



        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscriptionsList.remove(position);
                saveInFile();                 // make persistent
                finish();
            }
        });

        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subscriptionDetailsIntent = new Intent(view.getContext(), EditSubscriptionDetailsActivity.class);
                subscriptionDetailsIntent.putExtra("position", position);
                startActivity(subscriptionDetailsIntent);

                //saveInFile();                 // make persistent
            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        loadFromFile();
        final Subscription subscription = subscriptionsList.get(position);
        showSubscription(subscription);

    }

    private void initializeContent() {
        subscriptionNameTextView = (TextView) findViewById(R.id.nameField);
        subscriptionDateTextView = (TextView) findViewById(R.id.dateField);
        subscriptionChargeTextView = (TextView) findViewById(R.id.chargeField);
        subscriptionCommentTextView = (TextView) findViewById(R.id.commentField);

    }

    private void showSubscription(Subscription subscription){
        subscriptionNameTextView.setText(subscription.getName());
        subscriptionDateTextView.setText(subscription.getDate());
        subscriptionChargeTextView.setText(subscription.getCharge());
        subscriptionCommentTextView.setText(subscription.getComment());

    }

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
