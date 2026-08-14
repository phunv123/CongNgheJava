package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class ProductLoadFrame extends JFrame {
    private final JButton btnLoad = new JButton("Tải sản phẩm");
    private final ProductTableModel tableModel = new ProductTableModel();
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel lblStatus = new JLabel("Chưa tải dữ liệu");

    public ProductLoadFrame() {
        setTitle("Bài 9 - Mô phỏng tải danh sách sản phẩm");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        progressBar.setStringPainted(true);

        JPanel top = new JPanel(new GridLayout(3, 1, 8, 8));
        top.add(btnLoad);
        top.add(progressBar);
        top.add(lblStatus);
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        content.add(top, BorderLayout.NORTH);
        content.add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
        add(content);

        btnLoad.addActionListener(e -> loadProducts());
        setSize(650, 420);
        setLocationRelativeTo(null);
    }

    private void loadProducts() {
        btnLoad.setEnabled(false);
        tableModel.setProducts(List.of());
        progressBar.setValue(0);
        lblStatus.setText("Đang tải sản phẩm...");
        List<Product> sampleData = List.of(
                new Product("SP01", "Bàn phím", new BigDecimal("250000")),
                new Product("SP02", "Chuột", new BigDecimal("150000")),
                new Product("SP03", "Màn hình", new BigDecimal("2500000")));

        SwingWorker<Void, Product> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                for (int i = 0; i < sampleData.size(); i++) {
                    Thread.sleep(800);
                    publish(sampleData.get(i));
                    setProgress((i + 1) * 100 / sampleData.size());
                }
                return null;
            }

            @Override
            protected void process(List<Product> chunks) {
                chunks.forEach(tableModel::addProduct);
            }

            @Override
            protected void done() {
                try {
                    get();
                    progressBar.setValue(100);
                    lblStatus.setText("Đã tải " + tableModel.getRowCount() + " sản phẩm");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    lblStatus.setText("Tác vụ bị gián đoạn");
                } catch (ExecutionException ex) {
                    lblStatus.setText("Không thể tải dữ liệu");
                } finally {
                    btnLoad.setEnabled(true);
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
        SwingUtilities.invokeLater(() -> new ProductLoadFrame().setVisible(true));
    }
}
