package com.example.threewinsgame.Model;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class ThreeWins {

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
    }

    //Interface
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
    }
    public void resetGame(){
        userHint="Game has been reset.";
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
            userErrorHint = "Game is won.";
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

    private void getUpdatedFields(){

    }

    private void getUpdatedHints(){

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
}
