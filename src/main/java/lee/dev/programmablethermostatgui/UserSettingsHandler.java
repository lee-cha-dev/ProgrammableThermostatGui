package lee.dev.programmablethermostatgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UserSettingsHandler {
    // VAR TO HOLD THE SETTING FILE PATH
    String settingsFilePath = "files/temperature_settings.txt";

    // 2 DIMENSIONAL ARRAY TO HOLD THE STRINGS OF DATA PULLED FROM THE SETTINGS FILE
    ArrayList<ArrayList<String>> settings = new ArrayList<>();

    // INIT FOR THE CLASS
    UserSettingsHandler(){
        this.initSettingsArrayList();
    }

    // INIT/UPDATE THE ARRAYLIST WITH THE CURRENT DATA SETTINGS
    public void initSettingsArrayList(){
        this.settings = new ArrayList<>();
        try{
            File settingsFile = new File(this.settingsFilePath);
            Scanner reader = new Scanner(settingsFile);

            while (reader.hasNextLine()){
                // CREATE THE NEW ARRAY LIST TO HOLD THE TEMP SETTINGS FOR EACH HOUR
                ArrayList<String> newArray = new ArrayList<>();

                // USE THE READER TO GET THE DATA ON EACH LINE - SPLIT INTO A STRING ARRAY
                String data = reader.nextLine();
                String[] dataArr = data.split(",");

                // ADD THE STRING TO THE ARRAY LIST AND THEN ADD THE ARRAY LIST TO THE SETTINGS ARRAY LIST
                Collections.addAll(newArray, dataArr);
                this.settings.add(newArray);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSettingsFile() {
        try (FileWriter writer = new FileWriter(this.settingsFilePath)) {
            for (ArrayList<String> arrList : this.settings){
                String line = arrList.get(0) + "," + arrList.get(1) + "," + arrList.get(2) + "\n";
                writer.write(line);
            }
        } catch (IOException e){
            System.out.println("Could not open the settings file to write over the previous settings.\n");
        }
    }

    public void promptUserForNewSettings(){

        // INIT THE SCANNER AND VARIABLES FOR TEMP, MODE AND INDEX;
        Scanner input = new Scanner(System.in);
        float newTemp = 0.0f;
        String mode = "";
        int timeIdx = 0;


        // ITERATE OVER THE TIMES AND THEIR OPTIONS - DISPLAY TO USER
        int optionIdx = 1;
        for (ArrayList<String> arrayList : this.settings){
            System.out.printf("%s. Time: %s\tTemp: %s\tMode: %s\n", optionIdx, arrayList.get(0), arrayList.get(1), arrayList.get(2));
            ++optionIdx;
        }

        // PROMPT THE USER FOR THE TIME THEY WOULD LIKE TO EDIT -- VERIFY THEIR CHOICE IS VALID
        boolean acceptableInput = false;
        String userInput = "";

        while (!acceptableInput){
            System.out.print("\nChoose a time you would like to edit (enter 1 - 24): ");
            userInput = input.nextLine();
            int intUserInput = 0;

            // TRY/CATCH TO PARSE THE INT
            try {
                intUserInput = Integer.parseInt(userInput);
            } catch (Exception e){
                System.out.println("The input must be a whole number.");
                intUserInput = -1;
            }

            // VALIDATE THAT IT IS WITHIN AN ACCEPTABLE RANGE
            if (intUserInput > 0 && intUserInput < 25){
                acceptableInput = true;
                timeIdx = intUserInput - 1;
            } else {
                System.out.println("\nPlease enter 1 - 24..");
            }
        }

        // LOOP TO HANDLE PROMPTING THE USER FOR THE MODE
        acceptableInput = false;

        // HANDLES ENSURING THE NUMBER IS IN THE RANGE OF 1 - 3
        int intUserInput = 0;
        while (!acceptableInput){

            // LOOP TO HANDLE USER INPUTTING ANYTHING THAT IS NOT A NUMBER
            boolean matchedInput = false;
            while (!matchedInput){
                System.out.print("Choose a mode (heat, cold, auto)\n1. heat\n2. cold\n3. auto\nEnter 1 - 3: ");
                String response = input.nextLine();

                // TRY/CATCH TO PARSE THE INT
                boolean correct = true;
                try {
                    intUserInput = Integer.parseInt(response);
                } catch (InputMismatchException | NumberFormatException e){
                    System.out.println("\nPlease enter a number.");
                    correct = false;
                }
                if (correct) matchedInput = true;
            }

            // IF THE VALUE IS WITHIN RANGE SET THE MODE USING A SWITCH STATEMENT
            if (intUserInput > 0 && intUserInput < 4){
                acceptableInput = true;
                switch (intUserInput){
                    case 1:
                        mode = "heat";
                        break;
                    case 2:
                        mode = "cold";
                        break;
                    default:
                        mode = "auto";
                        break;
                }
            } else {
                System.out.println("\nPlease enter 1, 2, or 3\n");
            }
        }

        // LOOP TO HANDLE PROMPTING THE USER FOR THE TEMPERATURE
        acceptableInput = false;

        while (!acceptableInput){
            System.out.print("Choose a temperature to set the thermostat at by entering a temp: ");
            userInput = input.nextLine();
            boolean correct = true;

            float floatUserInput = 0.0f;
            // TRY/CATCH TO PARSE THE INT
            try {
                floatUserInput = Float.parseFloat(userInput);
            } catch (InputMismatchException | NumberFormatException e){
                System.out.println("Please enter a whole number.");
                correct = false;
            }

            // IF THE VALUE WAS PARSED THEN ASSIGN IT TO THE NEW TEMP VAR
            if (correct){
                acceptableInput = true;
                newTemp = floatUserInput;
            }
        }

        // UPDATE THE TEMPERATURE AND MODE FOR THE ARRAY LIST
        this.settings.get(timeIdx).set(1, String.valueOf(newTemp));
        this.settings.get(timeIdx).set(2, mode);

        System.out.printf("Time: %s, Temp: %s, Mode: %s\n", this.settings.get(timeIdx).get(0), this.settings.get(timeIdx).get(1), this.settings.get(timeIdx).get(2));

        this.updateSettingsFile();
    }
}
