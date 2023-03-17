package com.example.co2k_java_2;

public class Ingredient {

    private String name, amount;

    public Ingredient(String name, String amount){

        this.name = name;
        this.amount = amount;
    }

    public String getName(){
        return name;
    }

    public String getAmount() {
        return amount;
    }
}
