package com.example.cbinns_subbook;

import java.util.Date;

/**
 * Created by Abinsi on 1/27/2018.
 */

public class Subscription {
    private String name;
    private String date;            // want to be able to set as past date?
    private String charge;           // in CAD, non neg
    private String comment;
    private Boolean doneFlag;

    public Subscription() {
        this.doneFlag=Boolean.FALSE;
    }

    public Subscription(String name, String date, String charge, String comment) {
        this.name=name;
        this.comment=comment;
        this.date = date;
        this.charge = charge;
    }

    public void setName(String name) throws NameException {
        if (name.length() < 20 && name.length()>0 ){
            this.name = name;
        }
        else{
            throw new NameException();
        }
    }

    public void setComment(String comment) throws CommentTooLongException{
        if (comment.length() < 30 ){
            this.comment = comment;
        }
        else{
            throw new CommentTooLongException();
        }
    }

    public void setDate(String  date) {
        this.date = date;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public void setDone(Boolean done) {
        this.doneFlag = done;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getCharge() {
        return charge;
    }

    public String getComment() {
        return comment;
    }

    public Boolean isDone() {
        return doneFlag;
    }
}
