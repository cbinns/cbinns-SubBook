package com.example.cbinns_subbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubscriptionDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_SUBSCRIPTION = "SubscroptionDetailsActivity.Subscription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_details);
    }
}
