package control;

import model.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * Created by Oleg on 24.06.2015.
 */
public class TaskIO {
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(out);
        dataOut.writeInt(tasks.size());
        for (Task task : tasks) {
            dataOut.writeUTF(task.getTitle());
            dataOut.writeBoolean(task.isActive());
            dataOut.writeInt((int) task.getRepeatInterval());
            dataOut.writeLong(task.getStartTime().getTime());
            if (task.isRepeated()) {
                dataOut.writeLong(task.getEndTime().getTime());
            }
        }
        dataOut.flush();
    }

    public static void read(TaskList tasks, InputStream in) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);
        int size = dataIn.readInt();
        for (int i = 0; i < size; i++) {
            String title = dataIn.readUTF();
            boolean active = dataIn.readBoolean();
            int interval = dataIn.readInt();
            long start = dataIn.readLong();
            if (interval > 0) {
                long end = dataIn.readLong();
                Task task = new Task(title, new Date(start), new Date(end), interval);
                task.setActive(active);
                tasks.add(task);
            } else {
                Task task = new Task(title, new Date(start));
                task.setActive(active);
                tasks.add(task);
            }
        }
    }

    public static void writeBinary(TaskList tasks, File file) throws  IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        try {
            write(tasks, bos);
        } finally {
            bos.close();
        }
    }

    public static void readBinary(TaskList tasks, File file) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        try {
            read(tasks, bis);
        } finally {
            bis.close();
        }
    }

    public static void write(TaskList tasks, Writer out)throws IOException {
        PrintWriter dataOut = new PrintWriter(new BufferedWriter(out));
        int size = 0;
        int listSize = tasks.size();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'in' HH:mm");
        for (Task task : tasks) {
            dataOut.append("\"" + task.getTitle()+"\"");
            if(task.isRepeated()){
                dataOut.append("from");
                dataOut.append("["+dateFormat.format( task.getStartTime() )+"]");
                dataOut.append( "to");
                dataOut.append("["+dateFormat.format( task.getEndTime() )+"]");
                dataOut.append("every");
                int interval = task.getRepeatInterval();
                if(interval < 24){
                    dataOut.append("[" + interval   );
                    dataOut.append((interval==1) ? " hour]" : " hours]" );
                }
                else{
                    int days = interval / 24;
                    int hours = interval % 24;
                    if(hours !=0){
                        dataOut.append("[" + days   );
                        dataOut.append((days==1) ? " day " : " days " );
                        dataOut.append(""+ hours);
                        dataOut.append((days==1) ? " hour]" : " hours]" );
                    }
                    else{
                        dataOut.append("[" + days   );
                        dataOut.append((days==1) ? " day]" : " days]" );
                    }
                }
            }
            else{
                dataOut.append("at");
                dataOut.append("["+dateFormat.format( task.getTime() )+"]");
            }
            size++;
            dataOut.append(task.isActive() ? "active" : "inActive");
            dataOut.append(size==listSize ? "." : ";\n");
            dataOut.flush();
        }
        dataOut.close();
    }

    public static void read(TaskList tasks, Reader in) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(in);
        String task = " ";
        Task myTask;
        while( (task=br.readLine()) !=null){
            String[] words = task.split("[\\p{Punct}\\s]");
            String title = words[1];
            String activities = words[words.length-1];
            Boolean active = (activities.equals("active")?true:false);
            int year = Integer.parseInt(words[3]);
            int month = Integer.parseInt(words[4])-1;
            int day = Integer.parseInt(words[5]);
            int hours = Integer.parseInt(words[7]);
            int minutes = Integer.parseInt(words[8]);
            GregorianCalendar gregDate1 = new GregorianCalendar(year, month, day, hours, minutes);
            Date date1 = (Date)gregDate1.getTime();
            if(words.length != 10){
                int year1 = Integer.parseInt(words[10]);
                int month1 = Integer.parseInt(words[11])-1;
                int day1 = Integer.parseInt(words[12]);
                int hours1 = Integer.parseInt(words[14]);
                int minutes1= Integer.parseInt(words[15]);
                GregorianCalendar gregDate2 = new GregorianCalendar(year1,month1,day1,hours1,minutes1);
                Date date2 = (Date)gregDate2.getTime();
                int interval;
                if(words.length == 20){
                    interval = Integer.parseInt(words[17])*24;
                    myTask = new Task(title,date1,date2,interval);
                    System.out.println(title + date1 + date2 + interval);
                    myTask.setActive(active);
                    tasks.add(myTask);

                }
                else if(words.length == 22){
                    int interval1 = Integer.parseInt(words[8])*24;
                    int interval2 = Integer.parseInt(words[8]);
                    interval = interval1 + interval2;
                    myTask = new Task(title,date1,date2,interval);
                    System.out.println(title + date1 + date2 + interval);
                    myTask.setActive(active);
                    tasks.add(myTask);
                }
            }
            else{
                myTask = new Task(title,date1);
                myTask.setActive(active);
                System.out.println(title + date1);
                tasks.add(myTask);
            }
        }
        br.close();
    }

    public static void writeText(TaskList tasks, File file) throws IOException {
        PrintWriter dataOut = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        try {
            write(tasks, dataOut);
        } finally {
            dataOut.close();
        }
    }
    public static void readText(TaskList tasks, File file) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            read(tasks, br);
        } finally {
            br.close();
        }
    }
}