module ch.bosshard.matteo.todolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens ch.bosshard.matteo.todolist to javafx.fxml;
    exports ch.bosshard.matteo.todolist;
}