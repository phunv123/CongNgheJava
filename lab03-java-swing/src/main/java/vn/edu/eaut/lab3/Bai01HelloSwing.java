package vn.edu.eaut.lab3;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai01HelloSwing extends JFrame {
    private final JTextField txtName = new JTextField(20);

    public Bai01HelloSwing() {
        setTitle("Bai 1 - Chao nguoi dung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        add(new JLabel("Nhap ten:"));
        add(txtName);

        JButton btnHello = new JButton("Hien thi loi chao");
        btnHello.addActionListener(e -> hienThiLoiChao());
        add(btnHello);

        pack();
        setLocationRelativeTo(null);
    }

    private void hienThiLoiChao() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap ten!");
            txtName.requestFocus();
            return;
        }
        JOptionPane.showMessageDialog(this, "Xin chao, " + name + "!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai01HelloSwing().setVisible(true));
    }
}
