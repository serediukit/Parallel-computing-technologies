package main.java.Journal;

public class Lecturer extends Person {
    private String academicTitle;

    public Lecturer(String firstName, String lastName, String academicTitle) {
        super(firstName, lastName);
        this.academicTitle = academicTitle;
    }

    public Thread assignMarks(Journal journal) throws InterruptedException {
        return new MarkThread(journal);
    }
}
