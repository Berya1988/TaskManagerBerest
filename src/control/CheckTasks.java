package control;

import model.*;

/**
 * Created by Oleg on 26.06.2015.
 */
public class CheckTasks implements Runnable {
    public LinkedTaskList list;

    public CheckTasks(TaskList list) {
     this.list = (LinkedTaskList)list;
    }

    @Override
    public void run() {
        for (int i = 0; i >= 0 ; i++) {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am new thread");
            Tasks.checkList(list);
        }
    }
}
