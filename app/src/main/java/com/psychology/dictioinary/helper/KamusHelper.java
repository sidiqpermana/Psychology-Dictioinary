package com.psychology.dictioinary.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.psychology.dictioinary.db.DataBaseHelper;
import com.psychology.dictioinary.model.KamusModel;

import java.util.ArrayList;

public class KamusHelper {

	private static String DATABASE_TABLE = DataBaseHelper.TABLE_NAME;
	
	private Context context;
	
	private DataBaseHelper dataBaseHelper;
	
	private SQLiteDatabase database;
	
	public KamusHelper(Context context){
		this.context = context;
	}
	
	public KamusHelper open() throws SQLException{
		dataBaseHelper = new DataBaseHelper(context);
		database = dataBaseHelper.getWritableDatabase();
		return this;
		
	}
	
	public void close(){
		dataBaseHelper.close();
	}
	
	public Cursor searchQuery(String kata){
		
		return database.rawQuery("SELECT * FROM "+DATABASE_TABLE+" WHERE kata LIKE '%"+kata+"%'", null);
	}
	
	
	public ArrayList<KamusModel> getSearchResult(String keyword){
		ArrayList<KamusModel> arrayList = new ArrayList<KamusModel>();
		Cursor cursor = searchQuery(keyword);
		cursor.moveToFirst();
		KamusModel kamusModel;
		if (cursor.getCount()>0) {
			do {
				
				kamusModel = new KamusModel();
				kamusModel.setId(cursor.getInt(0));
				kamusModel.setKata(cursor.getString(1));
				kamusModel.setArti(cursor.getString(2));
				
				arrayList.add(kamusModel);
				cursor.moveToNext();
				
			} while (!cursor.isAfterLast());
		} 
		cursor.close();
		return arrayList;
	}
	
	public String getData(String kata){
		String result = "";
		Cursor cursor = searchQuery(kata);
		//startManagingCursor(cursor);
		cursor.moveToFirst();
		//KamusModel kamusModel;
		if (cursor.getCount()>0) {
			result = cursor.getString(2);
			for (; !cursor.isAfterLast(); cursor.moveToNext()) {
				result = cursor.getString(2);
			}
		} 
		cursor.close();
		return result;
	}
	
	public Cursor queryAllData(){
		
		return database.rawQuery("SELECT * FROM "+DATABASE_TABLE+" ORDER BY kata ASC", null);
	}
	
	public ArrayList<KamusModel> getAllData(){
		ArrayList<KamusModel> arrayList = new ArrayList<KamusModel>();
		Cursor cursor = queryAllData();
		//startManagingCursor(cursor);
		cursor.moveToFirst();
		KamusModel kamusModel;
		if (cursor.getCount()>0) {
			do {

				kamusModel = new KamusModel();
				kamusModel.setId(cursor.getInt(0));
				kamusModel.setKata(cursor.getString(1));
				kamusModel.setArti(cursor.getString(2));

				arrayList.add(kamusModel);
				cursor.moveToNext();
				
			} while (!cursor.isAfterLast());
		} 
		cursor.close();
		return arrayList;
	}
	
	public long insert(KamusModel kamus){
		ContentValues initialValues =  new ContentValues();
		initialValues.put(DataBaseHelper.FIELD_KATA, kamus.getKata());
		initialValues.put(DataBaseHelper.FIELD_ARTI, kamus.getArti());
		return database.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public void update(KamusModel kamus){
		ContentValues args = new ContentValues();
	    args.put(DataBaseHelper.FIELD_ARTI, kamus.getArti());
	    args.put(DataBaseHelper.FIELD_KATA, kamus.getKata());
	    database.update(DATABASE_TABLE, args, DataBaseHelper.FIELD_ID + "=" + kamus.getId(), null);
	}
	
	public void delete(int id){
		database.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.FIELD_ID + " = '"+id+"'", null);
	}
}
