package com.example.cbinns_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class HomeScreen extends AppCompatActivity {

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


    }

    private void launchAddScreen() {
        startActivity(new Intent(this, AddSubActivity.class));
    }


}
