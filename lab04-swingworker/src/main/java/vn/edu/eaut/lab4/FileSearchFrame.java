package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSearchFrame extends JFrame {
    private final JButton btnChoose = new JButton("Chọn file TXT");
    private final JButton btnSearch = new JButton("Tìm kiếm");
    private final JTextField txtKeyword = new JTextField(18);
    private final JLabel lblFile = new JLabel("Chưa chọn file");
    private final JLabel lblCount = new JLabel("Số dòng tìm thấy: 0");
    private final JTextArea txtMatches = new JTextArea();
    private final JProgressBar progressBar = new JProgressBar();
    private File selectedFile;

    public FileSearchFrame() {
        setTitle("Bài 7 - Tìm từ khóa trong file");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        txtMatches.setEditable(false);
        progressBar.setIndeterminate(false);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(btnChoose);
        controls.add(new JLabel("Từ khóa:"));
        controls.add(txtKeyword);
        controls.add(btnSearch);
        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.add(controls, BorderLayout.NORTH);
        top.add(lblFile, BorderLayout.CENTER);
        top.add(progressBar, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(top, BorderLayout.NORTH);
        content.add(new JScrollPane(txtMatches), BorderLayout.CENTER);
        content.add(lblCount, BorderLayout.SOUTH);
        add(content);

        btnChoose.addActionListener(e -> chooseFile());
        btnSearch.addActionListener(e -> search());
        setSize(820, 520);
        setLocationRelativeTo(null);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Tệp văn bản (*.txt)", "txt"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
        }
    }

    private void search() {
        String keyword = txtKeyword.getText().trim();
        if (selectedFile == null || !selectedFile.isFile()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file TXT trước.");
            return;
        }
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa cần tìm.");
            return;
        }

        setControlsEnabled(false);
        txtMatches.setText("");
        lblCount.setText("Đang tìm kiếm...");
        progressBar.setIndeterminate(true);
        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);

        SwingWorker<Integer, String> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws IOException {
                int count = 0;
                int lineNumber = 0;
                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.toLowerCase(Locale.ROOT).contains(normalizedKeyword)) {
                            count++;
                            publish("Dòng " + lineNumber + ": " + line);
                        }
                    }
                }
                return count;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String match : chunks) {
                    txtMatches.append(match + System.lineSeparator());
                }
            }

            @Override
            protected void done() {
                try {
                    lblCount.setText("Số dòng tìm thấy: " + get());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    lblCount.setText("Tìm kiếm bị gián đoạn");
                } catch (ExecutionException ex) {
                    lblCount.setText("Không thể đọc file: " + ex.getCause().getMessage());
                } finally {
                    progressBar.setIndeterminate(false);
                    setControlsEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void setControlsEnabled(boolean enabled) {
        btnChoose.setEnabled(enabled);
        btnSearch.setEnabled(enabled);
        txtKeyword.setEnabled(enabled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileSearchFrame().setVisible(true));
    }
}
