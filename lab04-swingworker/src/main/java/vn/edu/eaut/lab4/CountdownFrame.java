package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.concurrent.CancellationException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class CountdownFrame extends JFrame {
    private final JTextField txtSeconds = new JTextField("5");
    private final JButton btnStart = new JButton("Bắt đầu");
    private final JLabel lblTime = new JLabel("Thời gian còn lại: --", SwingConstants.CENTER);

    public CountdownFrame() {
        setTitle("Bài 1 - Đồng hồ đếm ngược");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel input = new JPanel(new GridLayout(1, 2, 8, 8));
        input.add(new JLabel("Số giây:"));
        input.add(txtSeconds);
        lblTime.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JPanel content = new JPanel(new GridLayout(3, 1, 10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        content.add(input);
        content.add(btnStart);
        content.add(lblTime);
        add(content);

        btnStart.addActionListener(e -> startCountdown());
        setSize(420, 220);
        setLocationRelativeTo(null);
    }

    private void startCountdown() {
        final int seconds;
        try {
            seconds = Integer.parseInt(txtSeconds.getText().trim());
            if (seconds <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số giây phải là số nguyên lớn hơn 0.", "Dữ liệu không hợp lệ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnStart.setEnabled(false);
        txtSeconds.setEnabled(false);
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                for (int i = seconds; i >= 0; i--) {
                    publish(i);
                    if (i > 0) {
                        Thread.sleep(1000);
                    }
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                lblTime.setText("Thời gian còn lại: " + value + " giây");
            }

            @Override
            protected void done() {
                btnStart.setEnabled(true);
                txtSeconds.setEnabled(true);
                try {
                    get();
                    JOptionPane.showMessageDialog(CountdownFrame.this, "Đếm ngược hoàn thành!");
                } catch (CancellationException ignored) {
                    lblTime.setText("Đã hủy");
                } catch (Exception ex) {
                    lblTime.setText("Tác vụ gặp lỗi");
                }
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CountdownFrame().setVisible(true));
    }
}
