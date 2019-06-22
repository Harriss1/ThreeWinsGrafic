package com.example.threewinsgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.threewinsgame.Model.Logging;
import com.example.threewinsgame.View.MainView;
import com.example.threewinsgame.ViewModel.DisplayableGame;
import com.example.threewinsgame.Model.VersionControl;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity  {

    TextView a1, a2, a3, b1, b2, b3, c1, c2, c3;

    boolean debugSwitch = true;
    int debugTemp1=0;
    String debugTemp1label="undefTemp1";
    int debugTemp2=0;
    String debugTemp2label="undefTemp2";
    Logging logging = new Logging("MainActivity.java");








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //I want to use external classes to handle the view
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(v);

        MainView mainView = new MainView(MainActivity.this, v,MainActivity.this);
        mainView.process();


        //testing of new image code; need to draw X or O after clicking onto the test button bottom left
        final Button testImgBtn = (Button)findViewById(R.id.imgtestbtn);
        ImageView testImg = (ImageView)findViewById(R.id.imageView);
        boolean imageTestMode=logging.getDeveloperModeSetting();
        imageTestMode=false;
        if (!imageTestMode) {
            testImgBtn.setVisibility(View.GONE);
            testImg.setVisibility(View.GONE);
        }
        testImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String test = getResources().getResourceEntryName(R.drawable.btn_olabel);
                /*
                int testImgID1 = testImg.getId();
                int testImgID2 = getResources().getIdentifier("btn_xlabel", "drawable", getPackageName());

                if (testImgID2==testImgID1){
                    testImg.setImageResource(R.drawable.btn_xlabel);
                } else
                {
                    testImg.setImageResource(R.drawable.btn_olabel);
                }


                debugTemp1label="testImgId1=";
                debugTemp2label="testImgId2=";
                debugTemp1=testImgID1;
                debugTemp2=testImgID2;
                */
                ImageView testImg = (ImageView)findViewById(R.id.imageView);
                String xlabelTag="xlabel";
                String olabelTag="olabel";
                boolean testMode = logging.getDeveloperModeSetting();
                testMode=false;
                //testImg.setTag(xlabelTag);
                if (testImg.getTag().equals(xlabelTag)){
                    testImg.setTag(olabelTag);
                    testImg.setImageResource(R.drawable.btn_olabel);
                } else if (testImg.getTag().equals(olabelTag)) {
                    testImg.setTag(xlabelTag);
                    testImg.setImageResource(R.drawable.btn_xlabel);
                }
                if (!testMode)
                    testImg.setVisibility(View.GONE);

                /* deprecated code!
                if(testImg.getDrawable().getCurrent()==
                        (getResources().getDrawable(R.drawable.btn_xlabel)).getCurrent()){
                    testImg.setImageResource(R.drawable.btn_olabel);
                } else testImg.setImageResource(R.drawable.btn_xlabel);
                */
            }
        });


        a1 = (TextView)findViewById(R.id.a1);
       // a2 = (TextView)findViewById(R.id.a2);
        a3 = (TextView)findViewById(R.id.a3);

        b1 = (TextView)findViewById(R.id.b1);
        b2 = (TextView)findViewById(R.id.b2);
        b3 = (TextView)findViewById(R.id.b3);

        c1 = (TextView)findViewById(R.id.c1);
        c2 = (TextView)findViewById(R.id.c2);
        c3 = (TextView)findViewById(R.id.c3);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.put();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        logging.setLogMessage("onCreateOptionsMenu called");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        logging.setLogMessage("onOptionsItemSelected called");


        if (id == R.id.action_about){
            VersionControl v = new VersionControl();
            String version = v.getVersionString();
            Toast.makeText(MainActivity.this, ("From Karl Klotz. Build version: "+version), Toast.LENGTH_LONG).show();
            logging.setLogMessage("condition for R.id.action_about called");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void updateGameView(DisplayableGame gameView){

        /*
        if (debugSwitch) {
            TextView debug = (TextView) findViewById(R.id.debug);

            String text = "debug: ";
            text += debugTemp1label + String.valueOf(debugTemp1)+"\n";
            text += debugTemp2label + String.valueOf(debugTemp2)+"\n";

            debug.setText(text);
        }
        */
        updateField(gameView.x,gameView.y,gameView);

        TextView userHint = (TextView) findViewById(R.id.userHint);
        userHint.setText(gameView.userHint);
        TextView userErrorHint = (TextView) findViewById(R.id.userErrorHint);
        userErrorHint.setText(gameView.userErrorHint);
    }
    public void updateField(int x, int y, DisplayableGame gameView){
        if (!gameView.setAllToEmpty) {
            //a3.setText("test");
            //a3.setText(Integer.toString(gameView.x)+Integer.toString(gameView.y)+gameView.fieldFilledWith);
            if (x == 1 && y == 3) a1.setText(gameView.fieldFilledWith);
            //if (x == 2 && y == 3) a2.setText(gameView.fieldFilledWith);
            if (x == 3 && y == 3) a3.setText(gameView.fieldFilledWith);

            if (x == 1 && y == 2) b1.setText(gameView.fieldFilledWith);
            if (x == 2 && y == 2) b2.setText(gameView.fieldFilledWith);
            if (x == 3 && y == 2) b3.setText(gameView.fieldFilledWith);

            if (x == 1 && y == 1) c1.setText(gameView.fieldFilledWith);
            if (x == 2 && y == 1) c2.setText(gameView.fieldFilledWith);
            if (x == 3 && y == 1) c3.setText(gameView.fieldFilledWith);
        }else{
            String zeroText;//=gameView.fieldFilledWith;
            zeroText=" ";
            a1.setText(zeroText);
         //   a2.setText(zeroText);
            a3.setText(zeroText);

            b1.setText(zeroText);
            b2.setText(zeroText);
            b3.setText(zeroText);

            c1.setText(zeroText);
            c2.setText(zeroText);
            c3.setText(zeroText);
            //a2.setText("2t");
        }
    }
}
