module ni.edu.uam.practicathreads {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.practicathreads to javafx.fxml;
    exports ni.edu.uam.practicathreads;
}