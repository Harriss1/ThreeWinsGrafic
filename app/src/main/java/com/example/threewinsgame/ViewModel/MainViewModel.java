package com.example.threewinsgame.ViewModel;

import com.example.threewinsgame.Model.ThreeWins;

public class MainViewModel {

    public MainViewModel(){

    }

    private ThreeWins game = new ThreeWins();

    private DisplayableView viewContainer = new DisplayableView();


    public DisplayableView getViewContainer(){
        setDisplayableView();
        return viewContainer;
    }

    //set and map all the info from the model I want to display into the view here
    private void setDisplayableView(){
        viewContainer.x=game.getDataUpdates().x;
        viewContainer.y=game.getDataUpdates().y;
        viewContainer.userErrorHint=game.getDataUpdates().userErrorHint;
        viewContainer.userHint=game.getDataUpdates().userHint;
        viewContainer.fieldFilledWith=game.getDataUpdates().filledWith;
        viewContainer.debugOut=game.getDataUpdates().debugOut;
        viewContainer.setAllToEmpty=game.getDataUpdates().resetAllFields;
    }

    public void setInput(String identifier, String value){
        mapInput(identifier,value);
    }
    public void setReset(){
        game.resetGame();
    }

    private void mapInput(String identifier, String value){

        switch (identifier){
            //for readability the possible inputs get mapped to the model here

            default:
            {}
            break;

            //the case mapping allows me to asynchronously define inputs, for example define an
            //input from the view, but don't make any function neither in view model or model that
            //uses it
            //only problem is that String identifiers are prone to spelling errors!!! but I think
            //later on I will find a better solution
            case ("A1") :
                game.setMove(1,3);
                break;
            case "A2" :
                game.setMove(2,3);
                break;
            case "A3":
                game.setMove(3,3);
                break;

            case "B1":
                game.setMove(1,2);
                break;
            case "B2":
                game.setMove(2,2);
                break;
            case "B3":
                game.setMove(3,2);
                break;

            case "C1":
                game.setMove(1,1);
                break;
            case "C2":
                game.setMove(2,1);
                break;
            case "C3":
                game.setMove(3,1);
                break;
        }
    }
}
