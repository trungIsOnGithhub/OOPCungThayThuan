package MenuAndItem;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import TextDataBase.DBController;
/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 *
 */
abstract class MainMenuIterator implements Iterator {
    private int currentPosition;
    private List<MenuItem> cached;

    MainMenuIterator(List<MenuItem> currentMenu) {
        this.currentPosition = 0;
        this.cached = currentMenu;
    }

    public final boolean hasNext() {
        return this.currentPosition < this.cached.size();
    }
}
class PriceIterator implements MainMenuIterator {
    public String getNext() {
        if( this.hasMoreItem() ) {
            return this.cached.get(this.currentPosition++).price;
        }
        return null;
    }
}
class NameIterator implements MainMenuIterator {
    public String getNext() {
        if( this.hasMoreItem() ) {
            return this.cached.get(this.currentPosition++).name;
        }
        return null;
    }
}
class ItemIterator implements MainMenuIterator {
    public String next() {
        if( this.hasMoreItem() ) {
            return this.cached.get(this.currentPosition++).serializeIntoString();
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

    public Iterator createIterator(String type) {
        if( type.equalsIgnoreCase("item") )
            return new ItemIterator(fileName);
        if( type.equalsIgnoreCase("name") )
            return new NameIterator(fileName);
        if( type.equalsIgnoreCase("price") )
            return new PriceIterator(fileName);

        return null;
    }
}
