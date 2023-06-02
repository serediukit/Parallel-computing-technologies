package main.java.Journal;

public class MarkThread extends Thread {
    private final Journal journal;

    public MarkThread(Journal journal) {
        this.journal = journal;
    }

    @Override
    public void run() {
        try {
            for (Group g : journal.getGroups())
                for (Student s : g.getStudents())
                    journal.assignMark(s, (int) (Math.random() * 100 + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
