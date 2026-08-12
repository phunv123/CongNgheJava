package vn.edu.eaut.lab3;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai02TongHaiSo extends JFrame {
    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();
    private final JLabel lblResult = new JLabel("Ket qua: ");

    public Bai02TongHaiSo() {
        setTitle("Bai 2 - Tinh tong hai so");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 8, 8));

        add(new JLabel("So thu nhat:"));
        add(txtA);
        add(new JLabel("So thu hai:"));
        add(txtB);

        JButton btnSum = new JButton("Tinh tong");
        JButton btnClear = new JButton("Lam moi");
        btnSum.addActionListener(e -> tinhTong());
        btnClear.addActionListener(e -> lamMoi());

        add(btnSum);
        add(btnClear);
        add(new JLabel(""));
        add(lblResult);

        setSize(380, 190);
        setLocationRelativeTo(null);
    }

    private void tinhTong() {
        try {
            double a = Double.parseDouble(txtA.getText().trim());
            double b = Double.parseDouble(txtB.getText().trim());
            lblResult.setText("Ket qua: " + (a + b));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Du lieu nhap phai la so hop le!");
        }
    }

    private void lamMoi() {
        txtA.setText("");
        txtB.setText("");
        lblResult.setText("Ket qua: ");
        txtA.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai02TongHaiSo().setVisible(true));
    }
}
