module com.sage.cems {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.sage.cems to javafx.fxml;
    exports com.sage.cems;
    exports com.sage.cems.controllers;
    opens com.sage.cems.controllers to javafx.fxml;
}