package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.ListCategory;
import ch.bosshard.matteo.todolist.enums.TaskCategory;
import ch.bosshard.matteo.todolist.enums.TaskImportance;
import ch.bosshard.matteo.todolist.enums.TaskStatus;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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

        double stageWidth = stage.getWidth();
        button.setPrefWidth(stageWidth * 0.9);
        button.setPrefHeight(button.getPrefWidth() / 6);

        Label titleLabel = new Label(list.getListTitle());
        Label categoryLabel = new Label(list.getListCategory().toString());
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
            tasksVBox.getChildren().add(new Label(task.getTaskName()));
        }

        listDetailVBox.getChildren().addAll(titleLabel, backButton, addTaskButton, taskStatusLabel, tasksVBox);

        Scene listDetailScene = new Scene(listDetailVBox, 350, 600);
        stage.setScene(listDetailScene);
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

        Scene popupScene = new Scene(taskCreationLayout, 300, 250);
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

        Scene popupScene = new Scene(popupLayout, 300, 200);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
