package OrdersAndHistory;

import java.util.ArrayList;

/**
 *
 * /**
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 * 
 * Class hien thuc cau truc du lieu bao chua(ArrrayList) lich su don hang
 * 1 method them 1 don hang moi vao lich su don hang, sau khi da chot don.
 * 1 method tra ve 1 deep copy cua danh sach lich su don hang
 */
public class OrderHistoryContainer {
    // Mac dinh orderHistory duoc sap xep theo thu tu thoi gian cua don hang
    private ArrayList<CustomerOrder> orderHistory;
    
    public OrderHistoryContainer() {
        this.orderHistory = new ArrayList<>();
    }
    
    public ArrayList<CustomerOrder> getDeepCopyOfOrderHistory() {
        ArrayList<CustomerOrder> result = new ArrayList<>();
        
        for(var item : this.orderHistory) {
            result.add( new CustomerOrder( item ) );
        }
        
        return result;
    }
    
    public void addNewOrderToHistory(CustomerOrder newOrder) {
        this.orderHistory.add( newOrder );
    }
}
