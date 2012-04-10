package com.book_movie_catalog;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;

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
		SQLiteDatabase db =openOrCreateDatabase("AppDatabase", MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE books (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, " +
				"author TEXT, publisher TEXT, isbn TEXT, release_date TEXT, pages NUMBER);");
	}

	/**
	 * @see android.app.Service#onStart(Intent,int)
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Put your code here
	}
}
