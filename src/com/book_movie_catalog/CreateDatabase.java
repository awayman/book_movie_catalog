package com.book_movie_catalog;

import java.util.Vector;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CreateDatabase extends Service {
	// Binder given to clients
		private final IBinder mBinder = new LocalBinder(); 
		 public class LocalBinder extends Binder {        
			 CreateDatabase getService() {            
				 // Return this instance of CreateDatabase so clients can call public methods            
				 return CreateDatabase.this;
				 }    
			 }	 
		@Override
		public IBinder onBind(Intent intent) {
			//use LocalBinder instead of this method
			return mBinder;
		}

	/**
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		/*
		 This method creates the database the first time it is called and opens the database
		 on subsequent calls. It also creates the books table.
		 */
		SQLiteDatabase db =openOrCreateDatabase("BookMovieDatabase", MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS books (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"title TEXT, author TEXT, publisher TEXT, isbn TEXT, release_date TEXT, " +
				"pages TEXT);");
	}

	/**
	 * @see android.app.Service#onStart(Intent,int)
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		openOrCreateDatabase("BookMovieDatabase", MODE_PRIVATE, null);
	}
	
	public void insertEntry(String title, String author, String publisher, String isbn, 
			String release_date, String pages) {
		SQLiteDatabase db =openOrCreateDatabase("BookMovieDatabase", MODE_PRIVATE, null);
		// Since SQL doesn't allow the insertion of a completely empty row, the second parameter of db.insert defines the column that will receive NULL if cv is empty
		ContentValues cv=new ContentValues();
		cv.put("title", title);
		cv.put("author", author);
		cv.put("publisher", publisher);
		cv.put("isbn", isbn);
		cv.put("release_date", release_date);
		cv.put("pages", pages);
		db.insert("books", "_id", cv);
	}
	
	public void viewCatalog() {
		SQLiteDatabase db =openOrCreateDatabase("BookMovieDatabase", MODE_PRIVATE, null);
		Cursor c=db.rawQuery("SELECT * FROM books;", null);
		c.moveToFirst();
		getResources().getStringArray(R.array.books);
		Vector<String> booksList = new Vector<String>();
		Log.i("viewCatalog", c.getString(c.getColumnIndex("title")));
		booksList.addElement(c.getString(c.getColumnIndex("title")));
		while (c.moveToNext()) {
			Log.i("viewCatalog", c.getString(c.getColumnIndex("title")));
			booksList.addElement(c.getString(c.getColumnIndex("title")));
		}
		//need to put vector of book titles into array and get it to CatalogViewList activity
	}
}
