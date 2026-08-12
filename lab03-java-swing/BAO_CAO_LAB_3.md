# Bao cao Lab 3 - Cong nghe Java

## 1. Thong tin bai lab

- Ten du an: lab03-java-swing
- Package: vn.edu.eaut.lab3
- Cong nghe: Java SE Desktop, Swing, Maven
- Noi dung: JFrame, JPanel, JLabel, JTextField, JButton, JTextArea, JTable, Layout Manager, Event Handling, EDT

## 2. Kiem tra moi truong

Da kiem tra cac lenh:

```bash
java -version
javac -version
mvn -version
echo $JAVA_HOME
```

Moi truong hien tai:

- Java Runtime: OpenJDK 21
- Java Compiler: javac 21
- Maven: Apache Maven 3.9.12
- JAVA_HOME goi y tren may Linux: /usr/lib/jvm/java-21-openjdk-amd64

Neu can cau hinh JAVA_HOME tam thoi trong Terminal:

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

## 3. Cau truc project

```text
lab03-java-swing/
├── pom.xml
└── src/main/java/vn/edu/eaut/lab3/
    ├── Bai01HelloSwing.java
    ├── Bai02TongHaiSo.java
    ├── Bai03PhuongTrinhBacNhat.java
    ├── Bai04TamGiacSwing.java
    ├── Bai05FibonacciSwing.java
    ├── Bai06LoginForm.java
    ├── Bai07MayTinhMini.java
    ├── Bai08QuanLySinhVien.java
    ├── Student.java
    └── StudentTableModel.java
```

## 4. Mo ta cac bai tap

| Bai | Noi dung | Thanh phan Swing chinh |
| --- | --- | --- |
| 1 | Chao nguoi dung | JFrame, JLabel, JTextField, JButton, JOptionPane |
| 2 | Tinh tong hai so | JFrame, GridLayout, JTextField, JLabel, JButton |
| 3 | Giai phuong trinh bac nhat | BorderLayout, GridLayout, JTextField, JLabel |
| 4 | Kiem tra va phan loai tam giac | JTextField, JButton, JOptionPane |
| 5 | Hien thi day Fibonacci | JTextArea, JScrollPane, JTextField |
| 6 | Form dang nhap | JPasswordField, JComboBox, JCheckBox |
| 7 | May tinh mini | JButton, JTextArea lich su, xu ly chia 0 |
| 8 | Quan ly sinh vien | JTable, Student, StudentTableModel |

Tat ca giao dien deu khoi dong bang:

```java
SwingUtilities.invokeLater(() -> new TenLop().setVisible(true));
```

## 5. Cach build va chay

Dung lenh sau tai thu muc goc project:

```bash
cd lab03-java-swing
mvn clean compile
mvn clean package
```

Chay file JAR mac dinh:

```bash
java -jar target/lab03-java-swing-1.0-SNAPSHOT.jar
```

Chay tung bai bang Maven:

```bash
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai01HelloSwing"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai02TongHaiSo"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai03PhuongTrinhBacNhat"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai04TamGiacSwing"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai05FibonacciSwing"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai06LoginForm"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai07MayTinhMini"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai08QuanLySinhVien"
```

## 6. Xu ly loi nhap lieu

- Cac bai nhap so su dung `try/catch NumberFormatException`.
- Diem va so canh duoc kiem tra khoang gia tri hop le.
- Bai Fibonacci gioi han `1 <= n <= 92` de tranh tran kieu `long`.
- Bai may tinh mini xu ly rieng loi chia cho 0.
- Bai dang nhap thong bao ro khi sai tai khoan, mat khau hoac vai tro.
- Bai quan ly sinh vien kiem tra thong tin rong va diem trung binh ngoai khoang 0-10.

## 7. Ket luan

Project da hoan thanh 8 bai Java Swing theo yeu cau Lab 3, co cau truc Maven dung package, tach logic co ban, xu ly su kien bang lambda va khoi dong giao dien dung EDT.
