
package gui;

import helper.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ComponentsNeededForOrdersPage extends Page {
    protected ComponentsNeededForOrdersPage() {
        super("Components needed for order");
        final Object[] columnHeader = new String[]{"Component name"};
        final JTable componentsTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JLabel countValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(componentsTable);
        final JButton okButton = new JButton("Ok");

        okButton.addActionListener(e -> {

            try {
                final List<model.Component> componentsList =
                        Helper.getComponentsNeededForOrders();
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                componentsList.forEach(component -> model.addRow(new Object[]{component.getName()}));
                componentsTable.setModel(model);
                countValueLabel.setText(String.valueOf(componentsList.size()));
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
        container.add(countValueLabel);
        container.add(okButton);
        container.add(backButton);
    }
}
