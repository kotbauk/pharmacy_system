package gui;

import helper.Helper;
import model.GoodsOnWarehouse;
import model.Type;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MedicineInStock extends Page {
    protected MedicineInStock() {
        super("Medicine in stock");
        final Object[] columnHeader = new String[]{"Medicine", "Count"};
        final JTable medicineTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JComboBox<model.Type> typeComboBox = new JComboBox<>();
        final JCheckBox useCategory = new JCheckBox();
        final JLabel typeLabel = new JLabel("Type");
        final JLabel rowCountLabel = new JLabel("Row count");
        final JTextField rowCountTextField = new JTextField();
        final JScrollPane pane = new JScrollPane(medicineTable);
        final JButton okButton = new JButton("Ok");


        typeComboBox.addItem(model.Type.OINTMENTS);
        typeComboBox.addItem(model.Type.PILLS);
        typeComboBox.addItem(model.Type.POWDERS);
        typeComboBox.addItem(model.Type.SOLUTE);
        typeComboBox.addItem(model.Type.POTIONS);
        typeComboBox.addItem(model.Type.TINCTURES);

        okButton.addActionListener(e -> {
            try {
                final List<GoodsOnWarehouse> medicineList = Helper.getMedicineInStock(useCategory.isSelected() ?
                                (model.Type) typeComboBox.getSelectedItem() :
                                null,
                        Integer.parseInt(rowCountTextField.getText()));
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                medicineList.forEach(m -> model.addRow(new Object[]{m.getMedicine(), m.getMedicineCount()}));
                medicineTable.setModel(model);
                this.validateTree();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

        final Container container = getContentPane();
        container.add(typeLabel);
        container.add(typeComboBox);
        container.add(useCategory);
        container.add(rowCountLabel);
        container.add(rowCountTextField);
        container.add(pane);
        container.add(okButton);
        container.add(backButton);
    }
}
