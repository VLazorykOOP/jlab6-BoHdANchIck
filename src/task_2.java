import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class CustomException extends ArithmeticException {
    public CustomException(String message) {
        super(message);
    }
}

public class task_2 {
    private JFrame frame;
    private JTable tableA, tableB;
    private JTextField sizeField;
    private int n;

    public task_2() {
        frame = new JFrame("Matrix Application");
        frame.setSize(900, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        sizeField = new JTextField(5);
        JButton calculateButton = new JButton("Calculate");
        JButton generateButton = new JButton("Generate Random Numbers");

        northPanel.add(new JLabel("Enter size of matrices (n):"));
        northPanel.add(sizeField);
        northPanel.add(generateButton);
        northPanel.add(calculateButton);
        frame.add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        tableA = new JTable(new DefaultTableModel());
        tableB = new JTable(new DefaultTableModel());

        centerPanel.add(new JScrollPane(tableA));
        centerPanel.add(new JScrollPane(tableB));
        frame.add(centerPanel, BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    n = Integer.parseInt(sizeField.getText());
                    if (n <= 0 || n > 20) {
                        throw new CustomException("Size must be between 1 and 20.");
                    }
                    updateTableModel(tableA, n);
                    updateTableModel(tableB, n);
                    generateRandomNumbers(tableA);
                    generateRandomNumbers(tableB);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input! Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (CustomException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateX();
            }
        });

        frame.setVisible(true);
    }

    private void updateTableModel(JTable table, int size) {
        DefaultTableModel model = new DefaultTableModel(size, size);
        table.setModel(model);
    }

    private void generateRandomNumbers(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int randomValue = random.nextInt(21) - 10;
                model.setValueAt(randomValue, i, j);
            }
        }
    }

    private void calculateX() {
        int[] X = new int[n];
        DefaultTableModel modelA = (DefaultTableModel) tableA.getModel();
        DefaultTableModel modelB = (DefaultTableModel) tableB.getModel();

        for (int i = 0; i < n; i++) {
            int negativeCountA = 0;
            int negativeCountB = 0;

            for (int j = 0; j < n; j++) {
                Integer valueA = (Integer) modelA.getValueAt(i, j);
                Integer valueB = (Integer) modelB.getValueAt(i, j);

                if (valueA != null && valueA < 0) {
                    negativeCountA++;
                }
                if (valueB != null && valueB < 0) {
                    negativeCountB++;
                }
            }

            if (negativeCountA == negativeCountB) {
                X[i] = 1;
            } else {
                X[i] = 0;
            }
        }

        StringBuilder result = new StringBuilder("X: ");
        for (int value : X) {
            result.append(value).append(" ");
        }
        JOptionPane.showMessageDialog(frame, result.toString().trim(), "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(task_2::new);
    }
}
