package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class FileLineCounterFrame extends JFrame {
    private final JButton btnChoose = new JButton("Chọn file");
    private final JButton btnCount = new JButton("Đếm dòng");
    private final JLabel lblFile = new JLabel("Chưa chọn file");
    private final JLabel lblResult = new JLabel("Số dòng: --");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private File selectedFile;

    public FileLineCounterFrame() {
        setTitle("Bài 5 - Đếm số dòng trong file");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        progressBar.setStringPainted(true);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnChoose);
        buttons.add(btnCount);
        JPanel center = new JPanel(new java.awt.GridLayout(3, 1, 8, 8));
        center.add(lblFile);
        center.add(progressBar);
        center.add(lblResult);
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        content.add(buttons, BorderLayout.NORTH);
        content.add(center, BorderLayout.CENTER);
        add(content);

        btnChoose.addActionListener(e -> chooseFile());
        btnCount.addActionListener(e -> countLines());
        setSize(650, 240);
        setLocationRelativeTo(null);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
            lblResult.setText("Số dòng: --");
            progressBar.setValue(0);
        }
    }

    private void countLines() {
        if (selectedFile == null || !selectedFile.isFile()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file cần đọc trước.");
            return;
        }

        setControlsEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Đang đọc file...");
        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() throws IOException {
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                long lines = 0;
                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines++;
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1L;
                        setProgress(totalBytes == 0 ? 100 : (int) Math.min(100, readBytes * 100 / totalBytes));
                    }
                }
                return lines;
            }

            @Override
            protected void done() {
                try {
                    lblResult.setText("Số dòng: " + get());
                    progressBar.setValue(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    lblResult.setText("Tác vụ đã bị gián đoạn");
                } catch (ExecutionException ex) {
                    lblResult.setText("Không thể đọc file: " + ex.getCause().getMessage());
                } finally {
                    setControlsEnabled(true);
                }
            }
        };
        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });
        worker.execute();
    }

    private void setControlsEnabled(boolean enabled) {
        btnChoose.setEnabled(enabled);
        btnCount.setEnabled(enabled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileLineCounterFrame().setVisible(true));
    }
}
