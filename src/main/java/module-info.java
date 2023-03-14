module com.undelir.dbstuff {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.undelir.controllers to javafx.fxml;
    exports com.undelir.controllers;
}