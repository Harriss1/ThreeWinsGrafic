package com.example.threewinsgame.ViewModel;

import android.app.Activity;
import android.os.Bundle;


import com.example.threewinsgame.MainActivity;
import com.example.threewinsgame.Model.GeneralSaveContainer;
import com.example.threewinsgame.Model.Logging;
import com.example.threewinsgame.Model.ThreeWins;

import java.util.ArrayList;

public class SaveRestoreInstanceStateHandler extends Activity {


    private Logging log;
    private GeneralSaveContainer generalSaveContainer;
    private static int keyIDcounter=0;
    private static int debugKeyIDcounter=0;

    public SaveRestoreInstanceStateHandler(GeneralSaveContainer generalSaveContainer){
        log = new Logging("SaveRestoreInstanceStateHandler.java");
        log.setLogMessage("created");

        this.generalSaveContainer = generalSaveContainer;
        //I get the variables to save from this container, and put them back into this container
        //IF I put variables back into the container I reset it before to prevent overflows, duplicates
        //other parts of the code can retrieve an already set variable from the general container, if it
        //already exists within it, if it doesn't these parts have to set them fresh theirself (unsecure?)

        //IF other parts of the code want to MAKE a variable that's supposed to be saved, they have to
        //use generalSaveContainer Code to create that variable->
        //int variable = generalSaveContainer.setDataOrRetrieveSaved(keyName, SavedValue, ValueSupposedForSaving);
        //  if "variable" is already saved, it will restore the saved value, if there was no save it will store the new value
        //  -> problem: what if we have an already initialised variable mid code? actually all code gets restored from start anyway?
        //CAN ONLY be used once within the code (check for duplicates!)

        //IF other parts of the code want to store a variable after changing it they have to refer to already
        //set variables from setDataOrRetrieveSaved -> changeData(key, value)
        //variable = changeData(key, value); //if invoked correctly changeData will return the value, if not it crashes the program
    }

    public void onSaveInstanceState(Bundle outState) {
        log.setLogMessage("onSaveInstanceState called");
        outState.putInt("key", 1234);
        saveDataEntries(outState);
    }




    //remove later, old way of retrieving data package for saving
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

    //can only be called from onSaveInstanceSate(Bundle outstate) in MainActivity.java:
    public void saveAndLockContainer(Bundle outState){

        //prevent wrong calls, eg. when there is nothing to save
        // or it's outside of onSaveInstanceState() from Mainactivity.java
        if(outState == null) {
            log.setLogMessage("saveContainerAndLock: outstate=NULL, nothing got saved");
            return;
        }

        String testKeyName1 = generalSaveContainer.getEntryKeyNameAt(0);
        String testTypeString1 = generalSaveContainer.getEntryTypeStringAt(0);
        log.setLogMessage("###\n####\n###################\n" +
                "saveContainerAndLock: " +
                "testKeyName1="+testKeyName1+
                "testTypeString1="+testTypeString1);
        //outState.putString(testKeyName1, testTypeString1);
        savePatternArrayEntryIntoOutState(outState, testKeyName1, testTypeString1);


    }

    private void savePatternArrayEntryIntoOutState(Bundle outState, String keyName, String value){
        //saves a String and it's KeyName into outState to retrieve it later on
        //I use the pattern:
        // keyName=KeyIDMax, value=Integer + keyName=KeyID0, value=#STR#KeyName + keyName=KeyName, value=value
        // keyID0, keyID1...keyID99 hold String Identifier Codes to figure out what type of value and with what
        // keyName the value got saved

        outState.putString(keyName, value);
        //String keyID="keyID0";
        String keyID="keyID"+Integer.toString(keyIDcounter);

        log.setLogMessage("###\n####\n###################\n" +
                "savePatternArrayEntryIntoOutState():" +
                "keyID="+keyID
        );
        log.setLogMessage("###\n####\n###################\n" +
                "##savePatternArrayEntryIntoOutState():" +
                "debugKeyIDcounter="+debugKeyIDcounter
        );
        debugKeyIDcounter++;
        outState.putInt("keyIDmax", keyIDcounter);
        keyIDcounter++;
        String keyIdentCode=labelTypeString+keyName; //pattern examples: #STR#TestValue, Int###NumberRadius, Bool#CheckResult
        outState.putString(keyID, keyIdentCode);


    }
    public void resetCounter(){
        //debugKeyIDcounter=0;
        keyIDcounter=0;
    }

    private final String labelTypeString="#STR#";
    private final String labelTypeInt="#INT#";
    private final String labelTypeBool="#BOO#";
    private final String labelTypeWrong="#ERR#";

    private final int labelLength=5;

