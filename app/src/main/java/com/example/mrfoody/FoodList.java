package com.example.mrfoody;

public class FoodList extends foodId {

    private String Chef_ID;
    private String Chef_name;
    private String Item_description;
    private String Item_name;
    private String Price;
    private String Time;
    private String image;

    public FoodList(){}


    public FoodList(String chef_ID, String chef_name, String item_description, String item_name, String price, String time, String image) {
        this.Chef_ID = chef_ID;
        this.Chef_name = chef_name;
        this.Item_description = item_description;
        this.Item_name = item_name;
        this.Price = price;
        this.Time = time;
        this.image = image;
    }

    public String getChef_ID() {
        return Chef_ID;
    }

    public void setChef_ID(String chef_ID) {
        Chef_ID = chef_ID;
    }

    public String getChef_name() {
        return Chef_name;
    }

    public void setChef_name(String chef_name) {
        Chef_name = chef_name;
    }

    public String getItem_description() {
        return Item_description;
    }

    public void setItem_description(String item_description) {
        Item_description = item_description;
    }

    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String item_name) {
        Item_name = item_name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
