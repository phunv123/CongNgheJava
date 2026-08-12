package vn.edu.eaut.lab3;

public class Student {
    private String studentId;
    private String fullName;
    private double averageScore;

    public Student(String studentId, String fullName, double averageScore) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.averageScore = averageScore;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getRank() {
        if (averageScore >= 8.5) {
            return "Gioi";
        }
        if (averageScore >= 7.0) {
            return "Kha";
        }
        if (averageScore >= 5.0) {
            return "Trung binh";
        }
        return "Yeu";
    }
}
