package vn.edu.eaut.lab4;

import java.awt.GridLayout;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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

public class FibonacciFrame extends JFrame {
    private final JTextField txtN = new JTextField("1000");
    private final JButton btnFind = new JButton("Tìm");
    private final JTextArea txtResult = new JTextArea(4, 40);
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public FibonacciFrame() {
        setTitle("Bài 4 - Fibonacci bằng memoization");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        progressBar.setStringPainted(true);
        txtResult.setEditable(false);
        txtResult.setLineWrap(true);
        txtResult.setWrapStyleWord(true);

        JPanel input = new JPanel(new GridLayout(1, 2, 8, 8));
        input.add(new JLabel("Chỉ số N:"));
        input.add(txtN);
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(input);
        panel.add(btnFind);
        panel.add(progressBar);
        panel.add(new JScrollPane(txtResult));
        add(panel);

        btnFind.addActionListener(e -> findFibonacci());
        setSize(650, 330);
        setLocationRelativeTo(null);
    }

    private void findFibonacci() {
        final int n;
        try {
            n = Integer.parseInt(txtN.getText().trim());
            if (n < 0 || n > 100000) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "N phải là số nguyên từ 0 đến 100000.", "Dữ liệu không hợp lệ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnFind.setEnabled(false);
        txtN.setEnabled(false);
        progressBar.setValue(0);
        txtResult.setText("Đang tính Fibonacci...");

        SwingWorker<BigInteger, Void> worker = new SwingWorker<>() {
            @Override
            protected BigInteger doInBackground() {
                Map<Integer, BigInteger> memo = new HashMap<>();
                memo.put(0, BigInteger.ZERO);
                memo.put(1, BigInteger.ONE);
                for (int i = 2; i <= n; i++) {
                    memo.put(i, memo.get(i - 1).add(memo.get(i - 2)));
                    setProgress((int) (i * 100L / Math.max(1, n)));
                    if (i > 2) {
                        memo.remove(i - 2);
                    }
                }
                return memo.get(n);
            }

            @Override
            protected void done() {
                try {
                    txtResult.setText("Fibonacci(" + n + ") = " + get());
                    progressBar.setValue(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    txtResult.setText("Tác vụ đã bị gián đoạn");
                } catch (ExecutionException ex) {
                    txtResult.setText("Có lỗi khi tính Fibonacci");
                } finally {
                    btnFind.setEnabled(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FibonacciFrame().setVisible(true));
    }
}
