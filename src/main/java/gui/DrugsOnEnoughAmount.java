package gui;

import helper.Helper;
import model.Drug;
import model.GoodsOnWarehouse;
import model.Type;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DrugsOnEnoughAmount extends Page {
    protected DrugsOnEnoughAmount() {
        super("Drugs on enough amount");
        final Object[] columnHeader = new String[]{"Drug name", "Count"};
        final JTable drugTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JComboBox<model.Type> typeComboBox = new JComboBox<>();
        final JCheckBox useCategory = new JCheckBox("Find specific category");
        final JLabel typeLabel = new JLabel("Type");
        final JScrollPane pane = new JScrollPane(drugTable);
        final JButton okButton = new JButton("Ok");

        for(model.Type type: model.Type.values()){
            typeComboBox.addItem(type);
        }

        okButton.addActionListener(e -> {
            try {
                final List<Drug> drugList=
                useCategory.isSelected()?
                        Helper.getDrugWithMinimalAmountByType((model.Type) typeComboBox.getSelectedItem()) :
                        Helper.getAllDrugWithMinimalAmount();
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                drugList.forEach(m ->
                        model.addRow(new Object[]{
                                m.getName(),
                                m.getType()
                                }));
                drugTable.setModel(model);
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
        container.add(pane);
        container.add(okButton);
        container.add(backButton);
    }
}
