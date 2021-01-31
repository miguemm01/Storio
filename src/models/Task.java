package models;

import java.time.*;

public class Task{
    private String taskName;
    private String taskDescription;
    private LocalDateTime taskDate;
    private boolean taskCompleted;
    private int taskID;

    public Task(){
        this.setTaskName("");
        this.setTaskDescription("");
        this.setTaskDate(LocalDateTime.now());
        this.setTaskCompleted(false);
        this.setTaskID(0);
    }

    public Task(String taskName, String taskDescription, int taskID){
        this.setTaskDate(LocalDateTime.now());
        this.setTaskName(taskName);
        this.setTaskDescription(taskDescription);
        this.setTaskID(taskID);
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        if(taskID >= 0){
            this.taskID = taskID;
        }
        else{
            this.taskID = -1;
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDateTime getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDateTime taskDate) {
        this.taskDate = taskDate;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public String toString(){
        String taskAll = "----------------\nTask Name: " + this.getTaskName() + "\nTask Description: " + this.getTaskDescription() + "\nTask Date: " + this.getTaskDate() + "\nIs Completed: " + this.isTaskCompleted() + "\nTask ID: " + this.getTaskID() + "\n----------------";
        return taskAll;
    }
}
