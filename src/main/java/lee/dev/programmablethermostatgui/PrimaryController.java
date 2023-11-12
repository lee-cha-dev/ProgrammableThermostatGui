package lee.dev.programmablethermostatgui;

import java.io.Console;
import java.io.IOException;
import java.text.DecimalFormat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class PrimaryController {

    // DEBUG VARIABLES -- CLASS INTEGRATION TO COME
    private int newTemperature = 73;
    private int system = 0;
    private int fan = 0;
    private float temp = 73.4F;
    private boolean isCelsius = false;

    // FXML VARIABLES
    @FXML
    private Button systemButton;

    @FXML
    private Label systemValue;

    @FXML
    private Button fanButton;

    @FXML
    private Label fanValue;

    @FXML
    private Button arrowUpButton;

    @FXML
    private Button arrowDownButton;

    @FXML
    private RadioButton radioFahrenheit;

    @FXML
    private RadioButton radioCelsius;

    @FXML
    public Label tempLabel;

    @FXML
    private Label tempValue;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label humidityValue;

    @FXML
    private Label currentLabel;

    @FXML
    private Label currentValue;

    @FXML
    private Label timeLabel;

    @FXML
    private Label timeValue;

    // CLASSES TO HANDLE THE DRIVER AND DATA
    private final ThermostatDriver driver = new ThermostatDriver("https://media.capella.edu/BBCourse_Production/IT4774/temperature.json");
    private final UserSettingsHandler settingsHandler = new UserSettingsHandler();

    // FXML METHODS
    @FXML
    private void toggleFehrenheit(ActionEvent event){
        // TOGGLE THE RADIOS BUTTONS
        this.radioCelsius.setSelected(false);
        this.radioFahrenheit.setSelected(true);

        // ENSURE THE TEMP IS SET TO CELSIUS
        if (isCelsius){
            // UPDATE THE TEMPERATURE VALUE TO FAHRENHEIT

            // SET A TEMPERATURE FORMAT - UPDATE THE TEMP TO FAHRENHEIT -- UPDATE THE LABEL
            DecimalFormat df = new DecimalFormat("#.#");
            temp = Float.parseFloat(df.format((temp * 9/5) + 32));
            this.tempValue.setText(String.valueOf(temp));

            isCelsius = !isCelsius;
        }

    }

    @FXML
    private void toggleCelsius(ActionEvent event){
        // TOGGLE THE RADIO BUTTONS
        this.radioCelsius.setSelected(true);
        this.radioFahrenheit.setSelected(false);

        // ENSURES TEMP IS SET TO FAHRENHEIT
        if (!isCelsius){
            // UPDATE THE TEMPERATURE TO CELSIUS

            // SET A TEMPERATURE FORMAT - UPDATE THE TEMP TO CELSIUS -- UPDATE THE LABEL
            DecimalFormat df = new DecimalFormat("#.#");
            temp = Float.parseFloat(df.format((temp - 32) * 5/9));
            this.tempValue.setText(String.valueOf(temp));

            isCelsius = !isCelsius;
        }

    }

    @FXML
    private void tempUp(ActionEvent event){
        System.out.println("Temp Up");

        // UPDATE THE LOCAL VARIABLE HOLDING THE USER'S SETTINGS

        if ((this.newTemperature + 1) <= 90){
            this.newTemperature += 1;
            this.currentValue.setText(String.valueOf(this.newTemperature));
        }
    }

    @FXML private void tempDown(){
        System.out.println("Temp Down");

        // UPDATE THE LOCAL VARIABLE HOLD THE USER'S SETTINGS

        if ((this.newTemperature - 1) >= 50){
            this.newTemperature -= 1;
            this.currentValue.setText(String.valueOf(this.newTemperature));
        }
    }

    @FXML
    public void program(ActionEvent actionEvent) {
        System.out.println("Program Clicked");

        // RESET TO DEFAULT -- DEBUG
        this.currentValue.setText("73");
        newTemperature = 73;
    }

    @FXML
    public void systemButton(ActionEvent actionEvent) {
        System.out.println("System Clicked");

        if (system >= 2){
            system = 0;
        } else {
            ++system;
        }

        switch (system){
            case 0:
                this.systemValue.setText("Cool");
                break;
            case 1:
                this.systemValue.setText("Heat");
                break;
            default:
                this.systemValue.setText("Auto");
                break;
        }
    }

    @FXML
    public void fanButton(ActionEvent actionEvent) {
        System.out.println("Fan Clicked");

        if (fan >= 2){
            fan = 0;
        } else {
            ++fan;
        }

        switch (fan){
            case 0:
                this.fanValue.setText("Auto");
                break;
            case 1:
                this.fanValue.setText("  On ");
                break;
            default:
                this.fanValue.setText(" Off ");
                break;
        }
    }
}
