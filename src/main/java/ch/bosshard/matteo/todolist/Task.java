package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.TaskCategory;
import ch.bosshard.matteo.todolist.enums.TaskImportance;
import ch.bosshard.matteo.todolist.enums.TaskStatus;

public class Task {
    private String taskName;
    private TaskStatus taskStatus;
    private TaskCategory taskCategory;
    private TaskImportance taskImportance;

    public Task(String taskName, TaskStatus taskStatus, TaskCategory taskCategory, TaskImportance taskImportance) {
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.taskCategory = taskCategory;
        this.taskImportance = taskImportance;
    }

    // BASE METHODS
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public TaskImportance getTaskImportance() {
        return taskImportance;
    }

    public void setTaskImportance(TaskImportance taskImportance) {
        this.taskImportance = taskImportance;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskCategory=" + taskCategory +
                ", taskImportance=" + taskImportance +
                '}';
    }
}
