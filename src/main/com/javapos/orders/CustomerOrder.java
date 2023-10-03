package com.javapos.orders;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 *
 * Class nay mo phong lai 1 don hang trong he thong, gom co: gio giac, tong gia tri, so dien thoai nguoi mua
 * 3 constructor, voi 1 copy constructor
 * Cac getter va setter lay ra cac gia tri can thiet
 * 1 method de tao folder info
 * 1 method de xuat chuoi chua thong tin don hang
 */
public class CustomerOrder {
    private String timeOfOrder;
    private Integer totalPrice;
    private String phoneNumber;
    
    public CustomerOrder() {
        this.timeOfOrder = "unknown";
        this.totalPrice = 0;
        this.phoneNumber = "unknown";
    }
    public CustomerOrder(String timeOfOrder, String totalPrice, String phoneNumber) {
        this.timeOfOrder = timeOfOrder;
        this.phoneNumber = phoneNumber;

        if( !isInteger(totalPrice) ) {
            this.totalPrice = 0;
        }
        else {
            this.totalPrice = Integer.valueOf( totalPrice );
        }
    }
    public CustomerOrder(CustomerOrder toBeCopied) {
        // Copy constructor
        this.timeOfOrder = toBeCopied.getTimeOfOrder();
        this.totalPrice = toBeCopied.getTotalPrice();
        this.phoneNumber = toBeCopied.getPhoneNumber();
    }
    
    public String getTimeOfOrder() {
        return this.timeOfOrder;
    }
    public Integer getTotalPrice() {
        return this.totalPrice;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public String toString() {
        String totalPriceStr = Integer.toString( totalPrice );
        
        String serialized = this.timeOfOrder.concat( " " );
        
        serialized = serialized.concat( this.phoneNumber );
        
        serialized = serialized.concat( " " );
        
        serialized = serialized.concat( totalPriceStr );

        return serialized;
    }
    
    public void setUpOrderInfo(String phoneNumberFromMenu, String totalPriceFromMenu) { 
        this.timeOfOrder = getCurrentTime();
        this.phoneNumber = phoneNumberFromMenu;

        if( !isInteger(totalPriceFromMenu) ) {
            this.totalPrice = 0;
            return;
        }

        this.totalPrice = Integer.valueOf(totalPriceFromMenu);
    }


    static private String dateFormat = "hh:mm-a";
    static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    static String getCurrentTime() {
        Date nowTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(nowTime);
    }
    static boolean isValidDate(String input) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            sdf.parse(input);
        } catch(ParseException e) {
            return false;
        }

        return true;
    }

    static void setDateFormatString(String dateFormat) {
        CustomerOrder.dateFormat = dateFormat;
    }
}
