package com.example.demo;

public class SkinBean {

    private String skin;


    public void init() {
        skin = "blueSky";
    }

    public String getSkin() {
        System.out.println("SkinBean.getSkin() chamado!");
        return skin;
    }


    public void setSkin(String skin) {
        this.skin = skin;
    }
}
