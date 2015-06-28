package model;

import java.util.Iterator;

/**
 * Created by Oleg on 16.06.2015.
 */
public class LinkedTaskList extends TaskList {
    private int size;
    Node head;

    public LinkedTaskList() {
        head = null;
        size = 0;
    }

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
        if(head == null)
            throw new RuntimeException("cannot delete");

        if(head.data.equals(task)) {
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
            public boolean hasNext(){
                return iteratorIndex < size();
            }
            public Object next(){
                return getTask(iteratorIndex++);
            }
            public void remove(){
                throw new UnsupportedOperationException();
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
}
