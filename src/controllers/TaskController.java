package controllers;

import models.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskController{
    private List<Task> TaskList;

    public TaskController(){
        TaskList = new ArrayList<Task>();
    }

    public List<Task> getTaskList() {
        return TaskList;
    }

    public void setTaskList(List<Task> taskList) {
        TaskList = taskList;
    }


    //###############
    //##OWN METHODS##
    //###############

    public boolean addTask(Task task){
        boolean success = false;
        if(task != null && task.getTaskName() != null && task.getTaskID() != -1){
            if(!taskIDExists(task.getTaskID())){
                this.getTaskList().add(task);
                success = true;
            }
        }
        return success;
    }

    public boolean removeTask(Task task){
        boolean success = false;
        if(task != null && task.getTaskName() != null && task.getTaskID() != -1){
            this.getTaskList().remove(task);
            success = true;
        }
        return success;
    }

    public boolean updateTask(Task task){
        boolean success = false;
        if(task != null){
            Task modifiedTask = this.getTaskList().set(searchTask(task), task);
            if(modifiedTask != null && modifiedTask.equals(task)){
                success = true;
            }
        }
        return success;
    }

    public int searchTask(Task task){
        int position = -1;
        int counter = 0;
        while(counter < this.getTaskList().size() && position == -1){
            if(this.getTaskList().get(counter).equals(task)){
                position = counter;
            }
            counter++;
        }
        return position;
    }

    public boolean removeTaskByID(int taskID){
        int counter;
        boolean success = false;
        for(counter = 0; counter < this.getTaskList().size(); counter++){
            if(this.getTaskList().get(counter).getTaskID() == taskID){
                this.getTaskList().remove(counter);
                success = true;
            }
        }
        return success;
    }

    public boolean updateTaskByID(int taskID, int updaterOption, String newTaskAtribute){
        boolean success = false;
        int counter;
        for(counter = 0; counter < this.getTaskList().size(); counter++){
            if(this.getTaskList().get(counter).getTaskID() == taskID){
                if(updaterOption == 1){
                    this.getTaskList().get(counter).setTaskName(newTaskAtribute);
                    success = true;
                }
                else{
                    this.getTaskList().get(counter).setTaskDescription(newTaskAtribute);
                    success = true;
                }
            }
        }
        return success;
    }

    public Task seeTask(int taskID){
        Task task = new Task();
        int counter;
        for(counter = 0; counter < this.getTaskList().size(); counter++){
            if(this.getTaskList().get(counter).getTaskID() == taskID){
                if(!this.getTaskList().get(counter).getTaskName().equals("")){
                    task = this.getTaskList().get(counter);
                }
            }
        }
        return task;
    }

    public boolean exportAllTasks(){
        boolean success = false;
        String exportedTask = "";
        int counter = 0;
        for(counter = 0; counter < this.getTaskList().size(); counter++){

        }
        return success;
    }

    public Task[] importAllTasks(){ //TODO
        int counter;
        Task taskArray[] = new Task[getDatabaseLength(new File("../resources/database"))];
        for(counter = 0; counter < getDatabaseLength(new File("../resources/database")); counter++){
            taskArray[counter] = importTask()
        }
    }

    public boolean importTask(String exportedTask){
        boolean success = false;
        String exportedTaskArray[] = exportedTask.split("_");
        Task task = new Task(exportedTaskArray[0], exportedTaskArray[1], exportedTaskArray[2], exportedTaskArray[3]);
        if(this.getTaskList().add(task)){
            success = true;
        }
        return success;
    }

    public boolean completeTaskByID(int taskID){
        int counter;
        boolean success = false;
        for(counter = 0; counter < this.getTaskList().size(); counter++){
            if(this.getTaskList().get(counter).getTaskID() == taskID){
                this.getTaskList().get(counter).setTaskCompleted(true);
                success = true;
            }
        }
        return success;
    }

    public void completeTask(Task task){
        task.setTaskCompleted(true);
    }

    public Task getTask(Task task){
        return task;
    }

    private boolean taskIDExists(int taskID){
        boolean taskExists = false;
        int counter;
        for(counter = 0; counter < this.getTaskList().size() && !taskExists; counter++){
            if(getTaskList().get(counter).getTaskID() == taskID){
                taskExists = true;
            }
        }
        return taskExists;
    }

    private int getDatabaseLength(File dbFile){
        int lineCounter = 0;
        try {
            FileReader dbFileReader = new FileReader(dbFile);
            BufferedReader dbFileBufferedReader = new BufferedReader(dbFileReader);
            while(dbFileBufferedReader.readLine() != null){
                lineCounter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCounter;
    }

    private String readDBLine(File dbFile){ //TODO

    }

}
