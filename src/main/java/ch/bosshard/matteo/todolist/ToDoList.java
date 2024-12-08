package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.ListCategory;
import ch.bosshard.matteo.todolist.enums.SortingOptions;
import ch.bosshard.matteo.todolist.enums.TaskStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoList {
    private String listTitle;
    private List<Task> allTasks;
    private List<Task> completedTasks;
    private ListCategory listCategory;
    private double completionPercentage;
    private String listColor;
    private SortingOptions sortingOptions = SortingOptions.UNCATEGORIZED;
    private List<Task> displayTaskList;


    Map<String, String> colorSwitcher = new HashMap<String, String>();

    public ToDoList(String listTitle, ListCategory listCategory, String listColor) {
        this.listTitle = listTitle;
        this.listCategory = listCategory;
        this.allTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
        this.completionPercentage = 0;
        this.displayTaskList = this.allTasks;

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
        this.sortingOptions = SortingOptions.UNCATEGORIZED;
    }

    public ToDoList(String listTitle, List<Task> allTasks, List<Task> completedTasks, ListCategory listCategory, double completionPercentage, String listColor, SortingOptions sortingOptions, List<Task> displayTaskList) {
        this.listTitle = listTitle;
        this.allTasks = allTasks;
        this.completedTasks = completedTasks;
        this.listCategory = listCategory;
        this.completionPercentage = completionPercentage;
        this.listColor = listColor;
        this.sortingOptions = sortingOptions;
        this.displayTaskList = displayTaskList;
        if (this.sortingOptions == null) this.sortingOptions = SortingOptions.UNCATEGORIZED;
    }

    public void updateCompletionPercentage() {
        completedTasks.clear();
        for (Task task : allTasks) {
            if (task.getTaskStatus() == TaskStatus.COMPLETED) completedTasks.add(task);
        }
        DecimalFormat df = new DecimalFormat("#.#");
        completionPercentage = Double.parseDouble(df.format((double) completedTasks.size() / allTasks.size() * 100));

        if (Double.isNaN(completionPercentage)) completionPercentage = 0;
    }

    public void sortList() {
        SortTasks sortTasks = new SortTasks(this.sortingOptions);
        if (this.sortingOptions == null) this.sortingOptions = SortingOptions.UNCATEGORIZED;
        displayTaskList = sortTasks.sortTasks(allTasks);
        System.out.println("Sorted " + allTasks + " to " + displayTaskList + " with " + sortingOptions.toFormattedString());
    }

    public void addTask(Task task) {
        allTasks.add(task);
        sortList();
    }

    public void deleteTask(Task task) {
        allTasks.remove(task);
        sortList();
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

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public List<Task> getDisplayTaskList() {
        return displayTaskList;
    }

    public void setDisplayTaskList(List<Task> displayTaskList) {
        this.displayTaskList = displayTaskList;
    }

    public SortingOptions getSortingOptions() {
        return sortingOptions;
    }

    public void setSortingOptions(SortingOptions sortingOptions) {
        this.sortingOptions = sortingOptions;
    }

    public String getListColor() {
        return listColor;
    }

    public void setListColor(String listColor) {
        this.listColor = listColor;
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
                ", sortingOptions=" + sortingOptions +
                ", displayTaskList=" + displayTaskList +
                ", colorSwitcher=" + colorSwitcher +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("listTitle", listTitle);

        if (sortingOptions == null) sortingOptions = SortingOptions.UNCATEGORIZED;

        JSONArray allTasksArray = new JSONArray();
        for (Task task : allTasks) {
            allTasksArray.put(task.toJSON());
        }
        jsonObject.put("allTasks", allTasksArray);

        JSONArray completedTasksArray = new JSONArray();
        for (Task task : completedTasks) {
            completedTasksArray.put(task.toJSON());
        }
        jsonObject.put("completedTasks", completedTasksArray);

        JSONArray displayTaskListArray = new JSONArray();
        for (Task task : displayTaskList) {
            displayTaskListArray.put(task.toJSON());
        }
        jsonObject.put("displayTaskList", displayTaskListArray);

        jsonObject.put("completionPercentage", completionPercentage);
        jsonObject.put("listCategory", listCategory.toString());
        jsonObject.put("sortingOptions", sortingOptions.toString());
        jsonObject.put("listColor", listColor);

        return jsonObject;
    }

    public static ToDoList fromJSON(JSONObject jsonObject) {
        String listTitle = jsonObject.getString("listTitle");

        List<Task> allTasks = new ArrayList<>();
        JSONArray allTasksArray = jsonObject.getJSONArray("allTasks");
        for (int i = 0; i < allTasksArray.length(); i++) {
            allTasks.add(Task.fromJSON(allTasksArray.getJSONObject(i)));
        }

        List<Task> completedTasks = new ArrayList<>();
        JSONArray completedTasksArray = jsonObject.getJSONArray("completedTasks");
        for (int i = 0; i < completedTasksArray.length(); i++) {
            completedTasks.add(Task.fromJSON(completedTasksArray.getJSONObject(i)));
        }

        List<Task> displayTaskList = new ArrayList<>();
        JSONArray displayTaskListArray = jsonObject.getJSONArray("displayTaskList");
        for (int i = 0; i < displayTaskListArray.length(); i++) {
            displayTaskList.add(Task.fromJSON(displayTaskListArray.getJSONObject(i)));
        }

        double completionPercentage = jsonObject.getDouble("completionPercentage");
        ListCategory listCategory = ListCategory.valueOf(jsonObject.getString("listCategory"));
        SortingOptions sortingOptions = SortingOptions.UNCATEGORIZED;
        if (jsonObject.has("sortingOptions")) {
            sortingOptions = SortingOptions.valueOf(jsonObject.getString("sortingOptions"));
        } else {
            sortingOptions = SortingOptions.UNCATEGORIZED;
        }
        String listColor = jsonObject.getString("listColor");

        return new ToDoList(listTitle, allTasks, completedTasks, listCategory, completionPercentage, listColor, sortingOptions, displayTaskList);
    }
}
