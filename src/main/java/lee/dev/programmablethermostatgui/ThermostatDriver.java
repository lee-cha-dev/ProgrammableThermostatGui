package lee.dev.programmablethermostatgui;

public class ThermostatDriver {
    
    // CLASS VARIABLES
    private String urlPath;
    private final TempDataHandler tempDataHandler;

    // CLASS INITIALIZER
    public ThermostatDriver(String urlPath){
        // URL PATH TO PASS TO THE TEMP DATA HANDLER
        this.urlPath = urlPath;
        
        // INSTANCE OF THE TEMP DATA HANDLER CLASS TO HANDLE 
        // DATA FROM THE API CALL
        this.tempDataHandler = new TempDataHandler(urlPath);
        
        // REQUEST THE DATA AND THEN CONVERT IT TO A DICTIONARY
        this.tempDataHandler.requestJsonData();
        this.tempDataHandler.convertJsonData();

        // ISSUE WITH URL DATA -- OVERRIDE INIT TEMP
        this.setTemperature("73");
    }

    // METHOD TO OUTPUT THE DISPLAY OF THE THERMOSTAT TO THE USER
    public void displayThermostat(){
        System.out.println("\nGood Afternoon!\n");
        System.out.println("Date: " + this.tempDataHandler.getDate());
        System.out.println("Time: " + this.tempDataHandler.getTime());
        System.out.println("UTC Date: " + this.tempDataHandler.getUtcDate());
        System.out.println("UTC Time: " + this.tempDataHandler.getUtcTime());
        System.out.println("Temperature: " + this.tempDataHandler.getTemperature());
        System.out.println("Humidity: " + this.tempDataHandler.getHumidity());
    }

    // GET/SET FOR THE URL
    public void setUrlPath(String newUrl){
        this.urlPath = newUrl;
    }

    public String getUrlPath(){
        return this.urlPath;
    }

    public String getDate(){ return this.tempDataHandler.getDate(); };
    public String getTime(){ return this.tempDataHandler.getTime(); }
    public String getUtcTime(){ return this.tempDataHandler.getUtcTime(); }
    public String getUtcDate(){ return this.tempDataHandler.getUtcDate(); }
    public String getTemperature(){ return this.tempDataHandler.getTemperature(); }
    public String getNewTemperature(){ return this.tempDataHandler.getNewTemperature(); }
    public String getHumidity(){ return this.tempDataHandler.getHumidity(); }
    public String getCode(){ return this.tempDataHandler.getCode(); };
    public String getMessage(){ return this.tempDataHandler.getMessage(); };
    public String getIdentifier(){ return this.tempDataHandler.getIdentifier(); }
    public String getName(){ return this.tempDataHandler.getName(); }

    // SET METHODS
    public void setTemperature(String newTemp){
        this.tempDataHandler.setTemperature(newTemp);
    }

    public void setNewTemperature(String newTemp){
        this.tempDataHandler.setNewTemperature(newTemp);
    }
}
