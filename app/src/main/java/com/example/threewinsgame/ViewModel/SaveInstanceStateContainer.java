package com.example.threewinsgame.ViewModel;

import android.app.Activity;
import android.os.Bundle;


import com.example.threewinsgame.MainActivity;
import com.example.threewinsgame.Model.GeneralSaveContainer;
import com.example.threewinsgame.Model.Logging;
import com.example.threewinsgame.Model.ThreeWins;

import java.util.ArrayList;

public class SaveInstanceStateContainer extends Activity {


    private Logging log;
    private GeneralSaveContainer generalSaveContainer = new GeneralSaveContainer();

    public SaveInstanceStateContainer(){
        log = new Logging("SaveInstanceStateContainer.java");
        log.setLogMessage("created");
    }

    public void onSaveInstanceState(Bundle outState) {
        log.setLogMessage("onSaveInstanceState called");
        outState.putInt("key", 1234);
        saveDataEntries(outState);
    }


    public void onRestoreInstanceState(Bundle savedInstanceState) {
        log.setLogMessage("onRestoreInstanceState called");
        int savedInt;
        savedInt = savedInstanceState.getInt("key");
        log.setLogMessage("key=\"" + Integer.toString(savedInt) + "\"");
        restoreDataEntries(savedInstanceState);

    }

    public void setSaveContainer(GeneralSaveContainer generalSaveContainer){
        for(int i=0; i<generalSaveContainer.getTotalEntries();i++){
            String str;
            int value;
            boolean b;
            String entryType=generalSaveContainer.getEntryTypeAt(i);

            if(!entryType.equals(this.generalSaveContainer.typeUndef)){
              if(entryType.equals(this.generalSaveContainer.typeBool)){
                  this.generalSaveContainer.addDataEntry(
                          generalSaveContainer.getEntryTypeBoolAt(i),
                          generalSaveContainer.getEntryKeyNameAt(i));
              } else if (entryType.equals(this.generalSaveContainer.typeInt)){
                  this.generalSaveContainer.addDataEntry(
                          generalSaveContainer.getEntryTypeIntAt(i),
                          generalSaveContainer.getEntryKeyNameAt(i)
                          );
              } else if (entryType.equals(this.generalSaveContainer.typeStr)){
                  this.generalSaveContainer.addDataEntry(
                          generalSaveContainer.getEntryTypeStringAt(i),
                          generalSaveContainer.getEntryKeyNameAt(i)
                  );
              }
            }
        }
    }

    private ArrayList<String> keyNames = new ArrayList<>();
    private int keyNameCounter = 0;
    private void setKeyName(String keyName){
        boolean valid=true;
        for(String element : keyNames){
            if(element.equals(keyName)) valid=false;
            log.setLogMessage("setKeyName didnt got a unique keyName!");
        }
        if(valid) {
            keyNames.add(keyName);
            keyNameCounter++;
            log.setLogMessage("setKeyname=("+keyName+")");
        }
    }
    private int getKeyNameCounter(){
        return keyNameCounter;
    }

    public void saveDataEntryLoopTest(GeneralSaveContainer generalSaveContainer, Bundle outState){

        String valueStr = generalSaveContainer.getEntryTypeStringAt(0);
        String keyName=generalSaveContainer.getEntryKeyNameAt(0);

        outState.putString(keyName, valueStr);

        log.setLogMessage("put ONE String into stash, value("+valueStr+") keyName("+keyName+")");

        //start experimental loop
        int maxCount = generalSaveContainer.getTotalEntries();

        for (int i=0; i < maxCount; i++){
            String entryType = generalSaveContainer.getEntryTypeAt(i);
            final String typStr=generalSaveContainer.typeStr;
            final String typInt=generalSaveContainer.typeInt;
            final String typBoo=generalSaveContainer.typeBool;
            final String typUnd=generalSaveContainer.typeUndef;
            if(entryType.equals(typUnd)){
                log.setLogMessage("saveDateEntryLoopTest: entry type undefined at index="+i);
            } else {
                if(entryType.equals(typStr)){
                    saveVariableDefinitionToList(generalSaveContainer.getEntryKeyNameAt(i),typStr,outState);
                } else if(entryType.equals(typInt)){

                } else if(entryType.equals(typBoo)){

                }
            }
        }

    }

    private final String keyNameMaxCount = "KeyNamesStoredMaxCountInteger";

