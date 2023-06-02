package main.java.Journal;

import java.util.ArrayList;
import java.util.List;

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
            List<Thread> threads = new ArrayList<>();
            try {
                threads.add(teacher.assignMarks(journal));
                threads.add(assist1.assignMarks(journal));
                threads.add(assist2.assignMarks(journal));
                threads.add(assist3.assignMarks(journal));

                for (Thread thread : threads) {
                    thread.start();
                }

                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        journal.printJournal();
    }
}
