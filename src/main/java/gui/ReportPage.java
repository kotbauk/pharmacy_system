package gui;

import javax.swing.*;
import java.awt.*;

public class ReportPage extends Page {
    protected ReportPage() {
        super("Report");
        final JButton buyerWithOverdueButton = new JButton("Buyers with overdue");
        final JButton ordersInProductionButton = new JButton("Orders in production");
        final JButton medicineInStockButton = new JButton("Medicine in stock");
        final JButton medicineInStockOrMinimumButton = new JButton("Medicine in stock or minimum");
        final JButton buyersByMedicineOrCategory = new JButton("Buyers by medicine or category");
        final JButton buyersByFreqMedicineOrCategory = new JButton("Buyers by freq medicine or category");
        final JButton backButton = new JButton("Back");
        final Container container = getContentPane();

        buyerWithOverdueButton.addActionListener(e -> new GuiManager(new BuyerWithOverdue()).showPage());
        ordersInProductionButton.addActionListener(e -> new GuiManager(new OrdersInProduction()).showPage());
        medicineInStockButton.addActionListener(e -> new GuiManager(new MedicineInStock()).showPage());
        medicineInStockOrMinimumButton.addActionListener(e -> new GuiManager(new MedicineOnStockOrMinimum()).showPage());
        buyersByMedicineOrCategory.addActionListener(e -> new GuiManager(new BuyersByMedicine()).showPage());
        buyersByFreqMedicineOrCategory.addActionListener(e -> new GuiManager(new BuyersByMedicineFrequency()).showPage());
        backButton.addActionListener(e -> new GuiManager(new MenuPage()).showPage());
        container.add(buyerWithOverdueButton);
        container.add(ordersInProductionButton);
        container.add(medicineInStockButton);
        container.add(medicineInStockOrMinimumButton);
        container.add(buyersByMedicineOrCategory);
        container.add(buyersByFreqMedicineOrCategory);
        container.add(backButton);
    }
}
