package pojo.logicEntity;

import pojo.Student;

import java.util.ArrayList;
import java.util.List;

class StudentNumberAndName {
    private String studentNumber;
    private String studentName;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}

public class StudentAttendanceInfo {
    private List<StudentNumberAndName> submitted = new ArrayList<>();
    private List<StudentNumberAndName> unSubmitted= new ArrayList<>();

    public void addSubmitted(String studentNumber, String studentName) {
        StudentNumberAndName studentNumberAndName = new StudentNumberAndName();
        studentNumberAndName.setStudentName(studentName);
        studentNumberAndName.setStudentNumber(studentNumber);

        submitted.add(studentNumberAndName);
    }

    public void addUnSubmitted(String studentNumber, String studentName) {
        StudentNumberAndName studentNumberAndName = new StudentNumberAndName();
        studentNumberAndName.setStudentName(studentName);
        studentNumberAndName.setStudentNumber(studentNumber);

        unSubmitted.add(studentNumberAndName);
    }

    public List<StudentNumberAndName> getSubmitted() {
        return submitted;
    }

    public void setSubmitted(List<StudentNumberAndName> submitted) {
        this.submitted = submitted;
    }

    public List<StudentNumberAndName> getUnSubmitted() {
        return unSubmitted;
    }

    public void setUnSubmitted(List<StudentNumberAndName> unSubmitted) {
        this.unSubmitted = unSubmitted;
    }

    @Override
    public String toString() {
        return "StudentAttendanceInfo{" +
                "submitted=" + submitted +
                ", unSubmitted=" + unSubmitted +
                '}';
    }
}
