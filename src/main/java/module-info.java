module com.spinero.company_appointment_tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.spinero.company_appointment_tracker to javafx.fxml;
    exports com.spinero.company_appointment_tracker;
}