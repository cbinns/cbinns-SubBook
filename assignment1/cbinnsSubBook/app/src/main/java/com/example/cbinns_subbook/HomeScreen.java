package com.example.cbinns_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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


public class HomeScreen extends AppCompatActivity {
    private ListView oldSubscriptionsList;
    private SubscriptionListAdapter listFeedAdapter;
    private ArrayAdapter<Subscription> adapter;
    private ArrayList<Subscription> subscriptionsList;
    private static final String FILENAME="subscriptions.sav";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);


        oldSubscriptionsList = (ListView) findViewById(R.id.oldSubscriptionsList);

        //listFeedAdapter = new SubscriptionListAdapter(this, R.id.listView);



        Button addButton = (Button) findViewById(R.id.add_sub_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchAddScreen();            // readd this to go to add new sub activity



                adapter.notifyDataSetChanged();

                saveInFile();                 // make persistent
            }
        });



        oldSubscriptionsList.setAdapter(listFeedAdapter);



        oldSubscriptionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                //Subscription subscription = (Subscription) adapterView.getAdapter().getItem(i);
                launchSubscriptionDetails(i);

            }
        });

    }

    private void launchAddScreen() {

        startActivity(new Intent(this, AddSubActivity.class));

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
        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionsList);
        oldSubscriptionsList.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadFromFile();
        adapter.notifyDataSetChanged();
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
