package com.thx.xuitest;

import java.util.ArrayList;

public class Drinks {
    private final int number;
    private String name;
    private String type;
    private float price;
    private String introduction;
    private int ImageResId;
    static private final ArrayList<Drinks> all_drinks = new ArrayList<>();
    Drinks(String name, String type, float price, String introduction, int ImageResId)
    {
        this.number = all_drinks.size();
        this.name = name;
        this.type = type;
        this.price = price;
        this.introduction = introduction;
        this.ImageResId = ImageResId;
        all_drinks.add(this);
    }
    Drinks(String name, float price, String introduction, int ImageResId)
    {
        this.number = all_drinks.size();
        this.type = null;
        this.name = name;
        this.price = price;
        this.introduction = introduction;
        this.ImageResId = ImageResId;
        all_drinks.add(this);
    }
    Drinks(int i){
        this.number = i-1;
        Drinks temp = all_drinks.get(i-1);
        this.name = temp.name;
        this.type = temp.type;
        this.price = temp.price;
        this.introduction = temp.introduction;
        this.ImageResId = temp.ImageResId;
    }
    public int get_number()
    {
        return number;
    }
    public String get_name()
    {
        return name;
    }
    public String get_type()
    {
        return type;
    }
    public float get_price() { return price;}
    public String get_introduction()
    {
        return introduction;
    }
    public int getImageResId(){return ImageResId;}
    public void set_name(String name)
    {
        this.name = name;
    }
    public void set_type(String type)
    {
        this.type = type;
    }
    public void set_price(float price)
    {
        this.price = price;
    }
    public void set_Introduction(String introduction)
    {
        this.introduction = introduction;
    }
    public void setImageResId(int id){this.ImageResId = id;}
}
