
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
    final Object[] columnHeader = new String[]{"Components", "Price component type"};
    final JLabel nameLabel = new JLabel("Drug name");
    final JTextField nameTextField = new JTextField();
    final JTable componentsTable = new JTable();
    final JButton backButton = new JButton("Back");
    final JScrollPane pane = new JScrollPane(componentsTable);
    final JButton okButton = new JButton("Ok");
    final JLabel drugTypeLabel = new JLabel();
    final JTextArea productionActionTextArea = new JTextArea();
    productionActionTextArea.setEditable(false);

    okButton.addActionListener(e -> {

        try {
            final InfoAboutDrug infoAboutDrug =
                    Helper.getInfoAboutSpecificDrug(nameTextField.getText());
            final List<model.Component> componentList = Helper.getAllComponentsByDrugName(nameTextField.getText());
            final DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnHeader);
            componentList.forEach(component -> model.addRow(new Object[]{
                    component.getName(),
                    component.getPricePerUnit()}));
            componentsTable.setModel(model);
            drugTypeLabel.setText("Drug type: " + infoAboutDrug.getType().toString());
            productionActionTextArea.setText("Production action: " + infoAboutDrug.getManufacturingAction());
            drugTypeLabel.setVisible(true);
            productionActionTextArea.setVisible(true);
            drugTypeLabel.repaint();
            productionActionTextArea.repaint();
            validateTree();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

    final Container container = getContentPane();
    drugTypeLabel.setVisible(false);
    productionActionTextArea.setVisible(false);
    container.add(drugTypeLabel);
    container.add(productionActionTextArea);
    container.add(nameLabel);
    container.add(nameTextField);
    container.add(pane);
    container.add(okButton);
    container.add(backButton);
    }
}
