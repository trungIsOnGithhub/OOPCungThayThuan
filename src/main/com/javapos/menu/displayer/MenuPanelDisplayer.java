package com.javapos.menu.displayer;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.javapos.menu.MainMenu;
import com.javapos.menu.MenuItem;
/*
 *
 * @author Nguyen Viet Trung
 *
 */
public class MenuPanelDisplayer {
    private JPanel mainMenuPanel;
    private ArrayList<MenuItemDisplayer> mainMenuDisplayerList;
    private MainMenu mainMenu;

    public MenuPanelDisplayer(JPanel UIPanel, MainMenu mainMenu) {// UIPanel maybe any chosen panel from pre-designed UI
        this.mainMenuPanel = UIPanel;
        
        this.mainMenu = mainMenu;
        
        this.mainMenuDisplayerList = new ArrayList<>();
    }
    
    public ArrayList<MenuItemDisplayer> getMainMenuDisplayerList() {
        return this.mainMenuDisplayerList;
    }

//    public void copyMainMenu(ArrayList<MenuItem> mainMenu) {
//        // Create a new copy for main menu
//        this.mainMenu = new MainMenu();
//        
//        if( mainMenu == null )
//            { return; }
//        
//        for( MenuItem item : mainMenu ) {
//            this.mainMenu.add( new MenuItem( item ) );
//        }
//    }
    public void updateMenuPanelDisplayer() {        
        this.mainMenuPanel.setLayout( new BoxLayout(this.mainMenuPanel,BoxLayout.Y_AXIS) );//Set mutual layout for big container
        this.mainMenuDisplayerList.clear();

        NameIterator menuIterator = this.mainMenu.createIterator("name");
        
        while( menuIterator.hasNext() ) {
            MenuItemDisplayer itemDisplayer = new MenuItemDisplayer( menuIterator.next() );

            this.mainMenuPanel.add( itemDisplayer.getJPanel() );
            this.mainMenuDisplayerList.add( itemDisplayer );
        }
    }
}

