package com.example.threewinsgame.ViewModel;
import com.example.threewinsgame.Model.VersionControl;

public class VersionControlVM extends ViewModel{
    VersionControl version = new VersionControl();

    public VersionControlVM(){

    }

    public String getVersionString(){
        return version.getVersionString();
    }
}
