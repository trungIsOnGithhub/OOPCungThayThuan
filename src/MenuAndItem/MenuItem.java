package MenuAndItem;

/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 *
 * Class nay dung de mo phong cac mon an trong menu, bao gom ten mon an va gia ca.
 * Class nay co 4 constructor, trong do co 1 copy constructor.
 * 1 method de xuat ra dang chuoi cua thong tin mon an dung cho viec in ra va viet vao file txt.
 */
public class MenuItem {
    // Ten va gia ca cua tung item phai nhin thay duoc tren toan bo he thong
    public String nameOfItem;
    public Integer priceOfItem;
    
    public MenuItem() {
        this.nameOfItem = "undefined";
        this.priceOfItem = 0;
    }
    public MenuItem(String name, Integer price) {
        this.nameOfItem = name;
        this.priceOfItem = price;
    }
    public MenuItem(String name, String price) {
        this.nameOfItem = name;
        this.priceOfItem = Integer.valueOf( price );
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
        
        String serialized = secondPart.concat( " VND" );
        
        return serialized;
    }
}