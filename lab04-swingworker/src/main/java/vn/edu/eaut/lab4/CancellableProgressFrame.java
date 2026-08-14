package vn.edu.eaut.lab4;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class CancellableProgressFrame extends JFrame {
    private final JButton btnStart = new JButton("Bắt đầu");
    private final JButton btnCancel = new JButton("Hủy");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel lblStatus = new JLabel("Sẵn sàng");
    private SwingWorker<Void, Void> worker;

    public CancellableProgressFrame() {
        setTitle("Bài 6 - Hủy tác vụ SwingWorker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        progressBar.setStringPainted(true);
        btnCancel.setEnabled(false);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.add(btnStart);
        buttons.add(btnCancel);
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.add(buttons);
        panel.add(progressBar);
        panel.add(lblStatus);
        add(panel);

        btnStart.addActionListener(e -> startTask());
        btnCancel.addActionListener(e -> cancelTask());
        setSize(470, 220);
        setLocationRelativeTo(null);
    }

    private void startTask() {
        btnStart.setEnabled(false);
        btnCancel.setEnabled(true);
        progressBar.setValue(0);
        lblStatus.setText("Đang xử lý...");

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                for (int i = 0; i <= 100; i++) {
                    if (isCancelled()) {
                        return null;
                    }
                    setProgress(i);
                    Thread.sleep(100);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    lblStatus.setText("Tác vụ hoàn thành");
                    progressBar.setValue(100);
                } catch (CancellationException ex) {
                    lblStatus.setText("Đã hủy tác vụ tại " + progressBar.getValue() + "%");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    lblStatus.setText("Tác vụ bị gián đoạn");
                } catch (ExecutionException ex) {
                    lblStatus.setText("Tác vụ gặp lỗi");
                } finally {
                    btnStart.setEnabled(true);
                    btnCancel.setEnabled(false);
                }
            }
        };
        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
                lblStatus.setText("Đang xử lý: " + evt.getNewValue() + "%");
            }
        });
        worker.execute();
    }

    private void cancelTask() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CancellableProgressFrame().setVisible(true));
    }
}
