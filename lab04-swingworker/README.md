# Lab 4 - SwingWorker

Họ tên: Nguyễn Văn Phú<br>
MSSV: 20230745<br>
Package: `vn.edu.eaut.lab4`

Project gồm 10 bài thực hành về xử lý sự kiện, EDT và `SwingWorker` trong Java Swing.

## Yêu cầu

- JDK 17 trở lên
- Apache Maven 3.x

## Build và chạy

```bash
cd lab04-swingworker
mvn clean package
java -jar target/lab04-swingworker-1.0-SNAPSHOT.jar
```

Màn hình chính cho phép mở từng bài. Cũng có thể chạy trực tiếp một lớp trong IDE, ví dụ `CountdownFrame` hoặc `ProductManagerFrame`.

## Dữ liệu thử

- `sample-data/sinh-vien.csv`: dùng cho Bài 8.
- `sample-data/san-pham.csv`: dùng thử chức năng đọc CSV của Bài 10.
- `sample-data/van-ban-mau.txt`: dùng cho Bài 5 và Bài 7.

Ảnh chạy chương trình nằm trong thư mục `minh-chung`.
