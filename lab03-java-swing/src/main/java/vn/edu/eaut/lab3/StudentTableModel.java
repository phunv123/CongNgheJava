package vn.edu.eaut.lab3;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class StudentTableModel extends AbstractTableModel {
    private final String[] columns = {"Ma SV", "Ho ten", "Diem TB", "Xep loai"};
    private final List<Student> students = new ArrayList<>();

    @Override
    public int getRowCount() {
        return students.size();
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
        Student student = students.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> student.getStudentId();
            case 1 -> student.getFullName();
            case 2 -> String.format("%.2f", student.getAverageScore());
            case 3 -> student.getRank();
            default -> "";
        };
    }

    public Student getStudentAt(int rowIndex) {
        return students.get(rowIndex);
    }

    public void addStudent(Student student) {
        students.add(student);
        int row = students.size() - 1;
        fireTableRowsInserted(row, row);
    }

    public void updateStudent(int rowIndex, Student student) {
        students.set(rowIndex, student);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void removeStudent(int rowIndex) {
        students.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
