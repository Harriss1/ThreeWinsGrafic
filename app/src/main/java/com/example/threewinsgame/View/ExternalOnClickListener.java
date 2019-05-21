package com.example.threewinsgame.View;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.threewinsgame.R;

public class ExternalOnClickListener implements View.OnClickListener {
    Context context;
    View v;

    public ExternalOnClickListener(Context context, View view){
        this.context = context;
        this.v = view;
    }

    public void test(){
        Button btn = (Button) v.findViewById(R.id.a2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hello I am inside Non Activity Class",Toast.LENGTH_LONG ).show();

            }
        });
    }
    ///*
    @Override public void onClick(View v){

    }
    //*/
}


//21.5.19 didnt get it to work...i can only alter texts but not include onclicklisteners...