    private void saveVariableDefinitionToList(String keyName, String entryType, Bundle outState){

        log.setLogMessage("###\nsaveVariableDefinitionToList(): keyName="+keyName+", type="+entryType);

        //because we set unique key names for each variable and only increase the max count if the
        //key name was tested for its uniqueness, we don't have to worry about multiple function calls

        //format: KeyId1 STRINGKeyName -> KeyName will always be unique


        if(!generalSaveContainer.checkKeyNameIsUnique(keyName)){
                //we start at MaxCount=1
                int maxCountInSaveInstance = outState.getInt(keyNameMaxCount);
                log.setLogMessage("saveVariableDefinitionToList: maxCountInSaveInstance="+maxCountInSaveInstance);
                if(maxCountInSaveInstance<=0)maxCountInSaveInstance++;

                String keyNameIndex=("##KeyIndex"+maxCountInSaveInstance);
                String keyNameLabelWithType=changeKeyNameToKeyNameWithVarTypeLabel(keyName,entryType);
                outState.putString(keyNameIndex,keyNameLabelWithType);
                outState.putInt(keyNameMaxCount,maxCountInSaveInstance);

            log.setLogMessage("saveVariableDefinitionToList: keyName("+keyNameIndex+")value("+keyNameLabelWithType+")");
            log.setLogMessage("saveVariableDefinitionToList: maxCountInSaveInstance("+maxCountInSaveInstance+")outState->keyNameMaxCount("+outState.getInt(keyNameMaxCount)+")");
            }

    }

    private final String labelTypeString="#STR#";
    private final String labelTypeInt="#INT#";
    private final String labelTypeBool="#BOO#";
    private final String labelTypeWrong="#ERR#";
    private final int labelLength=5;
    private String changeKeyNameToKeyNameWithVarTypeLabel(String keyName, String entryType){
        //at each String's start five letters will be added to identify the variable type

        if (entryType.equals(generalSaveContainer.typeStr))
            return labelTypeString+keyName;
        else if (entryType.equals(generalSaveContainer.typeInt))
            return labelTypeInt+keyName;
        else if (entryType.equals(generalSaveContainer.typeBool))
            return labelTypeBool+keyName;
        log.setLogMessage("changeKeyNameWithVarTypeLabel: EntryType wrong or undefined");
        return labelTypeWrong+keyName;
    }

    private String labelledKeyNameToLabelType(String labelledKeyName){
        if(labelledKeyName.startsWith(labelTypeWrong)){
            log.setLogMessage("labelKeyNameToLabelType(): labelTypeWrong with("+labelledKeyName+")");
        } else
        {
            if(labelledKeyName.startsWith(labelTypeString))
                return labelTypeString;
            if(labelledKeyName.startsWith(labelTypeInt))
                return labelTypeInt;
            if(labelledKeyName.startsWith(labelTypeBool))
                return labelTypeBool;
        }
        log.setLogMessage("labelKeyNameToLabelType: Found key with wrong format("+labelledKeyName+")");
        return labelTypeWrong;
    }

    private String labelledKeyNameToKeyName(String labelledKeyName){
        String keyName = labelledKeyName.substring(labelLength);
        return keyName;
    }

    private boolean checkIfLabelledKeyNameValid(String labelledKeyName){
        return true;
    }

    public void saveDataEntryTest(GeneralSaveContainer generalSaveContainer, Bundle outState){

        String valueStr = generalSaveContainer.getEntryTypeStringAt(0);
        String keyName=generalSaveContainer.getEntryKeyNameAt(0);

        outState.putString(keyName, valueStr);

        log.setLogMessage("put ONE String into stash, value("+valueStr+") keyName("+keyName+")");

    }

    public void restoreDataEntryTest(Bundle savedInstanceState){
        //generalSaveContainer.addDataEntry(savedInstanceState.getString("test1"), "test1");
    }

    private void saveDataEntries(Bundle outState){
        outState.putInt("TotalCount", generalSaveContainer.getTotalEntries());


        for(int i=0; i<=generalSaveContainer.getTotalEntries();i++){
            String entryType=generalSaveContainer.getEntryTypeAt(i);
            if(entryType.equals(generalSaveContainer.typeStr)){
                outState.putString(generalSaveContainer.getEntryKeyNameAt(i), generalSaveContainer.getEntryTypeStringAt(i));
                setKeyName(generalSaveContainer.getEntryKeyNameAt(i));
            } else if (entryType.equals(generalSaveContainer.typeInt)){
                outState.putInt(generalSaveContainer.getEntryKeyNameAt(i), generalSaveContainer.getEntryTypeIntAt(i));
                setKeyName(generalSaveContainer.getEntryKeyNameAt(i));
            } else if (entryType.equals(generalSaveContainer.typeBool)){
                outState.putBoolean(generalSaveContainer.getEntryKeyNameAt(i), generalSaveContainer.getEntryTypeBoolAt(i));
                setKeyName(generalSaveContainer.getEntryKeyNameAt(i));
            } else log.setLogMessage("saveDataEntries: type not set at id("+i+")");
        }


    }

    private void restoreDataEntries(Bundle savedInstanceState){
        int savedInt;
        savedInt=savedInstanceState.getInt("key");

        if(savedInstanceState.getString("nulllahah")==null){
            log.setLogMessage("worked");
        }
    }


    public void setDataEntry(int data){

    }

    public void setDataEntry(int data, String name, String source){

    }

    public boolean setDataEntry(int id, int data){
        //only use this in combination with an array list with id, name and type.
        //why not an array with id as return value?

        boolean idIsFreeAndSubsequential = false;

        return idIsFreeAndSubsequential;
    }

    public boolean setDataEntry(int id, String data){
        return false;
    }

    public int getDataEntry(int id){
        return 0;
    }

}
