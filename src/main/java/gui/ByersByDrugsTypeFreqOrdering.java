
package gui;

import helper.Helper;
import model.Buyer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ByersByDrugsTypeFreqOrdering extends Page {
    protected ByersByDrugsTypeFreqOrdering() {
        super("Top buyers ordering");
        final Object[] columnHeader = new String[]{"Surname", "Name", "Middlename",
                "Date of birth", "Phone number", "Address"};
        final JRadioButton useName = new JRadioButton("Use name");
        final JRadioButton useSpecificCategory = new JRadioButton("By specific category of drug");
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(useName);
        buttonGroup.add(useSpecificCategory);

        final JLabel nameLabel = new JLabel("Name");
        final JTextField nameTextField = new JTextField();
        final JLabel categoryLabel = new JLabel("Category");
        final JComboBox<model.Type> categoryCheckBox = new JComboBox<>();
        final JTable orderTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JLabel countValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(orderTable);
        final JButton okButton = new JButton("Ok");

        for(model.Type type : model.Type.values()) {
            categoryCheckBox.addItem(type);
        }

        okButton.addActionListener(e -> {
            try {
                final List<Buyer> buyersList = useName.isSelected() ?
                        Helper.getBuyerBySpecificDrugFreqOrdering(nameTextField.getText()):
                        Helper.getBuyerBySpecificDrugsTypeFreqOrdering((model.Type) categoryCheckBox.getSelectedItem());
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                buyersList.forEach(buyer -> model.addRow(new Object[]{
                        buyer.getSurname(),
                        buyer.getName(),
                        buyer.getMiddleName(),
                        buyer.getDateOfBirth(),
                        buyer.getPhoneNumber(),
                        buyer.getAddress()}));
                orderTable.setModel(model);
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
        container.add(useName);
        container.add(useSpecificCategory);
        container.add(nameLabel);
        container.add(nameTextField);
        container.add(categoryLabel);
        container.add(categoryCheckBox);
        container.add(pane);
        container.add(okButton);
        container.add(backButton);
    }
}
