package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai07MayTinhMini extends JFrame {
    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();
    private final JTextField txtResult = new JTextField();
    private final JTextArea txtHistory = new JTextArea(8, 36);

    public Bai07MayTinhMini() {
        setTitle("Bai 7 - May tinh mini");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.add(new JLabel("So thu nhat:"));
        inputPanel.add(txtA);
        inputPanel.add(new JLabel("So thu hai:"));
        inputPanel.add(txtB);
        inputPanel.add(new JLabel("Ket qua:"));
        txtResult.setEditable(false);
        inputPanel.add(txtResult);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton(buttonPanel, "Cong", "+");
        addButton(buttonPanel, "Tru", "-");
        addButton(buttonPanel, "Nhan", "*");
        addButton(buttonPanel, "Chia", "/");

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> lamMoi());
        buttonPanel.add(btnClear);

        txtHistory.setEditable(false);
        txtHistory.setLineWrap(true);
        txtHistory.setWrapStyleWord(true);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(txtHistory), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void addButton(JPanel panel, String label, String operator) {
        JButton button = new JButton(label);
        button.addActionListener(e -> tinh(operator));
        panel.add(button);
    }

    private void tinh(String operator) {
        try {
            double a = Double.parseDouble(txtA.getText().trim());
            double b = Double.parseDouble(txtB.getText().trim());
            double result;

            switch (operator) {
                case "+" -> result = a + b;
                case "-" -> result = a - b;
                case "*" -> result = a * b;
                case "/" -> {
                    if (Math.abs(b) < 1e-9) {
                        JOptionPane.showMessageDialog(this, "Khong the chia cho 0!");
                        return;
                    }
                    result = a / b;
                }
                default -> throw new IllegalArgumentException("Phep tinh khong hop le");
            }

            txtResult.setText(String.valueOf(result));
            txtHistory.append(String.format("%.4f %s %.4f = %.4f%n", a, operator, b, result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap hai so hop le!");
        }
    }

    private void lamMoi() {
        txtA.setText("");
        txtB.setText("");
        txtResult.setText("");
        txtHistory.setText("");
        txtA.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai07MayTinhMini().setVisible(true));
    }
}
