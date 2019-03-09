package com.example.threewinsgame.ViewModel;
import com.example.threewinsgame.Model.ThreeWins;
import com.example.threewinsgame.ViewModel.DisplayableGame;

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
        if(game.getDataUpdates().filledWith==0){
            viewUpdate.fieldFilledWith="";

        } else {
            if (game.getDataUpdates().filledWith==1) viewUpdate.fieldFilledWith="X";
            else viewUpdate.fieldFilledWith="O";
        }

        return viewUpdate;
    }

}
