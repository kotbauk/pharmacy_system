
package gui;

import transation.TransactionUtils;
import model.InfoAboutDrugWithItsComponents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class InfoAboutComponentDrugPage extends Page {
    protected InfoAboutComponentDrugPage() {
        super("Information about drugs components");
        final Object[] columnHeader = new String[]{"Component name", "Full price", "Needed Amount"};

        final JTextField nameTextField = new JTextField();
        final JTable componentsTable = new JTable();
        final JButton backButton = new JButton("Back");
        JLabel priceLabel = new JLabel("Drugs price: ");
        final JLabel priceValueLabel = new JLabel();
        final JScrollPane pane = new JScrollPane(componentsTable);
        final JButton okButton = new JButton("Ok");
        final String priceLabelContent = "Count of awaiting buyer: ";
        priceLabel.setVisible(false);
        okButton.addActionListener(e -> {
            try {
                final List<InfoAboutDrugWithItsComponents> infoAboutComponentsList =
                        TransactionUtils.getInfoAboutDrugsWithItsComponents(nameTextField.getText());
                Double priceDrug = TransactionUtils.getPriceOfSpecificDrug(nameTextField.getText());

                final DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnHeader);
                infoAboutComponentsList.forEach(o -> model.addRow(new Object[]{
                        o.getComponentName(),
                        o.getPrice(),
                        o.getAmount()
                }));
                componentsTable.setModel(model);
                priceLabel.setText(priceLabelContent + priceDrug);
                priceLabel.setVisible(true);
                validateTree();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

        final Container container = getContentPane();
        container.add(nameTextField);
        container.add(pane);
        container.add(priceLabel);
        container.add(priceValueLabel);
        container.add(okButton);
        container.add(backButton);
    }
}
