package com.example.cbinns_subbook;

import android.util.Log;

import com.example.cbinns_subbook.CommentTooLongException;
import com.example.cbinns_subbook.NameTooLongException;

import java.util.Date;

/**
 * Created by Abinsi on 1/27/2018.
 */

public class Subscription {
    private String name;
    private Date date;            // want to be able to set as past date?
    private double charge;           // in CAD, non neg
    private String comment;

    public Subscription(String name, Date date, double charge) {
        this.name=name;
        this.date = date;
        this.charge = charge;
    }

    public Subscription(String name, Date date, double charge, String comment) {
        this.name=name;
        this.comment=comment;
        this.date = date;
        this.charge = charge;
    }

    public void setName(String name) throws NameTooLongException{
        if (name.length() < 20){
            this.name = name;
        }
        else{
            throw new NameTooLongException();
        }
    }

    public void setComment(String comment) throws CommentTooLongException{
        if (comment.length() < 30){
            this.comment = comment;
        }
        else{
            throw new CommentTooLongException();
        }
    }





}
