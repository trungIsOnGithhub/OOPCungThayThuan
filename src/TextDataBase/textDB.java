package TextDataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import MenuAndItem.MenuItem;
import OrdersAndHistory.CustomerOrder;

/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 * 
 * Su dung 1 tap hop cac file txt lam noi luu tru du lieu lau dai cho ung dung, nhu la mot database.
 * 2 method doc menu tu file de render thanh UI va viet them mon vao menu.
 * 1 method ghi lich su don hang vao file txt.
 * 1 method kiem tra yeu cau nhap ten va mat khau(dang cho phat trien)
 */

/*** SINGLETON CLASS ***/
public class textDB {
    private static final String orderHistoryRecordFileName = "orderHistory.txt";
    private static File orderHistoryFile;
    
    private static final String customersListFileName = "customersList.txt";
    private static File customerListFile;

    private static final String accountsInfoFileName = "accountsInfo.txt";
    private static File accountsInfoFile;

    private static final String menuItemsFileName = "menuItems.txt";
    private static File menuItemsFile;
    
    public textDB() {
        textDB.orderHistoryFile = new File( textDB.orderHistoryRecordFileName );
        textDB.customerListFile = new File( textDB.customersListFileName );
        textDB.accountsInfoFile = new File( textDB.accountsInfoFileName );
        textDB.menuItemsFile = new File( textDB.menuItemsFileName );
    }
    
    public boolean checkLoginRequest(String username, String password) {
        try {
            Scanner accountsScanner = new Scanner( accountsInfoFile );
            
            boolean result = false;
            
            while( accountsScanner.hasNextLine() ){
                String line = accountsScanner.nextLine();
                
                int usernamePos = line.indexOf( username );
                int passwordPos = -1;
                
                if( usernamePos != -1 )
                    { passwordPos = line.indexOf( password ); }
                
                if( usernamePos != -1 && passwordPos != -1 ) {
                    result = true;
                    break;
                }
            }
            
            accountsScanner.close();
            
            return result;

        } catch(IOException excep) {
            excep.printStackTrace();
            return false;
        }
    }

    public boolean readMenuFromDB(ArrayList<MenuItem> mainMenu) {
        Scanner sc = null;

          try {
            sc = new Scanner( textDB.menuItemsFile );
          } catch(FileNotFoundException exception) {
              System.out.println("Kiem tra neu file ,menuItems.txt chua duoc tao");
              exception.printStackTrace();
              return false;
          } catch(IOException exception) {
              System.out.println("Co loi khi doc file menuItems.txt");
              exception.printStackTrace();
              return false;
          }
        
        if( sc != null ) {
            mainMenu.clear();

            String tenMon = "";
            String giaCaStr = "";

            boolean readModeSwitcher = true;
            while( sc.hasNext() ) {

                if( readModeSwitcher ) {
                    tenMon = sc.next();
                    // System.out.println("Ten mon: "+tenMon);
                    
                    readModeSwitcher = !readModeSwitcher;
                }
                else {
                    giaCaStr = sc.next();
                    // System.out.println("Gia ca: "+giaCaStr);
                    mainMenu.add( new MenuItem(tenMon,giaCaStr) );
                    
                    readModeSwitcher = !readModeSwitcher;
                }
            }

            sc.close();
            
            return true;
        }
        else {
            System.out.println("Loi khoi tao file input.txt");
            return false;
        }
    }
    public boolean writeNewMenuItemIntoDB(String newItemName, String newItemPrice) {
        try {
            FileWriter menuWriter = new FileWriter( textDB.menuItemsFile, true );
            
            menuWriter.write( System.lineSeparator() );
            
            menuWriter.write( newItemName );
            menuWriter.write( " " );
            menuWriter.write( newItemPrice );

            menuWriter.close();
            
            return true;
            
        } catch(IOException excep) {
            excep.printStackTrace();
            return false;
        }
    }
    
    public boolean writeOrderHistoryIntoDB(ArrayList<CustomerOrder> orderHistory, String dateToPrint, String timeToPrint) {
        try {
            FileWriter orderHistoryWriter = new FileWriter( textDB.orderHistoryFile, true );
            
            orderHistoryWriter.write( System.lineSeparator() );
            orderHistoryWriter.write( dateToPrint );
            orderHistoryWriter.write( System.lineSeparator() );
            orderHistoryWriter.write( timeToPrint );
            orderHistoryWriter.write( System.lineSeparator() );
            
            for( var order : orderHistory ) {
                System.out.println( order.serializeIntoString() );
                orderHistoryWriter.write( order.serializeIntoString() );
                orderHistoryWriter.write( System.lineSeparator() );
            }
            
            orderHistoryWriter.write( System.lineSeparator() );

            orderHistoryWriter.close();
            
            return true;
            
        } catch(IOException excep) {
            excep.printStackTrace();
            return false;
        }
    }
//    public ArrayList<Customer> readALLCustomerInfo() {
//        try {
//            Scanner customerScanner = new Scanner( textDB.customerListFile );
//            
//            ArrayList<Customer> customersInfoArrayList = new ArrayList<>();
//            
//            while( customerScanner.hasNextLine() ){
//                String line = customerScanner.nextLine();
//                
//                String[] splitedLineCustomerInfo = line.split("\\s+");//Copied from StackOverFlow
//                
//                customersInfoArrayList.add( new Customer( splitedLineCustomerInfo[0],
//                                                          splitedLineCustomerInfo[1],
//                                                          splitedLineCustomerInfo[2] ) );
//            }
//            
//            customerScanner.close();
//            
//            return customersInfoArrayList;
//            
//        } catch(IOException excep) {
//            excep.printStackTrace();
//            return null;
//        }
//    }
    
//    public boolean writeOrderHistoryDB() {
//        
//    }
//
//     public boolean writeAllCustomerInfoDB(ArrayList<Customer> customersInfoArrayList) {
//        try {
//            FileWriter customerWriter = new FileWriter( textDB.customerListFile, true );
//            
//            for( Customer currentCus : customersInfoArrayList ) {
//
//                StringBuilder newCustomerLine = new StringBuilder();
//
//                newCustomerLine.append( currentCus.getPhoneNumber()  );
//                newCustomerLine.append(" ");
//                newCustomerLine.append( currentCus.getCustomerName() );
//                newCustomerLine.append(" ");
//                newCustomerLine.append( currentCus.getPointString() );
//            
//                customerWriter.write( newCustomerLine.toString() );
//                customerWriter.write( System.lineSeparator() );
//            }
//            
//            customerWriter.close();
//            
//            return true;
//            
//        } catch(IOException excep) {
//            excep.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean writeAccountInfoDB() {
//        
//    }
}
