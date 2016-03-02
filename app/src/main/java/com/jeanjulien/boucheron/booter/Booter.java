package com.jeanjulien.boucheron.booter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.jeanjulien.boucheron.booter.controller.AppController;
import com.jeanjulien.boucheron.booter.model.Computer;
import com.jeanjulien.boucheron.booter.model.Network;

import java.util.List;

public class Booter extends AppCompatActivity {


    class AssyncBooter extends AsyncTask<Computer, Integer,Boolean> {
        @Override
        protected Boolean doInBackground(Computer... params) {
            params[0].boot();
            return true;
        }
    }

    public void addComputer(View view){
        Intent intent = new Intent(this, AddComputerActivity.class);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayComputerTable();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        displayComputerTable();

    }

    private void displayComputerTable(){

        AppController  appController = AppController.getInstance(getBaseContext());
        List<Computer> computerList = appController.getComputers();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.computer_table);
        tableLayout.removeAllViews();
        for (final Computer computer : computerList ) {
            TableRow tableRow =  new TableRow(this);
            tableRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
            TextView computerName =  new TextView(this);
            computerName.setGravity(Gravity.CENTER_VERTICAL);
            computerName.setText(computer.getName());
            computerName.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.30f));
            TextView computerMacAddress =  new TextView(this);
            computerMacAddress.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
            computerMacAddress.setGravity(Gravity.CENTER_VERTICAL);

            computerMacAddress.setText(computer.getMacAddress());
            ImageButton bootButton = initBootButton(computer);
            ImageButton deleteButton = initDeleteButton(computer);


            tableRow.addView(computerName);
            tableRow.addView(computerMacAddress);
            tableRow.addView(bootButton);
            tableRow.addView(deleteButton);
            tableLayout.addView(tableRow);
        }

    }

    @NonNull
    private ImageButton initBootButton(final Computer computer) {
        ImageButton bootButton  = new ImageButton(this);
        bootButton.setImageResource(R.drawable.boot);
        bootButton.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.10f));
        bootButton.setMaxHeight(50);
        bootButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                (new AssyncBooter()).execute(computer);
                return true;
            }
        });
        return bootButton;
    }

    @NonNull
    private ImageButton initDeleteButton(final Computer computer) {
        ImageButton deleteButton = new ImageButton(this);
        deleteButton.setImageResource(R.drawable.trash);
        deleteButton.setMaxHeight(50);
        deleteButton.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.10f));
        deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AppController.getInstance(getBaseContext()).removeComputer(computer);
                displayComputerTable();
                return true;
            }
        });
        return deleteButton;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_booter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
