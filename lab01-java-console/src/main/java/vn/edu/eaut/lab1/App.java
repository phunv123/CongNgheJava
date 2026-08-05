package vn.edu.eaut.lab1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            hienThiMenu();
            choice = nhapSoNguyen(scanner, "Chon bai tap: ");

            try {
                switch (choice) {
                    case 1 -> bai1(scanner);
                    case 2 -> bai2(scanner);
                    case 3 -> bai3(scanner);
                    case 4 -> bai4(scanner);
                    case 5 -> bai5(scanner);
                    case 0 -> System.out.println("Ket thuc chuong trinh.");
                    default -> System.out.println("Lua chon khong hop le!");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Loi: " + ex.getMessage());
            }
            System.out.println();
        } while (choice != 0);

        scanner.close();
    }

    private static void hienThiMenu() {
        System.out.println("========== LAB 1 - JAVA SE CONSOLE ==========");
        System.out.println("1. Tinh S = 2 + 4 + ... + n");
        System.out.println("2. Tinh S = 1 + 1/2 + ... + 1/n");
        System.out.println("3. Kiem tra so nguyen to");
        System.out.println("4. Kiem tra va phan loai tam giac");
        System.out.println("5. Hien thi n so Fibonacci dau tien");
        System.out.println("0. Thoat");
    }

    private static void bai1(Scanner scanner) {
        int n = nhapSoNguyen(scanner, "Nhap n: ");
        System.out.println("S = " + So.tongChanDenN(n));
    }

    private static void bai2(Scanner scanner) {
        int n = nhapSoNguyen(scanner, "Nhap n: ");
        System.out.printf("S = %.4f%n", So.tongNghichDao(n));
    }

    private static void bai3(Scanner scanner) {
        int n = nhapSoNguyen(scanner, "Nhap n: ");
        if (So.laSoNguyenTo(n)) {
            System.out.println(n + " la so nguyen to.");
        } else {
            System.out.println(n + " khong phai la so nguyen to.");
        }
    }

    private static void bai4(Scanner scanner) {
        double a = nhapSoThuc(scanner, "Nhap a: ");
        double b = nhapSoThuc(scanner, "Nhap b: ");
        double c = nhapSoThuc(scanner, "Nhap c: ");
        System.out.println("Ket qua: " + So.loaiTamGiac(a, b, c));
    }

    private static void bai5(Scanner scanner) {
        int n = nhapSoNguyen(scanner, "Nhap n: ");
        System.out.println("Day Fibonacci: " + So.dayFibonacci(n));
    }

    private static int nhapSoNguyen(Scanner scanner, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Vui long nhap so nguyen.");
                scanner.next();
            }
        }
    }

    private static double nhapSoThuc(Scanner scanner, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException ex) {
                System.out.println("Vui long nhap so.");
                scanner.next();
            }
        }
    }
}
