
package gui;

import transation.TransactionUtils;
import model.DrugsWithAmountResults;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MostUsedDrugs extends Page {
    protected MostUsedDrugs() {
        super("Most used drugs");
        final Object[] columnHeader = new String[]{"Drug name", "Drug type", "Count of usage"};
        final JTable drugTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JComboBox<model.Type> typeComboBox = new JComboBox<>();
        final JCheckBox useCategory = new JCheckBox("Search in all categories");
        final JLabel typeLabel = new JLabel("Drug type");
        final JScrollPane pane = new JScrollPane(drugTable);
        final JButton okButton = new JButton("Ok");

        for(model.Type type: model.Type.values()){
            typeComboBox.addItem(type);
        }

        okButton.addActionListener(e -> {
            try {
                final List<DrugsWithAmountResults> drugList=
                        useCategory.isSelected()?
                                TransactionUtils.getMostUsedDrugByType((model.Type) typeComboBox.getSelectedItem()) :
                                TransactionUtils.getMostUsedDrugs();
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                drugList.forEach(m ->
                        model.addRow(new Object[]{
                                m.getName(),
                                m.getType(),
                                m.getAmount()
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

