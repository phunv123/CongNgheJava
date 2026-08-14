package vn.edu.eaut.lab4;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {
    private final String[] columns = {"Mã SP", "Tên sản phẩm", "Đơn giá"};
    private final List<Product> products = new ArrayList<>();

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> product.id();
            case 1 -> product.name();
            case 2 -> product.price().toPlainString();
            default -> "";
        };
    }

    public Product getProductAt(int row) {
        return products.get(row);
    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }

    public boolean containsId(String id, int ignoredRow) {
        for (int i = 0; i < products.size(); i++) {
            if (i != ignoredRow && products.get(i).id().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public void addProduct(Product product) {
        int row = products.size();
        products.add(product);
        fireTableRowsInserted(row, row);
    }

    public void updateProduct(int row, Product product) {
        products.set(row, product);
        fireTableRowsUpdated(row, row);
    }

    public void removeProduct(int row) {
        products.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void setProducts(List<Product> newProducts) {
        products.clear();
        products.addAll(newProducts);
        fireTableDataChanged();
    }
}
