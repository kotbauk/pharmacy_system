package gui;

import helper.Helper;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OrdersInProduction extends Page {
    protected OrdersInProduction() {
        super("Orders in production");
        final Object[] columnHeader = new String[]{"Order", "Seller", "Technologist", "Status", "Date of order"};
        final JTable orderTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JLabel countLabel = new JLabel("Count");
        final JLabel countValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(orderTable);

        try {
            final List<Order> orderList = Helper.getOrdersInProduction();
            final DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnHeader);
            orderList.forEach(e -> model.addRow(new Object[]{
                    e.getId(),
                    e.getSeller(),
                    e.getTechnologist(),
                    e.getStatus(),
                    e.getDateOfOrder()}));
            orderTable.setModel(model);
            countValueLabel.setText(String.valueOf(orderList.size()));
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
