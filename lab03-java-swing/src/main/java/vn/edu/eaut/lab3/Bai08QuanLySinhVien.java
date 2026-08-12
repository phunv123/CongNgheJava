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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class Bai08QuanLySinhVien extends JFrame {
    private final JTextField txtStudentId = new JTextField();
    private final JTextField txtFullName = new JTextField();
    private final JTextField txtAverageScore = new JTextField();
    private final StudentTableModel tableModel = new StudentTableModel();
    private final JTable table = new JTable(tableModel);

    public Bai08QuanLySinhVien() {
        setTitle("Bai 8 - Quan ly sinh vien");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.add(new JLabel("Ma sinh vien:"));
        inputPanel.add(txtStudentId);
        inputPanel.add(new JLabel("Ho ten:"));
        inputPanel.add(txtFullName);
        inputPanel.add(new JLabel("Diem trung binh:"));
        inputPanel.add(txtAverageScore);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Them");
        JButton btnUpdate = new JButton("Sua");
        JButton btnDelete = new JButton("Xoa");
        JButton btnClear = new JButton("Lam moi");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiDongDuocChon();
            }
        });

        btnAdd.addActionListener(e -> themSinhVien());
        btnUpdate.addActionListener(e -> suaSinhVien());
        btnDelete.addActionListener(e -> xoaSinhVien());
        btnClear.addActionListener(e -> lamMoi());

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        napDuLieuMau();
        setSize(720, 420);
        setLocationRelativeTo(null);
    }

    private void themSinhVien() {
        Student student = taoSinhVienTuForm();
        if (student == null) {
            return;
        }
        tableModel.addStudent(student);
        lamMoi();
    }

    private void suaSinhVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon sinh vien can sua!");
            return;
        }
        Student student = taoSinhVienTuForm();
        if (student == null) {
            return;
        }
        tableModel.updateStudent(row, student);
        lamMoi();
    }

    private void xoaSinhVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon sinh vien can xoa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa sinh vien nay?");
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeStudent(row);
            lamMoi();
        }
    }

    private Student taoSinhVienTuForm() {
        String studentId = txtStudentId.getText().trim();
        String fullName = txtFullName.getText().trim();
        String scoreText = txtAverageScore.getText().trim();

        if (studentId.isEmpty() || fullName.isEmpty() || scoreText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
            return null;
        }

        try {
            double score = Double.parseDouble(scoreText);
            if (score < 0 || score > 10) {
                JOptionPane.showMessageDialog(this, "Diem trung binh phai nam trong khoang 0 den 10!");
                return null;
            }
            return new Student(studentId, fullName, score);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Diem trung binh phai la so hop le!");
            return null;
        }
    }

    private void hienThiDongDuocChon() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }
        Student student = tableModel.getStudentAt(row);
        txtStudentId.setText(student.getStudentId());
        txtFullName.setText(student.getFullName());
        txtAverageScore.setText(String.valueOf(student.getAverageScore()));
    }

    private void lamMoi() {
        txtStudentId.setText("");
        txtFullName.setText("");
        txtAverageScore.setText("");
        table.clearSelection();
        txtStudentId.requestFocus();
    }

    private void napDuLieuMau() {
        tableModel.addStudent(new Student("SV001", "Nguyen Van An", 8.6));
        tableModel.addStudent(new Student("SV002", "Tran Thi Binh", 7.4));
        tableModel.addStudent(new Student("SV003", "Le Van Cuong", 5.8));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai08QuanLySinhVien().setVisible(true));
    }
}
