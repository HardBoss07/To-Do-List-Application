package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.TaskCategory;
import ch.bosshard.matteo.todolist.enums.TaskImportance;
import ch.bosshard.matteo.todolist.enums.TaskStatus;

import java.util.HashMap;
import java.util.Map;

public class Task {
    private String taskName;
    private TaskStatus taskStatus;
    private TaskCategory taskCategory;
    private TaskImportance taskImportance;
    private String backgroundColor;

    Map<TaskCategory, String> backgroundColorMap = new HashMap<>(TaskCategory.values().length);


    public Task(String taskName, TaskStatus taskStatus, TaskCategory taskCategory, TaskImportance taskImportance) {
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.taskCategory = taskCategory;
        this.taskImportance = taskImportance;

        backgroundColorMap.put(TaskCategory.WORK, "#1E90FF");
        backgroundColorMap.put(TaskCategory.HOUSEHOLD, "#32CD32");
        backgroundColorMap.put(TaskCategory.GARDEN, "#6B8E23");
        backgroundColorMap.put(TaskCategory.HOBBY, "#FFA500");
        backgroundColorMap.put(TaskCategory.HEALTH, "#FF4500");
        backgroundColorMap.put(TaskCategory.MISCELLANEOUS, "#808080");

        backgroundColor = backgroundColorMap.get(taskCategory);
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

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
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
