package view;
import model.*;
import control.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.*;

/**
 * Created by Oleg on 13.06.2015.
 */
public class MainClass {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger log = Logger.getLogger(view.MainClass.class);

    public static void main(String[] args) throws IOException, ParseException {
        log.info("Let's begin...");
        TaskList test = new LinkedTaskList();
        Thread newThread = new Thread(new CheckTasks(test));
        handleVariant(menu(), test);
        newThread.start();
        reader.close();
        log.info("To be continued...1");
    }
    public static int menu()throws IOException{
        System.out.println("Choose activity (enter necessary number):");
        System.out.println("1. Check your tasks from your task list.");
        System.out.println("2. Add new task to your task list.");
        System.out.println("3. Remove task from your task list.");
        System.out.println("4. Edit task from your task list.");
        System.out.println("5. Choose necessary time interval.");
        System.out.println("6. Download tasks from the file.");
        System.out.println("7. Save task list.");
        System.out.println("8. Exit.");
        int variant = Integer.parseInt(reader.readLine());
        return variant;
    }
    public static void handleVariant(int currentVariant, TaskList list) throws IOException, ParseException {
        switch(currentVariant){
            case 1:
                checkTasks(list);
                handleVariant(menu(), list);
                break;
            case 2:
                addNew(list);
                handleVariant(menu(), list);
                break;
            case 3:
                removeTask(list);
                handleVariant(menu(), list);
                break;
            case 4:
                editTask(list);
                handleVariant(menu(), list);
                break;
            case 5:
                setTimeInterval(list);
                handleVariant(menu(), list);
                break;
            case 6:
                downloadList(list);
                handleVariant(menu(), list);
                break;
            case 7:
                saveList(list);
                handleVariant(menu(), list);
                break;
            case 8:
                reader.close();
                log.info("To be continued...");
                System.exit(0);
            default:
                System.out.println("Incorrect number. Try again...");
                handleVariant(menu(), list);
        }
    }
    public static void checkTasks(TaskList list){
        System.out.println("Please, wait. List is downloading...");
        list.show();
    }
    public static void addNew(TaskList list) throws IOException {
        System.out.println("Enter parameters for new task...");
        Task task = null;
        System.out.println("Enter title for new task");
        String title = reader.readLine();
        System.out.println("Is it repeated task?(true/false)");
        boolean repeated = Boolean.parseBoolean(reader.readLine());;
        if (repeated) {
            System.out.println("Enter start time for repeated task (yyyy-MM-dd)");
            String startS = reader.readLine();
            System.out.println("Enter end time for repeated task (yyyy-MM-dd)");
            String endS = reader.readLine();
            System.out.println("Enter interval (hours)");
            int interval = Integer.parseInt(reader.readLine());
            try {
                Date start = formatter.parse(startS);
                Date end = formatter.parse(endS);
                task = new Task(title, start, end, interval);
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Enter start time of unrepeated task (yyyy-MM-dd)");
            String timeS = reader.readLine();
            try {
                Date time = formatter.parse(timeS);
                task = new Task(title, time);
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        }
        System.out.println("Activate?(true/false)");
        boolean active = Boolean.parseBoolean(reader.readLine());;
        task.setActive(active);
        list.add(task);
        /*Task t1 = new Task("Morning running", 100, 200, 20);
        t1.setActive(true);
        Task t2 = new Task("Work", 10, 500, 20);
        t2.setActive(true);
        Task t3 = new Task("Forest camping", 290);
        t3.setActive(true);
        Task t4 = new Task("Sunbathing", 250, 400, 100);
        t4.setActive(true);
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);*/
    }
    public static void removeTask(TaskList list) throws IOException {
        System.out.println("Choose task from the list...");
        System.out.println("Enter number of a task to delete");
        int number = Integer.parseInt(reader.readLine());
        list.remove(list.getTask(number));
    }
    public static void editTask(TaskList list) throws IOException, ParseException {
        System.out.println("Please, choose necessary parameters...");
        System.out.println("Enter number of a task to edit");
        int number = Integer.parseInt(reader.readLine());
        System.out.println("What parameter of task would you like to edit: \n 1 - Title; \n 2 - Time parameters; \n 3 - Mode?");
        int number2 = Integer.parseInt(reader.readLine());
        switch(number2){
            case 1:
                System.out.println("Enter new title:");
                String title = reader.readLine();
                list.getTask(number).setTitle(title);
                break;
            case 2:
                if (list.getTask(number).isRepeated()) {
                    System.out.println("Enter new start time (yyyy-MM-dd):");
                    String startS = reader.readLine();
                    System.out.println("Enter new end time (yyyy-MM-dd):");
                    String endS = reader.readLine();
                    System.out.println("Enter interval (hours)");
                    int interval = Integer.parseInt(reader.readLine());
                    try {
                        Date start = formatter.parse(startS);
                        Date end = formatter.parse(endS);
                        list.getTask(number).setTime(start, end, interval);
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Enter new start time:");
                    String timeS = reader.readLine();
                    int interval = Integer.parseInt(reader.readLine());try {
                        Date time = formatter.parse(timeS);
                        list.getTask(number).setTime(time);
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }

                }
                break;
            case 3:
                System.out.println("The mode has been changed to opposite.");
                list.getTask(number).setActive(!list.getTask(number).isActive());
                break;
            default:
                System.out.println("Incorrect number. Try again...");
                handleVariant(menu(), list);
        }
    }
    public static void setTimeInterval(TaskList list) throws IOException {
        System.out.println("Select start date and end date for your time interval...");
        System.out.println("Enter new start date (yyyy-MM-dd):");
        String startS = reader.readLine();
        System.out.println("Enter new end time (yyyy-MM-dd):");
        String endS = reader.readLine();
        try {
            Date start = formatter.parse(startS);
            Date end = formatter.parse(endS);
            for (Task t:Tasks.incoming(list, start, end)){
                System.out.println(t.getTitle());
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }

    public static void downloadList(TaskList list) throws IOException, ParseException {
        TaskIO.readText(list, new File("e:\\temp\\workspace\\TaskManagerBerest\\data\\temp.txt"));
    }

    public static void saveList(TaskList list) throws IOException {
        TaskIO.writeText(list, new File("e:\\temp\\workspace\\TaskManagerBerest\\data\\temp.txt"));
    }

}
