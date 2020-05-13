package com.example.threewinsgame.Model;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class ThreeWins {

    private Logging log = new Logging("ThreeWins.java");

    private Grid gameGrid;

    private String userHint = "";
    private String userErrorHint = "";

    private String errorString="Error:";
    private String traceString="Trace:";

    private String player1Label = "X";
    private String player2Label = "O";
    private String playerAtTurn=player1Label;
    private String noLabel = " ";

    private class Grid{
        String errorString="";
        String traceString="";
        boolean isNew=true;

        class Pos {
            String label;
            int posX;
            int posY;
            boolean labelIsSet = false;
            String errorString="";
            String traceString="";
            Pos(int x, int y){
                posX = x;
                posY = y;
                label=noLabel;
            }
            boolean setField(String label) {
                boolean labelIsCorrect = false;
                if (label.equals(player1Label) || label.equals(player2Label))
                    labelIsCorrect = true;
                else
                    errorString += "Label for field " + posX + "," + posY + " is incorrect.";

                if (labelIsSet)
                    errorString += "Field " + posX + "," + posY + " already set to " + this.label;
                else if (labelIsCorrect) {
                    this.label = label;
                    labelIsSet=true;
                    traceString += "Field " + posX + "," + posY + " set to " + this.label;
                    return true;
                }
                return false;
            }
            void resetField(){
                labelIsSet=false;
                label=noLabel;
                errorString="";
                traceString+= "Field " + posX + "," + posY + " reset to " + this.label;
            }
        }

        ArrayList<Pos> grid;
        Pos lastModifiedField;

        Grid(){
            grid = new ArrayList<Pos>();
            for(int i=1;i<=3;i++){
                for (int j=1;j<=3;j++){
                Pos field = new Pos(i,j);
                grid.add(field);}
            }
            lastModifiedField = new Pos(0,0);
            traceString+=
                    "Grid initialized, contents:"
                            +getGridAsString() +
                            "lastModField(" +
                            lastModifiedField.posX + lastModifiedField.posY + lastModifiedField.label+
                            ")";
        }

        //code I tried to make a saveGame to work with, around march 2019.
        /*
        GameSaveContainer gameSaveContainer = new GameSaveContainer();


        class GameSaveContainer{

            ArrayList<SaveValue> saveValues;

            class SaveValue{
                private int id=0;
                private String keyName;

                private int valueInt;
                private String valueStr;
                private boolean valueBoo;

                public final String varTypeString="String";
                public final String varTypeInt="Integer";
                public final String varTypeBool="Boolean";
                public final String varTypeUndefined="undefined";

                private String variableType=varTypeUndefined;

                private boolean setValue(int id, String keyName){
                    boolean setupSuccess=false;
                    //ifthis.id==id-1

                    if(true){
                        this.id++;
                        this.keyName=keyName;
                        setupSuccess=true;
                    } else log.setLogMessage(
                            "SaveValue: id ("+Integer.toString(id)+ ") is not successor of last id("+Integer.toString(this.id)+")");

                    return setupSuccess;
                }
                SaveValue(int id, String keyName, int valueInt){
                    if (setValue(id, keyName)){
                        this.valueInt=valueInt;
                        this.variableType="Integer";
                    } else log.setLogMessage("SaveValue: Integer ("+Integer.toString(valueInt)+") not set");
                }

                SaveValue(int id, String keyName, String valueStr){
                    if (setValue(id, keyName)){
                        this.valueStr=valueStr;
                        this.variableType="String";
                    } else log.setLogMessage("SaveValue: String ("+valueStr+" not set");
                }

                SaveValue(int id, String keyName, boolean valueBoo){
                    if (setValue(id, keyName)){
                        this.valueBoo=valueBoo;
                        this.variableType="Boolean";
                    } else log.setLogMessage("SaveValue: Boolean ("+Boolean.toString(valueBoo)+") not set");
                }

            }

            public int toPositionValueAsInt(Pos pos){
                int posValueAsInt=10000; //VXXYY(value,xx,yy)

                if (!pos.labelIsSet){
                    posValueAsInt=10000; //label not set =1
                    posValueAsInt+=pos.posX*100+pos.posY;
                } else {
                    if(pos.label==player1Label) //player1Label=2; player2Label=3;
                        posValueAsInt=20000+pos.posX*100+pos.posY;
                    else
                        posValueAsInt=30000+pos.posX*100+pos.posY;
                }

                return posValueAsInt;
            }

            private boolean checkUniqueKeyName(String keyName){
                boolean isUnique = false;

                for(SaveValue element : saveValues){
                    if(keyName.equals(element.keyName)){
                        isUnique=true;
                    } else {
                        log.setLogMessage("checkUniqueKeyName(): keyName is not unique");
                    }
                }

                return isUnique;
            }

            private boolean addInteger(String keyName, int value){
                boolean success=false;
                SaveValue saveValue = new SaveValue(1, keyName, value);

                if(checkUniqueKeyName(keyName)){
                    saveValues.add(saveValue);
                    success = true;
                } else log.setLogMessage("addInteger(): nothing added");

                return success;
            }

            private int getInteger(String keyName){
                for(SaveValue element : saveValues){
                    if(element.keyName.equals(keyName)){
                        log.setLogMessage("getInteger: success ("+element.valueInt+")");
                        return element.valueInt;
                    }
                }
                log.setLogMessage("getInteger: keyName wrong");
                return 0;
            }

            public boolean setPosFromInteger(int containerInt){
                boolean setPositionSuccess=false;
                int labelidentifier=containerInt/10000;
                String label;
                if (labelidentifier==1)label=noLabel;
                else if (labelidentifier==2) label=player1Label;
                else if (labelidentifier==3) label=player2Label;
                else{
                    log.setLogMessage("setPosFromInteger: integer on the 5th digit from behind wrong(must be 1,2,3)");
                    return setPositionSuccess;
                }

                //VXXYY(value,xx,yy)
                int x=(containerInt-(containerInt/10000)*10000)/100;
                int stepForY=containerInt-(containerInt/10000)*10000;
                int y=stepForY-(stepForY/100)*100;

                if(x>=0 && x<=3 && y>=0 && y<=0) {
                    setLabel(x, y, label);
                    setPositionSuccess=true;
                } else log.setLogMessage("setPosFromInteger(): label cords invalid");

                return setPositionSuccess;
            }


            GameSaveContainer(){
                saveValues=new ArrayList<>();
            }

            private boolean saveGameState(){

                return false;
            }


            private boolean saveGridState(Grid grid){

                for(int x=1; x<=3;x++){
                    for (int y=1; y<=3; y++){
                        Pos pos = new Pos(x,y);
                        pos.setField(grid.getLabel(x,y));
                        SaveValue saveValue = new SaveValue(1,"gridPos"+x+y,toPositionValueAsInt(pos));
                        saveValues.add(saveValue);
                    }
                }

                //value of the positions
                //10102 = V XX YY  (value(1,2,3),x-cord,y-cord)
                //SaveValue savePos = new SaveValue(1,"Pos",10203);
                //saveValues.add(savePos);
                return false;
            }

            private boolean resumeGridState(){
                for (SaveValue element : saveValues){
                    setPosFromInteger(getInteger(element.keyName));
                }
                return false;
            }

            private boolean resumeGameFromOwnFormat(){
             resumeGridState();
             return false;
            }
        }
        */

        boolean setLabel(int x, int y, String label) {

            boolean success=false;
            for(Pos e:grid){
                if(e.posX ==x && e.posY==y){
                    if(!e.setField(label)) {
                        errorString+=e.errorString;
                        traceString+=e.traceString;
                        return false;
                    }
                    else {
                        traceString+=e.traceString;
                        lastModifiedField=e;
                        traceString+="SetLa("+label+")";
                        success=true;
                    }
                }
            }
            if (success){
                if (isNew) isNew=false;
                return true;
            }
            return false;
        }

        String getLabel(int x, int y) {
            String tempLabel="asfd";
            for(Pos e : grid){
                if(e.posX ==x && e.posY==y){
                    tempLabel=e.label;
                    traceString="LaLa("+e.label+")("+Integer.toString(e.posX)+Integer.toString(e.posY)+")";
                }
            }
            return tempLabel;
        }
        void resetGrid(){

            for (Pos e:grid){
                e.resetField();
                traceString+=e.traceString;
            }
            isNew=true;
        }

        String getGridAsString(){
            String wholeGrid = "grid: ";
                for(Pos e : grid){
                    String mx = Integer.toString(e.posX);
                    String my = Integer.toString(e.posY);
                    wholeGrid += "(" + mx + "," + my + ")=" + e.label;
                }

            return wholeGrid;
        }


    }

    public ThreeWins(){
        gameGrid = new Grid();
        traceString += gameGrid.traceString;
        errorString += gameGrid.errorString;
        log.setLogMessage("created");

        //Android lifecycle creates classes new in every case there was data loss,
        // so I only need to check for saved data at class creation
        checkSavedData();
        //i must have a saveGameState() function for this to work,
        // and need to implement it after setMove()

    }

    //Interface: DataUpdates is for listening to only
    public String getWholeGridString(){
        return gameGrid.getGridAsString();
    }

    public class DataUpdates{
        //I only give back the last field that has been altered by the User.
        //Or I set all fields to NULL at start and when I reset...
        //How do I reset the grid? - with "setalltozero"
        public int x,y;
        public boolean resetAllFields;
        public String filledWith;
        public String userHint;
        public String userErrorHint;
        public String debugOut;
        DataUpdates(int x, int y){
            resetAllFields=true;
            filledWith=noLabel;
            this.x = x;
            this.y = y;
            userHint="";
            userErrorHint="";
            debugOut = traceString + errorString;
        }
    }

    public DataUpdates getDataUpdates(){
        int lastX = gameGrid.lastModifiedField.posX;
        int lastY = gameGrid.lastModifiedField.posY;

        DataUpdates data = new DataUpdates(lastX, lastY);

        data.resetAllFields=gameGrid.isNew;
        data.filledWith=gameGrid.getLabel(lastX, lastY);
        String lastModifiedCoords=
                "lastCoords="
                        +Integer.toString(gameGrid.lastModifiedField.posX)
                        +Integer.toString(gameGrid.lastModifiedField.posY)
                +"-label="
                        +gameGrid.getLabel(lastX,lastY);
        data.userHint=this.userHint;
        data.userErrorHint=this.userErrorHint;
        data.debugOut += getWholeGridString()+lastModifiedCoords;
        return data;
    }

    public void setMove(int x, int y){
        //we are not making any move if the
        // game is won or the move is invalid, instead we get an ErrorHint.
        if (checkWin()){
            return;
        }

        if (checkOccupied(x,y)){
            return;
        }
        if (playerAtTurn.equals(player1Label)) {
            gameGrid.setLabel(x,y,player1Label);
        }
        else {
            gameGrid.setLabel(x,y,player2Label);

        }
        traceString+=gameGrid.traceString;
        errorString+=gameGrid.errorString;

        switchPlayer();
        checkWin();
        userHint="";

        saveGameState();
    }

    public void resetGame(){
        userHint="Game has been reset.";
        userErrorHint="";
        traceString+=gameGrid.traceString;
        gameGrid.resetGrid();
    }


    private void switchPlayer(){
        if (playerAtTurn.equals(player1Label)) playerAtTurn = player2Label;
        else playerAtTurn = player1Label;
    }

    private boolean checkWin(){
        boolean winCheck = false;

        for (int i=1; i<=3;i++){
            if(!winCheck) winCheck=checkRow(i);
        }
        for (int i=1;i<=3;i++){
            if(!winCheck) winCheck=checkHorizontal(i);
        }
        if(!winCheck) winCheck=checkVertical();

        if (winCheck){
            userErrorHint = "Game is won. "+checkWhoWins();
        } else userErrorHint = "";
        return winCheck;
    }

    private boolean checkOccupied(int x, int y){
        boolean isOccupied = false;

        if (!(gameGrid.getLabel(x,y).equals(noLabel))) {
            userErrorHint = "Field is already occupied.";
            isOccupied=true;
        } else userErrorHint ="";
        return isOccupied;
    }

    boolean checkHorizontal(int x){
        String testFor="";
        String win1=player1Label+player1Label+player1Label;
        String win2=player2Label+player2Label+player2Label;
        for (int i=1; i <= 3 ; i++){
            testFor+=gameGrid.getLabel(x,i);
        }
        if(testFor.equals(win1)||testFor.equals(win2))
            return true;
        return false;
    }
    boolean checkRow(int y){
        String testFor="";
        for (int i=1; i <= 3; i++){
            testFor+=gameGrid.getLabel(i,y);
        }
        if (testFor.equals(player1Label+player1Label+player1Label) || testFor.equals(player2Label+player2Label+player2Label)){
            traceString+="WWWWIIIIIN";
            return true;
        }
        traceString+="TS("+ testFor+")";
        return false;
    }
    boolean checkVertical(){
        String testForLR="";
        String testForRL="";
        String win1=player1Label+player1Label+player1Label;
        String win2=player2Label+player2Label+player2Label;
        for(int x=1;x<=3;x++){
            testForLR+=gameGrid.getLabel(x,x);
        }
        for(int x=3;x>=1;x--){
            testForRL+=gameGrid.getLabel(x,(4-x));
        }
        if (testForLR.equals(player1Label+player1Label+player1Label) || testForLR.equals(player2Label+player2Label+player2Label)){
            traceString+="WWWWIIIIIN";
            return true;
        }
        if (testForRL.equals(player1Label+player1Label+player1Label) || testForRL.equals(player2Label+player2Label+player2Label)){
            traceString+="WWWWIIIIIN";
            return true;
        }
        traceString+="Hor("+testForLR+")";

        return false;
    }

    private String checkWhoWins(){
        if (playerAtTurn.equals(player2Label))
        return "Player \""+player1Label+"\" is the winner.";
        return "Player \""+player2Label+"\" is the winner.";
    }

    private void checkSavedData(){

    }

    private void saveGameState(){
        //I use a container that stores all necessary data, and this container can be filled
        // with a function, this function get's called via checkSavedData...
        // if this function get's called the UI has to update though... ?

    }
}
