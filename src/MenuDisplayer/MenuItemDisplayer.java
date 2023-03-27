package MenuDisplayer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
/*
 *
 * @author nhom NullPointerException: Trung Nguyen va nhung nguoi ban, Lap trinh nang cao-L02-HK212
 *
 */
public class MenuItemDisplayer {
    private JPanel mainContainer;
    private JButton leftButton;
    private JSpinner rightSpinner;
    
    public MenuItemDisplayer(String menuItemInString) {
        mainContainer = new JPanel();

        leftButton = new JButton( menuItemInString );
        //need only string for a button so we only provide a string
        
        SpinnerNumberModel jSpinnerModel = new SpinnerNumberModel(0,-1,69,1);
        rightSpinner = new JSpinner( jSpinnerModel );
        
        mainContainer.setLayout( new FlowLayout() );
        mainContainer.add( leftButton );
        mainContainer.add( rightSpinner );
        
        // HIeu ung khi het mon an.
        this.leftButton.addActionListener( new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent eve) {
//                try { // Doan code duoi day dam bao update gia tri cua Spinner thanh gia tri moi nhat
//                    rightSpinner.commitEdit();
//                } catch(java.text.ParseException e) {
//                    System.out.println("Error commit Spinner value.");
//                    return;
//                }
//                
                if( (Integer)rightSpinner.getValue() == -1 ) {
                    leftButton.setEnabled( false );
                    rightSpinner.setEnabled( false );
                }
            }
        } );
    }
    
    public JPanel getJPanel(){
        return this.mainContainer;
    }
    public int getValueFromSpinner() {
        try {  // Doan code duoi day dam bao update gia tri cua Spinner thanh gia tri moi nhat
            rightSpinner.commitEdit();
        } catch(java.text.ParseException e) {
            System.out.println("Error commit Spinner value.");
            return -1;
        }
        
        return (Integer)rightSpinner.getValue();
    }
    public String getText() {
        return leftButton.getText();
    }
}