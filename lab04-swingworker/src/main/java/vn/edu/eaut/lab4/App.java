package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App extends JFrame {
    private record Exercise(String title, Supplier<Frame> frameFactory) {
    }

    private final List<Exercise> exercises = List.of(
            new Exercise("Bài 1 - Đồng hồ đếm ngược", CountdownFrame::new),
            new Exercise("Bài 2 - Mô phỏng tải dữ liệu", ProgressDemoFrame::new),
            new Exercise("Bài 3 - Tổng số nguyên tố", PrimeSumFrame::new),
            new Exercise("Bài 4 - Fibonacci", FibonacciFrame::new),
            new Exercise("Bài 5 - Đếm dòng trong file", FileLineCounterFrame::new),
            new Exercise("Bài 6 - Hủy tác vụ", CancellableProgressFrame::new),
            new Exercise("Bài 7 - Tìm từ khóa trong file", FileSearchFrame::new),
            new Exercise("Bài 8 - Thống kê điểm CSV", CsvStudentStatsFrame::new),
            new Exercise("Bài 9 - Tải danh sách sản phẩm", ProductLoadFrame::new),
            new Exercise("Bài 10 - Quản lý sản phẩm CSV", ProductManagerFrame::new));

    public App() {
        setTitle("Lab 4 - SwingWorker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        JLabel title = new JLabel("LAB 4 - EVENT DISPATCH THREAD VÀ SWINGWORKER", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(16, 10, 4, 10));
        add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(5, 2, 10, 10));
        buttons.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));
        for (Exercise exercise : exercises) {
            JButton button = new JButton(exercise.title());
            button.addActionListener(e -> exercise.frameFactory().get().setVisible(true));
            buttons.add(button);
        }
        add(buttons, BorderLayout.CENTER);

        setSize(760, 430);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Swing's default look and feel remains usable.
            }
            new App().setVisible(true);
        });
    }
}
