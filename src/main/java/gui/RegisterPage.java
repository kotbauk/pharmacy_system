package gui;

import helper.Helper;
import model.Role;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegisterPage extends Page {
    protected RegisterPage() {
        super("Register");
        final JLabel roleLabel = new JLabel("Role");
        final JLabel loginLabel = new JLabel("Login");
        final JLabel passwordLabel = new JLabel("Password");
        final JLabel surnameLabel = new JLabel("Surname");
        final JLabel nameLabel = new JLabel("Name");
        final JLabel middleNameLabel = new JLabel("Middle name");
        final JTextField loginTextField = new JTextField();
        final JTextField passwordTextField = new JTextField();
        final JTextField nameTextField = new JTextField();
        final JTextField surnameTextField = new JTextField();
        final JTextField middleNameTextField = new JTextField();
        final JComboBox<Role> roleComboBox = new JComboBox<>();

        final JButton okButton = new JButton("Ok");
        final JButton backButton = new JButton("Back");

        roleComboBox.addItem(Role.HEAD_OF_PHARMACY);
        roleComboBox.addItem(Role.PHARMACIST_SELLER);
        roleComboBox.addItem(Role.PHARMACIST_TECHNOLOGIST);
        roleComboBox.addItem(Role.DOCTOR);

        okButton.addActionListener(e -> {
            try {
                Helper.register((Role) roleComboBox.getSelectedItem(), loginTextField.getText(), passwordTextField.getText(),
                        surnameTextField.getText(), middleNameTextField.getText(), nameTextField.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Register error " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> new GuiManager(new StartPage()).showPage());

        final Container container = getContentPane();
        container.add(roleLabel);
        container.add(roleComboBox);
        container.add(loginLabel);
        container.add(loginTextField);
        container.add(passwordLabel);
        container.add(passwordTextField);
        container.add(surnameLabel);
        container.add(surnameTextField);
        container.add(middleNameLabel);
        container.add(middleNameTextField);
        container.add(nameLabel);
        container.add(nameTextField);
        container.add(okButton);
        container.add(backButton);

    }
}
