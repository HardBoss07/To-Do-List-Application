package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.ListCategory;
import ch.bosshard.matteo.todolist.enums.TaskCategory;
import ch.bosshard.matteo.todolist.enums.TaskImportance;
import ch.bosshard.matteo.todolist.enums.TaskStatus;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
        label.getStyleClass().add("title-label");

        // Create List Button
        Button createList = new Button("Create a New List");
        createList.setOnAction(e -> showCreateListPopup(mainStage));
        createList.getStyleClass().add("create-list-button");

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
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        mainStage.setTitle("To Do List App");
        mainStage.setScene(scene);
        mainStage.show();

        mainScene = scene;
    }

    // Create list buttons
    private Button createListObject(ToDoList list, Stage stage) {
        Button button = new Button();
        button.getStyleClass().add("list-detail");
        button.setStyle("-fx-background-color: " + list.getListColor() + ";");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + darkenColor(list.getListColor(), 0.2) + ";"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + list.getListColor() + ";"));

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

    private String darkenColor(String hexColor, double factor) {
        Color color = Color.web(hexColor);
        double red = Math.max(0, color.getRed() * (1 - factor));
        double green = Math.max(0, color.getGreen() * (1 - factor));
        double blue = Math.max(0, color.getBlue() * (1 - factor));
        return String.format("#%02X%02X%02X",
                (int) (red * 255),
                (int) (green * 255),
                (int) (blue * 255));
    }

    private void showListDetail(Stage stage, ToDoList list) {
        VBox listDetailVBox = new VBox(10);

        Label titleLabel = new Label(list.getListTitle());
        titleLabel.getStyleClass().add("list-title");
        Button backButton = new Button("< Back");
        backButton.getStyleClass().add("task-buttons");
        backButton.setOnAction(e -> {
            updateListGroup(stage);
            stage.setScene(mainScene);
        });

        Button addTaskButton = new Button("+ Add Task");
        addTaskButton.setOnAction(e -> showCreateTaskPopup(stage, list));
        addTaskButton.getStyleClass().add("task-buttons");

        HBox buttons = new HBox(backButton, addTaskButton);
        buttons.setSpacing(5);
        buttons.setMaxHeight(330);
        buttons.setPadding(new Insets(0, 10, 0, 0));

        Label taskStatusLabel = new Label(list.getAllTasks().isEmpty() ? "No current tasks" : "Current Tasks (" + list.getAllTasks().size() + "):");
        taskStatusLabel.getStyleClass().add("list-detail");

        VBox tasksVBox = new VBox(10);
        for (Task task : list.getAllTasks()) {
            tasksVBox.getChildren().add(createTaskObject(task, stage, list));
        }

        listDetailVBox.getChildren().addAll(titleLabel, buttons, taskStatusLabel, tasksVBox);

        HBox layout = new HBox(10);
        layout.setPadding(new Insets(0, 0, 0, 10));
        layout.getChildren().add(listDetailVBox);

        Scene listDetailScene = new Scene(layout, 350, 600);
        listDetailScene.getStylesheets().add(mainScene.getStylesheets().getFirst());
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
        HBox categoryLabel = createDotNextToCategory(task.getBackgroundColor(), task.getTaskCategory().toFormattedString());
        Label statusLabel = new Label(task.getTaskStatus().toFormattedString());
        Label importanceLabel = new Label(task.getTaskImportance().toFormattedString());

        titleLabel.getStyleClass().add("task-title");
        categoryLabel.getStyleClass().add("task-category");
        statusLabel.getStyleClass().add("task-status");
        importanceLabel.getStyleClass().add("task-importance");

        categoryLabel.setMaxWidth(Double.MAX_VALUE);
        categoryLabel.setAlignment(Pos.CENTER_RIGHT);
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        statusLabel.setAlignment(Pos.CENTER_RIGHT);

        VBox leftSide = new VBox(10);
        VBox rightSide = new VBox(10);

        leftSide.getChildren().addAll(titleLabel, statusLabel);
        leftSide.setAlignment(Pos.CENTER_LEFT);
        rightSide.getChildren().addAll(categoryLabel, importanceLabel);
        rightSide.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(rightSide, Priority.ALWAYS);

        HBox content = new HBox(10);
        content.getChildren().addAll(leftSide, rightSide);

        button.setGraphic(content);
        button.setOnAction(e -> taskButtonAction(task, stage, list));
        button.setUserData(task);
        button.getStyleClass().add("task-object");
        checkBox.getStyleClass().add("task-object");

        // Checkbox to the left
        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                task.setTaskStatus(TaskStatus.COMPLETED);
            } else {
                task.setTaskStatus(TaskStatus.NOT_STARTED);
            }
            statusLabel.setText(task.getTaskStatus().toFormattedString());
        });

        if (task.getTaskStatus() == TaskStatus.NOT_STARTED || task.getTaskStatus() == TaskStatus.IN_PROGRESS) checkBox.setSelected(false);
        else checkBox.setSelected(true);

        VBox checkBoxVBox = new VBox(checkBox);
        checkBoxVBox.setPadding(new Insets(0, 0, 0, 10));
        checkBoxVBox.setAlignment(Pos.CENTER_LEFT);
        checkBoxVBox.getStyleClass().add("task-left");
        VBox buttonVBox = new VBox(button);
        buttonVBox.getStyleClass().add("task-right");

        borderPane.setLeft(checkBoxVBox);
        borderPane.setRight(buttonVBox);
        borderPane.setMaxWidth(330);
        borderPane.setUserData(checkBox);
        borderPane.setPrefWidth(330);
        borderPane.getStyleClass().add("task-object");
        return borderPane;
    }

    // Task Button action
    private void taskButtonAction(Task task, Stage stage, ToDoList list) {
        System.out.println(task.toString());

        Stage editTaskStage = new Stage();
        editTaskStage.setTitle("Edit " + task.getTaskName());

        // Input fields
        TextField titleTextField = new TextField(task.getTaskName());

        ComboBox<TaskStatus> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(TaskStatus.values());
        statusComboBox.setValue(task.getTaskStatus());
        statusComboBox.setPromptText("Current Status");
        statusComboBox.setConverter(new EnumStringConverter<>());

        ComboBox<TaskCategory> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(TaskCategory.values());
        categoryComboBox.setValue(task.getTaskCategory());
        categoryComboBox.setPromptText("Task Category");
        categoryComboBox.setConverter(new EnumStringConverter<>());

        ComboBox<TaskImportance> importanceComboBox = new ComboBox<>();
        importanceComboBox.getItems().addAll(TaskImportance.values());
        importanceComboBox.setValue(task.getTaskImportance());
        importanceComboBox.setPromptText("Task Importance");
        importanceComboBox.setConverter(new EnumStringConverter<>());

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
            task.updateColor();
            editTaskStage.close();
            showListDetail(stage, list);
        });

        titleTextField.getStyleClass().add("list-popup");
        statusComboBox.getStyleClass().add("list-popup");
        categoryComboBox.getStyleClass().add("list-popup");
        importanceComboBox.getStyleClass().add("list-popup");
        saveButton.getStyleClass().add("list-popup");

        // Layout
        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(titleTextField, statusComboBox, categoryComboBox, importanceComboBox, saveButton);

        HBox layout = new HBox(10);
        layout.setPadding(new Insets(0, 0, 0, 10));
        layout.getChildren().add(mainVBox);
        layout.getStyleClass().add("list-popup");

        Scene editTaskScene = new Scene(layout, 300, 210);
        editTaskScene.getStylesheets().add(mainScene.getStylesheets().getFirst());
        editTaskStage.setScene(editTaskScene);
        editTaskStage.show();
    }

    // Show the popup for creating a new task
    private void showCreateTaskPopup(Stage stage, ToDoList list) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Create Task");

        // Input fields
        TextField taskNameField = new TextField("Task Name");

        ComboBox<TaskStatus> statusComboBox = new ComboBox<>();
        statusComboBox.setPromptText("Current Status");
        statusComboBox.getItems().addAll(TaskStatus.values());
        statusComboBox.setConverter(new EnumStringConverter<>());

        ComboBox<TaskCategory> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("Task Category");
        categoryComboBox.getItems().addAll(TaskCategory.values());
        categoryComboBox.setConverter(new EnumStringConverter<>());

        ComboBox<TaskImportance> importanceComboBox = new ComboBox<>();
        importanceComboBox.setPromptText("Task Importance");
        importanceComboBox.getItems().addAll(TaskImportance.values());
        importanceComboBox.setConverter(new EnumStringConverter<>());

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

        taskNameField.getStyleClass().add("list-popup");
        statusComboBox.getStyleClass().add("list-popup");
        categoryComboBox.getStyleClass().add("list-popup");
        importanceComboBox.getStyleClass().add("list-popup");
        createTaskButton.getStyleClass().add("list-popup");

        // Layout for task creation popup
        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(taskNameField, statusComboBox, categoryComboBox, importanceComboBox, createTaskButton);

        HBox layout = new HBox(10);
        layout.setPadding(new Insets(0, 0, 0, 10));
        layout.getChildren().add(mainVBox);
        layout.getStyleClass().add("list-popup");

        Scene popupScene = new Scene(layout, 300, 210);
        popupScene.getStylesheets().add(mainScene.getStylesheets().getFirst());
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    // Update list of buttons in the main view
    private void updateListGroup(Stage stage) {
        listGroup.getChildren().clear();
        for (ToDoList list : allLists) {
            list.updateCompletionPercentage();
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
        titleLabel.getStyleClass().add("list-popup");
        TextField titleField = new TextField("Write a title");
        titleField.getStyleClass().add("list-popup");

        ComboBox<ListCategory> listCategoryComboBox = new ComboBox<>();
        listCategoryComboBox.setPromptText("Select a category");
        listCategoryComboBox.getStyleClass().add("list-popup");
        listCategoryComboBox.getItems().addAll(ListCategory.values());
        listCategoryComboBox.setConverter(new EnumStringConverter<>());

        ComboBox<HBox> colorComboBox = new ComboBox<>();
        colorComboBox.setPromptText("Select a color");
        colorComboBox.getStyleClass().add("list-popup");
        String[] colorOptions = {
                "Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Pink", "Brown", "Gray"
        };

        for (String colorOption : colorOptions) {
            colorComboBox.getItems().add(createColorObject(colorOption));
        }

        Button createListButton = new Button("Create List");
        createListButton.getStyleClass().add("list-popup");
        createListButton.setOnAction(e -> {
            String listTitle = titleField.getText();
            ListCategory listCategory = listCategoryComboBox.getValue();
            String listColor = colorComboBox.getValue().getUserData().toString();

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
        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(titleField, listCategoryComboBox, colorComboBox, createListButton);

        HBox layout = new HBox(10);
        layout.setPadding(new Insets(0, 0, 0, 10));
        layout.getChildren().add(mainVBox);
        layout.getStyleClass().add("list-popup");

        Scene popupScene = new Scene(layout, 300, 170);
        popupScene.getStylesheets().add(mainScene.getStylesheets().getFirst());
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    private HBox createDotNextToCategory(String color, String category) {
        HBox hBox = new HBox(5);
        hBox.setUserData(color);
        Circle circle = new Circle(4);
        circle.setFill(Color.web(color));
        circle.setStroke(Color.web("#3b3b3b"));
        circle.setStrokeWidth(1);

        Label label = new Label(category);
        label.getStyleClass().add("task-category");

        hBox.getChildren().addAll(circle, label);

        return hBox;
    }

    private HBox createColorObject(String color) {
        HBox hBox = new HBox(5);
        hBox.setUserData(color);

        Rectangle colorRect = new Rectangle(10, 10);
        colorRect.setFill(Color.web(getHexColorFromString(color)));

        Label colorLabel = new Label(color);

        hBox.getChildren().addAll(colorRect, colorLabel);

        return hBox;
    }

    private String getHexColorFromString(String color) {
        return switch (color) {
            case "Red" -> "#E63946";
            case "Blue" -> "#457B9D";
            case "Green" -> "#2A9D8F";
            case "Yellow" -> "#E9C46A";
            case "Purple" -> "#9D4EDD";
            case "Orange" -> "#F4A261";
            case "Pink" -> "#F28482";
            case "Brown" -> "#BD7013";
            case "Gray" -> "#D4D4D4";
            default -> "#FFFFFF";
        };
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
