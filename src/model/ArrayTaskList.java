package model;

import java.util.Iterator;

/**
 * Created by Oleg on 13.06.2015.
 */
public class ArrayTaskList extends TaskList {
    private int size;
    private Task[] tasks = new Task[10];

    public ArrayTaskList() {
        size = 0;

    }

    public void add(Task task) {                // метод, що додаЇ до списку вказану задачу.
        if(task == null)
            throw new IllegalArgumentException("task = null");
        if (size == tasks.length){
            Task[] newArray = new Task[size+10];
            System.arraycopy(tasks, 0, newArray, 0, size);
            tasks = newArray;
        }
        tasks[size++] = task;

    }

    public boolean remove(Task task) {          // метод, що видал€Ї задачу ≥з списку ≥ повертаЇ ≥стину, €кщо така задача була у списку. якщо у списку було дек≥лька таких задач, необх≥дно видалити одну будь-€ку.
        for (int i = 0; i < size; i++) {
            if(tasks[i].equals(task)){
                System.arraycopy(tasks, i+1, tasks, i, size-i-1);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {                         // метод, що повертаЇ к≥льк≥сть задач у списку.
        return size;
    }

    public Task getTask(int index) {            // метод, що повертаЇ задачу, €ка знаходитьс€ на вказаному м≥сц≥ у списку, перша задача маЇ ≥ндекс 0.
        return tasks[index];
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {

            private int iteratorIndex = 0;

            @Override
            public boolean hasNext() {
                return iteratorIndex < size();
            }

            @Override
            public Task next() {
                return tasks[iteratorIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
