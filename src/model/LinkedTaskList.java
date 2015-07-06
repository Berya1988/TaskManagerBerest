package model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Oleg on 16.06.2015.
 */
public class LinkedTaskList extends TaskList implements Cloneable {
    private int size;
    private Node head = null;

    private class Node {
        private Task data;
        private Node next;

        public Node(){
        }

        public Node(Task data, Node next){
            this.data = data;
            this.next = next;
        }
    }

    public void add(Task task) {                // метод, що додаЇ до списку вказану задачу.
        if(task == null)
            throw new IllegalArgumentException("task = null");

        if(head == null)
            head = new Node(task, null);
        else{
            Node temp = head;
            while(temp.next != null)
                temp = temp.next;
            temp.next = new Node(task, null);
        }
        size++;
    }

    public boolean remove(Task task) {          // метод, що видал€Ї задачу ≥з списку ≥ повертаЇ ≥стину, €кщо така задача була у списку. якщо у списку було дек≥лька таких задач, необх≥дно видалити одну будь-€ку.
        if(task == null)
            throw new IllegalArgumentException("task = null");

        if(head == null)
            throw new RuntimeException("cannot delete");

        if(task.equals(head.data)) {
            head = head.next;
            size--;
            return true;
        }

        Node cur  = head;
        Node prev = null;

        while(cur != null && !cur.data.equals(task) ) {
            prev = cur;
            cur = cur.next;
        }
        if(cur != null) {
            prev.next = cur.next;
            size--;
            return true;
        }
        else
            return false;
    }

    public int size() {                         // метод, що повертаЇ к≥льк≥сть задач у списку.
        return size;
    }

    public Task getTask(int index) {            // метод, що повертаЇ задачу, €ка знаходитьс€ на вказаному м≥сц≥ у списку, перша задача маЇ ≥ндекс 0.
        int i = 0;

        if(index < 0 && index >= size())
            throw new IndexOutOfBoundsException("cannot delete");
        else {
            if(index == 0)
                return head.data;
            else {
                Node temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                    i++;
                    if (i == index)
                        return temp.data;
                }
                return temp.data;
            }
        }
    }

    public Iterator iterator(){
        return new Iterator(){
            private int iteratorIndex = 0;
            private Node nextElement = head;
            private Node lastReturned = null;
            public boolean hasNext(){
                return iteratorIndex < size;
            }
            public Object next(){
                if (!hasNext())
                    throw new NoSuchElementException();
                lastReturned = nextElement;
                nextElement = nextElement.next;
                iteratorIndex++;
                return lastReturned.data;
            }
            public void remove(){
                if(lastReturned.next != null) {
                    nextElement = nextElement.next;
                    lastReturned.next = nextElement;
                }
                else{
                    nextElement = null;
                }
                size--;
            }
        };
    }

    @Override
    public int hashCode() {
        return head.data.getTitle().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LinkedTaskList) {
            LinkedTaskList another = (LinkedTaskList) obj;
            return head.data.getTitle().equals(another.head.data.getTitle());
        }
        return false;
    }

    @Override
    public Object clone() {
        try{
            LinkedTaskList copy = (LinkedTaskList)super.clone();
            copy.size = this.size;
            copy.head = this.head;
            return copy;
        }
        catch(CloneNotSupportedException e){
            throw new AssertionError("Impossible");
        }
    }
}