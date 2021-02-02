package view;

import controllers.TaskController;
import models.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Storio{
    public static void main(String[] args){
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        byte option, updateOption;
        int taskID = 0;
        String newTaskAtribute;
        TaskController taskCtrl = new TaskController();
        if(taskCtrl.getDatabaseLength(new File("src/resources/database")) > 0){
            taskCtrl.importAllTasks();
        }
        do {
            option = menu();
            switch (option) {
                case 1:
                    String completeTask[] = new String[3];
                    completeTask = createTask();
                    if (taskCtrl.addTask(new Task(completeTask[0], completeTask[1], Integer.parseInt(completeTask[2])))) {
                        System.out.println("Task added to database");
                    } else {
                        System.out.println("Task cannot be added to database");
                    }
                    System.out.println("----------------");
                    break;
                case 2:
                    do {
                        try {
                            System.out.print("Type ID of the task you want to remove: ");
                            taskID = Integer.parseInt(keyboard.readLine());
                            if (taskID < 0) {
                                taskID = -1;
                            }
                        } catch (Exception e) {
                            taskID = -1;
                        }
                    } while (taskID == -1);
                    if (taskCtrl.removeTaskByID(taskID)) {
                        System.out.println("Task removed from database");
                    } else {
                        System.out.println("Task cannot be removed from database");
                    }
                    System.out.println("----------------");
                    break;
                case 3:
                    do {
                        try {
                            System.out.print("Type ID of the task you want to update: ");
                            taskID = Integer.parseInt(keyboard.readLine());
                            if (taskID < 0) {
                                taskID = -1;
                            }
                        } catch (Exception e) {
                            taskID = -1;
                        }
                    } while (taskID == -1);
                    updateOption = updaterMenu();
                    try {
                        System.out.print("Type the new attribute for the task: ");
                        newTaskAtribute = keyboard.readLine();
                    } catch (Exception e) {
                        newTaskAtribute = "";
                    }
                    if (taskCtrl.updateTaskByID(taskID, updateOption, newTaskAtribute)) {
                        System.out.println("Task updated");
                    } else {
                        System.out.println("Task cannot be updated");
                    }
                    break;
                case 4:
                    do {
                        try {
                            System.out.print("Type ID of the task you want to see: ");
                            taskID = Integer.parseInt(keyboard.readLine());
                            if (taskID < 0) {
                                taskID = -1;
                            }
                        } catch (Exception e) {
                            taskID = -1;
                        }
                    } while (taskID == -1);
                    System.out.println(taskCtrl.seeTask(taskID));
                    break;
                case 5:
                    do {
                        try {
                            System.out.print("Type ID of the task you want to mark as complete: ");
                            taskID = Integer.parseInt(keyboard.readLine());
                            if (taskID < 0) {
                                taskID = -1;
                            }
                        } catch (Exception e) {
                            taskID = -1;
                        }
                    } while (taskID == -1);
                    if (taskCtrl.completeTaskByID(taskID)) {
                        System.out.println("Task marked as complete");
                    } else {
                        System.out.println("Task cannot be marked as completed");
                    }
                    break;
                case 6:
                    seeAllTasks(taskCtrl);
                    break;
                case 7:
                    seeAllUncompletedTasks(taskCtrl);
                    break;
                case 8:
                    seeAllCompletedTasks(taskCtrl);
                    break;
                case 9:
                    taskCtrl.exportAllTasks();
                    break;
                default:
                    option = -1;
            }
        }while (option != 9) ;

    }

    private static byte updaterMenu(){
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        byte option;
        System.out.println("----------------");
        System.out.println("1.Update name");
        System.out.println("2.Update description");
        System.out.println("----------------");
        do{
            try {
                System.out.print("Select an option: ");
                option = Byte.parseByte(keyboard.readLine());
                if(option < 1 || option > 2){
                    option = -1;
                }
            } catch(Exception e) {
                option = -1;
            }
        }while(option == -1);
        return option;
    }



    private static String[] createTask(){
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        String task[] = new String[3];
        String taskName, taskDescription, taskID;
        int taskIDint;
        System.out.println("----------------");
        try {
            System.out.print("Type task name: ");
            taskName = keyboard.readLine();
        } catch (Exception e) {
            taskName = "Task " + e.getMessage();
        }
        try {
            System.out.print("Type task description: ");
            taskDescription = keyboard.readLine();
        } catch (Exception e) {
            taskDescription = "Task " + e.getMessage();
        }
        try {
            System.out.print("Type task ID(numeric): ");
            taskIDint = Integer.parseInt(keyboard.readLine());
        } catch (Exception e) {
            taskIDint = 0;
            System.out.println("Task ID stablished to 0");
        }
        System.out.println("----------------");
        taskID = taskIDint + "";
        task[0] = taskName;
        task[1] = taskDescription;
        task[2] = taskID;
        return task;
    }





    private static byte menu(){
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        byte option;
        System.out.println("################");
        System.out.println("#####Storio#####");
        System.out.println("################");
        System.out.println("----------------");
        System.out.println("1.Add task");
        System.out.println("2.Remove task");
        System.out.println("3.Update task");
        System.out.println("4.See task");
        System.out.println("5.Mark task as completed");
        System.out.println("6.See all tasks");
        System.out.println("7.See all uncompleted tasks");
        System.out.println("8.See all completed tasks");
        System.out.println("9.Exit");
        do{
            System.out.print("Choose an option: ");
            try {
                option = Byte.parseByte(keyboard.readLine());
                if(option < 1 || option > 9){
                    option = -1;
                }
            }catch(Exception e){
                option = -1;
            }
        }while(option == -1);
        return option;
    }

    private static void seeAllTasks(TaskController taskCtrl){
        int counter;
        List<Task> taskList = new ArrayList<Task>();
        taskList = taskCtrl.getTaskList();
        for(counter = 0; counter < taskList.size(); counter++){
            System.out.println(taskList.get(counter));
        }
    }

    private static void seeAllUncompletedTasks(TaskController taskCtrl){
        int counter;
        List<Task> uncompletedTaskList = new ArrayList<Task>();
        uncompletedTaskList = taskCtrl.getTaskList();
        for(counter = 0; counter < uncompletedTaskList.size(); counter++){
            if(!uncompletedTaskList.get(counter).isTaskCompleted()){
                System.out.println(uncompletedTaskList.get(counter));
            }
        }
    }

    private static void seeAllCompletedTasks(TaskController taskCtrl){
        int counter;
        List<Task> completedTaskList = new ArrayList<Task>();
        completedTaskList = taskCtrl.getTaskList();
        for(counter = 0; counter < completedTaskList.size(); counter++){
            if(completedTaskList.get(counter).isTaskCompleted()){
                System.out.println(completedTaskList.get(counter));
            }
        }
    }
}
