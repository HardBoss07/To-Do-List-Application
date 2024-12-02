package ch.bosshard.matteo.todolist;

import ch.bosshard.matteo.todolist.enums.ListCategory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Title label
        Label label = new Label("To Do List App");

        // Example lists
        ToDoList list1 = new ToDoList("Personal List", ListCategory.PERSONAL, "Red");
        Label listLabel1 = new Label(list1.getListTitle());

        ToDoList list2 = new ToDoList("Work List", ListCategory.PROFESSIONAL, "Blue");
        Label listLabel2 = new Label(list2.getListTitle());

        // Container for all lists
        VBox listVBox = new VBox(5);
        listVBox.getChildren().addAll(listLabel1, listLabel2);

        // Layout
        VBox mainVBox = new VBox(10);
        mainVBox.getChildren().addAll(label, listVBox);

        // Scene handling
        Scene scene = new Scene(mainVBox, 350, 600);
        stage.setTitle("To Do List App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}