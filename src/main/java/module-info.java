module lee.dev.programmablethermostatgui {
    requires javafx.controls;
    requires javafx.fxml;

    opens lee.dev.programmablethermostatgui to javafx.fxml;
    exports lee.dev.programmablethermostatgui;
}
