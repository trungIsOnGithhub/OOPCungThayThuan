package MenuAndItem;

import java.util.ArrayList;
import TextDataBase.DBController;
/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 *
 */
abstract class MainMenuIterator {
    private int currentPosition;
    private Collection<MenuItem> cached;

    MainMenuIterator(Collection<MenuItem> currentMenu) {
        this.currentPosition = 0;
        this.cached = currentMenu;
    }

    boolean hasMoreItem() {
        return this.currentPosition < this.cached.size()-1;
    }

    String getNext();
}
class PriceIterator implements MainMenuIterator {
    String getNext() {
        if( this.hasMoreItem() ) {
            ++this.currentPosition;
            return this.cached.get(this.currentPosition).price;
        }
        return null;
    }
}
class NameIterator implements MainMenuIterator {
    String getNext() {
        if( this.hasMoreItem() ) {
            ++this.currentPosition;
            return this.cached.get(this.currentPosition).name;
        }
        return null;
    }
}

public class MainMenu {
    private textDB myDB;
    private ArrayList<MenuItem> mainMenu;

    ArrayList<MenuItem> getShallowCopyOfCurrentMainMenu() {
        return this.mainMenu;
    }
    
    public MainMenu() {
        this.resetMainMenu();
    }

    public void resetMainMenu() {
        this.mainMenu = new ArrayList<>();
        
        DBController.getCurrentDBSession().readMenuFromDB( this.mainMenu );
    }
    public ArrayList<MenuItem> getDeepCopyOfCurrentMainMenu() {
        ArrayList<MenuItem> result = new ArrayList<>();
        
        for(MenuItem item : this.mainMenu) {
            result.add( new MenuItem( item ) );
        }
        
        return result;
    }
}
