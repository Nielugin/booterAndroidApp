package com.jeanjulien.boucheron.booter.model.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.jeanjulien.boucheron.booter.model.Computer;
import com.jeanjulien.boucheron.booter.model.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to ease computer loading
 */
public class ComputerLoader {
    public static List<Computer> loadComputers(Context context, Network network) {
        List<Computer> computers = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        dataBaseHelper.onCreate(db);
        String whereFilter = ComputerEntry.COLUMN_NAME_FK_NETWORK + "=?";
        String[] whereClauseValue = {String.valueOf(network.getId())};
        Cursor dataList = dataBaseHelper.getDataList(ComputerEntry.TABLE_NAME, ComputerEntry.getColumns(), whereFilter, whereClauseValue);
        while (dataList.moveToNext()) {
            long computerId = dataList.getLong(dataList.getColumnIndexOrThrow(ComputerEntry.COLUMN_NAME_COMPUTER_ID));
            String computerName = dataList.getString(dataList.getColumnIndexOrThrow(ComputerEntry.COLUMN_NAME_NAME));
            String computerMacAddress = dataList.getString(dataList.getColumnIndexOrThrow(ComputerEntry.COLUMN_NAME_MAC_ADDRESS));
            int computerPort = dataList.getInt(dataList.getColumnIndexOrThrow(ComputerEntry.COLUMN_NAME_PORT));
            computers.add(new Computer(computerId, computerName, computerMacAddress, computerPort, network));
        }

        return computers;
    }

    public static void save(Context context, Computer computer) {
        // Gets the data repository in write mode
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        long computerId = computer.getId();
        if (computerId != -1) {
            values.put(ComputerEntry.COLUMN_NAME_COMPUTER_ID, computer.getId());
        }
        values.put(ComputerEntry.COLUMN_NAME_FK_NETWORK, computer.getNetwork().getId());
        values.put(ComputerEntry.COLUMN_NAME_MAC_ADDRESS, computer.getMacAddress());
        values.put(ComputerEntry.COLUMN_NAME_PORT, computer.getWolPort());
        values.put(ComputerEntry.COLUMN_NAME_NAME, computer.getName());

        // Insert the new row, returning the primary key value of the new row
        if (computerId == -1) {
            long newRowId;
            newRowId = db.insert(ComputerEntry.TABLE_NAME, null, values);
            computer.setId(newRowId);
        } else {
            String filter = ComputerEntry.COLUMN_NAME_COMPUTER_ID + "=?";
            String[] filterValue = {String.valueOf(computer.getId())};
            db.update(ComputerEntry.TABLE_NAME, values, filter, filterValue);
        }
    }

    public static void removeComputer(Context context, long computerId) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String filter = ComputerEntry.COLUMN_NAME_COMPUTER_ID + "=?";
        String[] filterValue = {String.valueOf(computerId)};
        db.delete(ComputerEntry.TABLE_NAME, filter, filterValue);
    }


    /* Inner class that defines the table contents */
    public static abstract class ComputerEntry implements BaseColumns {
        // Columns definition
        public static final String TABLE_NAME = "Computer";
        public static final String COLUMN_NAME_COMPUTER_ID = "computer_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_MAC_ADDRESS = "mac_address";
        public static final String COLUMN_NAME_PORT = "port";
        public static final String COLUMN_NAME_FK_NETWORK = "network_id";

        public static String getCreationString() {

            return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                    COLUMN_NAME_COMPUTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + DataBaseHelper.COL_SEPARATOR +
                    COLUMN_NAME_NAME + DataBaseHelper.TEXT_TYPE + DataBaseHelper.COL_SEPARATOR +
                    COLUMN_NAME_MAC_ADDRESS + DataBaseHelper.TEXT_TYPE + DataBaseHelper.COL_SEPARATOR +
                    COLUMN_NAME_PORT + DataBaseHelper.TEXT_TYPE + DataBaseHelper.COL_SEPARATOR +
                    COLUMN_NAME_FK_NETWORK + DataBaseHelper.INTEGER_TYPE + ")";
        }

        public static String getDeletionString() {
            return "DROP TABLE " + TABLE_NAME;
        }

        public static String[] getColumns() {
            return new String[]{
                    COLUMN_NAME_COMPUTER_ID,
                    COLUMN_NAME_NAME,
                    COLUMN_NAME_MAC_ADDRESS,
                    COLUMN_NAME_PORT,
                    COLUMN_NAME_FK_NETWORK
            };
        }


    }
}
