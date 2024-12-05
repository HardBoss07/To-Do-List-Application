package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.ListCategory;
import ch.bosshard.matteo.todolist.enums.TaskCategory;
import ch.bosshard.matteo.todolist.enums.TaskImportance;
import ch.bosshard.matteo.todolist.enums.TaskStatus;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    List<ToDoList> allLists = new ArrayList<>();
    VBox listGroup = new VBox(10);  // Use VBox to arrange the buttons vertically
    Scene mainScene;

    // Start the main stage
    @Override
    public void start(Stage mainStage) {
        // Title label
        Label label = new Label("To Do List App");

        // Create List Button
        Button createList = new Button("New List");
        createList.setOnAction(e -> showCreateListPopup(mainStage));

        // Layout for main stage
        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(label, createList, listGroup);

        HBox mainHBox = new HBox(20);
        mainHBox.setPadding(new Insets(0, 0, 0, 10));
        mainHBox.getChildren().add(mainVBox);

        // Load example content
        createExampleListWithTasks();

        // Initial Scene setup
        updateListGroup(mainStage);
        Scene scene = new Scene(mainHBox, 350, 600);
        mainStage.setTitle("To Do List App");
        mainStage.setScene(scene);
        mainStage.show();

        mainScene = scene;
    }

    // Create list buttons
    private Button createListObject(ToDoList list, Stage stage) {
        Button button = new Button();

        button.setPrefWidth(330);
        button.setPrefHeight(52);

        Label titleLabel = new Label(list.getListTitle());
        Label categoryLabel = new Label(list.getListCategory().toFormattedString());
        Label tasksLabel = new Label(list.getAllTasks().size() + " Tasks");
        Label completionLabel = new Label(list.getCompletedTasks().size() + "/" + list.getAllTasks().size() + " (" + list.getCompletionPercentage() + "%)");

        HBox topLayer = new HBox(20, titleLabel, categoryLabel);
        HBox bottomLayer = new HBox(20, tasksLabel, completionLabel);
        VBox content = new VBox(10, topLayer, bottomLayer);

        button.setGraphic(content);

        button.setOnAction(e -> showListDetail(stage, list));

        button.setUserData(list);
        return button;
    }

    private void showListDetail(Stage stage, ToDoList list) {
        VBox listDetailVBox = new VBox(10);

        Label titleLabel = new Label(list.getListTitle());
        Button backButton = new Button("< Back");
        backButton.setOnAction(e -> {
            updateListGroup(stage);
            stage.setScene(mainScene);
        });

        Button addTaskButton = new Button("+ Add Task");
        addTaskButton.setOnAction(e -> showCreateTaskPopup(stage, list));

        Label taskStatusLabel = new Label(list.getAllTasks().isEmpty() ? "No current tasks" : "Current Tasks (" + list.getAllTasks().size() + "):");

        VBox tasksVBox = new VBox(10);
        for (Task task : list.getAllTasks()) {
            tasksVBox.getChildren().add(createTaskObject(task, stage, list));
        }

        listDetailVBox.getChildren().addAll(titleLabel, backButton, addTaskButton, taskStatusLabel, tasksVBox);

        HBox layout = new HBox(10);
        layout.setPadding(new Insets(0, 0, 0, 10));
        layout.getChildren().add(listDetailVBox);

        Scene listDetailScene = new Scene(layout, 350, 600);
        stage.setScene(listDetailScene);
    }

    // Create Task button
    private BorderPane createTaskObject(Task task, Stage stage, ToDoList list) {
        Button button = new Button();
        BorderPane borderPane = new BorderPane();
        CheckBox checkBox = new CheckBox();

        borderPane.setPrefWidth(330);
        borderPane.setPrefHeight(52);

        // Button to the right
        button.setPrefWidth(310);
        button.setPrefHeight(52);
        Label titleLabel = new Label(task.getTaskName());
        Label categoryLabel = new Label(task.getTaskCategory().toFormattedString());
        Label statusLabel = new Label(task.getTaskStatus().toFormattedString());
        Label importanceLabel = new Label(task.getTaskImportance().toFormattedString());

        HBox topLayer = new HBox(20, titleLabel, categoryLabel);
        HBox bottomLayer = new HBox(20, statusLabel, importanceLabel);
        VBox content = new VBox(10);
        content.getChildren().addAll(topLayer, bottomLayer);

        button.setGraphic(content);
        button.setOnAction(e -> taskButtonAction(task, stage, list));
        button.setUserData(task);

        // Checkbox to the left
        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                task.setTaskStatus(TaskStatus.COMPLETED);
            } else {
                task.setTaskStatus(TaskStatus.NOT_STARTED);
            }
            statusLabel.setText(task.getTaskStatus().toFormattedString());
        });
        VBox checkBoxVBox = new VBox(checkBox);
        checkBoxVBox.setAlignment(Pos.CENTER);

        borderPane.setLeft(checkBoxVBox);
        borderPane.setRight(button);
        borderPane.setUserData(checkBox);
        return borderPane;
    }

    // Task Button action
    private void taskButtonAction(Task task, Stage stage, ToDoList list) {
        System.out.println(task.toString());

        Stage editTaskStage = new Stage();
        editTaskStage.setTitle("Edit " + task.getTaskName());

        // Input fields
        Label titleLabel = new Label("Task Name: ");
        TextField titleTextField = new TextField();

        Label statusLabel = new Label("Status: ");
        ComboBox<TaskStatus> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(TaskStatus.values());
        statusComboBox.setValue(task.getTaskStatus());

        Label categoryLabel = new Label("Category: ");
        ComboBox<TaskCategory> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(TaskCategory.values());
        categoryComboBox.setValue(task.getTaskCategory());

        Label importanceLabel = new Label("Importance: ");
        ComboBox<TaskImportance> importanceComboBox = new ComboBox<>();
        importanceComboBox.getItems().addAll(TaskImportance.values());
        importanceComboBox.setValue(task.getTaskImportance());

        Button saveButton = new Button("Save changes");
        saveButton.setOnAction(e -> {
            String newTitle = titleTextField.getText();
            TaskStatus newStatus = statusComboBox.getValue();
            TaskCategory newCategory = categoryComboBox.getValue();
            TaskImportance newImportance = importanceComboBox.getValue();

            if (newTitle.isEmpty()) newTitle = task.getTaskName();

            task.setTaskName(newTitle);
            task.setTaskStatus(newStatus);
            task.setTaskCategory(newCategory);
            task.setTaskImportance(newImportance);
            editTaskStage.close();
            showListDetail(stage, list);
        });

        // Layout
        GridPane editTaskGrid = new GridPane();
        editTaskGrid.setHgap(10);
        editTaskGrid.setVgap(10);
        editTaskGrid.add(titleLabel, 0, 0);
        editTaskGrid.add(titleTextField, 1, 0);
        editTaskGrid.add(statusLabel, 0, 1);
        editTaskGrid.add(statusComboBox, 1, 1);
        editTaskGrid.add(categoryLabel, 0, 2);
        editTaskGrid.add(categoryComboBox, 1, 2);
        editTaskGrid.add(importanceLabel, 0, 3);
        editTaskGrid.add(importanceComboBox, 1, 3);
        editTaskGrid.add(saveButton, 0, 4);

        HBox layout = new HBox(10);
        layout.setPadding(new Insets(0, 0, 0, 10));
        layout.getChildren().add(editTaskGrid);

        Scene editTaskScene = new Scene(layout, 300, 250);
        editTaskStage.setScene(editTaskScene);
        editTaskStage.show();
    }

    // Show the popup for creating a new task
    private void showCreateTaskPopup(Stage stage, ToDoList list) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Create Task");

        // Input fields
        Label taskNameLabel = new Label("Task Name:");
        TextField taskNameField = new TextField();

        Label statusLabel = new Label("Status:");
        ComboBox<TaskStatus> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(TaskStatus.values());

        Label categoryLabel = new Label("Category:");
        ComboBox<TaskCategory> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(TaskCategory.values());

        Label importanceLabel = new Label("Importance:");
        ComboBox<TaskImportance> importanceComboBox = new ComboBox<>();
        importanceComboBox.getItems().addAll(TaskImportance.values());

        Button createTaskButton = new Button("Create Task");
        createTaskButton.setOnAction(e -> {
            String taskName = taskNameField.getText();
            TaskStatus status = statusComboBox.getValue();
            TaskCategory category = categoryComboBox.getValue();
            TaskImportance importance = importanceComboBox.getValue();

            if (taskName.isEmpty() || status == null || category == null || importance == null) {
                showAlert("Error", "Please fill in all fields!");
            } else {
                Task newTask = new Task(taskName, status, category, importance);
                list.getAllTasks().add(newTask);

                showListDetail(stage, list);
                popupStage.close();
            }
        });

        // Layout for task creation popup
        GridPane taskCreationLayout = new GridPane();
        taskCreationLayout.setHgap(10);
        taskCreationLayout.setVgap(10);
        taskCreationLayout.add(taskNameLabel, 0, 0);
        taskCreationLayout.add(taskNameField, 1, 0);
        taskCreationLayout.add(statusLabel, 0, 1);
        taskCreationLayout.add(statusComboBox, 1, 1);
        taskCreationLayout.add(categoryLabel, 0, 2);
        taskCreationLayout.add(categoryComboBox, 1, 2);
        taskCreationLayout.add(importanceLabel, 0, 3);
        taskCreationLayout.add(importanceComboBox, 1, 3);
        taskCreationLayout.add(createTaskButton, 0, 4);

        HBox layout = new HBox(10);
        layout.setPadding(new Insets(0, 0, 0, 10));
        layout.getChildren().add(taskCreationLayout);

        Scene popupScene = new Scene(layout, 300, 250);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    // Update list of buttons in the main view
    private void updateListGroup(Stage stage) {
        listGroup.getChildren().clear();
        for (ToDoList list : allLists) {
            Button button = createListObject(list, stage);
            listGroup.getChildren().add(button);
        }
    }

    // Show an alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Create the list creation popup
    private void showCreateListPopup(Stage stage) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Create List");

        // Input fields for creating a new list
        Label titleLabel = new Label("List Title:");
        TextField titleField = new TextField();

        Label listCategoryLabel = new Label("List Category:");
        ComboBox<ListCategory> listCategoryComboBox = new ComboBox<>();
        listCategoryComboBox.getItems().addAll(ListCategory.values());

        Label colorLabel = new Label("List Color:");
        ComboBox<String> colorComboBox = new ComboBox<>();
        colorComboBox.getItems().addAll("Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Pink", "Brown", "Gray");

        Button createListButton = new Button("Create List");
        createListButton.setOnAction(e -> {
            String listTitle = titleField.getText();
            ListCategory listCategory = listCategoryComboBox.getValue();
            String listColor = colorComboBox.getValue();

            if (listTitle.isEmpty() || listCategory == null || listColor == null) {
                showAlert("Error", "Please enter a valid title, a valid category and color!");
            } else {
                ToDoList newList = new ToDoList(listTitle, listCategory, listColor);
                allLists.add(newList);
                updateListGroup(stage);
                popupStage.close();
            }
        });

        // Layout for List Creation Popup
        GridPane popupLayout = new GridPane();
        popupLayout.setVgap(10);
        popupLayout.setHgap(10);
        popupLayout.add(titleLabel, 0, 0);
        popupLayout.add(titleField, 1, 0);
        popupLayout.add(listCategoryLabel, 0, 1);
        popupLayout.add(listCategoryComboBox, 1, 1);
        popupLayout.add(colorLabel, 0, 2);
        popupLayout.add(colorComboBox, 1, 2);
        popupLayout.add(createListButton, 1, 3);

        HBox layout = new HBox(10);
        layout.setPadding(new Insets(0, 0, 0, 10));
        layout.getChildren().add(popupLayout);

        Scene popupScene = new Scene(layout, 300, 200);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    private void createExampleListWithTasks() {
        ToDoList list = new ToDoList("Example List", ListCategory.PROFESSIONAL, "Red");
        Task task1 = new Task("Write Project description", TaskStatus.COMPLETED, TaskCategory.WORK, TaskImportance.HIGH);
        Task task2 = new Task("Clean out my drawer", TaskStatus.NOT_STARTED, TaskCategory.MISCELLANEOUS, TaskImportance.OPTIONAL);
        Task task3 = new Task("Drink 0.5L per hour", TaskStatus.IN_PROGRESS, TaskCategory.HEALTH, TaskImportance.ESSENTIAL);
        list.getAllTasks().add(task1);
        list.getAllTasks().add(task2);
        list.getAllTasks().add(task3);
        allLists.add(list);
    }

    public static void main(String[] args) {
        launch();
    }
}
