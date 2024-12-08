module ch.bosshard.matteo.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;


    opens ch.bosshard.matteo.todolist to javafx.fxml;
    exports ch.bosshard.matteo.todolist;
    exports ch.bosshard.matteo.todolist.enums;
    opens ch.bosshard.matteo.todolist.enums to javafx.fxml;
}