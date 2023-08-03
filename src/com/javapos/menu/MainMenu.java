package com.javapos.menu;

import java.util.Iterator;
import java.util.List;

import com.javapos.models.DBController;

import java.util.ArrayList;
/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 *
 */
 abstract class MainMenuIterator implements Iterator {
    protected int currentPosition;
    protected List<MenuItem> cached;

    MainMenuIterator(List<MenuItem> currentMenu) {
        this.currentPosition = 0;
        this.cached = currentMenu;
    }

    public final boolean hasNext() {
        return this.currentPosition < this.cached.size();
    }
}
class PriceIterator extends MainMenuIterator {
	PriceIterator(List<MenuItem> currentMenu) {
		super(currentMenu);
    }

    public Integer next() {
        if( this.hasNext() ) {
            return this.cached.get(this.currentPosition++).priceOfItem;
        }
        return null;
    }
}
class NameIterator extends MainMenuIterator {
	NameIterator(List<MenuItem> currentMenu) {
		super(currentMenu);
    }

    public String next() {
        if( this.hasNext() ) {
            return this.cached.get(this.currentPosition++).nameOfItem;
        }
        return null;
    }
}
class ItemIterator extends MainMenuIterator {
	ItemIterator(List<MenuItem> currentMenu) {
		super(currentMenu);
    }

    public String next() {
        if( this.hasNext() ) {
            return this.cached.get(this.currentPosition++).serializeIntoString();
        }
        return null;
    }
}

public class MainMenu {
    private DBController myDB;
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

    public MainMenuIterator createIterator(String type) {
        if( type.equalsIgnoreCase("item") )
            return new ItemIterator(this.mainMenu);
        if( type.equalsIgnoreCase("name") )
            return new NameIterator(this.mainMenu);
        if( type.equalsIgnoreCase("price") )
            return new PriceIterator(this.mainMenu);

        return null;
    }
}
