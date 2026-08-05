# Bao cao Lab 1 - Cong nghe Java

## 1. Thong tin bai lab

- Ten du an: lab01-java-console
- Package: vn.edu.eaut.lab1
- Cong nghe: Java SE Console, JDK, JVM, Maven, JAR

## 2. Kiem tra moi truong

Da kiem tra cac lenh:

```bash
java -version
javac -version
mvn -version
```

Moi truong hien tai:

- Java Runtime: OpenJDK 21
- Java Compiler: javac 21
- Maven: Apache Maven 3.9.12

## 3. Cau truc du an

```text
lab01-java-console/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── vn/
                └── edu/
                    └── eaut/
                        └── lab1/
                            ├── App.java
                            └── So.java
```

## 4. Mo ta chuong trinh

Lop `So` chua logic xu ly tinh toan:

- Tinh tong so chan tu 2 den n.
- Tinh tong nghich dao tu 1 den 1/n.
- Kiem tra so nguyen to.
- Kiem tra va phan loai tam giac.
- Hien thi n so Fibonacci dau tien.

Lop `App` hien thi menu Console, nhap du lieu bang `Scanner`, goi cac phuong thuc cua lop `So` va in ket qua.

## 5. Quy trinh bien dich va dong goi

Ma nguon `.java` duoc Maven bien dich bang JDK thanh bytecode `.class`. Khi chay chuong trinh, JVM doc bytecode va thuc thi tren may hien tai. JIT trong JVM co nhiem vu toi uu cac doan ma duoc chay nhieu lan trong thoi gian thuc thi. Maven dong goi cac file `.class` thanh file `.jar`, trong do manifest khai bao lop chinh `vn.edu.eaut.lab1.App`, nen co the chay bang:

```bash
java -jar target/lab01-java-console-1.0-SNAPSHOT.jar
```

## 6. Lenh build va chay

```bash
mvn clean compile
mvn exec:java
mvn clean package
java -jar target/lab01-java-console-1.0-SNAPSHOT.jar
```

## 7. Nhan xet ket qua

Chuong trinh dap ung du 5 bai tap Console, tach rieng phan xu ly logic va phan giao dien nhap xuat. File JAR duoc tao trong thu muc `target` va chay duoc bang lenh `java -jar`.
