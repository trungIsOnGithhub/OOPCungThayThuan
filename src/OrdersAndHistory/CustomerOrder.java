package OrdersAndHistory;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *
 * @author Trung Nguyen, Lap trinh nang cao-HK212
 *
 */
public class CustomerOrder {
    protected String timeOfOrder;
    protected Integer totalPrice;
    
    // Dependency Injection 101
    public CustomerOrder(String timeOfOrder, String totalPrice) {
        this.timeOfOrder = timeOfOrder;
        this.totalPrice = Integer.valueOf( totalPrice );
    }
    public CustomerOrder(CustomerOrder toBeCopied) {
        // Copy constructor
        this.timeOfOrder = toBeCopied.getTimeOfOrder();
        this.totalPrice = toBeCopied.getTotalPrice();
    }
    
    public String getTimeOfOrder() {
        return this.timeOfOrder;
    }
    public Integer getTotalPrice() {
        return this.totalPrice;
    }

    public String serializeIntoString() {
        String serialized = this.timeOfOrder.concat( " " ).concat( String.valueOf( this.totalPrice ) );
        return serialized;
    }
    
    public void setUpOrderInfo(String totalPriceFromMenu) {
        // Xu ly them thong tin cho thoi gian don hang
        Date nowTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm-a");
        
        this.timeOfOrder = sdf.format( nowTime );
        this.totalPrice = Integer.valueOf( totalPriceFromMenu );
    }
    
    public static void main(String args[]) {
      CustomerOrder co = new CustomerOrder("8h00","78");
      CustomerOrder cophone = new PhoneNumberDecorator(co,"1234567");
      System.out.println(cophone.serializeIntoString());
    }
}

class PhoneNumberDecorator extends CustomerOrder {
    private String phoneNumber;

    public PhoneNumberDecorator(CustomerOrder toBeCopied, String phoneNumber) {
        super(toBeCopied);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public String serializeIntoString() {
        return super.serializeIntoString().concat( " " ).concat( this.phoneNumber );
    }

    // public void setUpOrderInfo(String phoneNumberFromMenu) {
    //     super.setUpOrderInfo(this.totalPrice);
    //     this.phoneNumber = phoneNumberFromMenu;
    // }
}
