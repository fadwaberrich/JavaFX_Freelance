module com.example.pidevskillhub  {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.swing;
    requires itextpdf;



    requires java.desktop; // Add this line to require the java.desktop module
    // Other module declarations...




    opens com.example.pidevskillhub to javafx.fxml;


    exports com.example.pidevskillhub;

}