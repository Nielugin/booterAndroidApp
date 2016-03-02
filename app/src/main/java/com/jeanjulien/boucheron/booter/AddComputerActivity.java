package com.jeanjulien.boucheron.booter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jeanjulien.boucheron.booter.controller.AppController;
import com.jeanjulien.boucheron.booter.model.Network;

/**
 * Created by JBOUCHER on 26/01/2016.
 */
public class AddComputerActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_computer);
    }

    public void saveComputer(View view){
        EditText computerNameField = (EditText)findViewById(R.id.computer_name);
        EditText macAddressField = (EditText)findViewById(R.id.computer_mac_address);
        EditText portField = (EditText)findViewById(R.id.computer_port);
        // TODO: get from dropdown selected element
        AppController appController = AppController.getInstance(getApplicationContext());
        Network network = appController.getNetworks().get(0);
        String computerName =       computerNameField.getText().toString();
        String computerMacAddress =    macAddressField.getText().toString();
        int computerPort =    Integer.parseInt(portField.getText().toString());

        appController.addComputer(computerName,computerMacAddress,computerPort,network);
        AddComputerActivity.this.finish();
    }
}
