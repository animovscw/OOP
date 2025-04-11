module ru.nsu.anisimov.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.xml.dom;
    requires java.desktop;


    opens ru.nsu.anisimov.task_2_3_1 to javafx.fxml;
    exports ru.nsu.anisimov.task_2_3_1;
}