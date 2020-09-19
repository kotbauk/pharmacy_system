package gui;

import javax.swing.*;
import java.awt.*;

public class ReportPage extends Page {
    protected ReportPage() {
        super("Report");
        final JButton buyerWithOverdueButton = new JButton("Buyers with overdue");
        final JButton awaitingBuyer = new JButton("Awaiting buyers");
        final JButton mostUsedDrugs = new JButton("Most used drugs");
        final JButton amountOfSubstance = new JButton("Amount of substance");
        final JButton buyerByDrugOrderingButton = new JButton("Buyers by drugs order");
        final JButton componentsNeededForOrders = new JButton("Components needed for oreders");
        final JButton ordersInProductionButton = new JButton("Orders in production");
        final JButton drugsOnEnoughAmount = new JButton("Drugs on enough amount");
        final JButton drugsOnCriticalAmount = new JButton("Drugs on critical amount");
        final JButton technologiesDrug = new JButton("Drug technologies");
        final JButton drugComponentsInfo = new JButton("Information about drugs components");
        final JButton buyersByFreqOrdered = new JButton("Buyers by frequency ordering");
        final JButton drugInfoButton = new JButton("Information about drugs");
        final JButton backButton = new JButton("Back");
        final Container container = getContentPane();

        buyerWithOverdueButton.addActionListener(e -> new GuiManager(new OverdueBuyers()).showPage());
        awaitingBuyer.addActionListener(e -> new GuiManager(new AwaitingBuyers()).showPage());
        mostUsedDrugs.addActionListener(e -> new GuiManager(new MostUsedDrugs()).showPage());
        amountOfSubstance.addActionListener(e -> new GuiManager(new AmountOfSubstancePage()).showPage());
        buyerByDrugOrderingButton.addActionListener(e -> new GuiManager(new BuyerByDrugOrdering()).showPage());
        componentsNeededForOrders.addActionListener(e -> new GuiManager(new ComponentsNeededForOrdersPage()).showPage());
        ordersInProductionButton.addActionListener(e -> new GuiManager(new OrdersInProduction()).showPage());
        drugsOnEnoughAmount.addActionListener(e -> new GuiManager(new DrugsOnEnoughAmount()).showPage());
        drugsOnCriticalAmount.addActionListener(e -> new GuiManager(new DrugOnCriticalAmountOrEnded()).showPage());
        technologiesDrug.addActionListener(e -> new GuiManager(new DrugsTechnologies()).showPage());
        drugComponentsInfo.addActionListener(e -> new GuiManager(new InfoAboutComponentDrugPage()).showPage());
        buyersByFreqOrdered.addActionListener(e -> new GuiManager(new ByersByDrugsTypeFreqOrdering()).showPage());
        drugInfoButton.addActionListener(e -> new GuiManager(new DrugInfoPage()).showPage());
        backButton.addActionListener(e -> new GuiManager(new MenuPage()).showPage());

        container.add(buyerWithOverdueButton);
        container.add(awaitingBuyer);
        container.add(mostUsedDrugs);
        container.add(amountOfSubstance);
        container.add(buyerByDrugOrderingButton);
        container.add(componentsNeededForOrders);
        container.add(ordersInProductionButton);
        container.add(drugsOnCriticalAmount);
        container.add(drugsOnEnoughAmount);
        container.add(technologiesDrug);
        container.add(drugComponentsInfo);
        container.add(buyersByFreqOrdered);
        container.add(drugInfoButton);
        container.add(backButton);
    }
}
