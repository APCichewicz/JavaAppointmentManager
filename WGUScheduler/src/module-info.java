module WGUScheduler{
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens  com.example.wgusoftware2scheduler.Controllers;
    exports com.example.wgusoftware2scheduler to javafx.graphics;
}
