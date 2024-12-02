package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.ListCategory;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private String listTitle;
    private List<Task> allTasks;
    private List<Task> completedTasks;
    private ListCategory listCategory;
    private int completionPercentage;
    private String listColor;

    public ToDoList(String listTitle, ListCategory listCategory, String listColor) {
        this.listTitle = listTitle;
        this.listCategory = listCategory;
        this.allTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
        this.completionPercentage = 0;
        this.listColor = listColor;
    }

    // BASE METHODS
    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public void setAllTasks(List<Task> allTasks) {
        this.allTasks = allTasks;
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(List<Task> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public ListCategory getListCategory() {
        return listCategory;
    }

    public void setListCategory(ListCategory listCategory) {
        this.listCategory = listCategory;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "listTitle='" + listTitle + '\'' +
                ", allTasks=" + allTasks +
                ", completedTasks=" + completedTasks +
                ", listCategory=" + listCategory +
                ", completionPercentage=" + completionPercentage +
                ", listColor='" + listColor + '\'' +
                '}';
    }

    public String getListColor() {
        return listColor;
    }

    public void setListColor(String listColor) {
        this.listColor = listColor;
    }
}
