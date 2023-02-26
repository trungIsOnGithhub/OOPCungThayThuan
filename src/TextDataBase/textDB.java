package TextDataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import MenuAndItem.MenuItem;
import OrdersAndHistory.CustomerOrder;
// import java.text.SimpleDateFormat;
import java.util.Date;
/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 * 
 * Su dung 1 tap hop cac file txt lam noi luu tru du lieu lau dai cho ung dung, nhu la mot database.
 * 2 method doc menu tu file de render thanh UI va viet them mon vao menu.
 * 1 method ghi lich su don hang vao file txt.
 * 1 method kiem tra yeu cau nhap ten va mat khau(dang cho phat trien)
 */
abstract class AbstractFile {
    private String fileName;
    private File fileObject;
    // private Scanner scanner;
    // private FileWriter writer;

    public Scanner getFileScanner() {
        try
            { return new Scanner( new File(this.fileName) ); }
        catch(FileNotFoundException exception) {
            System.out.println("Kiem tra neu file "+this.fileName+" chua duoc tao");
            exception.printStackTrace();
            System.exit();
        }
        catch(IOException exception) {
            System.out.println("Co loi khi doc file "+this.fileName);
            exception.printStackTrace();
            System.exit();
        }
    }
    public FileWriter getFileWriter() {
        try
            { return new FileWriter( new File(this.fileName), true ); }
        catch(FileNotFoundException exception) {
            System.out.println("Kiem tra neu file "+this.fileName+" chua duoc tao");
            exception.printStackTrace();
            System.exit();
        }
        catch(IOException exception) {
            System.out.println("Co loi khi doc file "+this.fileName);
            exception.printStackTrace();
            System.exit();
        }
    }
}

public class HistoryFile extends AbstractFile {
    public HistoryFile(String historyFileName) {
        this.fileName = historyFileName;
    }
}

public class AccountsFile extends AbstractFile {
    public AccountsFile(String accountsFileName) {
        this.fileName = accountInfoFileName;
    }
}

public class CustomersFile extends AbstractFile {
    public CustomersFile(String customersFileName) {
        this.fileName = customersFileName;
    }
}

public class MenuFile extends AbstractFile {
    public MenuFile(String menuFileName) {
        this.fileName = menuFileName;
    }
}

public abstract class AbstractFileFactory {
    public static AbstractFile getFile(String type, String fileName) {
        if( type.equalsIgnoreCase("history") )
            return new HistoryFile(fileName);
        if( type.equalsIgnoreCase("customers") )
            return new CustomersFile(fileName);
        if( type.equalsIgnoreCase("accounts") )
            return new AccountsFile(fileName);
        if( type.equalsIgnoreCase("menu") )
            return new MenuFile(fileName);
        return null;
    }
}

abstract class DBFunctionalHelper {
    final static logFileName = "log_db.txt";
    final static configFileName = "config.txt";

    // We still enable access from outside to config info of Database, but limited to read-only
    public class configDBData {
        private static String orderHistoryRecordFileName = "orderHistory.txt";
        private static String customersListFileName = "customersList.txt";
        private static String accountsInfoFileName = "accountsInfo.txt";
        private static String menuItemsFileName = "menuItems.txt";
    }

    // we will only create the abstract File object on local for saving memory
    // Because we only need those for limited number of time

    // get config DB data by writing config file
    private static boolean resetDBConfigData() {
        Scanner sc = null;
        try { sc = new Scanner( new File(this.configFileName) ); }
        catch(FileNotFoundException exception) {
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
    // get config DB data by reading config file
    public static configDBData getDBConfigData() {
        return null;
    }
}

/*** SINGLETON CLASS ***/
public class DBController {
    //Singleton, One and Only Class
    private DBController currentSession;

    public static textDB getCurrentDBSession() {
        if( this.currentSession == null ) {
            this.currentSession = new DBController();
        }
        return this.currentSession;
    }

    private DBController(Date sessionInitTime) {
        this.sessionInitTime = sessionInitTime;// dependency injection
        this.configData = new configData();
    }

    private Date sessionInitTime;

    private configDBData configData;

    // private String orderHistoryRecordFileName = "orderHistory.txt";
    // private File orderHistoryFile;
    
    // private String customersListFileName = "customersList.txt";
    // private File customerListFile;

    // private String accountsInfoFileName = "accountsInfo.txt";
    // private File accountsInfoFile;

    // private String menuItemsFileName = "menuItems.txt";
    // private File menuItemsFile;
    
    public boolean checkLoginRequest(String username, String password) {
        Scanner accountsScanner = AbstractFileFactory.getFile("accounts").getFileScanner();
        
        boolean result = false;
        
        while( accountsScanner.hasNextLine() ){
            String lineInput = accountsScanner.nextLine();
            
            int usernamePos = lineInput.indexOf( username );
            int passwordPos = -1;
            
            if( usernamePos != -1 )
                { passwordPos = lineInput.indexOf( password ); }
            
            if( usernamePos != -1 && passwordPos != -1 )
                { result = true; break; }
        }
        
        accountsScanner.close();
        
        return result;
    }

    public boolean readMenuFromDB(ArrayList<MenuItem> mainMenu) {
        Scanner scanner = AbstractFileFactory.getFile("menu").getFileScanner();

        // // No need??       
        // if( scanner == null ) {
        //     System.out.println("Loi khoi tao trinh doc file");
        //     return false;
        // }

        mainMenu.clear();

        String tenMon = "";
        String giaCaStr = "";

        boolean readModeSwitcher = true;

        while( scanner.hasNext() ) {
            if( readModeSwitcher ) {
                tenMon = scanner.next();
            }
            else {
                mainMenu.add( new MenuItem(tenMon,giaCaStr) );
                giaCaStr = scanner.next();
            }
            readModeSwitcher = !readModeSwitcher;
        }
        scanner.close();

        return true;
    }
    public boolean writeNewMenuItemIntoDB(String newItemName, String newItemPrice) {
        FileWriter menuWriter = AbstractFileFactory.getFile("menu").getFileWriter();
            
        menuWriter.write( System.lineSeparator() );
        
        menuWriter.write( newItemName );
        menuWriter.write( " " );
        menuWriter.write( newItemPrice );

        menuWriter.close();
        
        return true;
    }
    
    public boolean writeOrderHistoryIntoDB(ArrayList<CustomerOrder> orderHistory, String dateToPrint, String timeToPrint) {
        FileWriter orderHistoryWriter = AbstractFileFactory.getFile("history").getFileWriter();
        
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
    }
}