package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginPage extends Page {
    protected LoginPage() {
        super("Login");
        final JLabel loginLabel = new JLabel("Login");
        final JLabel passwordLabel = new JLabel("Password");
        final JTextField loginTextField = new JTextField();
        final JTextField passwordTextField = new JTextField();
        final JButton okButton = new JButton("Ok");
        final JButton backButton = new JButton("Back");

        okButton.addActionListener(e -> {
            try {
                if (GuiManager.signIn(loginTextField.getText(), passwordTextField.getText())) {
                    new GuiManager(new MenuPage()).showPage();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Signing in error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Signing in error " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> new GuiManager(new StartPage()).showPage());

        final Container container = getContentPane();
        container.add(loginLabel);
        container.add(loginTextField);
        container.add(passwordLabel);
        container.add(passwordTextField);
        container.add(okButton);
        container.add(backButton);
    }
}
