
package gui;

import helper.Helper;
import model.Buyer;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OverdueBuyers extends Page {
    protected OverdueBuyers() {
        super("Overdue buyers");
        final Object[] columnHeader = new String[]{"Surname", "Name", "Middlename",
                "Date of birth", "Phone number", "Address"};
        final JRadioButton useName = new JRadioButton("Use name");
        final JLabel nameLabel = new JLabel("Name");
        final JTable buyersTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JLabel countLabel = new JLabel("Count");
        final JLabel countValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(buyersTable);
        final JButton okButton = new JButton("Ok");

        okButton.addActionListener(e -> {

            try {
                final List<Buyer> buyersList =
                        Helper.getAllOverdueBuyers();
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                buyersList.forEach(buyer -> model.addRow(new Object[]{
                        buyer.getSurname(),
                        buyer.getName(),
                        buyer.getMiddleName(),
                        buyer.getDateOfBirth(),
                        buyer.getPhoneNumber(),
                        buyer.getAddress()}));
                buyersTable.setModel(model);
                countValueLabel.setText(String.valueOf(buyersList.size()));
                validateTree();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

        final Container container = getContentPane();
        container.add(pane);
        container.add(countLabel);
        container.add(countValueLabel);
        container.add(okButton);
        container.add(backButton);
    }
}
