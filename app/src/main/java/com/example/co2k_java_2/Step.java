package com.example.co2k_java_2;

public class Step {

    private String step_content, url;
    private int state;

    public Step(String step_content){
        this.step_content = step_content;
        this.url = null;
        state = 0;
    }

    public Step(String step_content, String url){
        this.step_content = step_content;
        this.url = url;
        state = 1;
    }

    public String step_content(){ return step_content; }

    public String url(){ return url; }

    public int getState() {
        return state;
    }
}
