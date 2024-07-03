module kindowwill {
    requires javafx.controls;
    requires javafx.fxml;

    opens kindowwill to javafx.fxml;
    exports kindowwill;
}
