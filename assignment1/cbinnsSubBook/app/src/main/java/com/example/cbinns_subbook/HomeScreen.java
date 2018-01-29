package com.example.cbinns_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class HomeScreen extends AppCompatActivity {
    private ListView listView;
    private SubscriptionListAdapter listFeedAdapter;
    private static final String FILENAME = "file.sav";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        Button addButton = (Button) findViewById(R.id.add_sub_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddScreen();
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        listFeedAdapter = new SubscriptionListAdapter(this, R.id.listView);

        listView.setAdapter(listFeedAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subscription subscription = (Subscription) adapterView.getAdapter().getItem(i);
                launchSubscriptionDetails(subscription);

            }
        });
    }

    private void launchAddScreen() {
        startActivity(new Intent(this, AddSubActivity.class));
    }

    private void launchSubscriptionDetails(Subscription subscription){
        Intent subscriptionDetailsIntent = new Intent(this, SubscriptionDetailsActivity.class);
        //subscriptionDetailsIntent.putExtra(SubscriptionDetailsActivity.EXTRA_SUBSCRIPTION, subscription);
        startActivity(subscriptionDetailsIntent);

        startActivity(new Intent(this, SubscriptionDetailsActivity.class));


    }




}
