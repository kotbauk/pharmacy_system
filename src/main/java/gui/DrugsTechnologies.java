
package gui;

import transation.TransactionUtils;
import model.Technologies;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DrugsTechnologies extends Page {
    protected DrugsTechnologies() {
        super("Drugs technologies");
        final Object[] columnHeader = new String[]{"Production time", "Production action"};
        final JRadioButton useName = new JRadioButton("Use name");
        final JRadioButton useCategory = new JRadioButton("Use category");
        final JRadioButton useAll = new JRadioButton("All");
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(useName);
        buttonGroup.add(useCategory);
        buttonGroup.add(useAll);

        final JLabel nameLabel = new JLabel("Name");
        final JTextField nameTextField = new JTextField();
        final JLabel categoryLabel = new JLabel("Category");
        final JComboBox<model.Type> categoryCheckBox = new JComboBox<>();
        final JLabel allLabel = new JLabel("All in production");
        final JTable technologiesTable = new JTable();
        final JButton backButton = new JButton("Back");
        final JScrollPane pane = new JScrollPane(technologiesTable);
        final JButton okButton = new JButton("Ok");

        for (model.Type type : model.Type.getTypesOfManufacturedDrug()) {
            categoryCheckBox.addItem(type);
        }

        okButton.addActionListener(e -> {
            try {
                List<Technologies> technologiesList = null;
                if (useName.isSelected()) {
                    technologiesList = TransactionUtils.getAllTechnologiesForSpecificDrugName(nameTextField.getText());
                } else if (useCategory.isSelected()) {
                    technologiesList = TransactionUtils.getAllTechnologiesForSpecificDrugType((model.Type) categoryCheckBox.getSelectedItem());
                } else if (useAll.isSelected()) {
                    technologiesList = TransactionUtils.getAllTechnologiesForDrugInProduction();
                }
                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                technologiesList.forEach(technologies -> model.addRow(new Object[]{technologies.getProductionTime(), technologies.getProductionAction()}));
                technologiesTable.setModel(model);
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
        container.add(useAll);
        container.add(allLabel);
        container.add(nameLabel);
        container.add(nameTextField);
        container.add(categoryLabel);
        container.add(categoryCheckBox);
        container.add(pane);
        container.add(okButton);
        container.add(backButton);
    }
}
