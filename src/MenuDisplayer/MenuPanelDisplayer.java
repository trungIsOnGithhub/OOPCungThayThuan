package MenuDisplayer;

import MenuAndItem.MenuItem;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Nguyen Viet Trung
 */
public class MenuPanelDisplayer {
    private JPanel mainMenuPanel;
    private ArrayList<MenuItemDisplayer> mainMenuDisplayerList;
    private ArrayList<MenuItem> mainMenu;

    public MenuPanelDisplayer(JPanel UIPanel, ArrayList<MenuItem> mainMenu) {// UIPanel maybe any chosen panel from pre-designed UI
        this.mainMenuPanel = UIPanel;
        
        this.copyMainMenu( mainMenu );
        
        this.mainMenuDisplayerList = new ArrayList<>();
    }
    
    public ArrayList<MenuItemDisplayer> getMainMenuDisplayerList() {
        return this.mainMenuDisplayerList;
    }

    public void copyMainMenu(ArrayList<MenuItem> mainMenu) {
        // Create a new copy for main menu
        this.mainMenu = new ArrayList<>();
        
        if( mainMenu == null )
            { return; }
        
        for( MenuItem item : mainMenu ) {
            this.mainMenu.add( new MenuItem( item ) );
        }
    }
    public void updateMenuPanelDisplayer() {        
        this.mainMenuPanel.setLayout( new BoxLayout(this.mainMenuPanel,BoxLayout.Y_AXIS) );//Set mutual layout for big container
        this.mainMenuDisplayerList.clear();
        
        for( MenuItem item : mainMenu ) {
            MenuItemDisplayer itemDisplayer = new MenuItemDisplayer( item );
        
            this.mainMenuPanel.add( itemDisplayer.getJPanel() );
            this.mainMenuDisplayerList.add( itemDisplayer );
        }
    }
}

