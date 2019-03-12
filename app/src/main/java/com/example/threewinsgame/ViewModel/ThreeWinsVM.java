package com.example.threewinsgame.ViewModel;
import com.example.threewinsgame.Model.ThreeWins;

public class ThreeWinsVM extends ViewModel {

    ThreeWins game = new ThreeWins();


    public ThreeWinsVM(){
        super();
    }
    public void setMove(int x, int y){
        game.setMove(x,y);
    }
    public DisplayableGame getGameView(){
        DisplayableGame viewUpdate = new DisplayableGame();

        viewUpdate.x=game.getDataUpdates().x;
        viewUpdate.y=game.getDataUpdates().y;
        viewUpdate.userErrorHint=game.getDataUpdates().userErrorHint;
        viewUpdate.userHint=game.getDataUpdates().userHint;
        viewUpdate.fieldFilledWith=game.getDataUpdates().filledWith;
        viewUpdate.debugOut=game.getDataUpdates().debugOut;
        viewUpdate.setAllToEmpty=game.getDataUpdates().resetAllFields;

        return viewUpdate;
    }
    public void setReset(){
        game.resetGame();
    }

}
