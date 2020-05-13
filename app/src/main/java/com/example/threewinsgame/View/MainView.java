package com.example.threewinsgame.View;


import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.threewinsgame.MainActivity;
import com.example.threewinsgame.R;
import com.example.threewinsgame.ViewModel.DisplayableView;
import com.example.threewinsgame.ViewModel.MainViewModel;
import com.example.threewinsgame.Model.Logging;



public class MainView implements View.OnClickListener      {

    Context context;
    View view;
    MainActivity activity;
    String labelTypeX="xLabel";
    String labelTypeO ="oLabel";
    Logging log;
    boolean showDebugMessageLogInUI;

    helperForOptionsMenu helpOM;

    MainViewModel mainViewModel  = new MainViewModel();;

    public MainView(Context context, View view, MainActivity activity){
        helpOM=new helperForOptionsMenu();
        this.showDebugMessageLogInUI=helpOM.showDebugMessageLogInUI;
        this.activity=activity;
        this.context=context;
        this.view=view;
        log = new Logging("MainView.java");
        log.setLogMessage("constructed");


        //setLogOrigin("MainView.java");
        //setLogMessage("constructed");
    }

    @Override public void onClick(View view){
        log.setLogMessage("onClick()");
    }

    public void process(){
        handleInputs();
        //output or update of view
        handleViewUpdate();

    }

    private void handleInputs(){
        //function for button grid
        handleButtonGridInput();
        //functions for reset and additional tasks like pressed toolbar or testing buttons and so on
        handleToolbarInput();
        handleResetButtonInput();

    }

    private void handleViewUpdate(){
        updateViewParts(mainViewModel.getViewContainer());
    }

    private void updateViewParts(DisplayableView displayableView){
        drawGameGrid(displayableView);
        drawTexts(displayableView);


        activity.invalidateOptionsMenu();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        (activity).setSupportActionBar(toolbar);
        //toolbar.setTitle("haitidai");
        //log.setLogMessage("haitidai");
    }

    private void drawTexts(DisplayableView displayableView){
        TextView userHint = (TextView) view.findViewById(R.id.userHint);
        TextView userErrorHint = (TextView) view.findViewById(R.id.userErrorHint);

        userHint.setText(displayableView.userHint);
        userErrorHint.setText(displayableView.userErrorHint);

        TextView debug = (TextView) view.findViewById(R.id.debug);

        debug.setMovementMethod(new ScrollingMovementMethod());
        if(helpOM.showDebugMessageLogInUI) {
            if (log.getDeveloperModeSetting())
                debug.setText(log.getGlobalLogText() + displayableView.debugOut);
        }
    }

    private void drawGameGrid(DisplayableView displayableView){
        String labelType= labelTypeO;
        if (displayableView.fieldFilledWith.equals("X"))labelType=labelTypeX;
        else if (displayableView.fieldFilledWith.equals("Y"))labelType= labelTypeO;

        if(displayableView.setAllToEmpty!=true){
            drawLabel(displayableView.x,displayableView.y,labelType);
        } else {
            resetGameGridView();
        }
    }

    private void drawLabel(int positionX, int positionY, String labelType){

        int x=0;
        int y=0;
        if (positionX<=3&&positionX>=1) x=positionX;
        if (positionY<=3&&positionY>=1) y=positionY;

        ImageView imgA1 = (ImageView)view.findViewById(R.id.gridImgAreaA1);
        ImageView imgA2 = (ImageView)view.findViewById(R.id.gridImgAreaA2);
        ImageView imgA3 = (ImageView)view.findViewById(R.id.gridImgAreaA3);

        ImageView imgB1 = (ImageView)view.findViewById(R.id.gridImgAreaB1);
        ImageView imgB2 = (ImageView)view.findViewById(R.id.gridImgAreaB2);
        ImageView imgB3 = (ImageView)view.findViewById(R.id.gridImgAreaB3);

        ImageView imgC1 = (ImageView)view.findViewById(R.id.gridImgAreaC1);
        ImageView imgC2 = (ImageView)view.findViewById(R.id.gridImgAreaC2);
        ImageView imgC3 = (ImageView)view.findViewById(R.id.gridImgAreaC3);

        if(labelType==labelTypeX) {
            if (y == 3 && x == 1) imgA1.setImageResource(R.drawable.btn_xlabel);
            if (y == 3 && x == 2) imgA2.setImageResource(R.drawable.btn_xlabel);
            if (y == 3 && x == 3) imgA3.setImageResource(R.drawable.btn_xlabel);

            if (y == 2 && x == 1) imgB1.setImageResource(R.drawable.btn_xlabel);
            if (y == 2 && x == 2) imgB2.setImageResource(R.drawable.btn_xlabel);
            if (y == 2 && x == 3) imgB3.setImageResource(R.drawable.btn_xlabel);

            if (y == 1 && x == 1) imgC1.setImageResource(R.drawable.btn_xlabel);
            if (y == 1 && x == 2) imgC2.setImageResource(R.drawable.btn_xlabel);
            if (y == 1 && x == 3) imgC3.setImageResource(R.drawable.btn_xlabel);

        } else if (labelType== labelTypeO){
            if (y == 3 && x == 1) imgA1.setImageResource(R.drawable.btn_olabel);
            if (y == 3 && x == 2) imgA2.setImageResource(R.drawable.btn_olabel);
            if (y == 3 && x == 3) imgA3.setImageResource(R.drawable.btn_olabel);

            if (y == 2 && x == 1) imgB1.setImageResource(R.drawable.btn_olabel);
            if (y == 2 && x == 2) imgB2.setImageResource(R.drawable.btn_olabel);
            if (y == 2 && x == 3) imgB3.setImageResource(R.drawable.btn_olabel);

            if (y == 1 && x == 1) imgC1.setImageResource(R.drawable.btn_olabel);
            if (y == 1 && x == 2) imgC2.setImageResource(R.drawable.btn_olabel);
            if (y == 1 && x == 3) imgC3.setImageResource(R.drawable.btn_olabel);
        } else {
            //wrong label type used or wrong coordinate
        }
    }

