import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

public class IntegralCalculatorGUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField lowerLimitField;
    private JTextField upperLimitField;
    private JTextField stepField;

    public IntegralCalculatorGUI() {
        setTitle("Integral Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Lower Limit");
        tableModel.addColumn("Upper Limit");
        tableModel.addColumn("Step");
        tableModel.addColumn("Result");
// создание таблицы
        table = new JTable(tableModel);

        lowerLimitField = new JTextField(2);
        upperLimitField = new JTextField(2);
        stepField = new JTextField(2);

        JButton addButton = new JButton("Add");
        addButton.addActionListener((ActionEvent e) -> {
            tableModel.addRow(new Object[]{lowerLimitField.getText(), upperLimitField.getText(), stepField.getText(), ""});
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            }
        });

        JButton calculateButton = new JButton("Calculate");
        //проверка введеных значений
        calculateButton.addActionListener((var e) -> {
            double lowerLimit, upperLimit, step;
            try {
                lowerLimit = Double.parseDouble(lowerLimitField.getText());
                upperLimit = Double.parseDouble(upperLimitField.getText());
                step = Double.parseDouble(stepField.getText());
                
                double integralResult = calculateIntegral(lowerLimit, upperLimit, step);
                
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.setValueAt(integralResult, selectedRow, 3);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numerical values.");
            }
        });
// создание полей и кнопок
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Lower Limit:"));
        inputPanel.add(lowerLimitField);
        inputPanel.add(new JLabel("Upper Limit:"));
        inputPanel.add(upperLimitField);
        inputPanel.add(new JLabel("Step:"));
        inputPanel.add(stepField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(calculateButton);

        JPanel mainPanel = new JPanel();
        mainPanel.add(inputPanel);
        mainPanel.add(new JScrollPane(table));

        getContentPane().add(mainPanel);
    }

    // вычисление интеграла 1/ln(x)
    public static double calculateIntegral(double lowerLimit, double upperLimit, double step) {
        double result = 0;
        double x = lowerLimit;

        while (x < upperLimit) {
            double nextX = Math.min(x + step, upperLimit);
            result += ((nextX - x) * (1 / Math.log(x)+ 1 /Math.log(nextX)))*0.5;
            x = nextX;
        }

        return result;
    }

    // запуск GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IntegralCalculatorGUI gui = new IntegralCalculatorGUI();
            gui.setVisible(true);
        });
    }
}
