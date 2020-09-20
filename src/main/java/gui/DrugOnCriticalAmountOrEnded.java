package gui;

import helper.Helper;
import model.Drug;
import model.GoodsOnWarehouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DrugOnCriticalAmountOrEnded extends Page {
    protected DrugOnCriticalAmountOrEnded() {
        super("Drugs on critical amount");
        final Object[] columnHeader = new String[]{
                "Drug name",
                "DRUG_TYPE",
                "UNIT",
                "PRICE_PER_UNIT"};
        final JTable medicineTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JScrollPane pane = new JScrollPane(medicineTable);


        try {
            final List<Drug> medicineList =
                    Helper.getAllDrugsWithMinimalAMountOrEmpted();
            final DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnHeader);
            medicineList.forEach(m -> model.addRow(new Object[]{
                    m.getName(),
                    m.getType(),
                    m.getUnit(),
                    m.getPricePerUnit()}));
            medicineTable.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

        final Container container = getContentPane();
        container.add(pane);
        container.add(backButton);
    }
}
