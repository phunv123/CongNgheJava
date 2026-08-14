package vn.edu.eaut.lab4;

import java.awt.GridLayout;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class PrimeSumFrame extends JFrame {
    private final JTextField txtN = new JTextField("100000");
    private final JButton btnCalculate = new JButton("Tính");
    private final JLabel lblResult = new JLabel("Nhập N để tính tổng các số nguyên tố nhỏ hơn N");
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public PrimeSumFrame() {
        setTitle("Bài 3 - Tổng các số nguyên tố");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        progressBar.setStringPainted(true);

        JPanel input = new JPanel(new GridLayout(1, 2, 8, 8));
        input.add(new JLabel("Giá trị N:"));
        input.add(txtN);
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.add(input);
        panel.add(btnCalculate);
        panel.add(progressBar);
        panel.add(lblResult);
        add(panel);

        btnCalculate.addActionListener(e -> calculatePrimeSum());
        setSize(590, 250);
        setLocationRelativeTo(null);
    }

    private void calculatePrimeSum() {
        final int n;
        try {
            n = Integer.parseInt(txtN.getText().trim());
            if (n <= 2) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "N phải là số nguyên lớn hơn 2.", "Dữ liệu không hợp lệ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnCalculate.setEnabled(false);
        txtN.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Đang tính...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() {
                long sum = 0;
                int previousProgress = -1;
                for (int i = 2; i < n; i++) {
                    if (isPrime(i)) {
                        sum += i;
                    }
                    int progress = (int) (i * 100L / (n - 1));
                    if (progress != previousProgress) {
                        setProgress(progress);
                        previousProgress = progress;
                    }
                }
                return sum;
            }

            @Override
            protected void done() {
                try {
                    lblResult.setText("Tổng các số nguyên tố nhỏ hơn " + n + " = " + get());
                    progressBar.setValue(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    lblResult.setText("Tác vụ đã bị gián đoạn");
                } catch (ExecutionException ex) {
                    lblResult.setText("Có lỗi khi tính toán");
                } finally {
                    btnCalculate.setEnabled(true);
                    txtN.setEnabled(true);
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

    private boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= n / i; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrimeSumFrame().setVisible(true));
    }
}
