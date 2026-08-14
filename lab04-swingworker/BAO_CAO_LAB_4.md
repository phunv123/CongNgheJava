# BÁO CÁO LAB 4 - XỬ LÝ SỰ KIỆN, EDT VÀ SWINGWORKER

**Học phần:** Công nghệ Java<br>
**Sinh viên:** Nguyễn Văn Phú<br>
**MSSV:** 20230745<br>
**Lớp package:** `vn.edu.eaut.lab4`

## 1. Mục tiêu

Xây dựng 10 ứng dụng Java Swing có xử lý sự kiện và tác vụ nền. Các phép tính lâu, mô phỏng tải dữ liệu và thao tác file được thực hiện bằng `SwingWorker` để giao diện vẫn phản hồi bình thường.

## 2. Môi trường

- Java 17 (project được kiểm tra bằng JDK 21, biên dịch với `release 17`).
- Apache Maven 3.x.
- Java Swing và SwingWorker.

Lệnh build và chạy:

```bash
mvn clean package
java -jar target/lab04-swingworker-1.0-SNAPSHOT.jar
```

## 3. Kết quả thực hiện

| Bài | Nội dung | Kết quả |
|---|---|---|
| 1 | Đồng hồ đếm ngược | Dùng `publish/process` cập nhật số giây còn lại. |
| 2 | Mô phỏng tải dữ liệu | Dùng `setProgress` cập nhật `JProgressBar`. |
| 3 | Tổng số nguyên tố nhỏ hơn N | Tính trong luồng nền, trả tổng kiểu `long`. |
| 4 | Fibonacci thứ N | Dùng `BigInteger` và bảng ghi nhớ kết quả. |
| 5 | Đếm số dòng file | Dùng `JFileChooser`, đọc UTF-8 trong luồng nền. |
| 6 | Hủy tác vụ | Dùng `cancel(true)` và kiểm tra `isCancelled()`. |
| 7 | Tìm từ khóa trong file | Không phân biệt hoa/thường, đưa kết quả qua `publish/process`. |
| 8 | Thống kê điểm CSV | Hiển thị `JTable`, tính trung bình và điểm cao nhất. |
| 9 | Tải danh sách sản phẩm | Mô phỏng tải từng sản phẩm và cập nhật tiến độ. |
| 10 | Quản lý sản phẩm CSV | Thêm, sửa, xóa, đọc và lưu CSV bằng `SwingWorker`. |

## 4. Giải thích EDT và SwingWorker

EDT (Event Dispatch Thread) là luồng xử lý sự kiện và cập nhật component của Swing. Nếu thực hiện phép tính nặng, gọi mạng hoặc đọc file lớn ngay trong `ActionListener`, EDT bị chiếm giữ nên cửa sổ không thể vẽ lại và bị cảm giác treo.

`SwingWorker` tách công việc thành hai phần. `doInBackground()` chạy trên luồng nền. `process()` và `done()` chạy trên EDT nên có thể cập nhật giao diện an toàn. `publish/process` phù hợp để chuyển nhiều kết quả trung gian; `setProgress` phát sự kiện tiến độ dạng số từ 0 đến 100.

## 5. Trả lời câu hỏi củng cố

1. **EDT là gì?** EDT là luồng duy nhất xử lý phần lớn sự kiện và thao tác cập nhật component Swing.
2. **Vì sao không đọc file lớn trong ActionListener?** Vì `ActionListener` chạy trên EDT; thao tác lâu sẽ chặn xử lý vẽ lại và sự kiện người dùng.
3. **`doInBackground()` dùng để làm gì?** Dùng để thực hiện tác vụ lâu trên worker thread và trả về kết quả khi hoàn tất.
4. **`done()` được gọi khi nào?** Được gọi trên EDT sau khi worker hoàn thành, bị hủy hoặc gặp lỗi; kết quả được lấy bằng `get()`.
5. **`publish/process` khác `setProgress` thế nào?** `publish/process` chuyển dữ liệu trung gian có kiểu tùy ý; `setProgress` chỉ cập nhật giá trị tiến độ từ 0 đến 100 qua `PropertyChangeListener`.
6. **Vì sao Lab 4 chuẩn bị cho Lab 5?** Truy vấn JDBC có thể mất thời gian. Kỹ thuật SwingWorker giúp tải dữ liệu CSDL mà không làm treo giao diện và cập nhật bảng sau khi truy vấn xong.

## 6. Minh chứng

Ảnh chạy của màn hình chính và 10 bài nằm trong thư mục `minh-chung`. File Word đi kèm đã chèn trực tiếp các ảnh này.

## 7. Kết luận

Project hoàn thành đủ 10 bài, đúng cấu trúc Maven và package yêu cầu. Build tạo JAR có `Main-Class`, chạy trực tiếp bằng `java -jar`. Các thao tác lâu không chạy trực tiếp trên EDT; trạng thái nút và tiến trình được cập nhật phù hợp.
