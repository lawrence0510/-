package com.example.co2k_java_2;

public class Item {

    private String name, price, url;

    public Item( String name, String price, String url ) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getUrl() { return url; }
}
