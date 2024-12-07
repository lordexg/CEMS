module com.sage.cems {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;

    opens com.sage.cems to javafx.fxml;
    exports com.sage.cems;
    exports com.sage.cems.controllers;
    exports com.sage.cems.controllers.admin;
    exports com.sage.cems.controllers.student;
    exports com.sage.cems.controllers.lecturer;
    exports com.sage.cems.models;
    exports com.sage.cems.views;
    exports com.sage.cems.util;
    exports com.sage.cems.models.user;
    exports com.sage.cems.services;
}