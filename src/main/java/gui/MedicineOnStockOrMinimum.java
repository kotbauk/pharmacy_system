package gui;

import helper.Helper;
import model.GoodsOnWarehouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MedicineOnStockOrMinimum extends Page {
    protected MedicineOnStockOrMinimum() {
        super("Medicine in stock or minimum");
        final Object[] columnHeader = new String[]{"Medicine", "Count"};
        final JTable medicineTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JScrollPane pane = new JScrollPane(medicineTable);


        try {
            final List<GoodsOnWarehouse> medicineList = Helper.getMedicineInStockOrMinimum();
            final DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnHeader);
            medicineList.forEach(m -> model.addRow(new Object[]{m.getMedicine(), m.getMedicineCount()}));
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
