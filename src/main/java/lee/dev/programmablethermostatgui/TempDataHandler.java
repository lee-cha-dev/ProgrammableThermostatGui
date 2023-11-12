/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lee.dev.programmablethermostatgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author KCHAR
 */
public class TempDataHandler {
    // URL FOR THE API THAT IS HOSTING THE JSON DATA
    private String jsonUrl;
    
    // HOLDS THE STRING OF DATA THAT WILL BE MANIPULATED
    // TO CREATE THE DICTIONARY OF THERMOSTAT DATA
    private String jsonData = "";

    // DATA VALUES FOR THE THERMOMETER THAT WERE RETRIEVED FROM THE
    // API HOSTING THE JSON DATA -- WILL BE UPDATED WHEN THE
    // CONVERT JSON DATA METHOD IS CALLED
    private String date;
    private String timeValue;
    private String utcDate;
    private String utcTimeValue;
    private String temperature;
    private String humidity;
    private String code;
    private String message;
    private String identifier;
    private String name;

    private String newTemperature;

    // DICTIONARY TO HOLD THE KEY VALUES SETS FROM THE JSON OBJECT
    private Dictionary<String, String> dict = new Hashtable<>();

    // INITIALIZER
    TempDataHandler(String jsonUrl){
        this.jsonUrl = jsonUrl;
    }

    // GET & SET METHODS FOR THE JSON URL
    public String getJsonUrl(){
        return this.jsonUrl;
    }

    public void setJsonUrl(String newUrl){
        this.jsonUrl = newUrl;
    }

    // METHOD TO HANDLE REQUESTING THE JSON DATA FROM THE API/URL
    public void requestJsonData() {
        // MAKE A WEB CALL TO THE JSON URL TO GET THE JSON DATA
        URL url = null;

        // TRY TO SET THE URL
        try {
            url = new URL(this.jsonUrl);

        } catch (MalformedURLException e) {
            System.out.printf("The URL was malformed\n%s", e);
        }

        // TRY TO OPEN THE URL AND STREAM THE DATA USING INPUT STREAM READER, BUFFER READER, AND STRING BUILDER
        try {
            assert url != null;
            InputStream input = url.openStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bReader = new BufferedReader(reader);
            StringBuilder json = new StringBuilder();

            int i;
            while ((i = bReader.read()) != -1){
                json.append((char)i);
            }
            this.jsonData = json.toString();

        } catch (IOException e){
            System.out.printf("IOException for input when opening Json Url\n%s", e);
        }
    }

    // METHOD TO CONVERT THE JSON DATA INTO A USABLE DICTIONARY
    public void convertJsonData(){
        // REMOVE ALL NON ESSENTIAL CHARS
        String temp = this.jsonData.replace("{", "");
        temp = temp.replace("}", "");
        temp = temp.replace(",", "");
        temp = temp.replace("\"", "");
        temp = temp.replace("runtime:", "");
        temp = temp.replace("status:", "");

        // BOOL TO TOGGLE FROM TIME TO UTC TIME FOR THE DICT
        boolean isTime = true;

        // ITERATE THROUGH THE ARRAY, CLEAN UP THE WHITE SPACE AND ASSIGN THE KEY:VALUE
        // PAIRS TO THE DICTIONARY FOR REFERENCE
        String[] strArr = temp.split("\n");
        for (String arr : strArr){
            String[] dictTemp = arr.split(":");

            // ENSURE THERE IS A KEY:VALUE PAIR
            if (dictTemp.length > 1){
                String key = dictTemp[0].replace(" ", "");
                String value = "";

                // IF THE ARRAY HAS MORE THAN TWO STRINGS, THEN HAS DATA FOR DATE TIME THAT NEEDS
                // TO BE SEPARATED AND ADDED TO THE DICT
                if (dictTemp.length > 2){
                    String[] timeDate = dictTemp[1].split(" ");

                    // SET THE VALUE FOR THE TIME
                    value = timeDate[1];

                    // PARSE TOGETHER THE FULL TIME VALUE (SPLIT BETWEEN THREE AREAS
                    String timeValue = timeDate[2] + ":" + dictTemp[2] + ":" + dictTemp[3];

                    // IF ELSE TO HANDLE THE KEY VALUE FOR TIME AND UTC TIME
                    if (isTime){
                        this.dict.put("timeValue", timeValue);
                        isTime = false;
                    } else {
                        this.dict.put("utcTimeValue", timeValue);
                    }
                } else {
                    // IF THE ARRAY DOES NOT CONTAIN THE TIME DATE PAIR, THEN REMOVE THE WHITE SPACE
                    value = dictTemp[1].replace(" ", "");
                }

                // ADDS THE KEY VALUE PAIR TO THE DICTIONARY (BESIDES THE TIME AND UTC TIME)
                this.dict.put(key, value);
            }
        }
        // CALL METHOD TO HANDLE ASSIGNING THE THERMOMETER VALUES
        this.setThermometerValues();
    }

    public void setThermometerValues(){
        this.identifier = this.dict.get("identifier");
        this.name = this.dict.get("name");
        this.date = this.dict.get("thermostatTime");
        this.timeValue = this.dict.get("timeValue");
        this.utcDate = this.dict.get("utcTime");
        this.utcTimeValue = this.dict.get("utcTimeValue");
        this.temperature = this.dict.get("actualTemperature");
        this.humidity = this.dict.get("actualHumidity");
        this.code = this.dict.get("code");
        this.message = this.dict.get("message");

        this.newTemperature = "73";
    }

    // GET METHOD FOR THE JSON DATA - DOES NOT NEED A SET METHOD AS THE REQUEST METHOD HANDLES THAT
    public String getJsonData(){
        return this.jsonData;
    }

    // GET METHODS FOR THE THERMOSTAT -- SET METHODS ARE NOT 
    // NECESSARY AS THE DATA IS UPDATED/SET BY THE 
    // DATA FROM AN API
    public String getDate(){ return this.date; };
    public String getTime(){ return this.timeValue; }
    public String getUtcTime(){ return this.utcTimeValue; }
    public String getUtcDate(){ return this.utcDate; }
    public String getTemperature(){ return this.temperature; }
    public String getNewTemperature(){ return this.newTemperature; }
    public String getHumidity(){ return this.humidity; }
    public String getCode(){ return this.code; };
    public String getMessage(){ return this.message; };
    public String getIdentifier(){ return this.identifier; }
    public String getName(){ return this.name; }

    // SET METHODS
    public void setTemperature(String newTemp){
        this.temperature = newTemp;
    }

    public void setNewTemperature(String newTemp){
        this.newTemperature = newTemp;
    }
}
