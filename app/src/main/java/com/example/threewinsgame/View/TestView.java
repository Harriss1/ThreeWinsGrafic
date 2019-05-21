package com.example.threewinsgame.View;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.example.threewinsgame.R;

public class TestView {
    Context context;

    public TestView(Context context){
        this.context = context;
    }

    public void Update(){
        TextView testText = (TextView) ((Activity)context).findViewById(R.id.debug);
        testText.setText("testviewclass");
    }



}
