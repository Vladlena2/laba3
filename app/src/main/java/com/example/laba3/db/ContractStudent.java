package com.example.laba3.db;

public class ContractStudent {
    protected static final String TABLE_NAME = "students";
    protected static final String COLUMN_ID = "ID";
    protected static final String COLUMN_NAME = "FIO";
    protected static final String COLUMN_TIMESTAMP = "TIMESTAMP";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
