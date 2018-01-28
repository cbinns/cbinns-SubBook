package com.example.cbinns_subbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

public class AddSubActivity extends AppCompatActivity {
    private EditText nameText;
    private EditText chargeText;
    private DatePicker date;
    private EditText commentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        // find the fields
        nameText = (EditText) findViewById(R.id.nameText);
        chargeText = (EditText) findViewById(R.id.chargeText);
        date = (DatePicker) findViewById(R.id.datePicker);
        commentText = (EditText) findViewById(R.id.commentText);

        Button addButton = (Button) findViewById(R.id.add_sub_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create new sub object here and set attributes
                // do try catch to limit length of text fields and monetary value
                String name = nameText.getText().toString();
                String charge = chargeText.getText().toString();

            }
        });







    }
}
