package gui;

import javax.swing.*;
import java.awt.*;

public class MenuPage extends Page {
    protected MenuPage() {
        super("Menu");
        final JButton reportButton = new JButton("Report");
        final JButton backButton = new JButton("Back");
        final Container container = getContentPane();

        reportButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());
        backButton.addActionListener(e->new GuiManager(new LoginPage()).showPage());
        container.add(reportButton);
        container.add(backButton);
    }
}
