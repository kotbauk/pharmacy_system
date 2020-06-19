package gui;

import javax.swing.*;
import java.awt.*;

public class MenuPage extends Page {
    protected MenuPage() {
        super("Menu");
        final JButton reportButton = new JButton("Report");
        final Container container = getContentPane();

        reportButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());
        container.add(reportButton);
    }
}
