package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Oleg on 16.06.2015.
 */
public abstract class TaskList implements Iterable<Task>, Serializable {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract int size();

    public void show(){
        System.out.println("Size of the list is " + size());
        for (int i = 0; i < size(); i++) {
            System.out.println("Task #" + i + " is " + getTask(i).getTitle());
            if(getTask(i).isRepeated()) {
                System.out.println("Parameters for repeatet task: start time:" + getTask(i).getStartTime() + ", end time:" + getTask(i).getEndTime() + ", interval:" + getTask(i).getRepeatInterval());
            }
            else {
                System.out.println("Parameters for unrepeatet task: start time:" + getTask(i).getStartTime());
            }
            System.out.println("Mode:" + getTask(i).isActive());
            System.out.println(" - *** - ");
        }
    }

    @Override
    public String toString(){
        String str = "";
        str += "Size of the list is " + size() + "\n";
        for (int i = 0; i < size(); i++) {
            str += "Task #" + i + " is " + getTask(i).getTitle() + "\n";
            if(getTask(i).isRepeated()) {
                str += "Parameters for repeated task: start time:" + getTask(i).getStartTime() + ", end time:" + getTask(i).getEndTime() + ", interval:" + getTask(i).getRepeatInterval() + "\n";
            }
            else {
                str += "Parameters for unrepeated task: start time:" + getTask(i).getStartTime() + "\n";
            }
            str += "Mode:" + getTask(i).isActive() + "\n";
            str += " - *** - " + "\n";;
        }
        return str;
    }
    public int hashCode() {
        int result = 77;
        if (this.size() == 0) {
            return result;
        } else {
            for (int i = 0; i < size(); i++) {
                result += this.getTask(i).hashCode();
            }
            return result;
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() == this.getClass()) {
            TaskList taskList = (TaskList) obj;
            for (int i = 0; i < this.size(); i++) {
                if (!this.getTask(i).equals(taskList.getTask(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
