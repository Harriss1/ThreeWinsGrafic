package com.example.threewinsgame.Model;

import java.util.ArrayList;


//supposed to temporarily store data, readAndDelete() puts this data into another Container, here SaveInstance.
//its important to start any counter at zero and not at one to have a unified id scheme.
//I always did ArrayList<Integer> list = new ArrayList<Integer> wrong, forgot the second bracket.

//setSaveFormat(SaveRestoreInstanceStateHandler); ->this sets which class is used to save and restore data?
//example we use a class that saves into Android temp variables, and another which uses files.

//STEP ONE: We make it work in smallest parts!
//STEP TWO: SaveRestoreInstanceStateHandler will have a function "readAndDeleteContainer(classNameContainer)
// to store and restore data via restoreDataToContainer(generalSaveContainer).

public class GeneralSaveContainer {
    private Logging log;
    private static int counter =0;


    public final String typeInt="Integer";
    public final String typeStr="String";
    public final String typeBool="Boolean";
    public final String typeUndef="Variable type not defined";

    public final String keyNameNotSet="keyNameNotSet";

    private static ArrayList<DataEntry> dataEntries = new ArrayList<>();

    private class DataEntry{
        String keyName=keyNameNotSet;

        int valueInt=0;
        String valueStr="";
        boolean valueBool=false;

        String variableType=typeUndef;

        DataEntry(String valueStr, String keyName){
            this.valueStr=valueStr;
            this.variableType=typeStr;
            this.keyName=keyName;
            log.setLogMessage("new DataEntry("+valueStr+", key:"+keyName+")");
        }
        DataEntry(int valueInt, String keyName){
            this.valueInt=valueInt;
            this.variableType=typeInt;
            this.keyName=keyName;
            log.setLogMessage("new DataEntry("+valueInt+", key:"+keyName+")");
        }
        DataEntry(boolean valueBool, String keyName){
            this.valueBool=valueBool;
            this.variableType=typeBool;
            this.keyName=keyName;
            log.setLogMessage("new DataEntry("+valueBool+", key:"+keyName+")");
        }
    }

    public GeneralSaveContainer(){
        log = new Logging("GeneralSaveContainer.java");
        log.setLogMessage("created");

    }

    public boolean checkKeyNameIsUnique(String keyName1){
        for (DataEntry element : dataEntries){
            if(element.keyName.equals(keyName1)){
                log.setLogMessage("checkKeyNameIsUnique(): not unique key("+keyName1+")");
                return false;
            }
        }
        return true;
    }

    public void addDataEntry(String value, String keyName){
        log.setLogMessage("addDAtaEntrySTR: keyName:"+keyName);
        if (!checkKeyNameIsUnique(keyName)){
            log.setLogMessage("addDataEntry STR: keyName not unique");
            return;
        }
        DataEntry dataEntry=new DataEntry(value,keyName);
        dataEntries.add(dataEntry);
        counter++;
        log.setLogMessage("new Entry added: val("+value+")key("+keyName+")\n"+
                "counter("+counter+")dataEntries.size("+dataEntries.size()+")"
        );
    }
    public void addDataEntry(int value, String keyName){
        log.setLogMessage("addDAtaEntryINT: keyName:"+keyName);
        if (!checkKeyNameIsUnique(keyName)){
            log.setLogMessage("addDataEntry INT: keyName not unique");
            return;
        }
        DataEntry dataEntry=new DataEntry(value,keyName);
        log.setLogMessage("addDAtaEntryBOO: keyName:"+keyName);
        dataEntries.add(dataEntry);
        counter++;
        log.setLogMessage("new Entry added: val("+value+")key("+keyName+")\n"+
                "counter("+counter+")dataEntries.size("+dataEntries.size()+")"
        );
    }
    public void addDataEntry(boolean value, String keyName){
        if (!checkKeyNameIsUnique(keyName)){
            log.setLogMessage("addDataEntry BOO: keyName not unique");
            return;
        }
        DataEntry dataEntry=new DataEntry(value,keyName);
        dataEntries.add(dataEntry);
        counter++;
        log.setLogMessage("new Entry added: val("+value+")key("+keyName+")\n"+
                "counter("+counter+")dataEntries.size("+dataEntries.size()+")"
                );
    }

    public String getEntryKeyNameAt(int id){
        if(id<dataEntries.size() && id >=0)
            return dataEntries.get(id).keyName;
        log.setLogMessage("getEntryKeyNameAt:id out of bounds id("+id+")");
        return "id out of bounds id("+id+")";
    }

    public String getEntryTypeAt(int id){
        if(id < dataEntries.size() && id >= 0)
            return dataEntries.get(id).variableType;
        log.setLogMessage("getEntryTyeAt:id out of bounds id("+id+")");
        return "id out of bounds id("+id+")";
    }

    public String getEntryTypeStringAt(int id){
        //if(id<dataEntries.size() && id >=0);

        if(dataEntries.size()>0 && id<dataEntries.size() && id>=0)
        {

        }else return "getEntryTypeStringAt(id=)"+Integer.toString(id)+"): out of bounds";

        if(dataEntries.get(id).valueStr.isEmpty()){
            return "getEntryTypeStringAt(id=)"+Integer.toString(id)+"): isEmpty";
        }
        return dataEntries.get(id).valueStr;
    }

    public int getEntryTypeIntAt(int id){
        return dataEntries.get(id).valueInt;
    }

    public boolean getEntryTypeBoolAt(int id){
        return dataEntries.get(id).valueBool;
    }

    public int getTotalEntries(){
        return counter;
    }
}
