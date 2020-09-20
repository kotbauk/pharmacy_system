
package gui;

import transation.TransactionUtils;
import model.VolumeOfSubstanceResult;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class AmountOfSubstancePage extends Page {

    private static final String dayLabelContent = "DD:";
    private static final String monthLabelContent = "MM:";
    private static final String yearLabelContent = "YYYY:";
    private static final String fromDateLabelContent = "From:";
    private static final String toDateLabelContent = "To:";



    static final int FIRST_COLUMN_X = 50;
    static final int FIRST_ROW_Y = 50;
    static final int SECOND_COLUMN_X = FIRST_COLUMN_X + 300;
    static final int SECOND_ROW_Y = FIRST_ROW_Y + 75;
    static final int THIRD_ROW_Y = SECOND_ROW_Y + 75;

    protected AmountOfSubstancePage() {
        super("Amount of substance");
        getContentPane().setLayout(null);

        final JLabel substanceNameLabel = new JLabel("Name:");
        final JTextField substanceNameTextField = new JTextField();

        final JButton backButton = new JButton("Back");
        final JButton okButton = new JButton("Ok");

        JLabel countLabel = new JLabel("TEST----");

        JLabel fromDateLabel = new JLabel(fromDateLabelContent);
        JLabel toDateLabel = new JLabel(toDateLabelContent);

        Font defaultFont = new Font(fromDateLabel.getFont().getName(),Font.PLAIN,24);

        JLabel fromDayLabel = new JLabel(dayLabelContent);
        JLabel fromMonthLabel = new JLabel(monthLabelContent);
        JLabel fromYearLabel = new JLabel(yearLabelContent);

        JTextField fromDayDateField = new JTextField();
        JTextField fromMonthDateField = new JTextField();
        JTextField fromYearDateField = new JTextField();

        JLabel toDayLabel = new JLabel(dayLabelContent);
        JLabel toMonthLabel = new JLabel(monthLabelContent);
        JLabel toYearLabel = new JLabel(yearLabelContent);

        JTextField toDayDateField = new JTextField();
        JTextField toMonthDateField = new JTextField();
        JTextField toYearDateField = new JTextField();

        fromDateLabel.setBounds(FIRST_COLUMN_X, FIRST_ROW_Y,100,100);
        fromDateLabel.setFont(defaultFont);


        fromDayLabel.setBounds(FIRST_COLUMN_X, SECOND_ROW_Y,30,30);
        fromDayDateField.setBounds(FIRST_COLUMN_X + 30, SECOND_ROW_Y,30,30);

        fromMonthLabel.setBounds(FIRST_COLUMN_X + 60, SECOND_ROW_Y,30,30);
        fromMonthDateField.setBounds(FIRST_COLUMN_X + 90, SECOND_ROW_Y,30,30);

        fromYearLabel.setBounds(FIRST_COLUMN_X + 120, SECOND_ROW_Y,100,30);
        fromYearDateField.setBounds(FIRST_COLUMN_X + 160, SECOND_ROW_Y,100,30);

        toDateLabel.setBounds(SECOND_COLUMN_X, FIRST_ROW_Y, 100, 100);
        toDateLabel.setFont(defaultFont);

        toDayLabel.setBounds(SECOND_COLUMN_X, SECOND_ROW_Y,30,30);
        toDayDateField.setBounds(SECOND_COLUMN_X + 30, SECOND_ROW_Y,30,30);

        toMonthLabel.setBounds(SECOND_COLUMN_X + 60, SECOND_ROW_Y,30,30);
        toMonthDateField.setBounds(SECOND_COLUMN_X + 90, SECOND_ROW_Y,30,30);

        toYearLabel.setBounds(SECOND_COLUMN_X + 120, SECOND_ROW_Y,100,30);
        toYearDateField.setBounds(SECOND_COLUMN_X + 160, SECOND_ROW_Y,100,30);

        substanceNameLabel.setBounds(FIRST_COLUMN_X, THIRD_ROW_Y,50, 30);
        substanceNameTextField.setBounds(FIRST_COLUMN_X + 50, THIRD_ROW_Y, 100, 30);

        countLabel.setBounds(FIRST_COLUMN_X + 300, THIRD_ROW_Y, 300, 30);

        okButton.setBounds(SCREEN_SIZE.width - 100, SCREEN_SIZE.height - 85,100,30);
        backButton.setBounds(1, SCREEN_SIZE.height - 85, 100, 30);

        okButton.addActionListener(e -> {
            try {

                final VolumeOfSubstanceResult volumeOfSubstanceResult =
                        TransactionUtils.getVolumeOfSubstance(substanceNameTextField.getText(),
                                createTimestamp(fromDayDateField.getText(),
                                        fromMonthDateField.getText(),
                                        fromYearDateField.getText()),
                                        createTimestamp(toDayDateField.getText(),
                                        toMonthDateField.getText(),
                                        toYearDateField.getText()));
                Timestamp test = createTimestamp(fromDayDateField.getText(),
                        fromMonthDateField.getText(),
                        fromYearDateField.getText());
                countLabel.setText("Amount of substance: " + volumeOfSubstanceResult);
                countLabel.repaint();
                this.validateTree();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        backButton.addActionListener(e -> new GuiManager(new ReportPage()).showPage());

        final Container container = getContentPane();

        container.add(fromDateLabel);

        container.add(fromDayLabel);
        container.add(fromDayDateField);

        container.add(fromMonthLabel);
        container.add(fromMonthDateField);

        container.add(fromYearLabel);
        container.add(fromYearDateField);

        container.add(toDateLabel);

        container.add(toDayLabel);
        container.add(toDayDateField);

        container.add(toMonthLabel);
        container.add(toMonthDateField);

        container.add(toYearLabel);
        container.add(toYearDateField);

        container.add(substanceNameLabel);
        container.add(substanceNameTextField);

        container.add(countLabel);

        container.add(okButton);
        container.add(backButton);
    }



    private Timestamp createTimestamp(String day, String month, String year) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return new Timestamp(simpleDateFormat.parse(day + "-" + month + "-" + year, new ParsePosition(0)).getTime());
    }
}