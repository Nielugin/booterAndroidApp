package com.jeanjulien.boucheron.booter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jeanjulien.boucheron.booter.controller.AppController;
import com.jeanjulien.boucheron.booter.model.Network;

import java.util.List;

/**
 * Adds a new network to the application
 */
public class AddNetworkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_network);
        displayNetworkTable();
    }

    /**
     * Displays the computer table.
     */
    private void displayNetworkTable() {
        // getting computer list
        AppController appController = AppController.getInstance(getBaseContext());
        List<Network> netList = appController.getNetworks();
        // gets the layout
        TableLayout tableLayout = (TableLayout) findViewById(R.id.network_table);
        // reinit view
        tableLayout.removeAllViews();
        // foreach computer
        for (final Network net : netList) {
            // a new row is created
            TableRow tableRow = createNetworkTableRow(net);
            // the row is added to the layout.
            tableLayout.addView(tableRow);
        }

    }

    private TableRow createNetworkTableRow(Network network) {
        TableRow tableRow = new TableRow(this);
        tableRow.setMinimumHeight(50);
        TextView netName = new TextView(this);
        netName.setText(network.getName());
        netName.setGravity(Gravity.CENTER_VERTICAL);
        netName.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));

        TextView netIpRange = new TextView(this);
        netIpRange.setText(network.getIpRange());
        netIpRange.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
        netIpRange.setGravity(Gravity.CENTER_VERTICAL);
        ImageButton delButt = initDeleteButton(network);
        tableRow.addView(netName);
        tableRow.addView(netIpRange);
        tableRow.addView(delButt);
        return tableRow;

    }

    private ImageButton initDeleteButton(final Network network) {
        ImageButton deleteButton = new ImageButton(this);
        deleteButton.setImageResource(R.drawable.trash);
        deleteButton.setMaxHeight(50);
        deleteButton.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.10f));
        deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AppController.getInstance(getBaseContext()).removeNetwork(network);
                displayNetworkTable();
                return true;
            }
        });
        return deleteButton;
    }


    /**
     * Save the filled parameters as a new network.
     *
     * @param view Form view.
     */
    public void saveNetwork(View view) {
        // Field retrieval
        EditText networkNameField = (EditText) findViewById(R.id.network_name);
        EditText ipRangeField = (EditText) findViewById(R.id.ip_range);


        // Data retrieval
        String networkName = networkNameField.getText().toString();
        String ipRange = ipRangeField.getText().toString();

        // adds the new network to the application
        AppController appController = AppController.getInstance(getApplicationContext());
        appController.addNetwork(networkName, ipRange);
        displayNetworkTable();
        // AddNetworkActivity.this.finish();
    }
}
