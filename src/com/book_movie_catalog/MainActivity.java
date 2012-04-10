package com.book_movie_catalog;

import com.book_movie_catalog.CreateDatabase.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MainActivity extends Activity {
	CreateDatabase dbService;
	boolean dbBound = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    ////////ON START called after oncreate
    @Override
    public void onStart() {
    	Log.i("MyActivity", "MainActivity.onStart() called");
    	super.onStart();	
	
    	// Bind to CreateDatabase
    	Intent intent = new Intent(this, CreateDatabase.class);
    	bindService(intent, dbConnection, Context.BIND_AUTO_CREATE);
    	
    }
    
    public void onStop() {
		Log.i("MyActivity", "MainActivity.onStop() called");
		super.onStop();
		// Unbind from the service
		if (dbBound) {
			unbindService(dbConnection);
			dbBound = false;
		}
	}
    
    public void onManualEntryButtonClick(View v) {
		if (dbBound) {
			setContentView(R.layout.edit_view);
		}
	}
    
    public void onSubmitManualEntry(View v) {
		if (dbBound) {
			EditText textEntry = (EditText) findViewById(R.id.editTextTitle);
			String title = textEntry.getText().toString();
			
			textEntry = (EditText) findViewById(R.id.editTextAuthor);
			String author = textEntry.getText().toString();
			
			textEntry = (EditText) findViewById(R.id.editTextPublisher);
			String publisher = textEntry.getText().toString();
			
			textEntry = (EditText) findViewById(R.id.editTextISBN);
			String isbn = textEntry.getText().toString();
			
			textEntry = (EditText) findViewById(R.id.editTextReleaseDate);
			String release_date = textEntry.getText().toString();
			
			textEntry = (EditText) findViewById(R.id.editTextNumberOfPages);
			String pages = textEntry.getText().toString();
			
			//dbService.insertEntry(Author, Title, isbn);
			//Log.i("createDatabase", "Success!");
		}
	}
    
    /** Defines callbacks for service binding, passed to bindService() */
	private ServiceConnection dbConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// We've bound to LocalService, cast the IBinder and get LocalService instance
			LocalBinder binder = (LocalBinder) service;
			dbService = binder.getService();
			dbBound = true;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			dbBound = false;
		}
	};
}