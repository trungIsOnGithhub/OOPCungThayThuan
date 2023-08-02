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
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-HK212
 */
abstract class AbstractFile {
    protected String fileName;
    protected FileWriter fileWriter;
    // private File fileObject;

    abstract boolean appendToFile(String[] args);

    public Scanner getFileScanner() {
        Scanner scanner = null;
        try
            { scanner = new Scanner(new File(this.fileName)); }
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
        return scanner;
    }
    public void setUpFileWriter(boolean appendable) {
        try
            { this.fileWriter = new FileWriter(new File(this.fileName), appendable); }
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

    boolean appendToFile(String[] args) {
        if(args.length != 3) return false;

        this.setUpFileWriter(true);

        String orderInfoString = args[0],
                dateToPrint = args[1],
                timeToPrint = args[2];
        try {
            // this.fileWriter.write(System.lineSeparator());
            this.fileWriter.write(dateToPrint);
            this.fileWriter.write(System.lineSeparator());
            this.fileWriter.write(timeToPrint);
            this.fileWriter.write(System.lineSeparator());
            this.fileWriter.write(orderInfoString);
            this.fileWriter.write(System.lineSeparator());

            this.fileWriter.close();
        }
        catch(IOException exception) {
            System.out.println("Co loi khi doc file "+this.fileName);
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}

class AccountsFile extends AbstractFile {
    public AccountsFile(String accountsInfoFileName) {
        this.fileName = accountsInfoFileName;
    }

    boolean appendToFile(String[] args) {
        return false;
    }
}

class CustomersFile extends AbstractFile {
    public CustomersFile(String customersFileName) {
        this.fileName = customersFileName;
    }

    boolean appendToFile(String[] args) {
        return false;
    }
}

class MenuFile extends AbstractFile {
    public MenuFile(String menuFileName) {
        this.fileName = menuFileName;
    }

    boolean appendToFile(String[] args) {
        if(args.length != 2) return false;

        this.setUpFileWriter(true);

        String newItemName = args[0],
                newItemPrice = args[1];

        try {
            // this.fileWriter.write(System.lineSeparator());
            this.fileWriter.write(newItemName);
            this.fileWriter.write(" ");
            this.fileWriter.write(newItemPrice);

            this.fileWriter.write(System.lineSeparator());

            this.fileWriter.close();
        }
        catch(IOException exception) {
            System.out.println("Co loi khi doc file "+this.fileName);
            exception.printStackTrace();
            return false;
        }
        return true;
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
    private static DBController currentSession;
    
    private Date sessionInitTime;
    private DBFunctionalHelper.ConfigDBData configData;

    public static DBController getCurrentDBSession() {
        if( currentSession == null ) {
            currentSession = new DBController(new Date());
        }
        return currentSession;
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
        while( accountsScanner.hasNext() ) {
            if( readModeSwitchedItemName ) {
                menuItemName = accountsScanner.next();
            }
            else { // if( readMode switched to item price
                mainMenu.add( new MenuItem(menuItemName,menuItemPriceStr) );
                menuItemPriceStr = accountsScanner.next();
            }
            readModeSwitchedItemName = !readModeSwitchedItemName;
        }
        accountsScanner.close();

        return true;
    }

    public boolean writeNewMenuItemIntoDB(String newItemName, String newItemPrice) {
        AbstractFile currentUseFile = AbstractFileFactory.getFile("menu",
                                this.configData.getConfigFileName("menu"));

        currentUseFile.appendToFile( new String[] {newItemName, newItemPrice} );
        
        return true;// still need to return boolean as for further code, where writing data can be failed
    }
    
    public boolean writeOrderHistoryIntoDB(String orderInfoString, String dateToPrint, String timeToPrint) {
        AbstractFile currentUseFile = AbstractFileFactory.getFile("history",
                                this.configData.getConfigFileName("history"));

        currentUseFile.appendToFile( new String[] {orderInfoString, dateToPrint, timeToPrint} );
        
        return true;
    }
}