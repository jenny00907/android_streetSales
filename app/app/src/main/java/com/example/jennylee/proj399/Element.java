package com.example.jennylee.proj399;


public class Element {
    private String address;
    private int price;

    public Element(String outAddress, String outPrice){
        address = outAddress;
        price = Integer.parseInt(outPrice);
    }

    public String getAddress(){
        return address;
    }
    public int getPrice(){
        return price;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPrice(int price){
        this.price = price;
    }
}
