package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProductManagerFrame extends JFrame {
    private final JTextField txtId = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextField txtPrice = new JTextField();
    private final JButton btnAdd = new JButton("Thêm");
    private final JButton btnUpdate = new JButton("Sửa");
    private final JButton btnDelete = new JButton("Xóa");
    private final JButton btnClear = new JButton("Làm mới");
    private final JButton btnOpen = new JButton("Đọc CSV");
    private final JButton btnSave = new JButton("Lưu CSV");
    private final JLabel lblStatus = new JLabel("Sẵn sàng");
    private final JProgressBar progressBar = new JProgressBar();
    private final ProductTableModel tableModel = new ProductTableModel();
    private final JTable table = new JTable(tableModel);

    public ProductManagerFrame() {
        setTitle("Bài 10 - Quản lý sản phẩm bằng file CSV");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Mã sản phẩm:"));
        form.add(txtId);
        form.add(new JLabel("Tên sản phẩm:"));
        form.add(txtName);
        form.add(new JLabel("Đơn giá:"));
        form.add(txtPrice);

        JPanel editButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editButtons.add(btnAdd);
        editButtons.add(btnUpdate);
        editButtons.add(btnDelete);
        editButtons.add(btnClear);
        JPanel fileButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        fileButtons.add(btnOpen);
        fileButtons.add(btnSave);
        JPanel buttons = new JPanel(new BorderLayout());
        buttons.add(editButtons, BorderLayout.WEST);
        buttons.add(fileButtons, BorderLayout.EAST);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);
        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.add(progressBar, BorderLayout.NORTH);
        bottom.add(lblStatus, BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedProduct();
            }
        });

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(top, BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);
        content.add(bottom, BorderLayout.SOUTH);
        add(content);

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clearForm());
        btnOpen.addActionListener(e -> chooseAndLoad());
        btnSave.addActionListener(e -> chooseAndSave());

        tableModel.setProducts(List.of(
                new Product("SP01", "Bàn phím", new BigDecimal("250000")),
                new Product("SP02", "Chuột", new BigDecimal("150000")),
                new Product("SP03", "Màn hình", new BigDecimal("2500000"))));
        setSize(850, 570);
        setLocationRelativeTo(null);
    }

    private void addProduct() {
        Product product = readProductFromForm(-1);
        if (product != null) {
            tableModel.addProduct(product);
            clearForm();
            lblStatus.setText("Đã thêm sản phẩm " + product.id());
        }
    }

    private void updateProduct() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa.");
            return;
        }
        Product product = readProductFromForm(row);
        if (product != null) {
            tableModel.updateProduct(row, product);
            clearForm();
            lblStatus.setText("Đã cập nhật sản phẩm " + product.id());
        }
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa.");
            return;
        }
        Product product = tableModel.getProductAt(row);
        int answer = JOptionPane.showConfirmDialog(this, "Xóa sản phẩm " + product.id() + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            tableModel.removeProduct(row);
            clearForm();
            lblStatus.setText("Đã xóa sản phẩm " + product.id());
        }
    }

    private Product readProductFromForm(int ignoredRow) {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        if (id.isEmpty() || name.isEmpty() || txtPrice.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mã, tên và đơn giá.");
            return null;
        }
        if (tableModel.containsId(id, ignoredRow)) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại.");
            return null;
        }
        try {
            BigDecimal price = new BigDecimal(txtPrice.getText().trim());
            if (price.signum() < 0) {
                throw new NumberFormatException();
            }
            return new Product(id, name, price);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số không âm.");
            return null;
        }
    }

    private void showSelectedProduct() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Product product = tableModel.getProductAt(row);
            txtId.setText(product.id());
            txtName.setText(product.name());
            txtPrice.setText(product.price().toPlainString());
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        table.clearSelection();
        txtId.requestFocusInWindow();
    }

    private void chooseAndLoad() {
        JFileChooser chooser = createCsvChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadProducts(chooser.getSelectedFile());
        }
    }

    private void chooseAndSave() {
        JFileChooser chooser = createCsvChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase(Locale.ROOT).endsWith(".csv")) {
                file = new File(file.getParentFile(), file.getName() + ".csv");
            }
            if (file.exists()) {
                int answer = JOptionPane.showConfirmDialog(this, "File đã tồn tại. Ghi đè?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (answer != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            saveProducts(file);
        }
    }

    private JFileChooser createCsvChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Tệp CSV (*.csv)", "csv"));
        return chooser;
    }

    private void loadProducts(File file) {
        setBusy(true, "Đang đọc file CSV...");
        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws IOException {
                List<Product> products = new ArrayList<>();
                Set<String> ids = new HashSet<>();
                try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    int lineNumber = 0;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.isBlank() || (lineNumber == 1 && line.toLowerCase(Locale.ROOT).contains("masp"))) {
                            continue;
                        }
                        List<String> fields = CsvUtils.parseLine(line);
                        if (fields.size() != 3) {
                            throw new IOException("Dòng " + lineNumber + " không có đúng 3 cột");
                        }
                        try {
                            BigDecimal price = new BigDecimal(fields.get(2));
                            if (price.signum() < 0 || fields.get(0).isBlank() || fields.get(1).isBlank()) {
                                throw new NumberFormatException();
                            }
                            if (!ids.add(fields.get(0).toLowerCase(Locale.ROOT))) {
                                throw new IOException("Trùng mã sản phẩm tại dòng " + lineNumber);
                            }
                            products.add(new Product(fields.get(0), fields.get(1), price));
                        } catch (NumberFormatException ex) {
                            throw new IOException("Dữ liệu không hợp lệ tại dòng " + lineNumber);
                        }
                    }
                }
                return products;
            }

            @Override
            protected void done() {
                try {
                    List<Product> products = get();
                    tableModel.setProducts(products);
                    clearForm();
                    lblStatus.setText("Đã đọc " + products.size() + " sản phẩm từ " + file.getName());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    showWorkerError("Tác vụ bị gián đoạn");
                } catch (ExecutionException ex) {
                    showWorkerError(ex.getCause().getMessage());
                } finally {
                    setBusy(false, null);
                }
            }
        };
        worker.execute();
    }

    private void saveProducts(File file) {
        List<Product> snapshot = tableModel.getProducts();
        setBusy(true, "Đang lưu file CSV...");
        SwingWorker<Integer, Void> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws IOException {
                try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
                    writer.write("MaSP,TenSP,DonGia");
                    writer.newLine();
                    for (Product product : snapshot) {
                        writer.write(CsvUtils.escape(product.id()) + "," + CsvUtils.escape(product.name()) + "," + product.price().toPlainString());
                        writer.newLine();
                    }
                }
                return snapshot.size();
            }

            @Override
            protected void done() {
                try {
                    lblStatus.setText("Đã lưu " + get() + " sản phẩm vào " + file.getName());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    showWorkerError("Tác vụ bị gián đoạn");
                } catch (ExecutionException ex) {
                    showWorkerError(ex.getCause().getMessage());
                } finally {
                    setBusy(false, null);
                }
            }
        };
        worker.execute();
    }

    private void setBusy(boolean busy, String status) {
        btnAdd.setEnabled(!busy);
        btnUpdate.setEnabled(!busy);
        btnDelete.setEnabled(!busy);
        btnClear.setEnabled(!busy);
        btnOpen.setEnabled(!busy);
        btnSave.setEnabled(!busy);
        txtId.setEnabled(!busy);
        txtName.setEnabled(!busy);
        txtPrice.setEnabled(!busy);
        table.setEnabled(!busy);
        progressBar.setIndeterminate(busy);
        if (status != null) {
            lblStatus.setText(status);
        }
    }

    private void showWorkerError(String message) {
        lblStatus.setText("Thao tác thất bại");
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductManagerFrame().setVisible(true));
    }
}
