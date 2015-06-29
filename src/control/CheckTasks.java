package control;

import model.*;
import org.apache.log4j.*;

/**
 * Created by Oleg on 26.06.2015.
 */
public class CheckTasks implements Runnable {
    public LinkedTaskList list;
    private static final Logger log = Logger.getLogger(view.MainClass.class);

    public CheckTasks(TaskList list) {
     this.list = (LinkedTaskList)list;
    }

    @Override
    public void run() {
        for (int i = 0; i >= 0 ; i++) {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                log.error("Threw a InterruptedException in MainClass::MyMethod:", e);
            }
            System.out.println("I am new thread");
            if(list != null)
                Tasks.checkList(list);
        }
    }
}
