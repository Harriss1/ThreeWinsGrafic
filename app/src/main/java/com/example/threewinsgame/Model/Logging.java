package com.example.threewinsgame.Model;

import java.util.ArrayList;

public class Logging {
    private static long logStartTime = System.nanoTime();

    //set up whether or not we allow to show the debug access:

    private boolean developerMode = true;

    /*
    actually I want to use this class as superclass, via inheritance
     */
    private String originIdentifier;
    private String errorOriginNotSet = "originIdentifier not set";

    private ArrayList<String> logMessageArrayList = new ArrayList<>();

    private static ArrayList<String> globalLogArrayList = new ArrayList<>();
/*
    public Logging(){
        this.originIdentifier = errorOriginNotSet;
    }
*/
    public Logging(String originIdentifier){
        this.originIdentifier = originIdentifier;
    }

    public void setLogOrigin(String originIdentifier){
        this.originIdentifier = originIdentifier;
    }

    public void setLogMessage(String logMessage){
        if(originIdentifier.equals(errorOriginNotSet)){
            //add message or option so I am reminded or forced to always set the log origin?
            //it should not be enforced with an error or less functionality
            //for now I will add an message at the end of the getLogText() if it's not set
        }
        logMessageArrayList.add(logMessage);

        setGlobalLogMessage(logMessage, originIdentifier);
    }
    
    public String getLogText(){
        String logText = "Log from: " + originIdentifier + "\n";

        for (String text:logMessageArrayList
             ) {
            logText += text + "\n";
        }

        logText += "# End of Log instance. # \n";

        if (originIdentifier.equals(errorOriginNotSet))
            logText += "Reminder: Please set origin of this Log instance. (" + errorOriginNotSet + ")\n";
        else logText += "Origin: "+ originIdentifier + "\n";

        return logText;
    }

    private static void setGlobalLogMessage(String logMessage, String originIdentifier){
        String globalLogMessage;
        long timeElapsed = System.nanoTime() - logStartTime;
        String timeElapsedStr = Long.toString(timeElapsed);
        globalLogMessage = "\"" + originIdentifier + "\": " + logMessage + " (at " + timeElapsedStr + " ns)\n";
        globalLogArrayList.add(globalLogMessage);
    }

    public static String getGlobalLogText(){
        String globalLogText =
                "Complete Log:\n";

        for (String logMessage : globalLogArrayList){
            globalLogText += logMessage;
        }

        globalLogText+="\nThis is my first app. I left landscape mode functional, but I am unable to save data, it would require " +
                "a complete rework of all classes. In the end writing everything new would be faster. Deactivating landscape mode is bad practise" +
                " because some devices need it.\n";
        return globalLogText;
    }

    public boolean getDeveloperModeSetting(){
        return developerMode;
    }
}
