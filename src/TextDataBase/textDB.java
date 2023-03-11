package TextDataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import MenuAndItem.MenuItem;
import OrdersAndHistory.CustomerOrder;
import java.util.Date;
/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-HK212
 * 
 */
abstract class AbstractFile {
    private String fileName;
    // private File fileObject;

    public Scanner getFileScanner() {
        try
            { return new Scanner(new File(this.fileName)); }
        // catch(FileNotFoundException exception) {
        //     System.out.println("Kiem tra neu file "+this.fileName+" chua duoc tao");
        //     exception.printStackTrace();
        //     System.exit(1);
        // }
        catch(IOException exception) {
            System.out.println("Co loi khi doc file "+this.fileName);
            exception.printStackTrace();
            System.exit(1);
        }
    }
    public FileWriter getFileWriter(boolean appendable) {
        try
            { return new FileWriter(new File(this.fileName), appendable); }
        // catch(FileNotFoundException exception) {
        //     System.out.println("Kiem tra neu file "+this.fileName+" chua duoc tao");
        //     exception.printStackTrace();
        //     System.exit(1);
        // }
        catch(IOException exception) {
            System.out.println("Co loi khi doc file "+this.fileName);
            exception.printStackTrace();
            System.exit(1);
        }
    }
}

class HistoryFile extends AbstractFile {
    public HistoryFile(String historyFileName) {
        this.fileName = historyFileName;
    }
}

class AccountsFile extends AbstractFile {
    public AccountsFile(String accountsFileName) {
        this.fileName = accountInfoFileName;
    }
}

class CustomersFile extends AbstractFile {
    public CustomersFile(String customersFileName) {
        this.fileName = customersFileName;
    }
}

class MenuFile extends AbstractFile {
    public MenuFile(String menuFileName) {
        this.fileName = menuFileName;
    }
}

abstract class AbstractFileFactory {
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
    final static String logFileName = "log_db.txt";
    final static String configFileName = "config.txt";
    final static int numLineInConfigFile = 4;

    // we will only create the abstract File object on local for saving memory
    // Because we only need those for limited number of time
    // private class 

    // We still enable access from outside to config info of Database, but limited to read-only
    public static class ConfigDBData {
        private String orderHistoryRecordFileName;
        private String customersListFileName;
        private String accountsInfoFileName;
        private String menuItemsFileName;

        public String getConfigFileName(String type) {
            if( type.equalsIgnoreCase("history") )
                return orderHistoryRecordFileName;
            if( type.equalsIgnoreCase("customers") )
                return customersListFileName;
            if( type.equalsIgnoreCase("accounts") )
                return accountsInfoFileName;
            if( type.equalsIgnoreCase("menu") )
                return menuItemsFileName;
            return null;
        }
    }

    // get config DB data by writing config file
    private static boolean setDBConfigData() {
        return false;
    }
    // get config DB data by reading config file
    public static ConfigDBData getDBConfigData() {
        Scanner scanner = null;

        try
            { scanner = new Scanner(new File(configFileName)); }
        catch(IOException exception) {
            System.out.println("Co loi khi doc file "+configFileName);
            exception.printStackTrace();
            System.exit(1);
        }

        if(scanner == null)
            System.exit(1);

        ConfigDBData configDBData = new ConfigDBData();

        int numOfLineInput = 0;
        while(numOfLineInput < numLineInConfigFile && scanner.hasNext()) {
            // String inputLine = scanner.nextLine();
            // Its okay to pass the above line and read directly as we assumed our config file is well-formatted

            if(numOfLineInput == 0)
                configDBData.orderHistoryRecordFileName = scanner.nextLine();
            else if(numOfLineInput == 1)
                configDBData.customersListFileName = scanner.nextLine();
            else if(numOfLineInput == 2)
                configDBData.accountsInfoFileName = scanner.nextLine();
            else
                configDBData.menuItemsFileName = scanner.nextLine();

            ++numOfLineInput;
        }

        if(numOfLineInput < numLineInConfigFile)
            System.exit(1);// ill-formatted config file

        scanner.close();

        return configDBData;
    }
}

