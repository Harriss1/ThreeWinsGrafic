package com.example.threewinsgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.threewinsgame.Model.GeneralSaveContainer;
import com.example.threewinsgame.Model.Logging;
import com.example.threewinsgame.View.MainView;
import com.example.threewinsgame.Model.VersionControl;
import com.example.threewinsgame.ViewModel.SaveInstanceStateContainer;

import android.widget.ImageView;

public class MainActivity extends AppCompatActivity  {

    SaveInstanceStateContainer saveInstanceStateContainer;

    GeneralSaveContainer generalSaveContainer = new GeneralSaveContainer();

    TextView a1, a2, a3, b1, b2, b3, c1, c2, c3;

    boolean debugSwitch = true;
    int debugTemp1=0;
    String debugTemp1label="undefTemp1";
    int debugTemp2=0;
    String debugTemp2label="undefTemp2";
    Logging log = new Logging("MainActivity.java");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveInstanceStateContainer = new SaveInstanceStateContainer();

        //setContentView(R.layout.activity_main);

        //I want to use external classes to handle the view
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(v);

        MainView mainView = new MainView(MainActivity.this, v,MainActivity.this);
        mainView.process();


        // ---
        //testing of new image code; need to draw X or O after clicking onto the test button bottom left
        boolean imageTestMode= log.getDeveloperModeSetting();
        imageTestMode=false;

        final Button testImgBtn = (Button)findViewById(R.id.imgtestbtn);
        ImageView testImg = (ImageView)findViewById(R.id.imageView);

        if (!imageTestMode) {
            testImgBtn.setVisibility(View.GONE);
            testImg.setVisibility(View.GONE);
        }
        testImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView testImg = (ImageView)findViewById(R.id.imageView);
                String xlabelTag="xlabel";
                String olabelTag="olabel";
                boolean testMode = log.getDeveloperModeSetting();
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

            }
        });
        // --- end of image code testing

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
        generalSaveContainer.addDataEntry("key3", "test3");
        generalSaveContainer.addDataEntry("key2", "test2");
        generalSaveContainer.addDataEntry("key1", "test1");
        saveInstanceStateContainer.setSaveContainer(generalSaveContainer);

        saveInstanceStateContainer.saveDataEntryTest(generalSaveContainer,outState);
        saveInstanceStateContainer.saveDataEntryLoopTest(generalSaveContainer,outState);
        saveInstanceStateContainer.onSaveInstanceState(outState);
        log.setLogMessage("onSaveInstanceState called");


        //outState.put();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        saveInstanceStateContainer.onRestoreInstanceState(savedInstanceState);

        saveInstanceStateContainer.restoreDataEntryTest(savedInstanceState);
        String valStr = "";
        valStr += generalSaveContainer.getEntryTypeStringAt(0);
        String keyName = "";
        keyName += generalSaveContainer.getEntryKeyNameAt(0);
        log.setLogMessage("onRestoreInstanceSate: restored String? booth should NOT be empty: valStr("+valStr+") keyName("+keyName+")");
        log.setLogMessage("onRestoreInstanceState called");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        log.setLogMessage("onCreateOptionsMenu called");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        log.setLogMessage("onOptionsItemSelected called");


        if (id == R.id.action_about){
            VersionControl v = new VersionControl();
            String version = v.getVersionString();
            Toast.makeText(MainActivity.this, ("From Karl Klotz. Build version: "+version), Toast.LENGTH_LONG).show();
            log.setLogMessage("condition for R.id.action_about called");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
