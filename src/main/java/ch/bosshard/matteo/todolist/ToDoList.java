package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.ListCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoList {
    private String listTitle;
    private List<Task> allTasks;
    private List<Task> completedTasks;
    private ListCategory listCategory;
    private int completionPercentage;
    private String listColor;

    Map<String, String> colorSwitcher = new HashMap<String, String>();

    public ToDoList(String listTitle, ListCategory listCategory, String listColor) {
        this.listTitle = listTitle;
        this.listCategory = listCategory;
        this.allTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
        this.completionPercentage = 0;

        colorSwitcher.put("Red", "#E63946");
        colorSwitcher.put("Blue", "#457B9D");
        colorSwitcher.put("Green", "#2A9D8F");
        colorSwitcher.put("Yellow", "#E9C46A");
        colorSwitcher.put("Purple", "#9D4EDD");
        colorSwitcher.put("Orange", "#F4A261");
        colorSwitcher.put("Pink", "#F28482");
        colorSwitcher.put("Brown", "#BD7013");
        colorSwitcher.put("Gray", "#D4D4D4");

        this.listColor = colorSwitcher.get(listColor);
    }

    public void updateCompletionPercentage() {
        completionPercentage = allTasks.hashCode() / completedTasks.size() * 100;
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
