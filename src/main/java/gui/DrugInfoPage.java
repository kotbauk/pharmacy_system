
package gui;

import helper.Helper;
import model.InfoAboutDrug;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DrugInfoPage extends Page {
    protected DrugInfoPage() {
    super("Information about drug");
    final Object[] columnHeader = new String[]{"Name", "Type", "Production action",
            "Components", "Price", "Amount"};

    final JLabel nameLabel = new JLabel("Name");
    final JTextField nameTextField = new JTextField();
    final JTable orderTable = new JTable();
    final JButton backButton = new JButton("Back");
    final JLabel countLabel = new JLabel("Count");
    final JLabel countValueLabel = new JLabel();
    final JScrollPane pane = new JScrollPane(orderTable);
    final JButton okButton = new JButton("Ok");

    okButton.addActionListener(e -> {

        try {
            final List<InfoAboutDrug> infoAboutDrugList =
                    Helper.getInfoAboutSpecificDrug(nameTextField.getText());
            final List<model.Component> componentList = Helper.getAllComponentsByDrugName(nameTextField.getText());
            final DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnHeader);
            infoAboutDrugList.forEach(infoAboutDrug -> model.addRow(new Object[]{infoAboutDrug.getType(),
                    infoAboutDrug.getManufacturingAction(),
                    infoAboutDrug.getPricePerUnit(), infoAboutDrug.getAmountOnWarehouse(), componentList}));
            orderTable.setModel(model);
            validateTree();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

    final Container container = getContentPane();
    container.add(nameLabel);
    container.add(nameTextField);
    container.add(pane);
    container.add(okButton);
    container.add(backButton);
    }
}
