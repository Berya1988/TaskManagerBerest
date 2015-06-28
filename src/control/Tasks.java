package control;

import model.*;
import java.util.*;

/**
 * Created by Oleg on 24.06.2015.
 */
public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {
        if (tasks == null || start == null || end == null)
            throw new IllegalArgumentException("Incorrect argument");
        if (start.after(end))
            throw new IllegalArgumentException("Argument start is greater than end");
        TaskList result = new LinkedTaskList();
        for (Task task : tasks) {
            Date time = task.nextTimeAfter(start);
            if (time != null && time.compareTo(end) <= 0) {
                result.add(task);
            }
        }
        return result;
    }

    public static SortedMap<Date, Set<String>> calendar(Iterable<Task> tasks, Date start, Date end) {
        if (tasks == null || start == null || end == null)
            throw new NullPointerException();
        if (start.after(end))
            return null;
        SortedMap<Date, Set<String>> map = new TreeMap<Date, Set<String>>();
        for (Task task : tasks) {
            Date nextDate = task.nextTimeAfter(start);
            while (nextDate != null && (nextDate.compareTo(end) <= 0)) {
                if (map.containsKey(nextDate)) {
                    Set<String> set = map.get(nextDate);
                    set.add(task.getTitle());
                } else {
                    Set<String> set = new HashSet<String>();
                    set.add(task.getTitle());
                    map.put(nextDate, set);
                }
                nextDate = task.nextTimeAfter(nextDate);
            }
        }
        return map;
    }

    synchronized public static void checkList(Iterable<Task> tasks){
        Date currentDate = new Date();
        for (Task task : tasks) {
            if(task.getStartTime().compareTo(currentDate) == 0)//>0
                System.out.println("You have new task for today! Check your task list!");
        }
    }
}
