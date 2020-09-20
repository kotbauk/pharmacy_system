
package gui;

import transation.TransactionUtils;
import model.Buyer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.List;

public class BuyerByDrugOrdering extends Page {
    protected BuyerByDrugOrdering() {
        super("Buyers by drugs order");
        final Object[] columnHeader = new String[]{"Surname", "Name", "Middlename",
                "Date of birth", "Phone number", "Address"};
        final JRadioButton useName = new JRadioButton("Use name");
        final JRadioButton useCategory = new JRadioButton("Use category");
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(useName);
        buttonGroup.add(useCategory);
        final JLabel nameLabel = new JLabel("Name");
        final JTextField nameTextField = new JTextField();
        final JLabel categoryLabel = new JLabel("Category");
        final JComboBox<model.Type> categoryCheckBox = new JComboBox<>();
        final JTable buyerTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JLabel countLabel = new JLabel("");
        final JLabel countValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(buyerTable);
        final JButton okButton = new JButton("Ok");

        final JLabel fromDateLabel = new JLabel("From date dd-mm-yyyy");
        final JLabel toDateLabel = new JLabel("To date dd-mm-yyyy");


        final JTextField fromDayDateField = new JTextField();
        final JTextField fromMonthDateField = new JTextField();
        final JTextField fromYearDateField = new JTextField();
        final JTextField toDayDateField = new JTextField();
        final JTextField toMonthDateField = new JTextField();
        final JTextField toYearDateField = new JTextField();

        for(model.Type type : model.Type.values()) {
            categoryCheckBox.addItem(type);
        }

        okButton.addActionListener(e -> {
            try {
                final List<Buyer> buyersList = useName.isSelected()?
                        TransactionUtils.getBuyersOrderedSpecificDrugInPeriod(nameTextField.getText(),
                                createTimestamp(fromDayDateField.getText(),
                                    fromMonthDateField.getText(),
                                    fromYearDateField.getText()),
                                createTimestamp(toDayDateField.getText(),
                                        toMonthDateField.getText(),
                                        toYearDateField.getText())) :
                        TransactionUtils.getBuyersOrderedSpecificDrugTypeInPeriod((model.Type) categoryCheckBox.getSelectedItem(),
                                createTimestamp(fromDayDateField.getText(),
                                fromMonthDateField.getText(),
                                fromYearDateField.getText()),
                                createTimestamp(toDayDateField.getText(),
                                        toMonthDateField.getText(),
                                        toYearDateField.getText()));
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                buyersList.forEach(buyer -> model.addRow(new Object[]{
                        buyer.getSurname(),
                        buyer.getName(),
                        buyer.getMiddleName(),
                        buyer.getDateOfBirth(),
                        buyer.getPhoneNumber(),
                        buyer.getAddress()}));
                buyerTable.setModel(model);
                countLabel.setText("Buyer number: " + buyersList.size());
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
        container.add(countValueLabel);;
        container.add(fromDateLabel);
        container.add(fromDayDateField);
        container.add(fromMonthDateField);
        container.add(fromYearDateField);
        container.add(toDateLabel);
        container.add(toDayDateField);
        container.add(toMonthDateField);
        container.add(toYearDateField);
        container.add(okButton);
        container.add(backButton);
    }
    private Timestamp createTimestamp(String day, String month, String year) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return new Timestamp(simpleDateFormat.parse(day + "-" + month + "-" + year, new ParsePosition(0)).getTime());
    }
}
