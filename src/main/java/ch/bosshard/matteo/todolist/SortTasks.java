package ch.bosshard.matteo.todolist;

/**
 * SortTasks.java
 * @author Matteo Bosshard
 * @version 12.12.2024
 **/

import ch.bosshard.matteo.todolist.enums.SortingOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTasks {
    private SortingOptions selectedSortingOption;

    private List<Task> sortedTasks = new ArrayList<>();
    private List<Task> outList = new ArrayList<>();
    private List<Task> bucket1 = new ArrayList<>();
    private List<Task> bucket2 = new ArrayList<>();
    private List<Task> bucket3 = new ArrayList<>();
    private List<Task> bucket4 = new ArrayList<>();
    private List<Task> bucket5 = new ArrayList<>();
    private List<Task> bucket6 = new ArrayList<>();

    public SortTasks(SortingOptions selectedSortingOption) {
        if (selectedSortingOption == null) this.selectedSortingOption = SortingOptions.UNCATEGORIZED;
        this.selectedSortingOption = selectedSortingOption;
    }

    public List<Task> sortTasks(List<Task> list) {
        outList = list;
        if (selectedSortingOption == null) selectedSortingOption = SortingOptions.UNCATEGORIZED;
        switch (selectedSortingOption) {
            case CATEGORIZED -> outList = categorized(list);
            case UNCATEGORIZED -> outList = uncategorized(list);
            case STATUS_HIGH_TO_LOW -> outList = status(list, true);
            case STATUS_LOW_TO_HIGH -> outList = status(list, false);
            case IMPORTANCE_HIGH_TO_LOW -> outList = importance(list, false);
            case IMPORTANCE_LOW_TO_HIGH -> outList = importance(list, true);
            default -> outList = uncategorized(list);
        }

        return outList;
    }

    private List<Task> categorized(List<Task> list) {
        for (Task task : list) {
            switch (task.getTaskCategory()) {
                case WORK -> bucket1.add(task);
                case HOBBY -> bucket2.add(task);
                case GARDEN -> bucket3.add(task);
                case HEALTH -> bucket4.add(task);
                case HOUSEHOLD -> bucket5.add(task);
                case MISCELLANEOUS -> bucket6.add(task);
            }
        }

        sortedTasks.addAll(bucket1);
        sortedTasks.addAll(bucket2);
        sortedTasks.addAll(bucket3);
        sortedTasks.addAll(bucket4);
        sortedTasks.addAll(bucket5);
        sortedTasks.addAll(bucket6);

        return sortedTasks;
    }

    private List<Task> uncategorized(List<Task> list) {
        return list;
    }

    private List<Task> status(List<Task> list, boolean highToLow) {

        for (Task task : list) {
            switch (task.getTaskStatus()) {
                case NOT_STARTED -> bucket1.add(task);
                case IN_PROGRESS -> bucket2.add(task);
                case COMPLETED -> bucket3.add(task);
            }
        }

        sortedTasks.addAll(bucket1);
        sortedTasks.addAll(bucket2);
        sortedTasks.addAll(bucket3);

        if (highToLow) Collections.reverse(sortedTasks);

        return sortedTasks;
    }

    private List<Task> importance(List<Task> list, boolean highToLow) {

        for (Task task : list) {
            switch (task.getTaskImportance()) {
                case ESSENTIAL -> bucket1.add(task);
                case CRITICAL -> bucket2.add(task);
                case HIGH -> bucket3.add(task);
                case MEDIUM -> bucket4.add(task);
                case LOW -> bucket5.add(task);
                case OPTIONAL -> bucket6.add(task);
            }
        }

        sortedTasks.addAll(bucket1);
        sortedTasks.addAll(bucket2);
        sortedTasks.addAll(bucket3);
        sortedTasks.addAll(bucket4);
        sortedTasks.addAll(bucket5);
        sortedTasks.addAll(bucket6);

        if (!highToLow) Collections.reverse(sortedTasks);

        return sortedTasks;
    }
}
