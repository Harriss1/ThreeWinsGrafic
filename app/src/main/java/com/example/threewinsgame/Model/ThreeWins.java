package com.example.threewinsgame.Model;
import java.util.ArrayList;

public class ThreeWins {


    private String userHint = "";
    private String userErrorHint = "";

    private int playerAtTurn=1;
    private String player1Label = "X";
    private String player2Label = "O";

    ArrayList<Grid> grid = new ArrayList<Grid>();

    Grid lastModifiedPoint = new Grid();

    private class Grid{
        public int x;
        public int y;
        public int filledWith;
        public Grid(){
            //0=empty, 1=player1, 2=player2
            filledWith=0;
        }
    }

    public class DataUpdates{
        public int x,y;
        public int filledWith;
        public String userHint;
        public String userErrorHint;
        public DataUpdates(int x, int y){
            filledWith=0;
            this.x=x;
            this.y=y;
            userHint="";
            userErrorHint="";
        }
    }

    public ThreeWins(){
        for (int x=1;x<=3;x++){
            for (int y=1; y<=3;y++){
                Grid g = new Grid();
                g.x=x;
                g.y=y;
                grid.add(g);
            }
        }
    }

    private void updateGridFill(int x, int y, int filledWith){
        for (Grid i: grid){
            if(i.x==x && i.y==y){
                i.filledWith=filledWith;
                lastModifiedPoint=i;
            }
        }
    }

    public void setMove(int x, int y){
        if (checkWin()){
            return;
        }

        if (checkOccupied(x,y)){
            return;
        }
        updateGridFill(x,y,playerAtTurn);
        switchPlayer();
        checkWin();
    }

    private void switchPlayer(){
        if (playerAtTurn==1) playerAtTurn = 2;
        else playerAtTurn = 1;
    }

    private boolean checkWin(){
        boolean winCheck = false;

        if (false){
            userErrorHint = "Can't set move if you won.";
        }

        for (Grid i : grid){

        }
        if(grid.get(0).filledWith==0){

        }
        return winCheck;
    }

    private boolean checkOccupied(int x, int y){
        boolean isOccupied = false;
        if (lastModifiedPoint.x == x && lastModifiedPoint.y == y) isOccupied =true;
        if (isOccupied){
            userErrorHint = "Field is already occupied.";
        }
        return isOccupied;
    }

    private void getUpdatedFields(){

    }

    private void getUpdatedHints(){

    }

    public DataUpdates getDataUpdates(){
        DataUpdates data = new DataUpdates(lastModifiedPoint.x, lastModifiedPoint.y);
        data.userHint=this.userHint;
        data.userErrorHint=this.userErrorHint;
        data.filledWith=lastModifiedPoint.filledWith;
        //int x, int y, playerLabel
        //hint (won?), errorHint (occupied)
        return data;
    }
}
