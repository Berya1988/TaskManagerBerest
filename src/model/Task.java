package model;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.*;

/**
 * Created by Oleg on 12.06.2015.
 */
public class Task implements Serializable {

    private static final Logger log = Logger.getLogger(Task.class);

    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;
    private boolean repeated;

    public Task(String title, Date time){                         // конструює неактивну задачу, яка виконується у заданий час без повторення із заданою назвою
        this.title = title;
        this.time = time;
        repeated = false;
        log.info("New unrepeated task was created!");
        log.info(this.toString());
    }

    public Task(String title, Date start, Date end, int interval){   // конструює неактивну задачу, яка виконується у заданому проміжку часу (і початок і кінець включно) і з заданим інтервалом і має задану назву
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeated = true;
        log.info("New repeated task was created!");
        log.info(this.toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if(active)
            log.info("Active mode was chosen as true");
        else
            log.info("Active mode was chosen as false");
    }

    public Date getTime(){                                        // якщо задача повторюється метод має повертати час початку повторення
        if (repeated)
            return start;
        else
            return time;
    }

    public void setTime(Date time) {                             // якщо задача повторювалась, вона має стати такою, що не повторюється
        if(time == null)
            throw new IllegalArgumentException("time = null");
        if(repeated) {
            repeated = !repeated;
            this.interval = 0;
            this.start = null;
            this.end = null;
        }
        this.time = time;
    }

    public Date getStartTime(){                                  // якщо задача не повторюється метод має повертати час виконання задачі
        if(repeated)
            return start;
        else
            return time;
    }

    public Date getEndTime(){                                   // якщо задача не повторюється метод має повертати час виконання задачі
        if(repeated)
            return end;
        else
            return time;
    }

    public int getRepeatInterval() {                          // якщо задача не повторюється метод має повертати 0;
        if(repeated)
            return interval;
        else
            return 0;
    }

    public void setTime(Date start, Date end, int interval) {   // якщо задача не повторювалася, має стати такою, що повторюється.
        if(start == null || end == null || interval <= 0)
            throw new IllegalArgumentException("Bad parameters");
        if(!repeated) {
            time = null;
            repeated = !repeated;
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public Date nextTimeAfter(Date current){  // повертає час наступного виконання задачі після вказаного часу current, якщо після вказаного часу задача не виконується, то метод має повертати -1.
        if (active){
            if (repeated){
                log.info("Task is active and repeated!");
                if (end.before(current)){
                    return null;
                }
                else {
                    int i = 0;
                    while(start.getTime() + i*interval  <= current.getTime() && start.getTime() + i*interval <= end.getTime()) {
                        i++;
                    }
                    return new Date(start.getTime() + i*interval);
                }
            }
            else {
                log.info("Task is active and unrepeated!");
                if (time.after( current)){
                    return time;
                }
                else
                    return null;
            }
        }
        else {
            log.info("Task is not active!");
            return null;
        }
    }
    @Override
    public Task clone() {
        try {
            Task newTask = (Task) super.clone();
            return newTask;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
    @Override
    public String toString(){
        String str = "Task title: " + this.getTitle() + "\n";
            if(this.isRepeated()) {
                str += "Parameters for repeated task: start time:" + this.getStartTime() + ", end time:" + this.getEndTime() + ", interval:" + this.getRepeatInterval() + "\n";
            }
            else {
                str += "Parameters for unrepeated task: start time:" + this.getStartTime() + "\n";
            }
            str += "Mode:" + this.isActive() + "\n";
        return str;
    }
}



















