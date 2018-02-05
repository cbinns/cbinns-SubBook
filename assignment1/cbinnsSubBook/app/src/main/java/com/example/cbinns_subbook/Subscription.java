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


/**
 * Represents a Subscription
 *
 * @author Carolyn Binns
 */

public class Subscription {
    private String name;
    private String date;            // want to be able to set as past date?
    private String charge;           // in CAD, non neg
    private String comment;

    public Subscription() {
    }

    /**
     * This Subscription constructor does no length checking
     *
     * @param name
     * @param date
     * @param charge
     * @param comment
     */

    public Subscription(String name, String date, String charge, String comment) {
        this.name=name;
        this.comment=comment;
        this.date = date;
        this.charge = charge;
    }

    public void setName(String name) throws NameLengthException {
        if (name.length() < 20 && name.length()>0 ){
            this.name = name;
        }
        else{
            throw new NameLengthException();
        }
    }

    public void setComment(String comment) throws CommentLengthException {
        if (comment.length() < 30 ){
            this.comment = comment;
        }
        else{
            throw new CommentLengthException();
        }
    }

    public void setDate(String  date) {
        this.date = date;
    }

    public void setCharge(String charge) {
        this.charge = charge;
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

    public String toString() {
        return date.toString() + " | " + name;
    }
}
