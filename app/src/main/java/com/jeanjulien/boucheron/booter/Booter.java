package com.jeanjulien.boucheron.booter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jeanjulien.boucheron.booter.controller.AppController;
import com.jeanjulien.boucheron.booter.model.Computer;

import java.util.List;

public class Booter extends AppCompatActivity {


    class AssyncBooter extends AsyncTask<Computer, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Computer... params) {
            params[0].boot();
            return true;
        }
    }

    public void addComputer(View view) {
        Intent intent = new Intent(this, AddComputerActivity.class);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                toastAction("Add computer finished size : " + AppController.getInstance(getApplicationContext()).getComputers().size());
                break;
            case 2:
                toastAction("Add network finished size : " + AppController.getInstance(getApplicationContext()).getNetworks().size());
                break;
            default:
                break;
        }
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

    /**
     * Displays the computer table.
     */
    private void displayComputerTable() {
        // getting computer list
        AppController appController = AppController.getInstance(getBaseContext());
        List<Computer> computerList = appController.getComputers();
        // gets the layout
        TableLayout tableLayout = (TableLayout) findViewById(R.id.computer_table);
        // reinit view
        tableLayout.removeAllViews();
        // foreach computer
        for (final Computer computer : computerList) {
            // a new row is created
            TableRow tableRow = createComputerTableRow(computer);
            // the row is added to the layout.
            tableLayout.addView(tableRow);
        }

    }

    @NonNull
    private TableRow createComputerTableRow(Computer computer) {
        // creates the row
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));

        // init row components
        TextView computerName = initComputerNameTextView(computer);
        TextView computerMacAddress = getMacAddressTextView(computer);
        ImageButton bootButton = initBootButton(computer);
        ImageButton deleteButton = initDeleteButton(computer);

        // adds components to the row
        tableRow.addView(computerName);
        tableRow.addView(computerMacAddress);
        tableRow.addView(bootButton);
        tableRow.addView(deleteButton);
        return tableRow;
    }

    @NonNull
    /**
     * Init the mac address text field
     */
    private TextView getMacAddressTextView(Computer computer) {
        TextView computerMacAddress = new TextView(this);
        computerMacAddress.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
        computerMacAddress.setGravity(Gravity.CENTER_VERTICAL);
        computerMacAddress.setText(computer.getMacAddress());
        return computerMacAddress;
    }

    @NonNull
    /**
     * Init the computer name text field
     */
    private TextView initComputerNameTextView(Computer computer) {
        TextView computerName = new TextView(this);
        computerName.setGravity(Gravity.CENTER_VERTICAL);
        computerName.setText(computer.getName());
        computerName.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.30f));
        return computerName;
    }

    @NonNull
    /**
     * Init the boot button
     */
    private ImageButton initBootButton(final Computer computer) {
        ImageButton bootButton = new ImageButton(this);
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
    /**
     * Init the delete button
     */
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
        int id = item.getItemId();
        CharSequence text;
        switch (id) {
            case R.id.add_network:
                Intent intent = new Intent(this, AddNetworkActivity.class);
                startActivityForResult(intent, 2);
                text = "add net!";
                toastAction(text);
                break;
            case R.id.change_network:
                text = "change_network!";
                toastAction(text);
                break;
            default:
                text = "default!";
                toastAction(text);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toastAction(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
