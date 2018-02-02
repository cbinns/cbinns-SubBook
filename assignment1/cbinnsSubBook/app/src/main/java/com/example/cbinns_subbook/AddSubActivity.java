package com.example.cbinns_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.format;

public class AddSubActivity extends AppCompatActivity {
    private EditText nameText;
    private EditText chargeText;
    private DatePicker datePicker;
    private Date date;
    private EditText commentText;
    private Button createButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        // find the fields
        nameText = (EditText) findViewById(R.id.listNameText);
        chargeText = (EditText) findViewById(R.id.listChargeText);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        commentText = (EditText) findViewById(R.id.commentText);


        // -------------------------------------------------------------------- adding a sub
        createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create new sub object here and set attributes
                // do try catch to limit length of text fields and monetary value

                Subscription newSubscription = new Subscription();
                String name = nameText.getText().toString();
                String charge = chargeText.getText().toString();
                String comment = commentText.getText().toString();

                // get date out of date picker


                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, datePicker.getYear());
                cal.set(Calendar.MONTH, datePicker.getMonth());
                cal.set(Calendar.DATE, datePicker.getDayOfMonth());

                // set date -----------
                // TODO: cant be in the future?
                date=cal.getTime();

                newSubscription.setDate(new SimpleDateFormat("yyyy-MM-dd").format(date));

                try{
                    newSubscription.setName(name);
                }catch(Exception e ){
                    Context context = view.getContext();
                    CharSequence text = "Name must be between 0 and 20 characters";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                // set monthly charge, rounds to 2 decimal points
                try {
                    Double chargeDouble = Double.parseDouble(charge);
                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
                    String money = formatter.format(chargeDouble);
                    newSubscription.setCharge(money);
                }catch(Exception e){
                    Context context = view.getContext();
                    CharSequence text = "Must enter a charge";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                // set comment, may be null ---------
                try{
                    newSubscription.setComment(comment);
                    newSubscription.setDone(Boolean.TRUE);
                }catch(Exception e){
                    Context context = view.getContext();
                    CharSequence text = "Comment must be less than 30 characters";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


                if (newSubscription.isDone()){
                    // add the subscription to the list and return to home screen
                    Context context = view.getContext();
                    CharSequence text = "Subscription Created";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    finish();
                }


            }
        });


        // cancelling a sub -------------------------------------------------------------------
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create new sub object here and set attributes
                // do try catch to limit length of text fields and monetary value
                Context context = view.getContext();
                CharSequence text = "Aborted";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                finish();

            }
        });


    }
}
