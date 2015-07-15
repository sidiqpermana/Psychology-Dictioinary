package com.psychology.dictioinary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
	
	public static String DATABASE_NAME = "dbkamuspsikologi";
	public static String TABLE_NAME = "kamus";
	public static String FIELD_KATA = "kata";
	public static String FIELD_ARTI = "arti";
	public static String FIELD_ID = "_id";
	
	private static final int DATABASE_VERSION = 1;
	
	public static String CREATE_TABLE_KAMUS = "create table "+TABLE_NAME+" (_id integer primary key autoincrement, " +
			"kata text not null, " +
			"arti text not null);";
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_KAMUS);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);
	}

}
