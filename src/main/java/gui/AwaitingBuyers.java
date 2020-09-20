
package gui;

import helper.Helper;
import model.Buyer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AwaitingBuyers extends Page {
    protected AwaitingBuyers() {
        super("Awaiting Buyers");
        final Object[] columnHeader = new String[]{"Surname", "Name", "Middlename",
                "Date of birth", "Phone number", "Address"};
        final JRadioButton useAllCategory = new JRadioButton("All category");
        final JRadioButton useSpecificCategory = new JRadioButton("By specific category of drug");
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(useAllCategory);
        buttonGroup.add(useSpecificCategory);

        final JLabel categoryLabel = new JLabel("Category");
        final JComboBox<model.Type> categoryCheckBox = new JComboBox<>();
        final JTable orderTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JLabel countLabel = new JLabel("Count of awaiting buyer: ");
        final JLabel countValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(orderTable);
        final JButton okButton = new JButton("Ok");
        countLabel.setVisible(false);
        for(model.Type type : model.Type.values()) {
            categoryCheckBox.addItem(type);
        }

        okButton.addActionListener(e -> {
            try {
                final List<Buyer> buyersList = useAllCategory.isSelected() ?
                        Helper.getAllAwaitingBuyers():
                        Helper.getAwaitingBuyerByDrugType((model.Type) categoryCheckBox.getSelectedItem());
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                buyersList.forEach(buyer -> model.addRow(new Object[]{
                        buyer.getSurname(),
                        buyer.getName(),
                        buyer.getMiddleName(),
                        buyer.getDateOfBirth(),
                        buyer.getPhoneNumber(),
                        buyer.getAddress()}));;
                orderTable.setModel(model);
                countLabel.setText(countLabel.getText() + buyersList.size());
                countLabel.setVisible(true);
                validateTree();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

        final Container container = getContentPane();
        container.add(useAllCategory);
        container.add(useSpecificCategory);
        container.add(categoryLabel);
        container.add(categoryCheckBox);
        container.add(pane);
        container.add(countLabel);
        container.add(countValueLabel);
        container.add(okButton);
        container.add(backButton);
    }

}
