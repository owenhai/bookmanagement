module org.example.bookmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires mysql.connector.j;
    requires jbcrypt;
    requires kernel;
    requires layout;
    requires java.mail;


    opens org.example.bookmanagement to javafx.fxml;
    exports org.example.bookmanagement;

}