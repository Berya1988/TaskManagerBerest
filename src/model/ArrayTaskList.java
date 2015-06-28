package model;

import java.util.Iterator;

/**
 * Created by Oleg on 13.06.2015.
 */
public class ArrayTaskList extends TaskList {
    private int size;
    private Task[] array;

    public ArrayTaskList() {
        size = 0;
        array = new Task[10];
    }

    public void add(Task task) {                // �����, �� ���� �� ������ ������� ������.
        if(task == null)
            throw new IllegalArgumentException("task = null");
        if (size == array.length){
            Task[] newArray = new Task[size+10];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size] = task;
        size++;
    }

    public boolean remove(Task task) {          // �����, �� ������� ������ �� ������ � ������� ������, ���� ���� ������ ���� � ������. ���� � ������ ���� ������� ����� �����, ��������� �������� ���� ����-���.
        for (int i = 0; i < size; i++) {
            if(array[i].equals(task)){
                System.arraycopy(array, i+1, array, i, size-i-1);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {                         // �����, �� ������� ������� ����� � ������.
        return size;
    }

    public Task getTask(int index) {            // �����, �� ������� ������, ��� ����������� �� ��������� ���� � ������, ����� ������ �� ������ 0.
        return array[index];
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
                return array[iteratorIndex++];
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
