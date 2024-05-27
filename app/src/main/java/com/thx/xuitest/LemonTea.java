package com.thx.xuitest;


import java.util.ArrayList;

public class LemonTea {
    private final int number;
    private String name;
    private String type;
    private float price;
    private String intro;
    private int ImageResId;
    static private final ArrayList<LemonTea> all_drinks = new ArrayList<>();
    LemonTea(String name, String type, float price, String intro, int ImageResId){
        this.number = all_drinks.size();
        this.name = name;
        this.type = type;
        this.price = price;
        this.intro = intro;
        this.ImageResId = ImageResId;
        all_drinks.add(this);
    }
    LemonTea(String name, float price, String intro, int ImageResId){
        this.number = all_drinks.size();
        this.type = null;
        this.name = name;
        this.price = price;
        this.intro = intro;
        this.ImageResId = ImageResId;
        all_drinks.add(this);
    }
    LemonTea(int i){
        this.number = i - 1;
        LemonTea temp = all_drinks.get(i-1);
        this.name = temp.name;
        this.type = temp.type;
        this.price = temp.price;
        this.intro = temp.intro;
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
        return intro;
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

    public void set_Introduction(String intro)
    {
        this.intro = intro;
    }

    public void setImageResId(int id){this.ImageResId = id;}
}
