package com.jeanjulien.boucheron.booter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jeanjulien.boucheron.booter.controller.AppController;

/**
 * Adds a new network to the application
 */
public class AddNetworkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_network);
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
        AddNetworkActivity.this.finish();
    }
}
