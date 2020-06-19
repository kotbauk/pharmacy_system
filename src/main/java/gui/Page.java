package gui;

import javax.swing.*;
import java.awt.*;

public abstract class Page extends JFrame {
    protected final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    protected Page(String name) {
        super(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height);
        getContentPane().setLayout(new GridLayout(0,1));
    }
}
