package gui;

import javax.swing.*;
import java.awt.*;

public class StartPage extends Page {
    public StartPage() {
        super("Start");
        final JButton loginButton = new JButton("LOGIN");
        final JButton registerButton = new JButton("REGISTER");
        Container container = getContentPane();

        loginButton.addActionListener(e -> new GuiManager(new LoginPage()).showPage());
        registerButton.addActionListener(e -> new GuiManager(new RegisterPage()).showPage());

        container.add(loginButton);
        container.add(registerButton);
    }
}