    public void retrieveDataToContainer(Bundle savedInstanceState){
        resetCounter();
        String testString1 = "";
        testString1 = savedInstanceState.getString("testVar1String", "failed to retrieve testVar1String");
        generalSaveContainer.addDataEntry(testString1,"testVar1String");


        String typeAndKeyNamePattern = getTypeAndKeyNamePatternFromInstanceState(savedInstanceState,0);
        String typeTest2= parseKeyNamePatternToType(typeAndKeyNamePattern);
        String keyNameTest2=parseKeyNamePatternToKeyName(typeAndKeyNamePattern);
        String stringValueTest2="";
        if(typeTest2.equals(labelTypeString))
            log.setLogMessage("###\n####\n###################\n" +
                    "retrieveDataToContainer(): if-Statement if String: success" +
                    "keyNameTest2="+keyNameTest2
                    );
        //getStringFrom..() is still defective and faulty
            stringValueTest2= getStringFromInstanceState(savedInstanceState, keyNameTest2);

        String testString3 = savedInstanceState.getString(keyNameTest2);

        log.setLogMessage("###\n###\n###\n retrieveDataToContainer: stringValueTest2="+stringValueTest2);
        log.setLogMessage("###\n###\n###\n retrieveDataToContainer: testString1="+testString1);
        log.setLogMessage("###\n###\n###\n retrieveDataToContainer: testString3="+testString3);


        /*
        log.setLogMessage("###\n####\n###################\n" +
                "saveContainerAndLock: " +
                "testKeyName1="+testKeyName1+
                "testTypeString1="+testTypeString1);
        */

    }


    //One function gets type with supplying keyID integer, the other one gets the string
    //value with the keyName after figuring out at keyID is indeed a String.
    private String getTypeAndKeyNamePatternFromInstanceState(Bundle savedInstanceState, int keyID){

        //pattern examples: StringTestValue, Int###NumberRadius, Bool#CheckResult
        String typeAndKeyNamePattern = "";

        int keyIDmax = savedInstanceState.getInt("keyIDmax", 0);

        log.setLogMessage("###\n####\n###################\n" +
                "getTypeAndKeyNamePattern(): " +
                "keyIDMax="+keyIDmax+
                "typeAndKeyNamePattern="+typeAndKeyNamePattern);

        if(savedInstanceState != null)
            //typeAndKeyNamePattern = savedInstanceState.getString("keyID"+Integer.toString(keyID));
            typeAndKeyNamePattern = savedInstanceState.getString("keyID"+keyID);
        log.setLogMessage("###\n####\n###################\n" +
                "getTypeAndKeyNamePattern(): after getString(keyID0) " +
                "typeAndKeyNamePattern="+typeAndKeyNamePattern);

        if(typeAndKeyNamePattern.isEmpty()) {
            typeAndKeyNamePattern = "getTypeAndKeyNamePatternFromInstanceState(): isEmpty";
            log.setLogMessage("getTypeAndKeyNamePatternFromInstanceState(): isEmpty");
        }
        return typeAndKeyNamePattern;
    }

    private String parseKeyNamePatternToType(String keyNamePattern){
        if(keyNamePattern.startsWith(labelTypeString))
            return labelTypeString;
        if(keyNamePattern.startsWith(labelTypeInt))
            return labelTypeInt;
        if(keyNamePattern.startsWith(labelTypeBool))
            return labelTypeBool;

        log.setLogMessage("error in parseKeyNamePatternToType()");
        return "error in parseKeyNamePatternToType()";
    }

    private String parseKeyNamePatternToKeyName(String keyNamePattern){
        String keyName = keyNamePattern.substring(labelLength);
        log.setLogMessage("###\n####\n###################\n" +
                "parseKeyNamePatternToKeyName(): " +
                "keyName=\""+keyName+"\"");
        return keyName;
    }
    private String getStringFromInstanceState(Bundle savedInstanceState, String keyName){
        String valueString;
        valueString = savedInstanceState.getString(keyName,"failure in getStringFromInstanceState");
        log.setLogMessage("###\ngetStringFromInstanceState():"+
                "valueString=\""+valueString+"\"");
        return valueString;
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

        log.setLogMessage("###\n###\n put ONE String into stash, value("+valueStr+") keyName("+keyName+")");

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        log.setLogMessage("###\n###\n onRestoreInstanceState called");
        int savedInt;
        savedInt = savedInstanceState.getInt("key");
        log.setLogMessage("key=\"" + Integer.toString(savedInt) + "\"");
        restoreDataEntries(savedInstanceState);

    }

    public void restoreDataEntryTest(Bundle savedInstanceState){
        //this is to test if the value is retrieved correctly from the Android Storage
        //I read the value with the debug log
        log.setLogMessage("###\n###\n restoreDataEntryTest");
        int savedInt;
        savedInt = savedInstanceState.getInt("key");

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
