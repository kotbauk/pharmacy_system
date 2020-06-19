package gui;

import helper.Helper;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BuyersByMedicine extends Page {
    protected BuyersByMedicine() {
        super("Buyers by medicine");
        final Object[] columnHeader = new String[]{"Buyer", "Medicine"};
        final JRadioButton useName = new JRadioButton("Use name");
        final JRadioButton useCategory = new JRadioButton("Use category");
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(useName);
        buttonGroup.add(useCategory);

        final JLabel nameLabel = new JLabel("Name");
        final JTextField nameTextField = new JTextField();
        final JLabel categoryLabel = new JLabel("Category");
        final JComboBox<model.Type> categoryCheckBox = new JComboBox<>();
        final JTable orderTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JLabel countLabel = new JLabel("Count");
        final JLabel countValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(orderTable);
        final JButton okButton = new JButton("Ok");

        categoryCheckBox.addItem(model.Type.TINCTURES);
        categoryCheckBox.addItem(model.Type.POTIONS);
        categoryCheckBox.addItem(model.Type.SOLUTE);
        categoryCheckBox.addItem(model.Type.POWDERS);
        categoryCheckBox.addItem(model.Type.PILLS);
        categoryCheckBox.addItem(model.Type.OINTMENTS);

        okButton.addActionListener(e -> {

            try {
                final List<Order> orderList = useName.isSelected() ?
                        Helper.getBuyersByMedicineName(nameTextField.getText()) :
                        Helper.getBuyersByCategory((model.Type) categoryCheckBox.getSelectedItem());
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                orderList.forEach(o -> model.addRow(new Object[]{o.getRecipe().getBuyer(), o.getRecipe().getMedicine()}));
                orderTable.setModel(model);
                countValueLabel.setText(String.valueOf(orderList.size()));
                validateTree();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

        final Container container = getContentPane();
        container.add(useName);
        container.add(useCategory);
        container.add(nameLabel);
        container.add(nameTextField);
        container.add(categoryLabel);
        container.add(categoryCheckBox);
        container.add(pane);
        container.add(countLabel);
        container.add(countValueLabel);
        container.add(okButton);
        container.add(backButton);
    }
}
