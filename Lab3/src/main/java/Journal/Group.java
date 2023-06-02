package main.java.Journal;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Student> students;
    private String groupNumber;
    private static int groupsCount = 0;

    public Group(String groupNumber, int count) {
        this.groupNumber = groupNumber;
        students = new ArrayList<>();
        groupsCount++;

        while (count-- > 0) {
            students.add(Student.generateStudent());
        }
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public List<Student> getStudents() {
        return students;
    }

    public static Group generateGroup() {
        return new Group("group-" + groupsCount, (int) (Math.random() * 16 + 15));
    }
}
