package com.thx.xuitest;

public class Order {
    private final String order_num;
    private final String time;
    private final String take_away;
    private String cost;
    Order(String info){
        String[] info_list = info.split(",");
        order_num = info_list[0];
        time = info_list[1];
        take_away = info_list[2];
        cost = info_list[3];
    }
    public String getOrder_number(){
        return order_num;
    }
    public String getTime(){
        return time;
    }
    public String getTakeAway(){
        return take_away;
    }
    public String getCost(){
        return cost;
    }
}
