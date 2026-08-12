package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai06LoginForm extends JFrame {
    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();
    private final JComboBox<String> cboRole = new JComboBox<>(new String[]{"Admin", "User"});
    private final JCheckBox chkShowPassword = new JCheckBox("Hien thi mat khau");
    private final char defaultEchoChar;

    public Bai06LoginForm() {
        setTitle("Bai 6 - Form dang nhap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        defaultEchoChar = txtPassword.getEchoChar();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        inputPanel.add(new JLabel("Tai khoan:"));
        inputPanel.add(txtUsername);
        inputPanel.add(new JLabel("Mat khau:"));
        inputPanel.add(txtPassword);
        inputPanel.add(new JLabel("Vai tro:"));
        inputPanel.add(cboRole);
        inputPanel.add(new JLabel(""));
        inputPanel.add(chkShowPassword);

        JButton btnLogin = new JButton("Dang nhap");
        JButton btnClear = new JButton("Lam moi");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnClear);
        buttonPanel.add(btnLogin);

        chkShowPassword.addActionListener(e -> doiTrangThaiMatKhau());
        btnLogin.addActionListener(e -> dangNhap());
        btnClear.addActionListener(e -> lamMoi());

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(430, 230);
        setLocationRelativeTo(null);
    }

    private void doiTrangThaiMatKhau() {
        txtPassword.setEchoChar(chkShowPassword.isSelected() ? '\0' : defaultEchoChar);
    }

    private void dangNhap() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = String.valueOf(cboRole.getSelectedItem());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du tai khoan va mat khau!");
            return;
        }

        boolean adminOk = username.equals("admin") && password.equals("123456") && role.equals("Admin");
        boolean userOk = username.equals("user") && password.equals("123456") && role.equals("User");

        if (adminOk || userOk) {
            JOptionPane.showMessageDialog(this, "Dang nhap thanh cong. Xin chao " + username + "!");
        } else {
            JOptionPane.showMessageDialog(this, "Sai tai khoan, mat khau hoac vai tro!");
        }
    }

    private void lamMoi() {
        txtUsername.setText("");
        txtPassword.setText("");
        cboRole.setSelectedIndex(0);
        chkShowPassword.setSelected(false);
        doiTrangThaiMatKhau();
        txtUsername.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai06LoginForm().setVisible(true));
    }
}
