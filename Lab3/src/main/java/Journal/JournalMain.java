package main.java.Journal;

public class JournalMain {
    public static void main(String[] args) {
        final int weeksCount = 4;
        final int groupsCount = 3;

        Journal journal = new Journal(groupsCount);

        Lecturer teacher = new Lecturer(
                "teacherFirstName",
                "teacherLastName",
                "Lecturer"
        );
        Lecturer assist1 = new Lecturer(
                "a1fn",
                "a1ln",
                "Assistant"
        );
        Lecturer assist2 = new Lecturer(
                "a2fn",
                "a2ln",
                "Assistant"
        );
        Lecturer assist3 = new Lecturer(
                "a3fn",
                "a3ln",
                "Assistant"
        );

        for (int i = 0; i < weeksCount; i++) {
            try {
                teacher.assignMarks(journal);
                assist1.assignMarks(journal);
                assist2.assignMarks(journal);
                assist3.assignMarks(journal);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        journal.printJournal();
    }
}
