module com.example.libraryproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi;
    requires poi.ooxml;
    requires jbcrypt;


    opens com.example.libraryproject to javafx.fxml;
    exports com.example.libraryproject;
    exports com.example.libraryproject.dbClasses;
    opens com.example.libraryproject.dbClasses to javafx.fxml;
}