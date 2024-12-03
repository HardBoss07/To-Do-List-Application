package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.ListCategory;
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

    @Override
    public void start(Stage stage) {

        // Title label
        Label label = new Label("To Do List App");

        // Create List Button
        Button createList = new Button("New List");
        createList.setOnAction(e -> showCreateListPopup(stage));

        // Layout
        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(label, createList, listGroup);

        HBox mainHBox = new HBox(20);
        mainHBox.setPadding(new Insets(0, 0, 0, 10));

        mainHBox.getChildren().add(mainVBox);

        // Initial Scene setup
        updateListGroup(stage);
        Scene scene = new Scene(mainHBox, 350, 600);
        stage.setTitle("To Do List App");
        stage.setScene(scene);
        stage.show();
    }

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

        return button;
    }

    private void showCreateListPopup(Stage stage) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Create List");

        // Input fields
        Label titleLabel = new Label("List Title:");
        TextField titleField = new TextField();

        Label listCategoryLabel = new Label("List Category:");
        ComboBox<ListCategory> listCategoryComboBox = new ComboBox<>();
        listCategoryComboBox.getItems().addAll(ListCategory.values());

        Label colorLabel = new Label("List Color:");
        ComboBox<String> colorComboBox = new ComboBox<>();
        colorComboBox.getItems().addAll("Red", "Blue", "Green", "Yellow", "Purple", "Orange");

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

        // Layout for Popup
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

    private void updateListGroup(Stage stage) {
        listGroup.getChildren().clear();
        for (ToDoList list : allLists) {
            Button button = createListObject(list, stage);
            listGroup.getChildren().add(button);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
