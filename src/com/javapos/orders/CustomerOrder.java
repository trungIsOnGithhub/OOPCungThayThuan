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
        this.totalPrice = Integer.valueOf( totalPrice );
        this.phoneNumber = phoneNumber;
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
    
    public String serializeIntoString() {
        String totalPriceStr = Integer.toString( totalPrice );
        
        String serialized = this.timeOfOrder.concat( " " );
        
        serialized = serialized.concat( this.phoneNumber );
        
        serialized = serialized.concat( " " );
        
        serialized = serialized.concat( totalPriceStr );
        
        return serialized;
    }
    
    public void setUpOrderInfo(String phoneNumberFromMenu, String totalPriceFromMenu) {
        // Xu ly them thong tin cho thoi gian don hang
        Date nowTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm-a");
        
        this.timeOfOrder = sdf.format( nowTime );
        this.totalPrice = Integer.valueOf( totalPriceFromMenu );
        this.phoneNumber = phoneNumberFromMenu;
    }
}
