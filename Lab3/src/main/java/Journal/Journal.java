package main.java.Journal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Journal {
    private final List<Group> groups = new ArrayList<>();
    private final HashMap<Student, ArrayBlockingQueue<Integer>> hashMap = new HashMap<>();

    public Journal(int groupsCount) {

        while (groupsCount-- > 0) {
            Group group = Group.generateGroup();
            groups.add(group);
            for (Student s : group.getStudents()) {
                if (!hashMap.containsKey(s))
                    hashMap.put(s, new ArrayBlockingQueue<>(1000));
            }
        }
    }

    public List<Group> getGroups() {
        return groups;
    }

    public synchronized void assignMark(Student student, int mark) throws InterruptedException {
        hashMap.get(student).put(mark);
    }

    public void printJournal() {
        for (Group g : groups) {
            System.out.println("\n" + g.getGroupNumber());
            for (Student s : g.getStudents()) {
                System.out.println(s.getFullName() + " marks: " + hashMap.get(s).toString());
            }
        }
    }
}
