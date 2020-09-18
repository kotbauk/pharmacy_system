package gui;

import javax.swing.*;
import java.awt.*;

public class ReportPage extends Page {
    protected ReportPage() {
        super("Report");
        final JButton buyerWithOverdueButton = new JButton("Buyers with overdue");
        final JButton ordersInProductionButton = new JButton("Orders in production");
        final JButton drugsOnEnoughAmount = new JButton("Drugs on enough amount");
        final JButton drugsOnCriticalAmount = new JButton("Drugs on critical amount");
        final JButton buyersByMedicineOrCategory = new JButton("Buyers by medicine or category");
        final JButton buyersByFreqMedicineOrCategory = new JButton("Buyers by freq medicine or category");
        final JButton backButton = new JButton("Back");
        final Container container = getContentPane();

        buyerWithOverdueButton.addActionListener(e -> new GuiManager(new BuyerWithOverdue()).showPage());
        ordersInProductionButton.addActionListener(e -> new GuiManager(new OrdersInProduction()).showPage());
        drugsOnEnoughAmount.addActionListener(e -> new GuiManager(new DrugsOnEnoughAmount()).showPage());
        drugsOnCriticalAmount.addActionListener(e -> new GuiManager(new DrugOnCriticalAmountOrEnded()).showPage());
        buyersByMedicineOrCategory.addActionListener(e -> new GuiManager(new BuyersByMedicine()).showPage());
        buyersByFreqMedicineOrCategory.addActionListener(e -> new GuiManager(new BuyersByMedicineFrequency()).showPage());
        backButton.addActionListener(e -> new GuiManager(new MenuPage()).showPage());
        container.add(buyerWithOverdueButton);
        container.add(ordersInProductionButton);
        container.add(drugsOnCriticalAmount);
        container.add(drugsOnEnoughAmount);
        container.add(buyersByMedicineOrCategory);
        container.add(buyersByFreqMedicineOrCategory);
        container.add(backButton);
    }
}
