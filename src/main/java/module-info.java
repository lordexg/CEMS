module com.sage.cems {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.sage.cems to javafx.fxml;
    exports com.sage.cems;
    exports com.sage.cems.controllers;
    exports com.sage.cems.controllers.admin;
    exports com.sage.cems.controllers.student;
    exports com.sage.cems.controllers.lecturer;
    exports com.sage.cems.models;
    exports com.sage.cems.views;
}