package main.java.Journal;

public class Student extends Person{
    private static int studentsCount = 0;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        studentsCount++;
    }

    public static Student generateStudent() {
        return new Student("firstName" + studentsCount, "lastName" + studentsCount);
    }
}
