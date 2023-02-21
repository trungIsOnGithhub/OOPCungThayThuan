package MenuAndItem;

import java.util.ArrayList;
import TextDataBase.textDB;

/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 * 
 * Class bao chua gom 1 list(ArrayList) cac menuItem
 * Co 1 method de reset Menu, 1 method de lay ra 1 phien ban deep copy cua danh sach menu hien tai.
 */
public class MainMenu {
    // Co rieng mot DB cho MainMenu
    private textDB myDB;
    private ArrayList<MenuItem> mainMenu;
    
    public MainMenu() {
        this.resetMainMenu();
    }

    public void resetMainMenu() {
        this.mainMenu = new ArrayList<>();
        this.myDB = new textDB();
        
        myDB.readMenuFromDB( this.mainMenu );
    }
    public ArrayList<MenuItem> getDeepCopyOfCurrentMainMenu() {
        ArrayList<MenuItem> result = new ArrayList<>();
        
        for(MenuItem item : this.mainMenu) {
            result.add( new MenuItem( item ) );
        }
        
        return result;
    }
}
