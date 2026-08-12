package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai04TamGiacSwing extends JFrame {
    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();
    private final JTextField txtC = new JTextField();
    private final JLabel lblResult = new JLabel("Ket qua: ");

    public Bai04TamGiacSwing() {
        setTitle("Bai 4 - Kiem tra tam giac");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.add(new JLabel("Canh a:"));
        panel.add(txtA);
        panel.add(new JLabel("Canh b:"));
        panel.add(txtB);
        panel.add(new JLabel("Canh c:"));
        panel.add(txtC);

        JButton btnCheck = new JButton("Kiem tra");
        btnCheck.addActionListener(e -> kiemTraTamGiac());

        add(lblResult, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(btnCheck, BorderLayout.SOUTH);

        setSize(430, 230);
        setLocationRelativeTo(null);
    }

    private void kiemTraTamGiac() {
        try {
            double a = Double.parseDouble(txtA.getText().trim());
            double b = Double.parseDouble(txtB.getText().trim());
            double c = Double.parseDouble(txtC.getText().trim());
            lblResult.setText("Ket qua: " + loaiTamGiac(a, b, c));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ba canh phai la so hop le!");
        }
    }

    private String loaiTamGiac(double a, double b, double c) {
        final double eps = 1e-9;
        if (a <= 0 || b <= 0 || c <= 0 || a + b <= c || a + c <= b || b + c <= a) {
            return "Khong phai tam giac";
        }

        boolean deu = Math.abs(a - b) < eps && Math.abs(b - c) < eps;
        boolean can = Math.abs(a - b) < eps || Math.abs(a - c) < eps || Math.abs(b - c) < eps;

        double[] sides = {a, b, c};
        Arrays.sort(sides);
        boolean vuong = Math.abs(sides[0] * sides[0] + sides[1] * sides[1] - sides[2] * sides[2]) < eps;

        if (deu) {
            return "Tam giac deu";
        }
        if (vuong && can) {
            return "Tam giac vuong can";
        }
        if (vuong) {
            return "Tam giac vuong";
        }
        if (can) {
            return "Tam giac can";
        }
        return "Tam giac thuong";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai04TamGiacSwing().setVisible(true));
    }
}
