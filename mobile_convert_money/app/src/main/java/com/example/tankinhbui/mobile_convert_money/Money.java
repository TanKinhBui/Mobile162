package com.example.tankinhbui.mobile_convert_money;

/**
 * Created by tankinhbui on 08/04/2017.
 */

public class Money {
    public String name;
    public float rate;

    public Money(String name, float rate) {
        this.name = name;
        this.rate = rate;
    }


    public void setName(String name){
        this.name = name;
    }
    public void setRate(float rate){
        this.rate = rate;
    }
    public String getName(){
        return name;
    }
    public float getRate(){
        return rate;
    }
}
