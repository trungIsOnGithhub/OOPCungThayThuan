//Java Program to create a text editor using java

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
	
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


class Client extends JFrame implements ActionListener, KeyListener {

    public static Socket socClient;
    public static ObjectInputStream ClientInput;
    public static ObjectOutputStream ClientOutput;

    public String SelectedText;
    public String ClientIDToShare;

    // Text component
    JTextArea textArea;
    // Frame
    JFrame frame;

    public static void main(String[] args) {
        try {
            socClient = new Socket("localhost", 9999); // named argument

            System.out.println("Connected!");

            Client c1 = new Client();
            Scanner scn = new Scanner(System.in);
            ClientOutput = new ObjectOutputStream(socClient.getOutputStream());
            ClientInput = new ObjectInputStream(socClient.getInputStream());

            System.out.print("Write your ID : ");
            String id = scn.nextLine();
            ClientOutput.writeUTF(id);
            ClientOutput.flush();

            System.out.println("Write the name for your frame");
            String filename = scn.nextLine();

            c1.ClientGUI(filename);

            System.out.print("Now You Start your Real Connection");

            while (true) {
                String NewDataInTextArea = ClientInput.readUTF();
                c1.ChangeText(NewDataInTextArea);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    @SuppressWarnings("deprecation")
	public void ClientGUI(String str) {
        // Create a frame
        frame = new JFrame(str);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {	
            e.printStackTrace();
        }

        // Text component
        textArea = new JTextArea();
        textArea.setLineWrap(true);

        // Create a menu bar
        JMenuBar mb = new JMenuBar();

        // Create a menu for menu
        JMenu m1 = new JMenu("File");

        // Create menu items
        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi10 = new JMenuItem("Share");


        // Add action listener
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi10.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi10);

        // Create a menu for menu
        JMenu m2 = new JMenu("Edit");

        // Create menu items
        JMenuItem mi4 = new JMenuItem("cut");
        JMenuItem mi5 = new JMenuItem("copy");
        JMenuItem mi6 = new JMenuItem("paste");

        // Add action listener
        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);

        JMenuItem mc = new JMenuItem("close");

        mc.addActionListener(this);

        mb.add(m1);
        mb.add(m2);
        mb.add(mc);

        frame.setJMenuBar(mb);
        frame.add(textArea);
        frame.setSize(500, 500);
        frame.show();

        textArea.addKeyListener(this);
    }

    public void ChangeText(String str) {
        textArea.setText(str);
    }

    // If a button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("cut")) {
            textArea.cut();
        }
        else if (s.equals("copy")) {
            textArea.copy();
        }
        else if (s.equals("paste")) {
            textArea.paste();
        }
        else if (s.equals("Save")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsSaveDialog function to show the save dialog
            int r;
            r = j.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {

                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    // Create a file writer that doesn't append
                    FileWriter wr = new FileWriter(fi, false);

                    // Create buffered writer to write
                    BufferedWriter w = new BufferedWriter(wr);

                    // Write
                    w.write(textArea.getText());

                    w.flush();
                    w.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else {
                JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
            }
        }
        else if (s.equals("Open")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsOpenDialog function to show the save dialog
            int r;
            r = j.showOpenDialog(null);

            // If the user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    // String
                    String s2;

                    // File reader
                    FileReader fr = new FileReader(fi);

                    // Buffered reader
                    BufferedReader br = new BufferedReader(fr);

                    // Initailize sl
                    String sl = br.readLine();

                    // Take the input from the file
                    while ((s2 = br.readLine()) != null) {
                        sl = sl + "\n" + s2;
                    }

                    // Set the text
                    textArea.setText(sl);
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else {
                JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
            }
        }
        else if (s.equals("New")) {
            textArea.setText("");
        }
        else if (s.equals("close")) {
            frame.setVisible(false);
            try {
                ClientInput.close();
                socClient.close();
                ClientOutput.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (s.equals("Share")) {
            try {
                ClientOutput.writeUTF("Share");
                ClientOutput.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            ClientIDToShare = JOptionPane.showInputDialog("Enter the ID's of the Client to send this text..");
            try {
                ClientOutput.writeUTF(ClientIDToShare);
                ClientOutput.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }

    public void keyReleased(KeyEvent e) {
        SelectedText = textArea.getText();

        try {
            ClientOutput.writeUTF(SelectedText);
            ClientOutput.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    // for further development
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {}
}