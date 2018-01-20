package com.example.quiz3;

import java.util.ArrayList;

/**
 * Created by Abinsi on 1/17/2018.
 */

public class Manager extends Employee {
    private ArrayList<Employee> crew;

    public Manager(){
        this.crew = new ArrayList<Employee>();
    }

    public void addEmployee(Employee employee){
        this.crew.add(employee);
    }

}
