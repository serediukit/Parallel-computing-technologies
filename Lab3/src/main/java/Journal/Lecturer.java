package main.java.Journal;

public class Lecturer extends Person {
    private String academicTitle;

    public Lecturer(String firstName, String lastName, String academicTitle) {
        super(firstName, lastName);
        this.academicTitle = academicTitle;
    }

    public void assignMarks(Journal journal) throws InterruptedException {
        Thread marksThread = new MarkThread(journal);
        marksThread.start();
        marksThread.join();
    }
}
