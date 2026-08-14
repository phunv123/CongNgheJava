package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class CsvStudentStatsFrame extends JFrame {
    private final JButton btnOpen = new JButton("Đọc file CSV");
    private final JLabel lblFile = new JLabel("Chưa chọn file");
    private final JLabel lblAverage = new JLabel("Điểm trung bình: --");
    private final JLabel lblHighest = new JLabel("Sinh viên điểm cao nhất: --");
    private final JProgressBar progressBar = new JProgressBar();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Mã SV", "Họ tên", "Điểm"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public CsvStudentStatsFrame() {
        setTitle("Bài 8 - Thống kê điểm sinh viên từ CSV");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.add(btnOpen, BorderLayout.WEST);
        top.add(lblFile, BorderLayout.CENTER);
        top.add(progressBar, BorderLayout.SOUTH);
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 4));
        stats.add(lblAverage);
        stats.add(lblHighest);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(top, BorderLayout.NORTH);
        content.add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
        content.add(stats, BorderLayout.SOUTH);
        add(content);

        btnOpen.addActionListener(e -> chooseAndLoad());
        setSize(780, 470);
        setLocationRelativeTo(null);
    }

    private void chooseAndLoad() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Tệp CSV (*.csv)", "csv"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadStudents(chooser.getSelectedFile());
        }
    }

    private void loadStudents(File file) {
        btnOpen.setEnabled(false);
        progressBar.setIndeterminate(true);
        lblFile.setText("Đang đọc: " + file.getAbsolutePath());

        SwingWorker<List<Student>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Student> doInBackground() throws IOException {
                List<Student> students = new ArrayList<>();
                try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    int lineNumber = 0;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.isBlank() || (lineNumber == 1 && line.toLowerCase().contains("masv"))) {
                            continue;
                        }
                        List<String> fields = CsvUtils.parseLine(line);
                        if (fields.size() != 3) {
                            throw new IOException("Dòng " + lineNumber + " không có đúng 3 cột");
                        }
                        try {
                            double score = Double.parseDouble(fields.get(2));
                            if (score < 0 || score > 10) {
                                throw new NumberFormatException();
                            }
                            students.add(new Student(fields.get(0), fields.get(1), score));
                        } catch (NumberFormatException ex) {
                            throw new IOException("Điểm không hợp lệ tại dòng " + lineNumber);
                        }
                    }
                }
                return students;
            }

            @Override
            protected void done() {
                try {
                    List<Student> students = get();
                    showStudents(students);
                    lblFile.setText("File: " + file.getAbsolutePath());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    showError("Tác vụ bị gián đoạn");
                } catch (ExecutionException ex) {
                    showError(ex.getCause().getMessage());
                } finally {
                    progressBar.setIndeterminate(false);
                    btnOpen.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void showStudents(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.id(), student.fullName(), student.score()});
        }
        if (students.isEmpty()) {
            lblAverage.setText("Điểm trung bình: --");
            lblHighest.setText("Sinh viên điểm cao nhất: --");
            return;
        }
        double average = students.stream().mapToDouble(Student::score).average().orElse(0);
        Student highest = students.stream().max(java.util.Comparator.comparingDouble(Student::score)).orElseThrow();
        lblAverage.setText(String.format("Điểm trung bình: %.2f", average));
        lblHighest.setText("Cao nhất: " + highest.fullName() + " (" + highest.score() + ")");
    }

    private void showError(String message) {
        lblFile.setText("Đọc file thất bại");
        JOptionPane.showMessageDialog(this, message, "Lỗi CSV", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CsvStudentStatsFrame().setVisible(true));
    }
}
