package com.example.co2k_java_2;

public class Recipe1 {
    private String name, img, url;
    Recipe1( String name, String img, String url){
        this.name = name;
        this.img = img;
        this.url = url;
    }

    String getName(){
        return name;
    }

    String getImage(){ return img; }

    String getUrl(){
        return url;
    }
}
