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

    public boolean exportTask(Task taskToBeExported){
        boolean success = false;
        String exportedTask = "";
        exportedTask += taskToBeExported.getTaskName() + "_" + taskToBeExported.getTaskDescription() + "_" + taskToBeExported.getTaskDate() + "_" + taskToBeExported.getTaskID() + "\n";
        try {
            FileWriter dbWriter = new FileWriter("src/resources/database", true);
            dbWriter.write(exportedTask);
            dbWriter.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean exportAllTasks(){
        boolean success = false;
        int counter = 0;
        for(counter = 0; counter < this.getTaskList().size(); counter++){
            if(!checkForDuplicates(new File("src/resources/database"))){
                exportTask(this.getTaskList().get(counter));
                success = true;
            }
        }
        return success;
    }

    public boolean importAllTasks(){
        int counter;
        boolean success = false;
        String exportedTask;
        Task taskArray[] = new Task[getDatabaseLength(new File("src/resources/database"))];
        for(counter = 0; counter < getDatabaseLength(new File("src/resources/database")); counter++){
            exportedTask = readDBLine(new File("src/resources/database"));
            taskArray[counter] = importTask(exportedTask);
        }
        for(counter = 0; counter < taskArray.length; counter++){
            if(this.getTaskList().add(taskArray[counter])){
                success = true;
            }
            else{
                success = false;
            }
        }
        return success;
    }

    public Task importTask(String exportedTask){
        boolean success = false;
        String exportedTaskArray[] = exportedTask.split("_");
        Task task = new Task(exportedTaskArray[0], exportedTaskArray[1], exportedTaskArray[2], exportedTaskArray[3]);
        if(this.getTaskList().add(task)){
            success = true;
        }
        return task;
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

    public int getDatabaseLength(File dbFile){
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

    private String readDBLine(File dbFile){
        String dbLine = "";
        try {
            FileReader dbFileReader = new FileReader(dbFile);
            BufferedReader dbFileBufferedReader = new BufferedReader(dbFileReader);
            dbLine = dbFileBufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbLine;
    }

    private boolean checkForDuplicates(File dbFile){ //TODO
        boolean duplicated = false;
        String dbLine = "";
        FileReader dbFileReader = null;
        int counter;
        try {
            dbFileReader = new FileReader(dbFile);
            BufferedReader dbFileBufferedReader = new BufferedReader(dbFileReader);
            dbLine = dbFileBufferedReader.readLine();
            for(counter = 0; counter < this.getTaskList().size(); counter++){
                if(this.getTaskList().get(counter).exportTask().equals(dbLine)){
                    dbLine = dbFileBufferedReader.readLine();
                    duplicated = true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return duplicated;
    }



    private void deleteDuplicates(File dbFile){ //TODO
        String dbLine = "";
        FileReader dbFileReader;
        int counter, counter2;
        try {
            dbFileReader = new FileReader(dbFile);
            BufferedReader dbFileBufferedReader = new BufferedReader(dbFileReader);
            dbLine = dbFileBufferedReader.readLine();
            for(counter = 0; counter < this.getTaskList().size(); counter++){
                for(counter2 = 1; counter < this.getTaskList().size(); counter++){
                    if(this.getTaskList().get(counter) == this.getTaskList().get(counter2)){

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