/*** SINGLETON CLASS ***/
public class DBController {
    //Singleton, One and Only Object
    private DBController currentSession;
    
    private Date sessionInitTime;
    private configDBData configData;

    public static DBController getCurrentDBSession() {
        if( this.currentSession == null ) {
            this.currentSession = new DBController();
        }
        return this.currentSession;
    }

    private DBController(Date sessionInitTime) {
        this.sessionInitTime = sessionInitTime;// dependency injection
        this.configData = DBFunctionalHelper.getDBConfigData();
    }
    
    public boolean checkLoginRequest(String username, String password) {
        Scanner accountsScanner = AbstractFileFactory.getFile("accounts",
                                        this.configData.getConfigFileName("accounts"))
                                    .getFileScanner();
        
        boolean isSuccess = false;
        
        while( accountsScanner.hasNextLine() ){
            String lineInput = accountsScanner.nextLine();
            
            int usernamePos = lineInput.indexOf( username );
            int passwordPos = -1;
            
            if(usernamePos != -1)
                passwordPos = lineInput.indexOf( password );
            if(usernamePos != -1 && passwordPos != -1) {
                isSuccess = true;
                break;
            }
        }
        
        accountsScanner.close();
        
        return isSuccess;
    }

    public boolean readMenuFromDB(ArrayList<MenuItem> mainMenu) {
        Scanner accountsScanner = AbstractFileFactory.getFile("menu",
                                this.configData.getConfigFileName("menu"))
                            .getFileScanner();
        // // No need??       
        // if( scanner == null ) {
        //     System.out.println("Loi khoi tao trinh doc file");
        //     return false;
        // }

        mainMenu.clear();// make sure the main menu array is empty to ensure exact output

        String menuItemName = "", menuItemPriceStr = "";

        boolean readModeSwitchedItemName = true;
        while( scanner.hasNext() ) {
            if( readModeSwitchedItemName ) {
                menuItemName = scanner.next();
            }
            else { // if( readMode switched to item price
                mainMenu.add( new MenuItem(menuItemName,menuItemPriceStr) );
                menuItemPriceStr = scanner.next();
            }
            readModeSwitchedItemName = !readModeSwitchedItemName;
        }
        scanner.close();

        return true;
    }

    public boolean writeNewMenuItemIntoDB(String newItemName, String newItemPrice) {
        FileWriter menuWriter = AbstractFileFactory.getFile("menu",
                                this.configData.getConfigFileName("menu"))
                            .getFileWriter(true);
            
        menuWriter.write(System.lineSeparator());
        
        menuWriter.write(newItemName);
        menuWriter.write(" ");
        menuWriter.write(newItemPrice);

        menuWriter.close();
        
        return true;// still need to return boolean as for further code, where writing data can be failed
    }
    
    public boolean writeOrderHistoryIntoDB(ArrayList<CustomerOrder> orderHistory, String dateToPrint, String timeToPrint) {
        FileWriter orderHistoryWriter = AbstractFileFactory.getFile("history",
                                this.configData.getConfigFileName("history"))
                            .getFileWriter(true);
        
        orderHistoryWriter.write(System.lineSeparator());
        orderHistoryWriter.write(dateToPrint);
        orderHistoryWriter.write(System.lineSeparator());
        orderHistoryWriter.write(timeToPrint);
        orderHistoryWriter.write(System.lineSeparator());
        
        for(var order : orderHistory) {//using var keyword to avoid changing code
            orderHistoryWriter.write(order.serializeIntoString());
            orderHistoryWriter.write(System.lineSeparator());
        }
        orderHistoryWriter.write(System.lineSeparator());

        orderHistoryWriter.close();
        
        return true;
    }
}