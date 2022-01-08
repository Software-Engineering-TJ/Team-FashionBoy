package pojo.logicEntity;

import pojo.Instructor;
import pojo.Student;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
    private List<Instructor> instructors;
    private List<Student> assistants;
    private List<Student> students;

    public ClassInfo() {
        instructors = new ArrayList<Instructor>();
        assistants = new ArrayList<Student>();
        students = new ArrayList<Student>();
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public void addAssistant(Student assistant) {
        assistants.add(assistant);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public List<Student> getAssistants() {
        return assistants;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public void setAssistants(List<Student> assistants) {
        this.assistants = assistants;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
