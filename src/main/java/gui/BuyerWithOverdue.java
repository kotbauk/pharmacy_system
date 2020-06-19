package gui;

import helper.Helper;
import model.Buyer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerWithOverdue extends Page {

    protected BuyerWithOverdue() {
        super("Buyer with overdue");
        final Object[] columnHeader = new String[]{"Buyer"};
        final JTable buyerTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JLabel countLabel = new JLabel("Count");
        final JLabel countValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(buyerTable);

        try {
            final List<Buyer> buyerList = new ArrayList<>(Helper.getBuyersWithOverdue());
            final DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnHeader);
            buyerList.forEach(e -> model.addRow(new Object[]{e}));
            buyerTable.setModel(model);
            countValueLabel.setText(String.valueOf(buyerList.size()));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

        final Container container = getContentPane();
        container.add(pane);
        container.add(countLabel);
        container.add(countValueLabel);
        container.add(backButton);
    }
}
