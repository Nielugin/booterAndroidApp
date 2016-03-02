package com.jeanjulien.boucheron.booter.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.jeanjulien.boucheron.booter.model.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * Make network loading easier
 */
public class NetworkLoader {

    /**
     * Loads the networks from database.
     *
     * @param context The application context.
     * @return The application network list.
     */
    public static List<Network> loadNetwork(Context context) {

        List<Network> networks = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        dataBaseHelper.onCreate(db);

        Cursor dataList = dataBaseHelper.getDataList(NetworkEntry.TABLE_NAME, NetworkEntry.getColumns());
        while (dataList.moveToNext()) {
            long netId = dataList.getLong(dataList.getColumnIndexOrThrow(NetworkEntry.COLUMN_NAME_NET_ID));
            String netName = dataList.getString(dataList.getColumnIndexOrThrow(NetworkEntry.COLUMN_NAME_NAME));
            String netIpRange = dataList.getString(dataList.getColumnIndexOrThrow(NetworkEntry.COLUMN_NAME_IP_Range));
            networks.add(new Network(netId, netName, netIpRange));
        }

        return networks;
    }


    /* Inner class that defines the table contents */
    public static abstract class NetworkEntry implements BaseColumns {

        public static final String TABLE_NAME = "Network";
        public static final String COLUMN_NAME_NET_ID = "network_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_IP_Range = "ip_plage";

        public static String getCreationString() {

            return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                    COLUMN_NAME_NET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + DataBaseHelper.COL_SEPARATOR +
                    COLUMN_NAME_NAME + DataBaseHelper.TEXT_TYPE + DataBaseHelper.COL_SEPARATOR +
                    COLUMN_NAME_IP_Range + DataBaseHelper.TEXT_TYPE +
                    ")";
        }

        public static String getDeletionString() {
            return "DROP TABLE " + TABLE_NAME;
        }

        public static String[] getColumns() {
            return new String[]{
                    COLUMN_NAME_NET_ID,
                    COLUMN_NAME_NAME,
                    COLUMN_NAME_IP_Range
            };
        }
    }

}
