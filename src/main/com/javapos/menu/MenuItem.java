package com.javapos.menu;
/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 *
 */
public class MenuItem {
    static final currencyUnitString = "VND";
    // Ten va gia ca cua tung item phai nhin thay duoc tren toan bo he thong
    String nameOfItem;
    Integer priceOfItem;

    static private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        }
        catch( Exception e ) {
            return false;
        }
        return true;
    }
    
    public MenuItem() {
        this.nameOfItem = "undefined";
        this.priceOfItem = 0;
    }
    public MenuItem(String name, Integer price) {
        this.nameOfItem = name;
        this.priceOfItem = price;
    }
    public MenuItem(String name, String price) {
        if( !isInteger(price) ) {
            this.nameOfItem = "undefined";
            this.priceOfItem = 0;  
            return;
        }
        this.nameOfItem = name;
        this.priceOfItem = Integer.valueOf(price);
    }
    public MenuItem(MenuItem toBeCopied) {
        // Copy Constructor
        this.nameOfItem = toBeCopied.nameOfItem;
        this.priceOfItem = toBeCopied.priceOfItem;
    }
    
    public String serializeIntoString() {
        String priceOfItemStr = this.priceOfItem.toString();
       
        String firstPart = this.nameOfItem.concat( " " );

        String secondPart = firstPart.concat( priceOfItemStr );
        
        String serialized = secondPart.concat( " " ).concat(currencyUnitString);
        
        return serialized;
    }
}