    private void resetGameGridView(){
        ImageView imgA1 = (ImageView)view.findViewById(R.id.gridImgAreaA1);
        ImageView imgA2 = (ImageView)view.findViewById(R.id.gridImgAreaA2);
        ImageView imgA3 = (ImageView)view.findViewById(R.id.gridImgAreaA3);

        ImageView imgB1 = (ImageView)view.findViewById(R.id.gridImgAreaB1);
        ImageView imgB2 = (ImageView)view.findViewById(R.id.gridImgAreaB2);
        ImageView imgB3 = (ImageView)view.findViewById(R.id.gridImgAreaB3);

        ImageView imgC1 = (ImageView)view.findViewById(R.id.gridImgAreaC1);
        ImageView imgC2 = (ImageView)view.findViewById(R.id.gridImgAreaC2);
        ImageView imgC3 = (ImageView)view.findViewById(R.id.gridImgAreaC3);

        imgA1.setImageResource(android.R.color.transparent);
        imgA2.setImageResource(android.R.color.transparent);
        imgA3.setImageResource(android.R.color.transparent);

        imgB1.setImageResource(android.R.color.transparent);
        imgB2.setImageResource(android.R.color.transparent);
        imgB3.setImageResource(android.R.color.transparent);

        imgC1.setImageResource(android.R.color.transparent);
        imgC2.setImageResource(android.R.color.transparent);
        imgC3.setImageResource(android.R.color.transparent);
    }

    private void handleButtonGridInput(){

        //for readability all buttons are mapped first and then given actions
        Button btA1 = (Button) view.findViewById(R.id.a1);
        Button btA2 = (Button) view.findViewById(R.id.a2);
        Button btA3 = (Button) view.findViewById(R.id.a3);

        Button btB1 = (Button) view.findViewById(R.id.b1);
        Button btB2 = (Button) view.findViewById(R.id.b2);
        Button btB3 = (Button) view.findViewById(R.id.b3);

        Button btC1 = (Button) view.findViewById(R.id.c1);
        Button btC2 = (Button) view.findViewById(R.id.c2);
        Button btC3 = (Button) view.findViewById(R.id.c3);


        btA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("A1", "");
                handleViewUpdate();
            }
        });
        btA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("A2", "");
                handleViewUpdate();
                //if (log.getDeveloperModeSetting())
                //    Toast.makeText(context, "Hello I am inside MainView",Toast.LENGTH_LONG ).show();
            }
        });
        btA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("A3", "");
                handleViewUpdate();
            }
        });

        btB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("B1", "");
                handleViewUpdate();
            }
        });
        btB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("B2", "");
                handleViewUpdate();
            }
        });
        btB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("B3", "");
                handleViewUpdate();
            }
        });

        btC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("C1", "");
                handleViewUpdate();
            }
        });
        btC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("C2", "");
                handleViewUpdate();
            }
        });
        btC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setInput("C3", "");
                handleViewUpdate();
            }
        });



    }

    private void handleResetButtonInput(){
        final Button reset = (Button) view.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setReset();
                handleViewUpdate();
            }
        });
    }

    private void handleDebugButtonInput(){

    }

    private void handleToolbarInput(){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        log.setLogMessage("handleToobarInput()");


    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Your custom title");

    }*/


}
